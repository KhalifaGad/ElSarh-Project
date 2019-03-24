/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.controllers;

import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import com.khalifa.elsarh.modules.DailyPaymentsModule;
import com.khalifa.elsarh.pojos.InstallmentModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class DailyPaymentsController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @FXML
    private AnchorPane clientsAncPn;
    
    private double anchSpacing = 0;
    
    private DailyPaymentsModule module ;
    //private List<InstallmentModel> lateInstlmntsList = new ArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        module = new DailyPaymentsModule();
        
        anchSpacing = scenesHeight * 0.05 ;
        
        getClients();
    }    
    private void getClients(){
        
        LocalDate todayDate = LocalDate.now();
        java.sql.Date todaySqlDate = java.sql.Date.valueOf(todayDate);
        
        List<InstallmentModel> lateInstlmntsList = 
                module.getInstallments(todaySqlDate);
        showInstallments(lateInstlmntsList);
    }

    private void showInstallments(List<InstallmentModel> lateInstlmntsList ){
        double clientHBWidth = scenesWidth * 0.8,
               rightAnchorSpacing = scenesWidth * 0.1;
        
        for (InstallmentModel installment : lateInstlmntsList) {
            anchSpacing += scenesHeight * 0.08 ;
            HBox installmentHbox = new HBox();
            installmentHbox.setAlignment(Pos.CENTER);
            installmentHbox.setPrefWidth(clientHBWidth);
            installmentHbox.setSpacing(scenesWidth * 0.03);
            installmentHbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            installmentHbox.setId("hb");
            
            Text nameTxt = new Text();
            nameTxt.setText(module.getClientName(installment.getClientId()));
            nameTxt.setStyle("-fx-font-size: 1em;");

            Text clientCode = new Text();
            clientCode.setText(" ك.ع: "+installment.getClientId());
            clientCode.setStyle("-fx-font-size: 1em;");

            Text instlmntValueTxt = new Text();
            instlmntValueTxt.setText("بقيمه: "+
                    String.format("%.1f", installment.getInstallmentValue()));
            instlmntValueTxt.setStyle("-fx-font-size: 1em;");
            
            Text paymentTypeTxt = new Text();
            paymentTypeTxt.setStyle("-fx-font-size: 1em;");
            if(installment.isItCheck()){
                paymentTypeTxt.setText("شيك");
            } else {
                paymentTypeTxt.setText("نقدى");
            }
            
            
            Text date = new Text();
            date.setText("" + installment.getInstallmentDate());
            date.setStyle("-fx-font-size: 1em;");
            
            Text buildingNameTxt = new Text();
            buildingNameTxt.setText(module.getBuildingName(installment.
                    getBuildingId()));
            buildingNameTxt.setStyle("-fx-font-size: 1em;");
            
            Text unitCode = new Text();
            unitCode.setText("ك.و : " +
                    module.getUnitCode(installment.getUnitId()));
            unitCode.setStyle("-fx-font-size: 1em;");
            
            
            HBox mobileNumberHB = new HBox();
            mobileNumberHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            mobileNumberHB.setAlignment(Pos.BOTTOM_CENTER);
            
            Text phoneSymbolTxt = new Text();
            String phoneSymbol =  "\uD83D\uDCF1";
            phoneSymbolTxt.setText(phoneSymbol);
            phoneSymbolTxt.setStyle("-fx-font-size: 1.3em;");
            
            Text clientPhoneTxt = new Text();
            clientPhoneTxt.setText(" : " + module.
                    getClientPhone(installment.getClientId()));
            
            clientPhoneTxt.setStyle("-fx-font-size: 1em;");
            
            mobileNumberHB.getChildren().addAll(phoneSymbolTxt, clientPhoneTxt);
            
            installmentHbox.getChildren().addAll(nameTxt, clientCode,
                    instlmntValueTxt, paymentTypeTxt, date, buildingNameTxt,
                    unitCode, mobileNumberHB);
            AnchorPane.setTopAnchor(installmentHbox, anchSpacing);
            AnchorPane.setRightAnchor(installmentHbox, rightAnchorSpacing);

            clientsAncPn.getChildren().add(installmentHbox);
        }

    }
}
