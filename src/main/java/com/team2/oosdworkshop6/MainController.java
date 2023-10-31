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

/* OOSD Workshop 6 - Team 2
 *
 * This class acts as the controller for the main view of the application,
 * it has a sidebar of buttons for selecting which sub-controller to use and
 * handles swapping out views/controllers in the application interface
 * Author : Grayson
 */
public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox boxRoot;   // the container for the sidebar + sub-view

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

    // This swaps the current view (currentView) with the view corresponding to viewName
    // (where viewName is the name of an fxml file)
    private void setView(String viewName) {
        // Check that it's a difference view from the currently selected one,
        // otherwise don't bother swapping anything
        if (currentViewName != null && currentViewName.equals(viewName)) {
            return;
        }

        // Get the loader
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(viewName));

        try {
            // Try to instantiate the new view
            Scene newScene = new Scene(loader.load());

            // remove the current view and add the new view
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

        // by default, open the topmost view
        setView("customers-view.fxml");

        // the buttons of the sidebar just swap the current view with their views
        btnAgents.setOnAction(view -> setView("agents-view.fxml"));
        btnCustomers.setOnAction(view -> setView("customers-view.fxml"));
        btnBookings.setOnAction(view -> setView("bookings-view.fxml"));
        btnPackages.setOnAction(view -> setView("packages-view.fxml"));
    }
}