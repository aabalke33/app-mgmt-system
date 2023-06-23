package balke.c195.db;

import balke.c195.model.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
/**
 * This class supports the connection between customers and the SQL Server
 */
public abstract class CustomersQuery {

    /**
     * This method inserts a new customer into the SQL Server from the Application
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param division
     * @throws SQLException
     */
    public static void insert(String name,
                              String address,
                              String postal,
                              String phone,
                              int division) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,address);
        ps.setString(3,postal);
        ps.setString(4,phone);
        ps.setInt(5,division);
        ps.executeUpdate();
    }

    /**
     * This method updates a customer on the SQL Server with information from the application
     * @param customerId
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param division
     * @throws SQLException
     */
    public static void update(int customerId,
                              String name,
                              String address,
                              String postal,
                              String phone,
                              int division) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,address);
        ps.setString(3,postal);
        ps.setString(4,phone);
        ps.setInt(5,division);
        ps.setInt(6,customerId);
        ps.executeUpdate();
    }

    /**
     * This method deletes a customer from the SQL Server by Customer ID
     * @param CustomerId
     * @throws SQLException
     */
    public static void delete(int CustomerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, CustomerId);
        ps.executeUpdate();
    }


    /**
     * This method gets all customers from the SQL Server
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> select() throws SQLException {

        ObservableList<Customer> customers = FXCollections.observableArrayList();;

        String sql = "SELECT * FROM customers";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_code");
            String phone = rs.getString("Phone");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            Customer customer = new Customer(customerId, customerName, address,
                    postalCode, phone, createdDate, createdBy, lastUpdate,
                    lastUpdatedBy, divisionId);

            customers.addAll(customer);
        }
        return customers;
    }

    /**
     * This method gets all customers from the SQL Server, and adds Division and Country names
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> selectForNames() throws SQLException {

        ObservableList<Customer> customers = FXCollections.observableArrayList();;

        String sql = "SELECT * FROM customers";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_code");
            String phone = rs.getString("Phone");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            String sql2 = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

            PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
            ps2.setInt(1, divisionId);
            ResultSet rs2 = ps2.executeQuery();

            String divisionName = null;
            int countryId = 0;

            while (rs2.next()) {
                divisionName = rs2.getString("Division");
                countryId = rs2.getInt("Country_ID");
            }

            String sql3 = "SELECT * FROM countries WHERE Country_ID = ?";

            PreparedStatement ps3 = JDBC.connection.prepareStatement(sql3);
            ps3.setInt(1, countryId);
            ResultSet rs3 = ps3.executeQuery();

            String countryName = null;

            while (rs3.next()) {
                countryName = rs3.getString("Country");
            }

            Customer customer = new Customer(customerId, customerName, address,
                    postalCode, phone, createdDate, createdBy, lastUpdate,
                    lastUpdatedBy, divisionName, countryName);

            customers.addAll(customer);
        }
        return customers;
    }
}
