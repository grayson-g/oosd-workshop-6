package com.team2.oosdworkshop6;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
    private ComboBox<PackageDTO> cmbPackage;

    @FXML
    private ComboBox<TripTypeDTO> cmbTripType;

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

    private Booking editBooking;
    private BookingCustomer customer;

    private class PackageDTO {
        private final StringProperty packageName;
        private final int packageId;

        public PackageDTO(int packageId, String packageName) {
            this.packageName = new SimpleStringProperty(packageName);
            this.packageId = packageId;
        }

        public int getPackageId() {
            return packageId;
        }

        public String getPackageName() {
            return packageName.get();
        }

        public StringProperty packageNameProperty() {
            return packageName;
        }
    }

    private final class TripTypeDTO {
        private final StringProperty tripTypeName;
        private final String tripTypeId;

        public TripTypeDTO(String tripTypeId, String tripTypeName) {
            this.tripTypeId = tripTypeId;
            this.tripTypeName = new SimpleStringProperty(tripTypeName);
        }

        public String getTripTypeId() {
            return tripTypeId;
        }

        public String getTripTypeName() {
            return tripTypeName.get();
        }

        public StringProperty tripTypeNameProperty() {
            return tripTypeName;
        }
    }

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

        Connection connection = new DatabaseManager().getConnection();
        ResultSet rs;
        try {
            // Get the list of packages
            Statement getPackagesStatement = connection.createStatement();
            getPackagesStatement.execute(
              "SELECT packages.PkgName as packageName, " +
              "       packages.PackageId as packageId " +
              "FROM packages;");
            rs = getPackagesStatement.getResultSet();

            cmbPackage.getItems().clear();
            while (rs.next()) {
                cmbPackage.getItems().add(
                        new PackageDTO(rs.getInt("packageId"), rs.getString("packageName")));
            }

            rs.close();
            getPackagesStatement.close();

            // Get the list of trip types
            Statement getTripTypesStatement = connection.createStatement();
            getTripTypesStatement.execute(
                    "SELECT triptypes.TripTypeId as tripTypeId, " +
                    "       triptypes.TTName as tripTypeName " +
                    "FROM triptypes;");
            rs = getTripTypesStatement.getResultSet();

            cmbTripType.getItems().clear();
            while (rs.next()) {
                cmbTripType.getItems().add(
                        new TripTypeDTO(rs.getString("tripTypeId"), rs.getString("tripTypeName")));
            }

            rs.close();
            getTripTypesStatement.close();

            connection.close();
        }
        catch (SQLException e) {

        }

        cmbTripType.setConverter(new StringConverter<TripTypeDTO>() {
            @Override
            public String toString(TripTypeDTO tripType) {
                if (tripType != null)
                    return tripType.getTripTypeName();
                else
                    return "";
            }

            @Override
            public TripTypeDTO fromString(String string) {
                return null;
            }
        });
        cmbPackage.setConverter(new StringConverter<PackageDTO>() {
            @Override
            public String toString(PackageDTO pkg) {
                if (pkg != null)
                    return pkg.getPackageName();
                else
                    return "";
            }

            @Override
            public PackageDTO fromString(String string) {
                return null;
            }
        });

        btnCancel.setOnAction(action -> {
            Node node = (Node) action.getSource();
            ((Stage)node.getScene().getWindow()).close();
        });

        btnSave.setOnAction(action -> {
            if (validateBooking() && saveBooking()) {
                Node node = (Node) action.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void editBooking(BookingCustomer customer, Booking booking) {
        this.editBooking = booking;
        this.customer = customer;

        dpBookingDate.setValue(booking.getBookingDate().toLocalDate());
        txtBookingId.setText(booking.getBookingId() + "");
        txtBookingNo.setText(booking.getBookingNo());
        txtCustomerName.setText(booking.getCustomerName());
        txtTravellerCount.setText(booking.getTravellerCount() + "");

        List<PackageDTO> packages = cmbPackage.getItems();
        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).getPackageName().equals(booking.getPackageName())) {
                cmbPackage.getSelectionModel().select(i);
            }
        }

        List<TripTypeDTO> triptypes = cmbTripType.getItems();
        for (int i = 0; i < triptypes.size(); i++) {
            if (triptypes.get(i).getTripTypeName().equals(booking.getTripType())) {
                cmbTripType.getSelectionModel().select(i);
            }
        }
    }

    public void addBooking(BookingCustomer customer) {
        this.editBooking = null;
        this.customer = customer;

        txtCustomerName.setText(customer.getCustomerName());
        dpBookingDate.setValue(LocalDate.now());
    }

    private boolean validateBooking() {
        try {
            String bookingNo = txtBookingNo.getText().trim();
            Integer travellerCount = Integer.parseInt(
                    txtTravellerCount.getText());
            String bookingType = cmbTripType.getValue().getTripTypeId();
            Date bookingDate = Date.valueOf(dpBookingDate.getValue());

            if (bookingNo == null || bookingNo.isBlank() ||
                travellerCount == null || travellerCount < 1 ||
                bookingType == null || bookingType.isBlank() ||
                bookingDate == null) {
                return false;
            }

            return true;
        }
        catch (Exception e) { }
        return false;
    }

    private boolean saveBooking() {
        final String updateSQL =
                "UPDATE bookings SET " +
                "BookingDate = ?, " +
                "BookingNo = ?, " +
                "TravelerCount = ?, " +
                "TripTypeId = ?, " +
                "PackageId = ? " +
                "WHERE BookingId = ?;";

        final String insertSQL =
                "INSERT INTO bookings (BookingDate, BookingNo, TravelerCount, CustomerId, TripTypeId, PackageId) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?);";
        try {
            Connection conn = new DatabaseManager().getConnection();
            PreparedStatement statement;

            Date bookingDate = Date.valueOf(dpBookingDate.getValue());
            Integer packageId = cmbPackage.getValue() == null ? null : cmbPackage.getValue().getPackageId();
            String tripTypeId = cmbTripType.getValue().getTripTypeId();
            String bookingNo = txtBookingNo.getText();
            int travellerCount = Integer.parseInt(txtTravellerCount.getText());
            int customerId = customer.getCustomerId();

            if (editBooking != null) { // existing booking, so we're updating
                statement = conn.prepareStatement(updateSQL);
                statement.setDate(1, bookingDate);
                statement.setString(2, bookingNo);
                statement.setInt(3, travellerCount);
                statement.setString(4, tripTypeId);
                if (packageId == null) {
                    statement.setNull(5, java.sql.Types.NULL);
                }
                else {
                    statement.setInt(5, packageId);
                }
                statement.setInt(6, editBooking.getBookingId());
            }
            else { // no existing booking, so we're inserting
                statement = conn.prepareStatement(insertSQL);
                statement.setDate(1, bookingDate);
                statement.setString(2, bookingNo);
                statement.setInt(3, travellerCount);
                statement.setInt(4, customerId);
                statement.setString(5, tripTypeId);
                if (packageId == null) {
                    statement.setNull(6, java.sql.Types.NULL);
                }
                else {
                    statement.setInt(6, packageId);
                }
            }

            int rows = statement.executeUpdate();

            statement.close();
            conn.close();

            // there should be exactly 1 row updated/inserted
            return rows == 1;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
