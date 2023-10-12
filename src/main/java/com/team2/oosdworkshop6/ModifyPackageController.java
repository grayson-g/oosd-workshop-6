/**
 * Sample Skeleton for 'modifypackage-view.fxml' Controller Class
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
        btnModify.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnSaveClicked(mouseEvent);
            }
        });
    }

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

        DatabaseManager connectDB =new DatabaseManager();
        Connection conn = connectDB.getConnection();
        try {

            String sql1 = "";
            if(mode.equals("edit")){
                sql1= "UPDATE `packages` SET `PkgName`=?," +
                        "`PkgStartDate`=?,`PkgEndDate`=?,`PkgDesc`=?," +
                        "`PkgBasePrice`=?,`PkgAgencyCommission`=? WHERE PackageId=?";
            }else{
                sql1 = "INSERT INTO `packages`(`PkgName`, `PkgStartDate`, `PkgEndDate`, `PkgDesc`, `PkgBasePrice`, `PkgAgencyCommission`) VALUES" +
                        "(?,?,?,?,?,?)";
            }
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
            int numRows = stmt.executeUpdate();
            if(numRows == 0){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Save Failed!!!, Enter values in all fields");
                alert.showAndWait();
            }else{
                System.out.println("Data Saved Successfully.");

            }
            conn.close();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Save Failed!!!, Enter values in all fields");
            alert.showAndWait();
        }
    }

    public void passModeToDialog(String mode) {
        this.mode = mode;

    }
}
