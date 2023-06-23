package balke.c195.db;

import java.sql.Connection;
import java.sql.DriverManager;


// Make sure correct connection version
/**
 * This class starts and ends the SQL Server connection
 */
public abstract class JDBC {
    /**
     * This variable is used to define the SQL Server protocol
     */
    private static final String protocol = "jdbc";
    /**
     * This variable is used to define the SQL Server vendor
     */
    private static final String vendor = ":Mysql:";
    /**
     * This variable is used to define the SQL Server location
     */
    private static final String location = "//localhost/";
    /**
     * This variable is used to define the SQL Server database name
     */
    private static final String databaseName = "client_schedule";
    /**
     * This variable is used to define the URL
     */
    private static final String jdbUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    /**
     * This variable is used to define the SQL Server driver
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    /**
     * This variable is used to define the SQL Server username
     */
    private static final String userName = "user";
    /**
     * This variable is used to define the SQL Server password
     */
    private static final String password = "password";
    /**
     * This variable is used to define the SQL Server connection
     */
    public static Connection connection;
    /**
     * This method starts the SQL Server connection
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbUrl, userName, password);
            System.out.println("Connection Successful");


        } catch (Exception e) {
            System.out.println("Connection Failed");
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method ends the SQL Server connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection Closed");


        } catch (Exception e) {
            System.out.println("Connection Close Failed");
            System.out.println(e.getMessage());
        }
    }
}
