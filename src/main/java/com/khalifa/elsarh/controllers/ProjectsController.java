package com.khalifa.elsarh.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class ProjectsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML 
    private AnchorPane projectsAnchorPane;
    @FXML
    private Button projectsSearchBtn, addProjectBtn, bookingUnitBtn;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             
        AnchorPane.setTopAnchor(projectsSearchBtn,scenesHeight * 0.18);
        AnchorPane.setTopAnchor(addProjectBtn,scenesHeight * 0.18);
        AnchorPane.setTopAnchor(bookingUnitBtn,scenesHeight * 0.58);
        AnchorPane.setLeftAnchor(projectsSearchBtn,scenesWidth * 0.15);
        AnchorPane.setLeftAnchor(bookingUnitBtn,scenesWidth * 0.35);
        AnchorPane.setRightAnchor(addProjectBtn,scenesWidth * 0.15);
        
    }    
    @FXML
    private void projectsSearch(MouseEvent event) {
        loadUI("/fxml/projectsSearching.fxml");
    }
    @FXML
    private void unitBooking(MouseEvent event) {
        loadUI("/fxml/unitBooking.fxml");
    }
    @FXML
    private void addProject(MouseEvent event) {
        loadUI("/fxml/addProject.fxml");
    }
    private void loadUI(String ui){
        AnchorPane loadedStkPane = null ;
        StackPane mainAppStackPane = (StackPane) projectsAnchorPane.getParent();
        mainAppStackPane.getChildren().clear();
        try {
            loadedStkPane = (AnchorPane) FXMLLoader.load(getClass().getResource(ui));
            FadeTransition ft = new FadeTransition(Duration.seconds(1.30),
                loadedStkPane);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
        } catch (IOException ex) {
            Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainAppStackPane.getChildren().add(loadedStkPane);
    }
    
}
