package balke.c195.controller;

import balke.c195.Events;
import balke.c195.model.Appointment;
import balke.c195.model.Database;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * This class controls the days report page of the GUI
 */
public class ReportsDayController implements Initializable {
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private MenuBar menuBar;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private DatePicker dateField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableView<Appointment> appsTable;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, Integer> appIdCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> titleCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> locactionCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, Integer> contactCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> typeCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> startTimeCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> endTimeCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> startDateCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, String> endDateCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;
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
     * Additionally, it preps the table view for appointments
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Defocus (Fixes auto select of username)
        Platform.runLater( () -> bg.requestFocus() );

        appIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locactionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endTime"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endDate"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
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
     * This method loads the appointments page when the appointments button is clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void appsClick(ActionEvent actionEvent) throws IOException {
        Events.loadApps(actionEvent, menuBar);
    }
    /**
     * This method loads the customers page when the customers button is clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void customersClick(ActionEvent actionEvent) throws IOException {
        Events.loadCustomers(actionEvent, menuBar);
    }

    /**
     * This method sets the table view items to the date filtered appointments
     * @param actionEvent
     */
    @FXML
    private void dayAction(ActionEvent actionEvent) {
        appsTable.setItems(Database.getAppointmentsByDay(dateField.getValue()));
    }
}
