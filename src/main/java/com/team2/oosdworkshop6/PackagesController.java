/**
 * Sample Skeleton for 'packages-view.fxml' Controller Class
 */

package com.team2.oosdworkshop6;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PackagesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="tblData"
    private TableView<Packages> tblData; // Value injected by FXMLLoader
    private String mode ;

    ObservableList<Packages> data = FXCollections.observableArrayList();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert tblData != null : "fx:id=\"tblData\" was not injected: check your FXML file 'packages-view.fxml'.";

        getTableData();
        tblData.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Packages>() {
            @Override
            public void changed(ObservableValue<? extends Packages> observableValue, Packages packages, Packages t1) {
                if(tblData.getSelectionModel().isSelected(tblData.getSelectionModel().getSelectedIndex())){
                    btnDelete.setDisable(false);
                    btnEdit.setDisable(false);
                    btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            mode="edit";
                            openDialog(t1,mode);
                        }
                    });
                    btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            boolean confirmed = showConfirmationDialog("Confirm Deletion", "Are you sure you want to delete this record?");
                            if (confirmed) {
                                getDeletePackage(t1);
                                System.out.println("Record deleted.");
                            }
                        }
                    });
                }
            }
        });
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                openDialog(null,"add");
            }
        });

    }

    private void getDeletePackage(Packages t1) {

        try {
            DatabaseManager connectDB =new DatabaseManager();
            Connection conn = connectDB.getConnection();
            String sql1 = "Delete from `packages`  WHERE PackageId=?";

            PreparedStatement stmt = conn.prepareStatement(sql1);
            stmt.setInt(1,t1.getPackageId());

            int numRows = stmt.executeUpdate();
            if(numRows == 0){
                System.out.println("Delete Failed!!!");
            }else{
                System.out.println("Package Deleted Successfully.");
            }
            conn.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getTableData();
    }


    private void openDialog(Packages t1, String mode) {
        FXMLLoader fxmlloader = new FXMLLoader(HelloApplication.class.getResource("modifypackage-view.fxml"));
        Parent parent = null;
        try {
            parent = fxmlloader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ModifyPackageController modifyPackageController = fxmlloader.<ModifyPackageController>getController();
        modifyPackageController.passModeToDialog(mode);

        if(mode.equals("edit")){
            modifyPackageController.processPackage(t1);
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Modify Package");
        stage.setScene(scene);
        stage.showAndWait();
        getTableData();
    }

    private void getTableData() {
        tblData.getColumns().clear();
        data.clear();

        try {
            DatabaseManager connectDB =new DatabaseManager();
            Connection conn = connectDB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs  = stmt.executeQuery("SELECT `PackageId` as 'Id',`PkgName` as 'PkgName',DATE_FORMAT(`PkgStartDate`, '%Y-%m-%d') as 'PkgStartDate',DATE_FORMAT(`PkgEndDate`, '%Y-%m-%d') as 'PkgEndDate',`PkgDesc` as 'Description',ROUND(`PkgBasePrice`, 2) AS `PkgBasePrice`,ROUND(`PkgAgencyCommission`, 2) AS `PkgAgencyCommission` FROM `packages`");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                columnNames.add(columnName);
            }
            for (String columnName : columnNames) {
                TableColumn<Packages, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(new PropertyValueFactory<>(columnName));

                // Use a switch statement to map the original column names to new headers
                String columnHeader;
                switch (columnName) {
                    case "PackageId":
                        columnHeader = "ID";
                        break;
                    case "PkgName":
                        columnHeader = "Package Name";
                        break;
                    case "PkgStartDate":
                        columnHeader = "Start Date";
                        break;
                    case "PkgEndDate":
                        columnHeader = "End Date";
                        break;
                    case "PkgDesc":
                        columnHeader = "Description";
                        break;
                    case "PkgBasePrice":
                        columnHeader = "Base Price";
                        break;
                    case "PkgAgencyCommission":
                        columnHeader = "Agency Commission";
                        break;
                    default:
                        columnHeader = columnName; // Use the original column name if no mapping is defined
                        break;
                }

                column.setText(columnHeader); // Set the column header text
                tblData.getColumns().add(column);
            }
            while(rs.next()){

                data.add(new Packages(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7)));

            }
            conn.close();
            tblData.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    // Method to show a confirmation dialog
    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Show the dialog and wait for a response
        alert.showAndWait();

        // Return true if the user clicked OK, false otherwise
        return alert.getResult() == javafx.scene.control.ButtonType.OK;
    }

}
