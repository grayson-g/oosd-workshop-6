package com.team2.oosdworkshop6;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/* OOSD Workshop 6 - Team 2
 *
 * This is the controller class for the bookings-view.fxml view.
 * It handles:
 *     creating a dropdown of all customers in the database,
 *     creating a table of all bookings for the currently selected customer,
 *     and deleting bookings from the database
 *
 */
public class BookingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnNew;

    @FXML
    private ComboBox<BookingCustomer> cmbCustomers;

    @FXML
    private TableView<Booking> tblBookings;

    @FXML
    private TableColumn<Booking, String> tcBookingDate;

    @FXML
    private TableColumn<Booking, String> tcBookingNo;

    @FXML
    private TableColumn<Booking, String> tcCustomerName;

    @FXML
    private TableColumn<Booking, String> tcPackageName;

    @FXML
    private TableColumn<Booking, Integer> tcTravellerCount;

    @FXML
    private TableColumn<Booking, String> tcTripType;

    List<BookingCustomer> customers;
    List<Booking> customerBookings;

    @FXML
    void initialize() {
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert btnNew != null : "fx:id=\"btnNew\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert cmbCustomers != null : "fx:id=\"cmbCustomers\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert tcBookingDate != null : "fx:id=\"tcBookingDate\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert tcBookingNo != null : "fx:id=\"tcBookingNo\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert tcCustomerName != null : "fx:id=\"tcCustomerName\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert tcPackageName != null : "fx:id=\"tcPackageName\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert tcTravellerCount != null : "fx:id=\"tcTravellerCount\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert tcTripType != null : "fx:id=\"tcTripType\" was not injected: check your FXML file 'bookings-view.fxml'.";

        // initialize the customers and customerBookings lists
        customers = new ArrayList<>();
        customerBookings = new ArrayList<>();

        // customers is a list of BookingCustomer (customerId/customerName) BookingCustomer -> String
        cmbCustomers.setConverter(new StringConverter<BookingCustomer>() {
            @Override
            public String toString(BookingCustomer bookingCustomer) {
                return bookingCustomer.getCustomerName();
            }

            @Override
            public BookingCustomer fromString(String s) {
                // this won't be called as the combobox is non-editable
                for (BookingCustomer customer : customers) {
                    if (customer.getCustomerName().equals(s)) {
                        return customer;
                    }
                }

                return null;
            }
        });

        // on customer select, reload the table with that customer's data
        cmbCustomers.setOnAction(action -> {
            BookingCustomer customer = cmbCustomers.getSelectionModel().getSelectedItem();
            System.out.println(customer.getCustomerId());
            loadCustomerBookings(customer.getCustomerId());
        });

        // on click edit, (if there is a booking selected) open an edit dialogue for the currently
        // selected booking
        btnEdit.setOnAction(action -> {
            Booking booking = tblBookings.getSelectionModel().getSelectedItem();
            if (booking == null) { return; }

            modifyBooking(booking);
        });

        // on click new, open an add dialogue to create a new booking
        btnNew.setOnAction(action -> {
            modifyBooking(null);
        });

        // on click delete, (if there is a booking selected) delete the booking
        btnDelete.setOnAction(action -> {
            // check that there is a selected booking
            if (cmbCustomers.getValue() != null && tblBookings.getSelectionModel().getSelectedItem() != null) {
                // delete the selected booking
                deleteBooking(tblBookings.getSelectionModel().getSelectedItem().getBookingId());
            }
        });

        // load the customer combobox
        loadCustomers();

        // map Booking fields to corresponding column data
        tcBookingDate.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingDateString"));
        tcBookingNo.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingNo"));
        tcTravellerCount.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("travellerCount"));
        tcCustomerName.setCellValueFactory(new PropertyValueFactory<Booking, String>("customerName"));
        tcTripType.setCellValueFactory(new PropertyValueFactory<Booking, String>("tripType"));
        tcPackageName.setCellValueFactory(new PropertyValueFactory<Booking, String>("packageName"));
    }

    // This method handles loading the list of customers from the database to be selected from
    // the cmbCustomers dropdown.
    private void loadCustomers() {
        // select customerId, and "lastname, firstname" for all customers
        String customersSQL =
                "SELECT CONCAT(CustLastName, ', ', CustFirstName) as customerName, " +
                "       CustomerId as customerId " +
                "FROM customers;";

        // empty the customers list
        customers.clear();
        cmbCustomers.getItems().clear();

        try {
            // connect to the database and execute the sql statement
            Connection conn = new DatabaseManager().getConnection();
            Statement customersStatement = conn.createStatement();
            customersStatement.execute(customersSQL);
            ResultSet resultSet = customersStatement.getResultSet();

            // foreach customer...
            while (resultSet.next()) {
                // create a new BookingCustomer to add to the combobox
                BookingCustomer customer = new BookingCustomer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("customerName"));

                customers.add(customer);
            }

            // close the connection
            resultSet.close();
            customersStatement.close();
            conn.close();
        }
        catch (SQLException e) {

        }

        // sort customers by name
        customers.sort(new Comparator<BookingCustomer>() {
            // just String.compareTo
            @Override
            public int compare(BookingCustomer o1, BookingCustomer o2) {
                return o1.getCustomerName().compareTo(o2.getCustomerName());
            }
        });

        // add the customers to the combobox
        cmbCustomers.getItems().addAll(customers);
    }

    // This function handles loading bookings for the customer with id customerId
    // and displaying them in the table
    private void loadCustomerBookings(int customerId) {
        // sql to select booking data + related data from the database
        // (customer name instead of id,  trip type name instead of id, package name instead of id)
        String bookingsSQL =
                "SELECT bookings.BookingId as bookingId, " +
                        "       bookings.BookingNo as bookingNo, " +
                        "       bookings.BookingDate as bookingDate, " +
                        "       bookings.TravelerCount as travellerCount, " +
                        "       CONCAT(customers.CustLastName, ', ', customers.CustFirstName) as customerName, " +
                        "       triptypes.TTName as tripType, " +
                        "       packages.PkgName as packageName " +
                        "FROM bookings " +
                        "JOIN triptypes ON bookings.TripTypeId = triptypes.TripTypeId " +
                        "JOIN customers ON bookings.CustomerId = customers.CustomerId " +
                        "LEFT JOIN packages ON bookings.PackageId = packages.PackageId " +
                        "WHERE bookings.CustomerId = ?;";

        // clear the existing entries
        customerBookings.clear();
        tblBookings.getItems().clear();

        try {
            // connect to the database
            Connection conn = new DatabaseManager().getConnection();

            // set the parameter in the where clause to be customerId
            PreparedStatement bookingsStatement = conn.prepareStatement(bookingsSQL);
            bookingsStatement.setInt(1, customerId);

            // execute the database
            bookingsStatement.execute();
            ResultSet resultSet = bookingsStatement.getResultSet();

            while (resultSet.next()) {
                Booking booking = new Booking(
                        resultSet.getInt("bookingId"),
                        resultSet.getDate("bookingDate"),
                        resultSet.getString("bookingNo"),
                        resultSet.getInt("travellerCount"),
                        resultSet.getString("customerName"),
                        resultSet.getString("tripType"),
                        resultSet.getString("packageName"));

                customerBookings.add(booking);
            }

            resultSet.close();
            bookingsStatement.close();
            conn.close();
        }
        catch (SQLException e) {

        }

        customerBookings.sort((a, b) -> {
            // sort by date descending
            return b.getBookingDate().compareTo(a.getBookingDate());
        });
        tblBookings.getItems().addAll(customerBookings);
    }

    // this opens the modify-booking-view in a dialogue in edit mode with the currently selected
    // booking
    private void modifyBooking(Booking booking) {
        FXMLLoader fxmlloader = new FXMLLoader(HelloApplication.class.getResource("modify-bookings-view.fxml"));
        Parent parent = null;

        try {
            // load the view
            parent = fxmlloader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // get the customer data
        BookingCustomer customer = cmbCustomers.getSelectionModel().getSelectedItem();

        if (customer == null) { return; }

        // get the controller
        ModifyBookingController controller = fxmlloader.<ModifyBookingController>getController();
        if (booking == null) {
            controller.addBooking(customer);
        }
        else {
            controller.editBooking(customer, booking);
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        if (booking == null) {
            stage.setTitle("Create Booking");
        }
        else {
            stage.setTitle("Edit Booking");
        }
        stage.setScene(scene);
        stage.showAndWait();

        loadCustomerBookings(customer.getCustomerId());
    }

    // This function handles deleting a booking by bookingId
    private void deleteBooking(int bookingId) {
        // delete any associated details first
        final String sqlDeleteDetails =
                "DELETE FROM bookingdetails WHERE BookingId = ?;";

        // then delete the booking
        final String sqlDeleteBooking =
                "DELETE FROM bookings WHERE BookingId = ?;";

        try {
            // connect to db and prepare statements
            Connection conn = new DatabaseManager().getConnection();
            PreparedStatement statementDeleteBooking = conn.prepareStatement(sqlDeleteBooking);
            PreparedStatement statementDeleteDetails = conn.prepareStatement(sqlDeleteDetails);

            statementDeleteBooking.setInt(1, bookingId);
            statementDeleteDetails.setInt(1, bookingId);

            // delete the booking/bookingdetials
            statementDeleteDetails.executeUpdate();
            statementDeleteDetails.close();
            statementDeleteBooking.executeUpdate();
            statementDeleteBooking.close();

            conn.close();

            // reload the table
            loadCustomerBookings(cmbCustomers.getValue().getCustomerId());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}