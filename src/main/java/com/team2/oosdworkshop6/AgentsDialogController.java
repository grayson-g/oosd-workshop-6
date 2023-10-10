/**
 * Sample Skeleton for 'agentdialog-view.fxml' Controller Class
 */
// Author: Alice

package com.team2.oosdworkshop6;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AgentsDialogController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyId"
    private TextField tfAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgentId"
    private TextField tfAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtBusPhone"
    private TextField tfAgtBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtEmail"
    private TextField tfAgtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtFirstName"
    private TextField tfAgtFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtLastName"
    private TextField tfAgtLastName; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtMiddleInitial"
    private TextField tfAgtMiddleInitial; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtPosition"
    private TextField tfAgtPosition; // Value injected by FXMLLoader

    private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert tfAgentId != null : "fx:id=\"tfAgentId\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert tfAgtBusPhone != null : "fx:id=\"tfAgtBusPhone\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert tfAgtEmail != null : "fx:id=\"tfAgtEmail\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert tfAgtFirstName != null : "fx:id=\"tfAgtFirstName\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert tfAgtLastName != null : "fx:id=\"tfAgtLastName\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert tfAgtMiddleInitial != null : "fx:id=\"tfAgtMiddleInitial\" was not injected: check your FXML file 'agentdialog-view.fxml'.";
        assert tfAgtPosition != null : "fx:id=\"tfAgtPosition\" was not injected: check your FXML file 'agentdialog-view.fxml'.";

        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnSaveClicked(mouseEvent);
            }
        });

        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                closeWindow(mouseEvent);
            }
        });
    }

    private void closeWindow(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    private Properties getProperties() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("E:\\CMPP264 - Java Programming\\connection.properties");
            Properties properties = new Properties();
            properties.load(fis);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void btnSaveClicked(MouseEvent mouseEvent) {
        Properties p = getProperties();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection((String) p.get("url"), p);

            String sql = "";
            if (mode.equals("edit"))
            {
                sql = "UPDATE `agents` SET `AgtFirstName`=?," +
                        "`AgtMiddleInitial`=?,`AgtLastName`=?," +
                        "`AgtBusPhone`=?,`AgtEmail`=?," +
                        "`AgtPosition`=?,`AgencyId`=? WHERE AgentId=?";
            }
            else {
                sql = "INSERT INTO `agents`(`AgtFirstName`," +
                        " `AgtMiddleInitial`, `AgtLastName`, `AgtBusPhone`," +
                        " `AgtEmail`, `AgtPosition`, `AgencyId`)" +
                        " VALUES (?,?,?,?,?,?,?)";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tfAgtFirstName.getText());
            stmt.setString(2, tfAgtMiddleInitial.getText());
            stmt.setString(3, tfAgtLastName.getText());
            stmt.setString(4, tfAgtBusPhone.getText());
            stmt.setString(5, tfAgtEmail.getText());
            stmt.setString(6, tfAgtPosition.getText());
            stmt.setInt(7, Integer.parseInt(tfAgencyId.getText()));
            if (mode.equals("edit"))
            {
                stmt.setInt(8, Integer.parseInt(tfAgentId.getText()));
            }
            int numRows = stmt.executeUpdate();
            if (numRows == 0)
            {
                System.out.println("Save failed.");
            }
            else
            {
                System.out.println("Data saved.");
            }
            conn.close();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void passModeToDialog(String mode) {
        this.mode = mode;
        tfAgentId.setDisable(true);
    }

    public void processAgent(Agent t1) {
        tfAgentId.setText(t1.getAgentId() + "");
        tfAgtFirstName.setText(t1.getAgtFirstName());
        tfAgtMiddleInitial.setText(t1.getAgtMiddleInitial());
        tfAgtLastName.setText(t1.getAgtLastName());
        tfAgtBusPhone.setText(t1.getAgtBusPhone());
        tfAgtEmail.setText(t1.getAgtEmail());
        tfAgtPosition.setText(t1.getAgtPosition());
        tfAgencyId.setText(t1.getAgencyId() + "");
    }
}
