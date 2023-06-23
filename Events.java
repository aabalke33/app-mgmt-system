package balke.c195;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * The Events class hold events used throughout the application, They can be organized into Loading Events, and Alert Events
 */
public class Events {
    /**
     * The loadPage (2 Param) version is the abstraction for loading a page using a standard FXML button.
     * @param actionEvent
     * @param page FXML page being referenced in other methods
     * @throws IOException
     */
    public static void loadPage(ActionEvent actionEvent, String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Events.class.getResource(page)));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The loadPage (3 Param) version is the abstraction for loading a page using a FXML menuBar, which require different staging from buttons
     * @param actionEvent
     * @param page FXML page being referenced in other methods
     * @param menuBar FXML menuBar the loadPage is being called from
     * @throws IOException
     */
    public static void loadPage(ActionEvent actionEvent, String page,  MenuBar menuBar) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Events.class.getResource(page)));
        Stage stage = (Stage) menuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method loads the login View FXML
     * @param actionEvent
     * @throws IOException
     */
    public static void loadLogin(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent, "login-view.fxml");
    }
    /**
     * This method loads the appointment View FXML
     * @param actionEvent
     * @throws IOException
     */
    public static void loadApps(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent, "apps-view.fxml");
    }
    /**
     * This method loads the app add View FXML
     * @param actionEvent
     * @throws IOException
     */
    public static void loadAddApps(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent, "apps-add-view.fxml");
    }
    /**
     * This method loads the app modify View FXML
     * @param actionEvent
     * @throws IOException
     */
    public static void loadModApps(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent, "apps-mod-view.fxml");
    }
    /**
     * This method loads the customer View FXML
     * @param actionEvent
     * @throws IOException
     */
    public static void loadCustomers(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent, "customers-view.fxml");
    }
    /**
     * This method loads the customer add View FXML
     * @param actionEvent
     * @throws IOException
     */
    public static void loadAddCustomers(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent, "customers-add-view.fxml");
    }
    /**
     * This method loads the customer modify View FXML
     * @param actionEvent
     * @throws IOException
     */
    public static void loadModCustomers(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent, "customers-mod-view.fxml");
    }
    /**
     * This method loads the report View FXML
     * @param actionEvent
     * @throws IOException
     */
    public static void loadReports(ActionEvent actionEvent) throws IOException {
        loadPage(actionEvent, "reports-contact-view.fxml");
    }
    /**
     * This method loads the login View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadLogin(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "login-view.fxml", menuBar);
    }
    /**
     * This method loads the appointment View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadApps(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "apps-view.fxml", menuBar);
    }
    /**
     * This method loads the apps add View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadAddApps(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "apps-add-view.fxml", menuBar);
    }
    /**
     * This method loads the app modify View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadModApps(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "apps-mod-view.fxml", menuBar);
    }
    /**
     * This method loads the customer View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadCustomers(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "customers-view.fxml", menuBar);
    }
    /**
     * This method loads the customer add View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadAddCustomers(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "customer-add-view.fxml", menuBar);
    }
    /**
     * This method loads the customer modify View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadModCustomers(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "customer-mod-view.fxml", menuBar);
    }
    /**
     * This method loads the contact report View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadContactReport(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "reports-contact-view.fxml", menuBar);
    }
    /**
     * This method loads the type report View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadTypeReport(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "reports-type-view.fxml", menuBar);
    }
    /**
     * This method loads the month report View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadMonthReport(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "reports-month-view.fxml", menuBar);
    }
    /**
     * This method loads the day report View FXML, from a menuBar
     * @param actionEvent
     * @param menuBar
     * @throws IOException
     */
    public static void loadDayReport(ActionEvent actionEvent, MenuBar menuBar) throws IOException {
        loadPage(actionEvent, "reports-day-view.fxml", menuBar);
    }

    /**
     * This method provides an error alert for Input Validation
     * @param title
     * @param header
     * @param content
     */
    // Alert Abstractions
    public static void error(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * This method provides an alert for general use, such as appointment reminders
     * @param title
     * @param header
     * @param content
     */
    public static void alert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
