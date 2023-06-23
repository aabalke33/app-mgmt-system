package balke.c195;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * The Log Class hold members related to the logging of User Logins
 */
public class Log {
    /**
     * The appendLog method creates, and appends a record to the login_activity.txt file for login attempts
     * @param successful true if log is successful
     */
    public static void appendlog(boolean successful) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            FileWriter writer = new FileWriter("login_activity.txt",true);

            if (successful) {
                writer.write("\nSuccessful Login at " + timestamp);
            } else {
                writer.write("\nUnsuccessful Login at " + timestamp);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred. In the Logging Process");
        }
    }
}