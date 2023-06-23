package balke.c195.controller;

import balke.c195.Events;
import balke.c195.db.AppsQuery;
import balke.c195.db.ContactsQuery;
import balke.c195.model.Contact;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/**
 * This class controls the Appointment Add page of the GUI
 */
public class AppsAddController implements Initializable {
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private AnchorPane bg;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField titleField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField descriptionField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField locationField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private ComboBox<String> contactField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField typeField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField customerIdField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField userIdField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private DatePicker startDateField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private DatePicker endDateField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField startTimeField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField endTimeField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private Button save;

    /**
     * This method provides what happens when the page is loaded
     * For this pages initialization, the first box cannot be focused on since this hides the prompt text.
     * The original "defocus" expression used a runnable with a run function built in, which was simplified greatly by
     * a lambda function. 4 lines of code can be replaced by 4 words.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Defocus (Fixes auto select)
//        Lambda Original
//        Platform.runLater(new Runnable() {
//            public void run() {
//                bg.requestFocus();
//            }
//        });
        Platform.runLater( () -> bg.requestFocus() );
    }

    /**
     * this method loads the appointment page when the Exit button is clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void exitClick(ActionEvent actionEvent) throws IOException {
        Events.loadApps(actionEvent);
    }

    /**
     * This method takes all field values and tries to create a new appointment from them with validation.
     * This occurs when the save button is clicked.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    private void saveClick(ActionEvent actionEvent) throws SQLException, IOException {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        int contact = ContactsQuery.getId(contactField.getValue());
        String type = typeField.getText();
        int customerId = Integer.parseInt(customerIdField.getText());
        int userId = Integer.parseInt(userIdField.getText());
        // Convert String to Local Date
        LocalDate startDate = LocalDate
                .parse(startDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate endDate = LocalDate
                .parse(endDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // Convert String to Local Time
        LocalTime startTime = LocalTime.parse(startTimeField.getText(),DateTimeFormatter.ofPattern("[H:]mm"));
        LocalTime endTime = LocalTime.parse(endTimeField.getText(),DateTimeFormatter.ofPattern("[H:]mm"));
        // Combine Local Date and Time into Timestamp for SQL in UTC Zone
        Timestamp start = Timestamp.valueOf(LocalDateTime.of(startDate,startTime)
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime());
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(endDate,endTime)
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime());
        // Combine Local Time into LocalTime in UTC for Input Validation
        LocalTime startTimeUTC = LocalDateTime.of(LocalDate.now(), startTime)
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalTime();
        LocalTime endTimeUTC = LocalDateTime.of(LocalDate.now(), endTime)
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalTime();
        // Local Time of business start and end
        LocalTime businessStart = LocalTime
                .parse("12:00",DateTimeFormatter.ofPattern("[H:]mm"));
        LocalTime businessEnd = LocalTime
                .parse("2:00",DateTimeFormatter.ofPattern("[H:]mm"));

        //Input Validation
        if ((startTimeUTC.isAfter(businessStart) || startTimeUTC.isBefore(businessEnd)) &&
                (endTimeUTC.isAfter(businessStart) || endTimeUTC.isBefore(businessEnd))) {
            if (AppsQuery.selectByRange(start, end, customerId).size() == 0) {
                AppsQuery.insert(title, description, location, contact,
                        type, start, end, customerId, userId);
                Events.loadApps(actionEvent);
            } else {
                Events.error("Overlapping Appointments", "Overlapping Appointments",
                        "This appointment overlaps a scheduled appointment.");
            }
        } else {
            Events.error("Out of Hours", "Out of Hours",
                    "This appointment is outside of business hours.");
        }
    }
    /**
     * This method changes the Save Button color when hovered over
     * @param mouseEvent
     */
    @FXML
    private void saveHover(MouseEvent mouseEvent) {
        save.setStyle("-fx-background-color:#2f6e89;");
    }

    /**
     * This method returns the Save Button color to the original color
     * @param mouseEvent
     */
    @FXML
    private void saveUnHover(MouseEvent mouseEvent) {
        save.setStyle("-fx-background-color:#2bae91;");
    }

    /**
     * This method provides action for when the contact Field is pressed. It sets the avialable items to the contact names.
     * Originally this method used a For Each loop to get all contact names, however it has been simplified using a lambda expression
     * This removes unnessesary defining and space.
     * @param mouseEvent
     * @throws SQLException
     */
    @FXML
    private void contactClick(MouseEvent mouseEvent) throws SQLException {

        ObservableList<Contact> contacts = ContactsQuery.select();
        ObservableList<String> contactNames = FXCollections.observableArrayList();

        contacts.forEach(contact -> contactNames.addAll(contact.getContactName()));

        contactField.setItems(contactNames);
    }
}
