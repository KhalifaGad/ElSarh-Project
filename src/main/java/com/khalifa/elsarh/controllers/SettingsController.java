/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.controllers;

import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import com.khalifa.elsarh.modules.SettingsModule;
import static com.khalifa.elsarh.pojos.InstallmentModel.ADDITIONAL_INSTALLMENT;
import static com.khalifa.elsarh.pojos.InstallmentModel.ADJUSTMENT_INSTALLMENT;
import static com.khalifa.elsarh.pojos.InstallmentModel.ESSENTIAL_INSTALLMENT;
import static com.khalifa.elsarh.pojos.InstallmentModel.FACILITIES_INSTALLMENT;
import static com.khalifa.elsarh.pojos.InstallmentModel.MAINTENANCE_INSTALLMENT;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class SettingsController implements Initializable {
    
    
    @FXML
    private TextField installmentName;
    
    @FXML
    private ToggleGroup installmentsTypeToggleGroup;
    
    @FXML
    private RadioButton essentialPaymentRB, additionalPaymentRB,
            facilitiesPaymentRB, maintenancePaymentRB, adjustmentPaymentRB;
    
    @FXML
    private AnchorPane addInstallmentsType;
    
    @FXML
    private HBox installmentHead, addInstallmentsTypeHB, savingHB;
    
    private SettingsModule module ;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        module = new SettingsModule();
        
        addInstallmentsTypeHB.setSpacing(scenesWidth * 0.02);
                
        AnchorPane.setTopAnchor(addInstallmentsType, scenesHeight * 0.2);
        AnchorPane.setTopAnchor(installmentHead, scenesHeight * 0.03);
        AnchorPane.setTopAnchor(addInstallmentsTypeHB, scenesHeight * 0.2);
        AnchorPane.setTopAnchor(savingHB, scenesHeight * 0.35);
        
        AnchorPane.setRightAnchor(addInstallmentsType, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(addInstallmentsTypeHB, scenesWidth * 0);
        
        essentialPaymentRB.setSelected(true);
    }
    
    @FXML
    private void addInstallmentType(MouseEvent event){
        String installmentTypeName = installmentName.getText();
        
        if(installmentTypeName.isEmpty()){
            installmentName.setStyle("-fx-border-color: red;");
            return;
        } else {
            installmentName.setStyle("-fx-border-color: green;");
        }
        
        String installmentType = ESSENTIAL_INSTALLMENT,
                forConfirmation = "مستحقات الاساسيه";
        
        RadioButton selectedToggle = (RadioButton) installmentsTypeToggleGroup
                .getSelectedToggle();
        if(selectedToggle == facilitiesPaymentRB){
            
            installmentType = FACILITIES_INSTALLMENT;
            forConfirmation = "مستحقات مرافق";
            
        } else if(selectedToggle == additionalPaymentRB){
            
            installmentType = ADDITIONAL_INSTALLMENT;
            forConfirmation = "مستحقات اضافيه";
            
        } else if(selectedToggle == maintenancePaymentRB){
            
            installmentType = MAINTENANCE_INSTALLMENT;
            forConfirmation = "مستحقات صيانه";
            
        } else if(selectedToggle == adjustmentPaymentRB){
            
            installmentType = ADJUSTMENT_INSTALLMENT;
            forConfirmation = "مستحقات تعديلات";
            
        }
        
        String confirmationBody = "تأكيد اضافة : " + installmentTypeName
                +System.lineSeparator() + forConfirmation;
        
        if(confirmProcess(null, confirmationBody)){
            module.addInstallmentType(installmentTypeName, installmentType);
            installmentName.setText("");
        }
    }
    
    private boolean confirmProcess(String headerStr, String confirmationStr) {
        Alert confirmationAlert
                = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.getDialogPane().setNodeOrientation(
                NodeOrientation.RIGHT_TO_LEFT);
        confirmationAlert.setTitle("تأكيد");
        confirmationAlert.setHeaderText(headerStr);
        confirmationAlert.setContentText(confirmationStr);
        Optional<ButtonType> result = confirmationAlert
                .showAndWait();
        return result.get() == ButtonType.OK;
    }
    
}
