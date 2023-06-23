package balke.c195.db;

import balke.c195.model.Appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
/**
 * This class supports the connection between appointments and the SQL Server
 */
public abstract class AppsQuery {
    /**
     * This method inserts a new appointment into the SQL Server from the application
     * @param title
     * @param description
     * @param location
     * @param contactId
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @throws SQLException
     */
    public static void insert(String title,
                              String description,
                              String location,
                              int contactId,
                              String type,
                              Timestamp start,
                              Timestamp end,
                              int customerId,
                              int userId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setInt(4,contactId);
        ps.setString(5,type);
        ps.setTimestamp(6,start);
        ps.setTimestamp(7,end);
        ps.setInt(8,customerId);
        ps.setInt(9,userId);
        ps.executeUpdate();
    }

    /**
     * This method updates an appointment on the SQL Server with information from the application
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param contactId
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @throws SQLException
     */
    public static void update(int appointmentId,
                              String title,
                              String description,
                              String location,
                              int contactId,
                              String type,
                              Timestamp start,
                              Timestamp end,
                              int customerId,
                              int userId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setInt(4,contactId);
        ps.setString(5,type);
        ps.setTimestamp(6,start);
        ps.setTimestamp(7,end);
        ps.setInt(8,customerId);
        ps.setInt(9,userId);
        ps.setInt(10,appointmentId);
        ps.executeUpdate();
    }

    /**
     * This method deletes a appointment from the SQL Server
     * @param AppointmentId
     * @throws SQLException
     */
    public static void delete(int AppointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, AppointmentId);
        ps.executeUpdate();
    }

    /**
     * This method gets all appointments from the SQL Server within a range depending on the parameter range
     * @param range
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> select(String range) throws SQLException {
        String sql = "SELECT * FROM appointments";

        if (Objects.equals(range, "Month")) {
            sql = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(CURRENT_DATE()) AND YEAR(Start) = YEAR(CURRENT_DATE())";
        } else if (Objects.equals(range, "Week")) {
            sql = "SELECT * FROM appointments WHERE YEARWEEK(Start) = YEARWEEK(CURRENT_DATE())";
        }

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return queryToAppointment(ps);
    }

    /**
     * This method finds all appointments for a specified contact ID
     * @param contactIdIn
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectByContact(int contactIdIn) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactIdIn);
        return queryToAppointment(ps);
    }

    /**
     * This method finds all distinct Type values for appointments
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> selectDistinctTypes() throws SQLException {

        ObservableList<String> types = FXCollections.observableArrayList();;

        String sql = "SELECT DISTINCT Type FROM appointments";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String type = rs.getString("Type");

            types.addAll(type);
        }

        return types;
    }

    /**
     * This method gets all appointments for a specified type
     * @param typeIn
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectByType(String typeIn) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Type = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, typeIn);

        return queryToAppointment(ps);
    }

    /**
     * This method gets all methods within 15 minutes of the specified timestamp
     * @param start
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectByStart(Timestamp start) throws SQLException {

        String sql = "SELECT * FROM appointments WHERE (Start BETWEEN ? AND date_add(?,interval 15 minute))";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, start);
        ps.setTimestamp(2, start);

        return queryToAppointment(ps);
    }

    /**
     * This method returns all appointments of a specified month this year
     * @param month
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectByMonth(String month) throws SQLException {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        int monthId = 0;

        for (int i =0; i < 11; i++) {
            if (Objects.equals(month, months[i])) {
                monthId = i+1;
            }
        }

        String sql = "SELECT * FROM appointments WHERE MONTH(Start) = ? AND YEAR(CURRENT_DATE()) = YEAR(Start)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, monthId);

        return queryToAppointment(ps);
    }

    /**
     * This method returns all appointments for a specified date
     * @param day
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectByDay(String day) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE DATE(Start) = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, day);

        return queryToAppointment(ps);
    }

    /**
     * This method gets all appointments for a specified range
     * @param start
     * @param end
     * @param customerId
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectByRange(Timestamp start, Timestamp end, int customerId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND (Start BETWEEN ? AND ? OR End BETWEEN ? AND ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.setTimestamp(2, start);
        ps.setTimestamp(3, end);
        ps.setTimestamp(4, start);
        ps.setTimestamp(5, end);
        return queryToAppointment(ps);
    }

    /**
     * This method is the points variables to SQL columns to provide a place for the queried info to be written to. This is used in all appointment queries.
     * @param ps
     * @return
     * @throws SQLException
     */
    private static ObservableList<Appointment> queryToAppointment(PreparedStatement ps) throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactId = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            ZonedDateTime s = start.toLocalDateTime()
                    .atZone(ZoneId.ofOffset("", ZoneOffset.UTC))
                    .withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime e = end.toLocalDateTime()
                    .atZone(ZoneId.ofOffset("", ZoneOffset.UTC))
                    .withZoneSameInstant(ZoneId.systemDefault());
            LocalTime startTime = s.toLocalTime();
            LocalDate startDate = s.toLocalDate();
            LocalTime endTime = e.toLocalTime();
            LocalDate endDate = e.toLocalDate();
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            Appointment appointment = new Appointment(appointmentId, title,
                    description, location, contactId, type, startTime, startDate, endTime, endDate, createDate,
                    createdBy, lastUpdate, lastUpdatedBy, customerId, userId);

            appointments.addAll(appointment);
        }
        return appointments;
    }
}