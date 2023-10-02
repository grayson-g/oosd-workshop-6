package com.team2.oosdworkshop6;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

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
    private ComboBox<?> cmbCustomers;

    @FXML
    private ListView<?> lvBookings;

    @FXML
    void initialize() {
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert btnNew != null : "fx:id=\"btnNew\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert cmbCustomers != null : "fx:id=\"cmbCustomers\" was not injected: check your FXML file 'bookings-view.fxml'.";
        assert lvBookings != null : "fx:id=\"lvBookings\" was not injected: check your FXML file 'bookings-view.fxml'.";

        try {

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
