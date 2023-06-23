package balke.c195.model;

import balke.c195.db.AppsQuery;
import balke.c195.db.CustomersQuery;
import balke.c195.db.UsersQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * The Database Class is used to store the information related to the Database
 */
public class Database {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList<Customer> allCustomersWithNames = FXCollections.observableArrayList();
    private static final ObservableList<User> allUsers = FXCollections.observableArrayList();
    public static void deleteAppointment(Appointment selectedAppointment) throws SQLException {
        AppsQuery.delete(selectedAppointment.getAppointmentId());
    }

    public static ObservableList<Appointment> getAllAppointments() {
        try {
            return AppsQuery.select("All");
        } catch (Exception e) {
            System.out.println("Couldn't create List of Appointments");
        }
        return allAppointments;
    }
    public static ObservableList<Appointment> getThisWeekAppointments() {
        try {
            return AppsQuery.select("Week");
        } catch (Exception e) {
            System.out.println("Couldn't create List of Appointments");
        }
        return allAppointments;
    }
    public static ObservableList<Appointment> getThisMonthAppointments() {
        try {
            return AppsQuery.select("Month");
        } catch (Exception e) {
            System.out.println("Couldn't create List of Appointments");
        }
        return allAppointments;
    }
    public static ObservableList<Customer> getAllCustomersWithNames() {
        try {
            return CustomersQuery.selectForNames();

        } catch (Exception e) {
            System.out.println("Couldn't create List of Customers");
        }
        return allCustomersWithNames;
    }
    public static void deleteCustomer(Customer selectedCustomer) throws SQLException {
        CustomersQuery.delete(selectedCustomer.getCustomerId());
    }

    public static ObservableList<User> getUsers() {
        try {
            return UsersQuery.select();
        } catch (Exception e) {
            System.out.println("Couldn't create List of Users");
        }
        return allUsers;
    }
    public static ObservableList<Appointment> getAppointmentsByContactId(int contactId) {
        try {
            return AppsQuery.selectByContact(contactId);
        } catch (Exception e) {
            System.out.println("Couldn't create List of Appointments");
        }
        return allAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsByType(String type) {
        try {
            return AppsQuery.selectByType(type);
        } catch (Exception e) {
            System.out.println("Couldn't create List of Appointments");
        }
        return allAppointments;
    }
    public static ObservableList<Appointment> getAppointmentsByMonth(String month) {
        try {
            return AppsQuery.selectByMonth(month);
        } catch (Exception e) {
            System.out.println("Couldn't create List of Appointments");
        }
        return allAppointments;
    }
    public static ObservableList<Appointment> getAppointmentsByDay(LocalDate localDate) {
        try {
            DateTimeFormatter formatterSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = localDate.format(formatterSQL);

            return AppsQuery.selectByDay(formattedDate);
        } catch (Exception e) {
            System.out.println("Couldn't create List of Appointments");
        }
        return allAppointments;
    }
}
