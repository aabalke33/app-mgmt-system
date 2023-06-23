package balke.c195.controller;

import balke.c195.Log;
import balke.c195.Events;
import balke.c195.db.AppsQuery;
import balke.c195.model.Appointment;
import balke.c195.model.Database;
import balke.c195.model.User;

import javafx.application.Platform;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static balke.c195.Events.error;

/**
 * This class controls the login page of the GUI
 */
public class LoginController implements Initializable {
    /**
     * This variable stores the local language
     */
    private String lang;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private AnchorPane bg;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField userField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField passwordField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private Label label;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private ChoiceBox<String> langBox;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private Button login;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private Label zone;
    /**
     * This method provides what happens when the page is loaded
     * For this pages initialization, the first box cannot be focused on since this hides the prompt text.
     * The original "defocus" expression used a runnable with a run function built in, which was simplified greatly by
     * a lambda function. 4 lines of code can be replaced by 4 words.
     * Additionally, it loads gets the local systems timezone and language, and sets the GUI to be in that Language and Timezone
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Defocus (Fixes auto select of username)
        Platform.runLater( () -> bg.requestFocus() );

        //Timezone
        zone.setText(ZoneId.systemDefault().toString());

        //Language
        setLang(Locale.getDefault().getLanguage());
        langBox.setOnAction(this::setLangBox);

        if (Objects.equals(getLang(), "en")) {
            langBox.setValue("English");
        } else if (Objects.equals(getLang(), "fr")) {
            langBox.setValue("Français");
        }
    }

    /**
     * This method is used to get the local language
     * @return
     */
    private String getLang() {
        return lang;
    }

    /**
     * this method is used to set the local language
     * @param lang
     */
    private void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * This method is used to change the language using the language box
     * @param event
     */
    private void setLangBox(ActionEvent event) {
        if (Objects.equals(langBox.getValue(), "Français")) {
            setFrench();
            setLang("fr");
        }
        if (Objects.equals(langBox.getValue(), "English")) {
            setEnglish();
            setLang("en");
        }
    }

    /**
     * This method sets all text on the login page to french
     */
    private void setFrench() {
        label.setText("Système de gestion\ndes rendez-vous");
        userField.setPromptText("Nom d'utilisateur");
        passwordField.setPromptText("Mot de passe");
        login.setText("Connexion");
    }
    /**
     * This method sets all text on the login page to english
     */
    private void setEnglish() {
        label.setText("Appointment\nManagement System");
        userField.setPromptText("Username");
        passwordField.setPromptText("Password");
        login.setText("Login");
    }
    /**
     * This method changes the Logout Button color when hovered over
     * @param event
     */
    @FXML
    private void loginHover(MouseEvent event) {
        login.setStyle("-fx-background-color:#2f6e89;");
    }
    /**
     * This method returns the Logout Button color to the original color
     * @param event
     */
    @FXML
    private void loginUnHover(MouseEvent event) {
        login.setStyle("-fx-background-color:#2bae91;");
    }

    /**
     * This method provides functionality to the login button. This includes,
     * Checking the username and password against the SQL Server database,
     * Providing input vlaidation based on that,
     * and Logging the login attempt.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void loginClick(ActionEvent actionEvent) throws IOException {

        ObservableList<User> users =  Database.getUsers();

        int match = 0;

        for (User user : users) {
            if (Objects.equals(userField.getText(), user.getUserName()) &&
                    Objects.equals(passwordField.getText(), user.getPassword())) {
                match = 1;
            }
        }

        if (!Objects.equals(userField.getText(), "") ||
                !Objects.equals(passwordField.getText(), "")) {
            if (match == 1) {
                Events.loadApps(actionEvent);

                Log.appendlog(true);

                try {
                    ObservableList<Appointment> appointments = AppsQuery
                            .selectByStart(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));

                    if (appointments.size() > 0) {
                        Events.alert("Appointments Soon", "Appointments Soon",
                                "App ID: " + appointments.get(0).getAppointmentId()
                                        + "\nDate: " + appointments.get(0).getStartDate() + "\nTime: "
                                        + appointments.get(0).getStartTime());
                    } else {
                        Events.alert("No Upcoming Appointments", "No Upcoming Appointments",
                                "There are no appointments in the next 15 minutes");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else {

                Log.appendlog(false);


                if (Objects.equals(lang, "en")) {
                    error("Invalid Login",
                            "Invalid Login",
                            "Invalid Username or Password, please try again.");
                } else {
                    error("Identifiant invalide",
                            "Identifiant invalide",
                            "Pseudo ou mot de passe invalide. Veuillez réessayer.");
                }
            }
        } else {

            Log.appendlog(false);

            if (Objects.equals(lang, "en")) {
                error("Missing Field",
                        "Missing Field",
                        "Username and Password are Required Fields.");
            } else {
                error("Champ manquant",
                        "Champ manquant",
                        "Pseudo ou mot de passe invalide. Veuillez réessayer.");
            }
        }

    }
}
