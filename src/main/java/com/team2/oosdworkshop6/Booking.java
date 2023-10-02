package com.team2.oosdworkshop6;

import java.sql.Date;

public class Booking {
    private Integer bookingID;
    private Date bookingDate;
    private String bookingNo;
    private Integer travellerCount;
    private Integer customerID;
    private String tripTypeID;
    private Integer packageID;

    public Booking(Integer bookingID, Date bookingDate, String bookingNo, Integer travellerCount, Integer customerID, String tripTypeID, Integer packageID) {
        this.bookingID = bookingID;
        this.bookingDate = bookingDate;
        this.bookingNo = bookingNo;
        this.travellerCount = travellerCount;
        this.customerID = customerID;
        this.tripTypeID = tripTypeID;
        this.packageID = packageID;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", bookingDate=" + bookingDate +
                ", bookingNo='" + bookingNo + '\'' +
                ", travellerCount=" + travellerCount +
                ", customerID=" + customerID +
                ", tripTypeID='" + tripTypeID + '\'' +
                ", packageID=" + packageID +
                '}';
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Integer getTravellerCount() {
        return travellerCount;
    }

    public void setTravellerCount(Integer travellerCount) {
        this.travellerCount = travellerCount;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getTripTypeID() {
        return tripTypeID;
    }

    public void setTripTypeID(String tripTypeID) {
        this.tripTypeID = tripTypeID;
    }

    public Integer getPackageID() {
        return packageID;
    }

    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }
}
