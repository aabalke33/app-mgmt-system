package balke.c195.controller;

import balke.c195.Events;
import balke.c195.db.AppsQuery;
import balke.c195.model.Appointment;
import balke.c195.model.Customer;
import balke.c195.model.Database;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * This class controls the customer page of the GUI
 */
public class CustomersController implements Initializable {
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private MenuBar menuBar;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableView<Customer> customerTable;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> nameCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> addressCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> postalCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> divisionCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> countryCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> phoneCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private AnchorPane bg;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private Button logout;
    /**
     * This method provides what happens when the page is loaded
     * For this pages initialization, the first box cannot be focused on since this hides the prompt text.
     * The original "defocus" expression used a runnable with a run function built in, which was simplified greatly by
     * a lambda function. 4 lines of code can be replaced by 4 words.
     * Additionally, it loads the customers from the sQL Server into the table view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Defocus (Fixes auto select of username)
        Platform.runLater( () -> bg.requestFocus() );

        customerTable.setItems(Database.getAllCustomersWithNames());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("address"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("divisionName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("countryName"));
        postalCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("phone"));
    }
    /**
     * This method changes the Logout Button color when hovered over
     * @param event
     */
    @FXML
    private void logoutHover(MouseEvent event) {
        logout.setStyle("-fx-background-color:#2f6e89; -fx-text-fill: white; -fx-background-radius: 0;");
    }
    /**
     * This method returns the Logout Button color to the original color
     * @param event
     */
    @FXML
    private void logoutUnHover(MouseEvent event) {
        logout.setStyle("-fx-background-color:#f2f2f2; -fx-text-fill: #545454; -fx-background-radius: 0;");
    }
    /**
     * This method loads the login page when the logout button is clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void logoutClick(ActionEvent actionEvent) throws IOException {
        Events.loadLogin(actionEvent);
    }
    /**
     * This method loads the customer add page
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void addCustClick(ActionEvent actionEvent) throws IOException {
        Events.loadAddCustomers(actionEvent,menuBar);
    }
    /**
     * This method loads the customer modify add page based on the selected customer
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void modCustClick(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Events.class.getResource("customer-mod-view.fxml")));
            Parent root = loader.load();
            CustomersModController controller = loader.getController();
            controller.setCustomer(selectedCustomer);
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    /**
     * This method deleted the selected customer from the SQL Server
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void delCustClick(ActionEvent actionEvent) throws IOException, SQLException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {

            if (AppsQuery.selectByContact(selectedCustomer.getCustomerId()).size() == 0) {
                Database.deleteCustomer(selectedCustomer);
                Events.loadCustomers(actionEvent, menuBar);
                Events.alert("Customer Deleted","Customer Deleted", "Customer: " + selectedCustomer.getCustomerName() + " has been deleted.");
            } else {
                Events.error("Cannot Delete", "Cannot Delete", "Please Delete all Customer Appointments before deleting the Customer");
            }
        }
    }

    /**
     * This method loads the appointment page
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void appsClick(ActionEvent actionEvent) throws IOException {
        Events.loadApps(actionEvent, menuBar);
    }
}
