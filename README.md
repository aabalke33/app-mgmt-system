# app-mgmt-system
This is a basic Java-based GUI program to access an appointment management SQL database.

Allows for adding, modifying, and deleting appointments and customers. Also provides reporting tools for appointments by Contact, Type, Month, and Date.

## Build Info

IDE: IntelliJ IDEA 2023.1.2 (Community Edition)
JDK: Java SE 17.0.7
JavaFX: JavaFX-SDK-17.0.1
MySQL Connector Driver: mysql-connector-java-8.0.26

Instruction: Run the MainApp.java file to being the application. From there you will be prompted with a login page. The default login is username "test", and password "test".

Instruction A3F: The additional functionality of this program is the day/date report. This report allows you to filter appointments by date. Use the DatePicker at the top of the page to choose a date to filter the appointments by.

## Functionality
### Login Page

The Login Page provides username and password login functionality, Language preference (English or French), and local Timezone indication. Language preference will change the page content to match while keeping the same functionality. The login page also provides error messages if invalid login information, or no login information is entered. User Login data is stored in the MySQL Database and requires a Query through the Java Connector to access.

![Login Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_1.jpg)
![Login Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_2.jpg)
![Login Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_3.jpg)

### Appointment Page

Upon initial loading, the appointments page will provide an information window letting the user know if any appointments are upcoming. Afterward, this page provides the ability to see all appointment information and then choose between Appointment Menu options (Add, Modify, Delete), Report Menu Options (By Contact, By Type, By Month, By Day), View Menu Options (All, Weekly, Monthly, Customers) or to Logout and return to the login page.
The delete options will remove the selected appointment from the SQL database.

![Appointment Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_4.jpg)
![Appointment Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_5.jpg)

### Add / Modify Appointment Pages

The add and modify appointment pages allow users to alter SQL appointment records through the GUI. These pages provide data validation and error checking before making SQL record changes. The interface will verify appointments are not overlapping, are within business hours, date values are actually dates, and numerical values are actually numbers. These ideas are very similar to the Customer add and modify pages as well.

![Add App Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_6.jpg)
![Add App Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_7.jpg)

### Report Pages

These pages filter the appointments by Contact, Type, Month, or Day.

![Login Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_8.jpg)

### Customer Pages

This page provides the ability to see all appointment information and then choose between Customer Menu options (Add, Modify, Delete)

![Login Page](https://github.com/aabalke33/app-mgmt-system/blob/main/screenshots/graph_9.jpg)
