package com.khalifa.elsarh.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import static com.khalifa.elsarh.controllers.
        UnitBookingController.renderingClientTrack;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
public class ClientsSearchController implements Initializable {

    /**
     * Initializes the controller class.
     */
  
    @FXML
    private ToggleButton idBtn, phoneBtn, nameBtn;
    
    @FXML
    private TextField clientSearchingByPhone, clientSearchingByID,
            clientSearchingByName;
    
    @FXML
    private HBox clientsSearchingHBox, toggleBtnsHB;
    
    @FXML
    ScrollPane clientsSearchingScPn;
    
    @FXML
    private AnchorPane searchingRsAncPn, searchingClientsAnchorPane;
    
    double horizontalSpacing = 0;
    String searchFor=null;
    public static String pubClientName;
    public static int clientID;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        renderingClientTrack = "default" ;
        clientSearchingByName.managedProperty().
                bind(clientSearchingByName.visibleProperty());
        clientSearchingByPhone.managedProperty().
                bind(clientSearchingByPhone.visibleProperty());
        clientSearchingByID.managedProperty().
                bind(clientSearchingByID.visibleProperty());
        clientSearchingByName.setVisible(false);
        clientSearchingByID.setVisible(false);
        clientSearchingByPhone.setVisible(false);
        clientsSearchingHBox.setSpacing(5);
        horizontalSpacing = scenesWidth * 0.02;
        AnchorPane.setTopAnchor(toggleBtnsHB, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(clientsSearchingScPn, scenesHeight * 0.1265);
    }
    @FXML
    private void search(MouseEvent event){
        String searchingClientQuery = "";
        switch(searchFor){
            case "name":
                String clientName = clientSearchingByName.getText();
                searchingClientQuery = "SELECT NAME, ID FROM CLIENTS_F WHERE "
                        + "NAME LIKE '"+clientName+"%'";
                break;
            case "phone":
                String phone = clientSearchingByPhone.getText();
                searchingClientQuery = "SELECT NAME, ID FROM CLIENTS_F WHERE "
                        + "MOBILE_1 = '" + phone +"' "
                        + "OR MOBILE_2 = '" + phone + "'";
                break;
            case "id":
                String clientIdStr = clientSearchingByID.getText();
                int clientId = Integer.parseInt(clientIdStr);
                searchingClientQuery = "SELECT NAME, ID FROM CLIENTS_F WHERE "
                        +"ID = "+clientId;
                break;
        }
        updateUI(searchingClientQuery);
    }
    
      private void updateUI(String searchingParam){
        ResultSet projectsSearchingRs = MainApp.dbUtility.
                databaseQuerying(con, searchingParam);
        try {
            while (projectsSearchingRs.next()) {
                String clientName = projectsSearchingRs.getString("NAME");
                int clientId = projectsSearchingRs.getInt("ID");
                createUINode(clientName, clientId);
            }
            projectsSearchingRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddProjectController.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void searchingByName(MouseEvent event){
        if(nameBtn.isSelected()){
            FadeTransition ft = new FadeTransition(Duration.seconds(1),
                clientSearchingByName);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            clientSearchingByName.setVisible(true);
            
            clientSearchingByID.setVisible(false);
            clientSearchingByPhone.setVisible(false);
            searchFor = "name";
        }
    }
    @FXML
    private void searchingByPhone(MouseEvent event){
        if(phoneBtn.isSelected()){
            FadeTransition ft = new FadeTransition(Duration.seconds(1),
                clientSearchingByPhone);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            clientSearchingByPhone.setVisible(true);
            clientSearchingByName.setVisible(false);
            clientSearchingByID.setVisible(false);
            searchFor = "phone";
        }
    }
    @FXML
    private void searchingById(MouseEvent event){
        if(idBtn.isSelected()){
            FadeTransition ft = new FadeTransition(Duration.seconds(1),
                clientSearchingByID);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            clientSearchingByID.setVisible(true);
            
            clientSearchingByName.setVisible(false);
            clientSearchingByPhone.setVisible(false);
            searchFor = "id";
        }
    }
    private void createUINode(String clientName,final int clientId){
        HBox hbox = new HBox();
        hbox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefWidth(scenesWidth * 0.6);
        hbox.setSpacing(scenesWidth * 0.08);
        
        hbox.setId("searchRsHbox");
        Text txt = new Text(clientName);
        txt.setId("searchRsTxt");
        
        Text idText = new Text(""+clientId);
        idText.setId("searchRsTxt");
        
        
        hbox.getChildren().addAll(idText, txt);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7),
                hbox);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        searchingRsAncPn.getChildren().add(hbox);
        AnchorPane.setTopAnchor(hbox,horizontalSpacing);
        AnchorPane.setLeftAnchor(hbox, scenesWidth * 0.17);
        hbox.setOnMouseClicked((MouseEvent event) -> {
            clientID = clientId;
            StackPane mainAppStackPane = (StackPane)
                    searchingClientsAnchorPane.getParent();
            mainAppStackPane.getChildren().clear();
            
            try {
                AnchorPane loadedAncPane = (AnchorPane)
                        FXMLLoader.load(getClass()
                                .getResource("/fxml/clientProfile.fxml"));
                mainAppStackPane.getChildren().add(loadedAncPane);
            } catch (IOException ex) {
                Logger.getLogger(ClientsSearchController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        });
        horizontalSpacing += scenesHeight * 0.08;
    }
}
