package com.team2.oosdworkshop6;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
