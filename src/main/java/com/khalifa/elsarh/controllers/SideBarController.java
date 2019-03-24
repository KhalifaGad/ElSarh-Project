/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.controllers;

import java.io.IOException;
import java.net.URL;
import static com.khalifa.elsarh.MainApp.con;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**ٍ
 * FXML Controller class
 *
 * @author khalifa
 */
public class SideBarController implements Initializable {

    @FXML
    private Circle homeBtnCrcl;
    @FXML
    private Circle HomeCrcl;
    @FXML
    private StackPane mainAppStackPane, textStck;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private Text headTxt; 
    
    public static double scenesWidth, scenesHeight;
    /*
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image img = new Image(getClass()
                .getResource("/images/logo - small.png").toString());
        ImagePattern imgPtrn = new ImagePattern(img);
        homeBtnCrcl.setFill(imgPtrn);
        headTxt.setFill(Color.WHITE);
        
        Image img2 = new Image(getClass()
                .getResource("/images/logo - original.png").toString());
        
        ImagePattern imgPtrn2 = new ImagePattern(img2);
        HomeCrcl.setFill(imgPtrn2);
        textStck.autosize();
        textStck.setAlignment(Pos.CENTER);
        scenesWidth = mainContainer.getPrefWidth() * 0.799;
        scenesHeight = mainContainer.getPrefHeight() * 0.93;
       
       if(mainContainer.getPrefWidth() <= 1200.0){
           homeBtnCrcl.setTranslateX(- mainContainer.getPrefWidth() * 0.038439);
       }
        
    }    

    @FXML
    private void home(MouseEvent event) {
        loadUI("/fxml/sideBar.fxml");
    }

    @FXML
    private void projects(MouseEvent event) {
        loadUI("/fxml/projects.fxml");
    }

    @FXML
    private void clients(MouseEvent event) {
        loadUI("/fxml/clientsSearch.fxml");
    }

    @FXML
    private void reports(MouseEvent event) {
        loadUI("/fxml/reports.fxml");
    }
    @FXML
    private void dailyPayment(MouseEvent event) {
        loadUI("/fxml/dailyPayments.fxml");
    }
    @FXML
    private void settings(MouseEvent event) {
        loadUI("/fxml/settings.fxml");
    }
    @FXML
    private void webDealing(MouseEvent event) {
        loadUI("/fxml/webDealing.fxml");
    }
    @FXML
    private void installmentsFactor(MouseEvent event) {
        loadUI("/fxml/installmentsFactorVariable.fxml");
    }

    @FXML
    private void exit(MouseEvent event) {
        Stage stage = (Stage) mainContainer.getScene().getWindow();
        try {
            DriverManager.getConnection("jdbc:derby:elsarhEmbDB;shutdown=true;");
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SideBarController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        stage.close();
    }
    
    private void loadUI(String ui){
        AnchorPane loadedAncPane = null ;
        mainAppStackPane.getChildren().clear();
        try {
            loadedAncPane = (AnchorPane) 
                    FXMLLoader.load(getClass().getResource(ui));
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.30),
                loadedAncPane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        } catch (IOException ex) {
            Logger.getLogger(SideBarController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        mainAppStackPane.getChildren().add(loadedAncPane);
        
    }
    
}
