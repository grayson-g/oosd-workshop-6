package com.team2.oosdworkshop6;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxRoot;

    @FXML
    private Button btnAgents;

    @FXML
    private Button btnBookings;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnQuit;

    private Node currentView;
    private String currentViewName;

    private void setView(String viewName) {
        if (currentViewName != null && currentViewName.equals(viewName)) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(viewName));

        try {
            Scene newScene = new Scene(loader.load());

            boxRoot.getChildren().remove(currentView);
            boxRoot.getChildren().add(newScene.getRoot());

            currentView = newScene.getRoot();
            currentViewName = viewName;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert boxRoot != null : "fx:id=\"boxRoot\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnAgents != null : "fx:id=\"btnAgents\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnBookings != null : "fx:id=\"btnBookings\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnCustomers != null : "fx:id=\"btnCustomers\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnPackages != null : "fx:id=\"btnPackages\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnQuit != null : "fx:id=\"btnQuit\" was not injected: check your FXML file 'main-view.fxml'.";

        setView("bookings-view.fxml");

        btnAgents.setOnAction(view -> setView("agents-view.fxml"));
        btnCustomers.setOnAction(view -> setView("customers-view.fxml"));
        btnBookings.setOnAction(view -> setView("bookings-view.fxml"));
        btnPackages.setOnAction(view -> setView("packages-view.fxml"));
    }
}