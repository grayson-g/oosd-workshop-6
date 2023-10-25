package com.team2.oosdworkshop6;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ModifyBookingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<?> cmbPackage;

    @FXML
    private ComboBox<?> cmbTripType;

    @FXML
    private DatePicker dpBookingDate;

    @FXML
    private TextField txtBookingId;

    @FXML
    private TextField txtBookingNo;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtTravellerCount;

    @FXML
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";
        assert cmbPackage != null : "fx:id=\"cmbPackage\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";
        assert cmbTripType != null : "fx:id=\"cmbTripType\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";
        assert dpBookingDate != null : "fx:id=\"dpBookingDate\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";
        assert txtBookingId != null : "fx:id=\"txtBookingId\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";
        assert txtBookingNo != null : "fx:id=\"txtBookingNo\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";
        assert txtCustomerName != null : "fx:id=\"txtCustomerName\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";
        assert txtTravellerCount != null : "fx:id=\"txtTravellerCount\" was not injected: check your FXML file 'modify-bookings-view.fxml'.";

    }

}
