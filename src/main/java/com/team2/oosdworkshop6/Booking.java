package com.team2.oosdworkshop6;

import javafx.beans.property.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class Booking {
    private int bookingId;
    private StringProperty bookingDate;
    private StringProperty bookingNo;
    private IntegerProperty travellerCount;
    private StringProperty customerName;
    private StringProperty tripType;
    private StringProperty packageName;

    private final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY");

    public Booking(int bookingId, Date bookingDate, String bookingNo, Integer travellerCount, String customerName, String tripType, String packageName) {
        this.bookingDate = new SimpleStringProperty(dateFormat.format(bookingDate));
        this.bookingNo = new SimpleStringProperty(bookingNo);
        this.travellerCount = new SimpleIntegerProperty(travellerCount);
        this.customerName = new SimpleStringProperty(customerName);
        this.tripType = new SimpleStringProperty(tripType);
        this.packageName = new SimpleStringProperty(packageName);
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public StringProperty bookingDateProperty() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate.set(dateFormat.format(bookingDate));
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
