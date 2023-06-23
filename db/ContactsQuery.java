package balke.c195.db;

import balke.c195.model.Contact;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This class supports the connection between contacts and the SQL Server
 */
public abstract class ContactsQuery {
    /**
     * This method gets all contacts from the SQL Server
     * @return
     * @throws SQLException
     */
    public static ObservableList<Contact> select() throws SQLException {

        ObservableList<Contact> contacts = FXCollections.observableArrayList();;

        String sql = "SELECT * FROM contacts";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(contactId, contactName, email);

            contacts.addAll(contact);
        }
        return contacts;
    }

    /**
     * This method returns a contact ID for a correlating Name
     * @param contactName
     * @return
     * @throws SQLException
     */
    public static int getId(String contactName) throws SQLException {

        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();

        int contactId = 0;

        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }

    /**
     * This method returns a contact Name for a correlating ID
     * @param contactId
     * @return
     * @throws SQLException
     */
    public static String getName(int contactId) throws SQLException {

        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();

        String contactName = null;

        while (rs.next()) {
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }
}
