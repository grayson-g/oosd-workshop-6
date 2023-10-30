/**
 * 'modifypackage-view.fxml' Controller Class
 * Created by : Deepa Thoppil
 * Dated : 27-09-2023
 */

package com.team2.oosdworkshop6;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyPackageController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;


    @FXML // fx:id="btnModify"
    private Button btnModify; // Value injected by FXMLLoader

    @FXML // fx:id="etAgencyCommission"
    private TextField etAgencyCommission; // Value injected by FXMLLoader

    @FXML // fx:id="etBasePrice"
    private TextField etBasePrice; // Value injected by FXMLLoader

    @FXML // fx:id="etDescription"
    private TextField etDescription; // Value injected by FXMLLoader

    @FXML // fx:id="etEndDate"
    private TextField etEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="etPackageId"
    private TextField etPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="etPackageName"
    private TextField etPackageName; // Value injected by FXMLLoader

    @FXML // fx:id="etStartDate"
    private TextField etStartDate; // Value injected by FXMLLoader
    private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        assert btnModify != null : "fx:id=\"btnModify\" was not injected: check your FXML file 'modifypackage-view.fxml'.";
        assert etAgencyCommission != null : "fx:id=\"etAgencyCommission\" was not injected: check your FXML file 'modifypackage-view.fxml'.";
        assert etBasePrice != null : "fx:id=\"etBasePrice\" was not injected: check your FXML file 'modifypackage-view.fxml'.";
        assert etDescription != null : "fx:id=\"etDescription\" was not injected: check your FXML file 'modifypackage-view.fxml'.";
        assert etEndDate != null : "fx:id=\"etEndDate\" was not injected: check your FXML file 'modifypackage-view.fxml'.";
        assert etPackageId != null : "fx:id=\"etPackageId\" was not injected: check your FXML file 'modifypackage-view.fxml'.";
        assert etPackageName != null : "fx:id=\"etPackageName\" was not injected: check your FXML file 'modifypackage-view.fxml'.";
        assert etStartDate != null : "fx:id=\"etStartDate\" was not injected: check your FXML file 'modifypackage-view.fxml'.";

        /*Add event handlers for Save button*/
        btnModify.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnSaveClicked(mouseEvent);
            }
        });
    }

    /*method to populate selected package information*/
    public void processPackage(Packages t1) {

        etPackageId.setText(String.valueOf((Integer) t1.getPackageId()));
        etPackageName.setText(t1.getPkgName());
        etDescription.setText(t1.getPkgDesc());
        etStartDate.setText(t1.getPkgStartDate());
        etEndDate.setText(t1.getPkgEndDate());
        etBasePrice.setText(t1.getPkgBasePrice());
        etAgencyCommission.setText(t1.getPkgAgencyCommission());
    }

    private void btnSaveClicked(MouseEvent mouseEvent) {
        /*Establish a database connection*/
        DatabaseManager connectDB =new DatabaseManager();
        Connection conn = connectDB.getConnection();
        String sql1;


        try {
            /*Determine the SQL statement based on the mode (edit or add)*/
            if(mode.equals("edit")){
                sql1= "UPDATE `packages` SET `PkgName`=?," +
                        "`PkgStartDate`=?,`PkgEndDate`=?,`PkgDesc`=?," +
                        "`PkgBasePrice`=?,`PkgAgencyCommission`=? WHERE PackageId=?";
            }else{
                sql1 = "INSERT INTO `packages`(`PkgName`, `PkgStartDate`, `PkgEndDate`, `PkgDesc`, " +
                        "`PkgBasePrice`, `PkgAgencyCommission`) VALUES" +
                        "(?,?,?,?,?,?)";
            }
            /*Create a PreparedStatement with the SQL statement*/
            PreparedStatement stmt = conn.prepareStatement(sql1);
            stmt.setString(1,etPackageName.getText());
            stmt.setString(2,etStartDate.getText());
            stmt.setString(3,etEndDate.getText());
            stmt.setString(4,etDescription.getText());
            stmt.setString(5,etBasePrice.getText());
            stmt.setString(6,etAgencyCommission.getText());
            if(mode.equals("edit")){
                stmt.setInt(7,Integer.parseInt(etPackageId.getText()));
            }
            /*Execute the SQL statement*/
            int numRows = stmt.executeUpdate();
            /*Check if the operation was successful*/
            if(numRows == 0){
                /*Display an error alert if the operation failed*/
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Save Failed!!!, Enter values in all fields");
                alert.showAndWait();
            }else{
                System.out.println("Data Saved Successfully.");

            }
            /*Close the db connection and current stage (dialog)*/
            conn.close();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            /*Display an error alert if a database connection error occurs*/
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Connection Failed!!!");
            alert.showAndWait();
        }
    }
    /*method to set the mode*/
    public void passModeToDialog(String mode) {
        this.mode = mode;

    }
}
