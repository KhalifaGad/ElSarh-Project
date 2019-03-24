/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.controllers;

import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import com.khalifa.elsarh.utilities.JerseyClient;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class WebDealingController implements Initializable {

    
    @FXML
    private AnchorPane uploadUnitPn;
    
    @FXML
    private HBox firstHB, secondHB, thirdHB, fourthHB, fifthHB, sixthHB,
            seventhHB;
    
    @FXML
    private TextField unitNameTxtFld, unitTypeTxtFld, villageTxtFld, statusTxtFld,
            deliveryTxtFld, areaTxtFld, areaLandTxtFld, priceTxtFld;
    
    @FXML
    private ToggleButton uploadUnitTB, showUnitsTB;
    
    @FXML
    private ScrollPane showUnitsScPn;
    
    @FXML
    private ComboBox roomsCountCB;
    
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6;
    
    @FXML
    private RadioButton installmentStatusRB;
    
    private FileChooser imageChooser ;
    
    private File imgFile1 = null, imgFile2 = null, imgFile3 = null,
            imgFile4 = null, imgFile5 = null, imgFile6 = null;
    private String greenStyle = "-fx-background-color: green;";
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        firstHB.setSpacing(scenesWidth * 0.07);
        secondHB.setSpacing(scenesWidth * 0.07);
        thirdHB.setSpacing(scenesWidth * 0.07);
        fourthHB.setSpacing(scenesWidth * 0.07);
        fifthHB.setSpacing(scenesWidth * 0.07);
        sixthHB.setSpacing(scenesWidth * 0.07);
        seventhHB.setSpacing(scenesWidth * 0.07);
        
        AnchorPane.setTopAnchor(uploadUnitPn, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(firstHB, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(secondHB, scenesHeight * 0.17);
        AnchorPane.setTopAnchor(thirdHB, scenesHeight * 0.24);
        AnchorPane.setTopAnchor(fourthHB, scenesHeight * 0.31);
        AnchorPane.setTopAnchor(fifthHB, scenesHeight * 0.39);
        AnchorPane.setTopAnchor(sixthHB, scenesHeight * 0.46);
        AnchorPane.setTopAnchor(seventhHB, scenesHeight * 0.53);
        
        AnchorPane.setRightAnchor(uploadUnitPn, scenesWidth * 0.05);
        AnchorPane.setRightAnchor(firstHB, scenesWidth * 0);
        AnchorPane.setRightAnchor(secondHB, scenesWidth * 0.);
        AnchorPane.setRightAnchor(thirdHB, scenesWidth * 0);
        AnchorPane.setRightAnchor(fourthHB, scenesWidth * 0);
        AnchorPane.setRightAnchor(fifthHB, scenesWidth * 0);
        AnchorPane.setRightAnchor(sixthHB, scenesWidth * 0);
        AnchorPane.setRightAnchor(seventhHB, scenesWidth * 0);
        
        roomsCountCB.getItems().addAll("1","2","3","4","5");
        
        imageChooser = new FileChooser(); 
        imageChooser.getExtensionFilters().addAll( new FileChooser.
                ExtensionFilter("Images", "*.jpg", "*.png"));
        
        uploadUnitPn.visibleProperty().bind(uploadUnitTB.selectedProperty());
        showUnitsScPn.visibleProperty().bind(showUnitsTB.selectedProperty());
    }    
    
    @FXML
    private void uploadUnit(){
        String name = unitNameTxtFld.getText(),
                unitType = unitTypeTxtFld.getText(),
                village = villageTxtFld.getText(),
                status = statusTxtFld.getText(),
                delivery = deliveryTxtFld.getText(),
                area = areaTxtFld.getText(),
                areaLand = areaLandTxtFld.getText(),
                price = priceTxtFld.getText(),
                installmentStatus;
        
        if(installmentStatusRB.isSelected()){
            installmentStatus = "yes";
        } else {
            installmentStatus = "no";
        }
        String rooms = String.valueOf(roomsCountCB.getValue());
        
         Alert confirmationAlert
                = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.getDialogPane().setNodeOrientation(
                NodeOrientation.RIGHT_TO_LEFT);
        confirmationAlert.setTitle("تأكيد");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("تأكيد عرض الوحده على الانترنت");
        Optional<ButtonType> result = confirmationAlert
                .showAndWait();
        if (result.get() == ButtonType.OK) {
            JerseyClient jersey = new JerseyClient();
            jersey.uploadUnit(imgFile1, imgFile2, imgFile3, imgFile4, imgFile5,
                    imgFile6, name, unitType, rooms, areaLand, area,
                    installmentStatus, village, status, delivery, price);
        } 
    }

    @FXML
    private void uploadImg1(MouseEvent event){
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        imgFile1 = imageChooser.showOpenDialog(stage);
        if(imgFile1 != null){
            btn1.setStyle(greenStyle);
        }
    } 
    
    @FXML
    private void uploadImg2(MouseEvent event){
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        imgFile2 = imageChooser.showOpenDialog(stage);
        if(imgFile2 != null){
            btn2.setStyle(greenStyle);
        }
    } 
    
    @FXML
    private void uploadImg3(MouseEvent event){
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        imgFile3 = imageChooser.showOpenDialog(stage);
        if(imgFile3 != null){
            btn3.setStyle(greenStyle);
        }
    } 
    
    @FXML
    private void uploadImg4(MouseEvent event){
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        imgFile4 = imageChooser.showOpenDialog(stage);
        if(imgFile4 != null){
            btn4.setStyle(greenStyle);
        }
    } 
    
    @FXML
    private void uploadImg5(MouseEvent event){
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        imgFile5 = imageChooser.showOpenDialog(stage);
        if(imgFile5 != null){
            btn5.setStyle(greenStyle);
        }
    } 
    
    @FXML
    private void uploadImg6(MouseEvent event){
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        imgFile6 = imageChooser.showOpenDialog(stage);
        if(imgFile6 != null){
            btn6.setStyle(greenStyle);
        }
    } 
    
}
