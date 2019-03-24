package com.khalifa.elsarh.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import com.khalifa.elsarh.modules.UnitBookingModule;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class UnitBookingController implements Initializable {

    @FXML
    private AnchorPane unitAncPn, clientInfoAncPn, unitBookingAnchorPane,
            existedClientInfoAncPn, newClientAnchPn;

    @FXML
    private TextField clientName, clientMobileNumber, clientCareer,
            clientPersonalId, existedClientCode, clientMobileNumber1,
            emailTxtFld, addressTxtFld, reservingDayTxtFld,
            reservingMonthTxtFld, reservingYearTxtFld;

    @FXML
    private ComboBox projectName, unitCodeCB;

    @FXML
    private HBox projectNameHB, unitCodeHB, clientInfoHB, unitInfoHB,
            existedClientInputHB, existedClientDataHB, firstLayerHB,
            secondLayerHB, thirdLayerHB, fourthLayerHB, reserveUnitHB;

    @FXML
    private Text existedClientNameTxt, existedClientPhoneTxt;

    @FXML
    private Button savingBtn;

    @FXML
    private RadioButton existedClientRB;

    private int buildingId = 0, clientId = 0;

    UnitBookingModule module;

    public static String renderingClientTrack = "default";
    public static int freshClientId;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        module = new UnitBookingModule();

        clientInfoHB.setSpacing(scenesWidth * 0.05);
        existedClientInputHB.setSpacing(scenesWidth * 0.05);
        existedClientDataHB.setSpacing(scenesWidth * 0.05);
        fourthLayerHB.setSpacing(scenesWidth * 0.02);
        firstLayerHB.setSpacing(scenesWidth * 0.05);
        secondLayerHB.setSpacing(scenesWidth * 0.05);
        thirdLayerHB.setSpacing(scenesWidth * 0.05);
        reserveUnitHB.setSpacing(scenesWidth * 0.03);

        AnchorPane.setTopAnchor(unitAncPn, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(unitInfoHB, scenesHeight * 0.01);
        AnchorPane.setTopAnchor(clientInfoHB, scenesHeight * 0.01);

        AnchorPane.setTopAnchor(existedClientInfoAncPn, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(newClientAnchPn, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(firstLayerHB, scenesHeight * 0.0);

        AnchorPane.setTopAnchor(secondLayerHB, scenesHeight * 0.06);
        AnchorPane.setTopAnchor(thirdLayerHB, scenesHeight * 0.12);
        AnchorPane.setTopAnchor(fourthLayerHB, scenesHeight * 0.18);
        AnchorPane.setTopAnchor(existedClientDataHB, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(clientInfoAncPn, scenesHeight * 0.42);

        AnchorPane.setTopAnchor(savingBtn, scenesHeight * 0.86);
        AnchorPane.setTopAnchor(projectNameHB, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(reserveUnitHB, scenesHeight * 0.16);
        AnchorPane.setTopAnchor(unitCodeHB, scenesHeight * 0.08);
        AnchorPane.setRightAnchor(unitAncPn, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(clientInfoAncPn, scenesWidth * 0.1);

        AnchorPane.setRightAnchor(projectNameHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(reserveUnitHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(existedClientInputHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(newClientAnchPn, scenesWidth * 0.0);

        AnchorPane.setRightAnchor(firstLayerHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(secondLayerHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(thirdLayerHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(existedClientDataHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(unitCodeHB, scenesWidth * 0.38);

        AnchorPane.setLeftAnchor(savingBtn, scenesWidth * 0.142);

        projectName.getItems().addAll(module.getBuildingNames());

        existedClientInfoAncPn.setVisible(false);

    }

    boolean makeItRed(TextField txtFld) {
        txtFld.setStyle("-fx-border-color:red;-fx-border-style:solid;");
        return false;
    }

    @FXML
    private void prepareBookingUnit() {
        
        String reservingDayStr = reservingDayTxtFld.getText(),
                    reservingMonthStr = reservingMonthTxtFld.getText(),
                    reservingYear = reservingYearTxtFld.getText();

            if (reservingDayStr.isEmpty()) {
                reservingDayTxtFld.setStyle("-fx-border-color: red;");
                return;
            } else {
                reservingDayTxtFld.setStyle("-fx-border-color: transparent;");
            }
            if (reservingMonthStr.isEmpty()) {
                reservingMonthTxtFld.setStyle("-fx-border-color: red;");
                return;
            } else {
                reservingMonthTxtFld.setStyle("-fx-border-color: transparent;");
            }
            if (reservingYear.isEmpty()) {
                reservingYearTxtFld.setStyle("-fx-border-color: red;");
                return;
            } else {
                reservingYearTxtFld.setStyle("-fx-border-color: transparent;");
            }
            
            String reservingDateStr = reservingYear + "-" +
                reservingMonthStr + "-" + reservingDayStr;
            Date reservingDate;
            try {
                reservingDate = Date.valueOf(reservingDateStr);
            }catch(IllegalArgumentException ex){
                reservingDayTxtFld.setStyle("-fx-border-color: red;");
                reservingMonthTxtFld.setStyle("-fx-border-color: red;");
                reservingYearTxtFld.setStyle("-fx-border-color: red;");
                return;
            }
        
        if (!existedClientRB.isSelected()) {
            String name = clientName.getText(),
                    clientIdStr = clientPersonalId.getText(),
                    mobile1 = clientMobileNumber.getText(),
                    career = clientCareer.getText(),
                    mobile2 = clientMobileNumber1.getText(),
                    email = emailTxtFld.getText(),
                    address = addressTxtFld.getText();

            boolean check = name.isEmpty()
                    ? makeItRed(clientName)
                    : clientIdStr.isEmpty()
                    ? makeItRed(clientPersonalId)
                    : mobile1.isEmpty()
                    ? makeItRed(clientMobileNumber)
                    : career.isEmpty()
                    ? makeItRed(clientCareer)
                    : email.isEmpty()
                    ? makeItRed(emailTxtFld)
                    : address.isEmpty()
                    ? makeItRed(addressTxtFld)
                    : true;
            
            if (!check) {
                return;
            }

            module.addClient(name, career, clientIdStr, mobile1, mobile2,
                    address, email);

            clientId = module.getClientId();
        }
        
            
        String confirmationBody = "تأكيد حجز الوحده الى عميل"
                + System.lineSeparator()+" بتاريخ: " + reservingDateStr;
        if (confirmProcess(null, confirmationBody)) {
            int unitCode = Integer.parseInt((String) unitCodeCB.getValue());
            bookUnit(unitCode, buildingId, clientId, reservingDate);
        }
    }

    private void bookUnit(int unitCode, int buildingId, int clientId,
            Date reservingDateStr) {

        module.makeItBusy(unitCode, buildingId, clientId, reservingDateStr);

        freshClientId = clientId;
        renderingClientTrack = "fromUnitBooking";
        renderClientProfile();
    }

    private void renderClientProfile() {
        AnchorPane clientProfilePage = null;
        StackPane mainAppStackPane = (StackPane)
                unitBookingAnchorPane.getParent();
        mainAppStackPane.getChildren().clear();
        try {
            clientProfilePage = (AnchorPane) FXMLLoader.load(getClass()
                    .getResource("/fxml/clientProfile.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(SideBarController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        mainAppStackPane.getChildren().add(clientProfilePage);
    }

    @FXML
    private void checkProjName() {
        String projectNameStr = String.valueOf(projectName.getValue());
        buildingId = module.getBuildingId(projectNameStr);
        unitCodeCB.getItems().clear();
        unitCodeCB.getItems().addAll(module.getAvailableUnits(buildingId));
    }

    @FXML
    private void existedClient() {

        if (existedClientRB.isSelected()) {
            existedClientInfoAncPn.setVisible(true);
            newClientAnchPn.setVisible(false);

        } else {
            existedClientInfoAncPn.setVisible(false);
            newClientAnchPn.setVisible(true);
        }
    }

    @FXML
    private void getOldeClientInfo() {
        String clientIdStr = existedClientCode.getText();

        clientId = Integer.valueOf(clientIdStr);
        String existedClientName = module.getClientName(clientId);

        String mobileNumber = module.getClientPhone(clientId);

        existedClientNameTxt.setText(existedClientName);
        existedClientPhoneTxt.setText(mobileNumber);
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
