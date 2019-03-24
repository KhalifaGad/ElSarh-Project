package com.khalifa.elsarh.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class ProjectsSearchingController implements Initializable {
    
    @FXML
    private TextField searchingPrjNameTxtField, searchingPrjCountryTxtField,
                      searchingPrjAreaTxtField;

    @FXML
    private RadioButton nameBtn, locationBtn;
    @FXML
    private HBox projectsSearchingHBox, searchingOptionsHB;
    @FXML
    AnchorPane searchingRsAncPn, searchingProjectsAnchorPane;
    @FXML
    ScrollPane searchingRsScrlPn;
    
    double horizontalSpacing = 20;
    public static String projectName;
    boolean firstParam;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO AnchorPane.topAnchor="64.0"
       // projectsSearchingParam.selectToggle(defaultTap);
        searchingPrjNameTxtField.managedProperty().
                bind(searchingPrjNameTxtField.visibleProperty());
       
        searchingPrjCountryTxtField.managedProperty().
                bind(searchingPrjCountryTxtField.visibleProperty());
        searchingPrjAreaTxtField.managedProperty().
                bind(searchingPrjAreaTxtField.visibleProperty());
        
        searchingPrjNameTxtField   .setVisible(false);
        searchingPrjAreaTxtField   .setVisible(false);
        searchingPrjCountryTxtField.setVisible(false);
        projectsSearchingHBox      .setSpacing(5);
        AnchorPane.setTopAnchor(searchingOptionsHB, scenesHeight * 0.094);
        AnchorPane.setTopAnchor(searchingRsScrlPn, scenesHeight * 0.145);
    }
    
    @FXML
    private void search(MouseEvent event){
        String searchingParam = "SELECT * FROM BUILDINGS_F WHERE " ;
        firstParam = true ;
        horizontalSpacing = 20;
        
        if (nameBtn.isSelected()) {
            String buildingName = searchingPrjNameTxtField.getText();
            if (!"".equals(buildingName)) {
                String queryConstraint = "NAME LIKE '" + buildingName +"%'";
                searchingParam += firstParam
                        ? queryConstraint : assignAND(queryConstraint);
            }
        }
        if (locationBtn.isSelected()) {
            String country = searchingPrjCountryTxtField.getText();
            String area = searchingPrjAreaTxtField.getText();
            if ("".equals(country) && "".equals(area)) {
            } else if ("".equals(country)) {
                String queryConstraintDash = "area = '" + area+"'";
                searchingParam += firstParam
                        ? queryConstraintDash : assignAND(queryConstraintDash);
            } else {
                String queryConstraint = "country = '" + country+"'";
                searchingParam += firstParam
                        ? queryConstraint : assignAND(queryConstraint);
            }

        }
        if("SELECT * FROM BUILDINGS_F WHERE ".equals(searchingParam)){
            return;
        }
        updateUI(searchingParam);
    }
   
    private void updateUI(String searchingParam){
        ResultSet projectsSearchingRs = MainApp.dbUtility.
                databaseQuerying(con, searchingParam);
        try {
            while (projectsSearchingRs.next()) {
                String buildingName = projectsSearchingRs.getString("NAME");
                createUINode(buildingName);
            }
            projectsSearchingRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddProjectController.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    private void createUINode(String buildingName){
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefHeight(30.0);
        hbox.setPrefWidth(450.0);
        hbox.setSpacing(8.8);
        hbox.setStyle("-fx-background-color: white;");
        hbox.setId("searchRsHbox");
        Text txt = new Text();
        txt.setStyle("-fx-backround-color:transparent;");
        txt.setText(buildingName);
        txt.setId("searchRsTxt");
        hbox.getChildren().add(txt);
        
        hbox.setOnMouseClicked((MouseEvent event) -> {
            AnchorPane loadedAncPane = null;
            StackPane mainAppStackPane = (StackPane)
                    searchingProjectsAnchorPane.getParent();
            mainAppStackPane.getChildren().clear();
            projectName = buildingName;
            try {
                loadedAncPane = (AnchorPane) FXMLLoader.load(getClass().
                        getResource("/fxml/projectProfile.fxml"));
                FadeTransition fadeTransition = new
                                FadeTransition(Duration.seconds(1.30),
                                        loadedAncPane);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            } catch (IOException ex) {
                Logger.getLogger(ProjectsSearchingController.class.
                        getName()).log(Level.SEVERE, null, ex);
            }
            mainAppStackPane.getChildren().add(loadedAncPane);
        });
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7),
                hbox);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        searchingRsAncPn.getChildren().add(hbox);
        AnchorPane.setTopAnchor(hbox,horizontalSpacing);
        AnchorPane.setRightAnchor(hbox, searchingRsAncPn.getPrefWidth()/4);
        horizontalSpacing += 50;
    }
    
    String assignAND(String searchingParam){
        firstParam = false; 
        return " AND "+ searchingParam;
    }
    
    @FXML
    private void checkingNameBtn(MouseEvent event){
        if(nameBtn.isSelected()){
            FadeTransition lft = new FadeTransition(Duration.seconds(0.30),
                searchingPrjNameTxtField);
            lft.setFromValue(0.0);
            lft.setToValue(1.0);
            lft.play();
            searchingPrjNameTxtField.setVisible(true);
        
        }else{
            searchingPrjNameTxtField.setVisible(false);
        }
    }
    @FXML
    private void checkingLocationBtn(MouseEvent event){
        if(locationBtn.isSelected()){
            FadeTransition rft = new FadeTransition(Duration.seconds(0.30),
                searchingPrjCountryTxtField);
            rft.setFromValue(0.0);
            rft.setToValue(1.0);
            rft.play();    
            FadeTransition lft = new FadeTransition(Duration.seconds(0.30),
                searchingPrjCountryTxtField);
            lft.setFromValue(0.0);
            lft.setToValue(1.0);
            lft.play();
            searchingPrjCountryTxtField.setVisible(true);
            searchingPrjAreaTxtField.setVisible(true);
        
        }else{
            searchingPrjCountryTxtField.setVisible(false);
            searchingPrjAreaTxtField.setVisible(false);
        }
    }
}
