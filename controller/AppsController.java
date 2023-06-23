package balke.c195.controller;

import balke.c195.Events;
import balke.c195.model.Appointment;
import balke.c195.model.Database;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * This class controls the appointment page of the GUI
 */
public class AppsController implements Initializable {
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private Label label;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private MenuBar menuBar;
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
     * Additionally, it loads the appointments from the sQL Server into the table view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Defocus (Fixes auto select of username)
//        Lambda Original
//        Platform.runLater(new Runnable() {
//            public void run() {
//                bg.requestFocus();
//            }
//        });
        Platform.runLater( () -> bg.requestFocus() );

        appsTable.setItems(Database.getAllAppointments());
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
    @FXML
    /**
     * This method loads the login page when the logout button is clicked
     * @param actionEvent
     * @throws IOException
     */
    private void logoutClick(ActionEvent actionEvent) throws IOException {
        Events.loadLogin(actionEvent);
    }

    /**
     * This method loads the appointment add page
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void addAppClick(ActionEvent actionEvent) throws IOException {
        Events.loadAddApps(actionEvent, menuBar);
    }
    /**
     * This method loads the appointment modify add page based on the selected appointment
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void modAppClick(ActionEvent actionEvent) throws IOException, SQLException {
        Appointment selectedAppointment = appsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Events.class.getResource("apps-mod-view.fxml")));
            Parent root = loader.load();
            AppsModController controller = loader.getController();
            controller.setAppointment(selectedAppointment);
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * This method deleted the selected appointment from the SQL Server
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void delAppClick(ActionEvent actionEvent) throws IOException, SQLException {
        Appointment selectedAppointment = appsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Database.deleteAppointment(selectedAppointment);

            Events.loadApps(actionEvent, menuBar);
        }
    }
    /**
     * This method loads the customer page
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void customersClick(ActionEvent actionEvent) throws IOException {
        Events.loadCustomers(actionEvent, menuBar);
    }
    /**
     * This method loads the appointment page with all appointments
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void viewAllClick(ActionEvent actionEvent) {
        label.setText("Appointments (All)");
        appsTable.setItems(Database.getAllAppointments());
    }
    /**
     * This method loads the appointment page with appointments this week
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void viewWeekClick(ActionEvent actionEvent) {
        label.setText("Appointments (This Week)");
        appsTable.setItems(Database.getThisWeekAppointments());
    }
    /**
     * This method loads the appointment page with appointments this month
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void viewMonthClick(ActionEvent actionEvent) {
        label.setText("Appointments (This Month)");
        appsTable.setItems(Database.getThisMonthAppointments());
    }

    /**
     * This method loads the contact report page
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void contactReportClick(ActionEvent actionEvent) throws IOException {
        Events.loadContactReport(actionEvent,menuBar);
    }
    /**
     * This method loads the type report page
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void typeReportClick(ActionEvent actionEvent) throws IOException {
        Events.loadTypeReport(actionEvent,menuBar);
    }
    /**
     * This method loads the month report page
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void monthReportClick(ActionEvent actionEvent) throws IOException {
        Events.loadMonthReport(actionEvent,menuBar);
    }
    /**
     * This method loads the day report page
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void dayReportClick(ActionEvent actionEvent) throws IOException {
        Events.loadDayReport(actionEvent,menuBar);
    }
}
