package com.team2.oosdworkshop6;

import javafx.beans.property.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* OOSD Workshop 6 - Team 2
 *
 * This class represents a row of the bookings table in the travelexperts
 * database
 *
 * It does not represent an entry directly, as customerName and tripTypeName are
 * used instead of their respective ids
 */
public class Booking {
    private int bookingId;
    // a user-friendly string of the bookingDate for displaying
    private StringProperty bookingDateString;
    private StringProperty bookingNo;
    private IntegerProperty travellerCount;
    private StringProperty customerName;
    private StringProperty tripType;
    private StringProperty packageName;

    // The format used to convert between date & date string
    private final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY");
    private Date bookingDate;

    // Create a new booking
    public Booking(int bookingId, Date bookingDate, String bookingNo, Integer travellerCount, String customerName, String tripType, String packageName) {
        this.bookingDate = bookingDate;
        this.bookingId = bookingId;
        this.bookingDateString = new SimpleStringProperty(dateFormat.format(bookingDate));
        this.bookingNo = new SimpleStringProperty(bookingNo);
        this.travellerCount = new SimpleIntegerProperty(travellerCount);
        this.customerName = new SimpleStringProperty(customerName);
        this.tripType = new SimpleStringProperty(tripType);
        this.packageName = new SimpleStringProperty(packageName);
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getBookingDateString() {
        return bookingDateString.get();
    }

    public StringProperty bookingDateStringProperty() {
        return bookingDateString;
    }

    public void setBookingDateString(String bookingDateString) {
        this.bookingDate = java.sql.Date.valueOf(bookingDateString);
        this.bookingDateString.set(bookingDateString);
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDateString.set(dateFormat.format(bookingDate));
    }

    public Date getBookingDate() {
        return this.bookingDate;
    }

    public String getBookingNo() {
        return bookingNo.get();
    }

    public StringProperty bookingNoProperty() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo.set(bookingNo);
    }

    public int getTravellerCount() {
        return travellerCount.get();
    }

    public IntegerProperty travellerCountProperty() {
        return travellerCount;
    }

    public void setTravellerCount(int travellerCount) {
        this.travellerCount.set(travellerCount);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getTripType() {
        return tripType.get();
    }

    public StringProperty tripTypeProperty() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType.set(tripType);
    }

    public String getPackageName() {
        return packageName.get();
    }

    public StringProperty packageNameProperty() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName.set(packageName);
    }
}
