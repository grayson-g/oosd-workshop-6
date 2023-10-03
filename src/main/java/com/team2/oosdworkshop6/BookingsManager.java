package com.team2.oosdworkshop6;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class BookingsManager {
    public static List<Booking> getBookings(int customerID) {
        List<Booking> bookings = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/travelexperts");

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Bookings WHERE CustomerID = ?;");

            statement.setInt(1, customerID);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                bookings.add(new Booking(
                        results.getInt("bookingID"),
                        results.getDate("bookingDate"),
                        results.getString("bookingNo"),
                        results.getInt("travellerCount"),
                        results.getInt("customerID"),
                        results.getString("tripTypeID"),
                        results.getInt("packageID")));
            }
        }
        catch (SQLException e) { }

        return bookings;
    }

    public static void addBooking(Booking booking) { }

    public static void updateBooking(Booking booking) { }

    public static void deleteBooking(Booking booking) { }
}
