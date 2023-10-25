package com.team2.oosdworkshop6;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

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

        customers = new ArrayList<>();
        customerBookings = new ArrayList<>();

        cmbCustomers.setConverter(new StringConverter<BookingCustomer>() {
            @Override
            public String toString(BookingCustomer bookingCustomer) {
                return bookingCustomer.getCustomerName();
            }

            @Override
            public BookingCustomer fromString(String s) {
                for (BookingCustomer customer : customers) {
                    if (customer.getCustomerName().equals(s)) {
                        return customer;
                    }
                }

                return null;
            }
        });

        cmbCustomers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BookingCustomer customer = cmbCustomers.getSelectionModel().getSelectedItem();
                System.out.println(customer.getCustomerId());
                loadCustomerBookings(customer.getCustomerId());
            }
        });

        loadCustomers();

        tcBookingDate.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingDate"));
        tcBookingNo.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingNo"));
        tcTravellerCount.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("travellerCount"));
        tcCustomerName.setCellValueFactory(new PropertyValueFactory<Booking, String>("customerName"));
        tcTripType.setCellValueFactory(new PropertyValueFactory<Booking, String>("tripType"));
        tcPackageName.setCellValueFactory(new PropertyValueFactory<Booking, String>("packageName"));
    }

    private void loadCustomers() {
        String customersSQL =
                "SELECT CONCAT(CustLastName, ', ', CustFirstName) as customerName, " +
                "       CustomerId as customerId " +
                "FROM customers;";

        customers.clear();
        cmbCustomers.getItems().clear();

        try {
            Connection conn = new DatabaseManager().getConnection();
            Statement customersStatement = conn.createStatement();
            customersStatement.execute(customersSQL);
            ResultSet resultSet = customersStatement.getResultSet();

            while (resultSet.next()) {
                BookingCustomer customer = new BookingCustomer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("customerName"));

                customers.add(customer);
            }

            resultSet.close();
            customersStatement.close();
            conn.close();
        }
        catch (SQLException e) {

        }

        customers.sort(new Comparator<BookingCustomer>() {
            @Override
            public int compare(BookingCustomer o1, BookingCustomer o2) {
                return o1.getCustomerName().compareTo(o2.getCustomerName());
            }
        });

        cmbCustomers.getItems().addAll(customers);
    }

    private void loadCustomerBookings(int customerId) {
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

        customerBookings.clear();
        tblBookings.getItems().clear();

        try {
            Connection conn = new DatabaseManager().getConnection();

            PreparedStatement bookingsStatement = conn.prepareStatement(bookingsSQL);
            bookingsStatement.setInt(1, customerId);
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

        tblBookings.getItems().addAll(customerBookings);
    }
}