package com.team2.oosdworkshop6;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/* OOSD Workshop 6 - Team 2
 *
 * This class represents the customer who made a booking, without any extraneous
 * data. (i.e. the database id and a user-friendly display string)
 * Author : Grayson
 */
public class BookingCustomer {
    private StringProperty customerName;
    private int customerId;

    public BookingCustomer(int customerId, String customerName) {
        this.customerName = new SimpleStringProperty(customerName);
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
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
}
