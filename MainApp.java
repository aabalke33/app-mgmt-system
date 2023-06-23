package balke.c195;

import balke.c195.db.JDBC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The MainApp Class initiates the GUI Application
 */
public class MainApp extends Application {
    /**
     * The start method start the GUI, providing the stage parameters for the GUI, including resizablility, resolution, title and starting scene
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login-view.fxml"));
        stage.setTitle("Appointment Management System");
        stage.setScene(new Scene(fxmlLoader.load(), 1280, 720));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The main method launches the GUI and starts / stops the SQL Server Connection
     * @param args
     */
    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}