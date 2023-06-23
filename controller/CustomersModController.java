package balke.c195.controller;

import balke.c195.Events;
import balke.c195.db.CustomersQuery;
import balke.c195.db.DivisionsQuery;
import balke.c195.model.Customer;
import balke.c195.model.Division;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
/**
 * This class controls the customer modify page of the GUI
 */
public class CustomersModController {
    private Customer selectedCustomer;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField nameField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField phoneField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField addressField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private TextField postalField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private ComboBox<String> countryField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private ComboBox<String> divisionField;
    /**
     * This variable stores the FXML field for communicating with the GUI
     */
    @FXML
    private Button save;
    /**
     * this method loads the customer page when the Exit button is clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void exitClick(ActionEvent actionEvent) throws IOException {
        Events.loadCustomers(actionEvent);
    }

    /**
     * This method fills the field values at load based on the selected customer
     * @param selectedCustomer
     */
    public void setCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;

        int customerId = selectedCustomer.getCustomerId();
        nameField.setText(selectedCustomer.getCustomerName());
        phoneField.setText(selectedCustomer.getPhone());
        addressField.setText(selectedCustomer.getAddress());
        postalField.setText(selectedCustomer.getPostalCode());
        countryField.setValue(selectedCustomer.getCountryName());
        divisionField.setValue(selectedCustomer.getDivisionName());
    }
    /**
     * This method saves the customer data in the input fields into the SQL Server and loads the customer page
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    private void saveClick(ActionEvent actionEvent) throws SQLException, IOException {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String postal = postalField.getText();
        int divisionId = DivisionsQuery.getId(divisionField.getValue());

        CustomersQuery.update(selectedCustomer.getCustomerId(), name, address, postal, phone, divisionId);

        Events.loadCustomers(actionEvent);
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
     * This method alters the divisions available to be chosen by which country is chosen
     *
     * @param mouseEvent
     * @throws SQLException
     */
    @FXML
    private void divisionClick(MouseEvent mouseEvent) throws SQLException {
        String country = countryField.getValue();
        int countryId;
        if (Objects.equals(country, "U.S")) {
            countryId = 1;
        } else if (Objects.equals(country, "UK")) {
            countryId = 2;
        } else if (Objects.equals(country, "Canada")) {
            countryId = 3;
        } else {
            countryId = 0;
        }
        ObservableList<Division> countryDivision = DivisionsQuery.selectByCountry(countryId);
        System.out.println("HERE");

        ObservableList<String> divisionNames = FXCollections.observableArrayList();

        countryDivision.forEach(div -> divisionNames.addAll(div.getDivisionName()));

        divisionField.setItems(divisionNames);
    }
}
