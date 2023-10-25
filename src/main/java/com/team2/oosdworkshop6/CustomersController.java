//Author: Osaid Masood
package com.team2.oosdworkshop6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomersController {
//UI elements of FXML file
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private TextField custid;
    @FXML
    private TextField custfn;
    @FXML
    private TextField custln;
    @FXML
    private TextField custaddr;
    @FXML
    private TextField custc;
    @FXML
    private TextField custpr;
    @FXML
    private TextField custpo;
    @FXML
    private TextField custcountry;
    @FXML
    private TextField custphone;
    @FXML
    private TextField agtid;
    @FXML
    private Button editb;
    @FXML
    private Button saveb;
    @FXML
    private Button cancelb;

    private boolean isEditMode = false; //decides the state of edit mode
    private String originalCustomerId = null; //string to store CustomerId

    @FXML
    public void initialize() {
        //creates strings for customer IDs so that they can be displayed in the ComboBox
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        //if you encounter issues with an unsuitable driver, try adding the latest mysql connector JAR file to external libraries
        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "root", "");
            Connection connection = new DatabaseManager().getConnection();

            //SQL statement for selecting CustomerId
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CustomerId FROM Customers ORDER BY CustomerId ASC");

            //populates the ComboBox using resultSet
            while (resultSet.next()) {
                customerIds.add(resultSet.getString("CustomerId"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //sets the ComboBox items
        combobox.setItems(customerIds);

        //adds an event handler to the ComboBox for item selection changes
        combobox.setOnAction(event -> {
            String selectedCustomerId = combobox.getValue();
            if (selectedCustomerId != null) {
                originalCustomerId = selectedCustomerId; //stores tbe original customer ID
                //SQL statement to select customer ID from the database
                try {
                    Connection connection = new DatabaseManager().getConnection();
//                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "root", "");
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customers WHERE CustomerId = ?");
                    preparedStatement.setString(1, selectedCustomerId);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //adds data from resultSet to the text fields
                    if (resultSet.next()) {
                        custid.setText(resultSet.getString("CustomerId"));
                        custfn.setText(resultSet.getString("CustFirstName"));
                        custln.setText(resultSet.getString("CustLastName"));
                        custaddr.setText(resultSet.getString("CustAddress"));
                        custc.setText(resultSet.getString("CustCity"));
                        custpr.setText(resultSet.getString("CustProv"));
                        custpo.setText(resultSet.getString("CustPostal"));
                        custcountry.setText(resultSet.getString("CustCountry"));
                        custphone.setText(resultSet.getString("CustHomePhone"));
                        agtid.setText(resultSet.getString("AgentId"));
                    }
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //handleEditButton method enables and disables appropriate UI elements
    @FXML
    public void handleEditButton(ActionEvent event) {
        isEditMode = true;
        editb.setDisable(true);
        saveb.setDisable(false);
        cancelb.setDisable(false);
        combobox.setDisable(true);

        enableTextFields(true);

        //stores customer ID when the edit button is clicked
        originalCustomerId = combobox.getValue();
    }

    //handleSaveButton method which contains an SQL UPDATE query
    @FXML
    public void handleSaveButton(ActionEvent event) {
        if (isEditMode) {
            try {
                Connection connection = new DatabaseManager().getConnection();
//                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "root", "");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE Customers " +
                                "SET CustFirstName = ?, CustLastName = ?, CustAddress = ?, CustCity = ?, CustProv = ?, CustPostal = ?, CustCountry = ?, CustHomePhone = ?, AgentId = ? " +
                                "WHERE CustomerId = ?"
                );
                //gets text from modified textfields for the SQL query to use
                preparedStatement.setString(1, custfn.getText());
                preparedStatement.setString(2, custln.getText());
                preparedStatement.setString(3, custaddr.getText());
                preparedStatement.setString(4, custc.getText());
                preparedStatement.setString(5, custpr.getText());
                preparedStatement.setString(6, custpo.getText());
                preparedStatement.setString(7, custcountry.getText());
                preparedStatement.setString(8, custphone.getText());
                preparedStatement.setString(9, agtid.getText());
                preparedStatement.setString(10, originalCustomerId);

                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //and then finally resets the UI
            resetUI();
        }
    }
//method for resetting UI back to normal
    @FXML
    public void handleCancelButton(ActionEvent event) {
        resetUI();
    }
//method for enabling appropriate textfields
    private void enableTextFields(boolean enable) {
        custfn.setEditable(enable);
        custln.setEditable(enable);
        custaddr.setEditable(enable);
        custc.setEditable(enable);
        custpr.setEditable(enable);
        custpo.setEditable(enable);
        custcountry.setEditable(enable);
        custphone.setEditable(enable);
    }
//method for resetting UI back to default
    private void resetUI() {
        editb.setDisable(false);
        saveb.setDisable(true);
        cancelb.setDisable(true);
        combobox.setDisable(false);
        combobox.getSelectionModel().clearSelection();
        custid.clear();
        custfn.clear();
        custln.clear();
        custaddr.clear();
        custc.clear();
        custpr.clear();
        custpo.clear();
        custcountry.clear();
        custphone.clear();
        agtid.clear();
        isEditMode = false;
        originalCustomerId = null;
        enableTextFields(false);
    }
}