package balke.c195.db;

import balke.c195.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
/**
 * This class supports the connection between users and the SQL Server
 */
public abstract class UsersQuery {
    /**
     * This method gets all users in the SQL Server and returns them to the application
     * @return
     * @throws SQLException
     */
    public static ObservableList<User> select() throws SQLException {

        ObservableList<User> users = FXCollections.observableArrayList();;

        String sql = "SELECT * FROM users";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdateBy = rs.getString("Last_Updated_By");

            User user = new User(userID, userName, password, createdDate,
                    createdBy, lastUpdate, lastUpdateBy);

            users.addAll(user);
        }
        return users;
    }
}
