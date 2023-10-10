/**
 * Sample Skeleton for 'agents-view.fxml' Controller Class
 */
// Author: Alice

package com.team2.oosdworkshop6;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgentsController {

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

    @FXML // fx:id="cbAgtName"
    private ComboBox<String> cbAgtName; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyId"
    private TableColumn<Agent, Integer> colAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="colAgentId"
    private TableColumn<Agent, Integer> colAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtBusPhone"
    private TableColumn<Agent, String> colAgtBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtEmail"
    private TableColumn<Agent, String> colAgtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtFirstName"
    private TableColumn<Agent, String> colAgtFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtLastName"
    private TableColumn<Agent, String> colAgtLastName; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtMiddleInitial"
    private TableColumn<Agent, String> colAgtMiddleInitial; // Value injected by FXMLLoader

    @FXML // fx:id="colAgtPosition"
    private TableColumn<Agent, String> colAgtPosition; // Value injected by FXMLLoader

    @FXML // fx:id="tvAgents"
    private TableView<Agent> tvAgents; // Value injected by FXMLLoader

    private String mode;

    ObservableList<Agent> data = FXCollections.observableArrayList();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert cbAgtName != null : "fx:id=\"cbAgtName\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgencyId != null : "fx:id=\"colAgencyId\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgtBusPhone != null : "fx:id=\"colAgtBusPhone\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgtEmail != null : "fx:id=\"colAgtEmail\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgtFirstName != null : "fx:id=\"colAgtFirstName\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgtLastName != null : "fx:id=\"colAgtLastName\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgtMiddleInitial != null : "fx:id=\"colAgtMiddleInitial\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgtPosition != null : "fx:id=\"colAgtPosition\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert tvAgents != null : "fx:id=\"tvAgents\" was not injected: check your FXML file 'agents-view.fxml'.";

        colAgentId.setCellValueFactory(new PropertyValueFactory<Agent, Integer>("agentId"));
        colAgtFirstName.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtFirstName"));
        colAgtMiddleInitial.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtMiddleInitial"));
        colAgtLastName.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtLastName"));
        colAgtBusPhone.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtBusPhone"));
        colAgtEmail.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtEmail"));
        colAgtPosition.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtPosition"));
        colAgencyId.setCellValueFactory(new PropertyValueFactory<Agent, Integer>("agencyId"));

        tvAgents.setItems(data);

        getAgents();
        loadAgentNames();
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);

        tvAgents.getSelectionModel().selectedItemProperty().addListener((observable, notSelected,selected)-> {
            if (selected != null) {
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
            } else {
                btnEdit.setDisable(true);
                btnDelete.setDisable(true);
            }
        });

        cbAgtName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (s != null) {
                    filterTableView(t1);
                } else {
                    tvAgents.setItems(data);
                }
            }
        });

        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mode = "add";
                openDialog(null, mode);
            }
        });

        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setContentText("Are you sure you want to delete this agent?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get()==ButtonType.OK) {
                    deleteSelectedAgent();
                }
            }
        });

        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Check if an item is selected in the TableView
                Agent selectedAgent = tvAgents.getSelectionModel().getSelectedItem();
                if (selectedAgent != null) {
                    mode = "edit";
                    openDialog(selectedAgent, mode);
                }
            }
        });

    }

    private void deleteSelectedAgent() {
        Agent selectedAgent = tvAgents.getSelectionModel().getSelectedItem();
        if (selectedAgent == null) {
            // No agent selected, show an error message or handle as needed
            return;
        }

        Properties p = getProperties();
        try (Connection conn = DriverManager.getConnection((String) p.get("url"), p);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Agents WHERE AgentId = ?")) {

            stmt.setInt(1, selectedAgent.getAgentId());
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                System.out.println("Delete failed");
            } else {
                System.out.println("Agent deleted");
                // Remove the agent from the TableView's data
                data.remove(selectedAgent);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Failed!");
            alert.setContentText("Agent has customers and cannot be deleted.");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting agent", e);
        }
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

    private void openDialog(Agent t1, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("agentdialog-view.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AgentsDialogController dialogController = fxmlLoader.<AgentsDialogController>getController();
        dialogController.passModeToDialog(mode);
        if (mode.equals("edit"))
        {
            dialogController.processAgent(t1);
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Enter data");
        stage.setScene(scene);
        stage.showAndWait();

        getAgents();

    }

    private void filterTableView(String agentName) {
        // Create a filtered list based on the selected agent name
        ObservableList<Agent> filteredList = data.filtered(agent -> agent.getAgtFirstName().equals(agentName));

        // Check if the filtered list is empty
        if (filteredList.isEmpty()) {
            tvAgents.setItems(data);
        } else {
            // Set the filtered list to the TableView
            tvAgents.setItems(filteredList);
        }
    }

    private void loadAgentNames() {
        cbAgtName.getItems().clear();

        String url = "";
        String user = "";
        String password = "";

        try{
            FileInputStream fis = new FileInputStream("E:\\CMPP264 - Java Programming\\connection.properties");
            Properties p = new Properties();
            p.load(fis);
            url = (String) p.get("url");
            user = (String) p.get("user");
            password = (String) p.get("password");
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT AgtFirstName FROM Agents");
            while (rs.next()){
                String agentName = rs.getString("AgtFirstName");
                cbAgtName.getItems().add(agentName);
            }
            conn.close();
        } catch (IOException | SQLException e)
        {
            throw new RuntimeException();
        }
    }

    private void getAgents() {
        data.clear();

        String url = "";
        String user = "";
        String password = "";

        try {
            FileInputStream fis = new FileInputStream("E:\\CMPP264 - Java Programming\\connection.properties");
            Properties p = new Properties();
            p.load(fis);
            url = (String) p.get("url");
            user = (String) p.get("user");
            password = (String) p.get("password");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Agents");
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next())
            {
                data.add(new Agent(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8)));
            }
            conn.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
