/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.modules;

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
import com.khalifa.elsarh.pojos.InstallmentModel;
import static com.khalifa.elsarh.pojos.InstallmentModel.ESSENTIAL_INSTALLMENT;
import com.khalifa.elsarh.pojos.ReceiptModel;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author khalifa
 */
public class ClientUnitsModule {
    
    public String getBuildingName(int buildingId){
        
        String getBuilindNameQuery
                = "SELECT NAME FROM BUILDINGS_F "
                + "WHERE ID = " + buildingId, buildingName = "";
        
        ResultSet buildingNameRs = MainApp.dbUtility.
                databaseQuerying(con, getBuilindNameQuery);
        try {
            while (buildingNameRs.next()) {
                buildingName = buildingNameRs.getString(1);
            }
            buildingNameRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return buildingName;
    }
    
    public int getClientId(int unitId){
        
        int clientId = 0;
        
        String getClientIdQuery
                = "SELECT CLIENT_ID FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet clientIdRs = MainApp.dbUtility.
                databaseQuerying(con, getClientIdQuery);
        try {
            while (clientIdRs.next()) {
                clientId = clientIdRs.getInt(1);
            }
            clientIdRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return clientId ; 
    }
    
    public ArrayList getBuildingLocation(int buildingId){
        ArrayList<String> location = new ArrayList();
        String getBuilindLocationQuery
                = "SELECT STREET, AREA, COUNTRY FROM BUILDINGS_F "
                + "WHERE ID = " + buildingId;
        
        ResultSet buildingLocationRs = MainApp.dbUtility.
                databaseQuerying(con, getBuilindLocationQuery);
        try {
            while (buildingLocationRs.next()) {
                location.add(buildingLocationRs.getString(1));
                location.add(buildingLocationRs.getString(2));
                location.add(buildingLocationRs.getString(3));
            }
            buildingLocationRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return location;
    }
   
    public String getUnitType(int unitId){
        String unitType = "";
        String getUnitTypeQuery
                = "SELECT TYPE FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet unitTypeRs = MainApp.dbUtility.
                databaseQuerying(con, getUnitTypeQuery);
        try {
            while (unitTypeRs.next()) {
                unitType = unitTypeRs.getString(1);
            }
            unitTypeRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return unitType;
    }
    
    public int getUnitCode(int unitId){
        int unitCode = 0;
        String getUnitTypeQuery
                = "SELECT UNIT_CODE FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet unitCodeRs = MainApp.dbUtility.
                databaseQuerying(con, getUnitTypeQuery);
        try {
            while (unitCodeRs.next()) {
                unitCode = unitCodeRs.getInt(1);
            }
            unitCodeRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return unitCode;
    }
    
    public int getUnitFloor(int unitId){
        int Floor = 0;
        String getUnitTypeQuery
                = "SELECT FLOOR_NUMBER FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet unitFloorRs = MainApp.dbUtility.
                databaseQuerying(con, getUnitTypeQuery);
        try {
            while (unitFloorRs.next()) {
                Floor = unitFloorRs.getInt(1);
            }
            unitFloorRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return Floor;
    }
    
    public String getUnitModel(int unitId){
        String unitModel = "";
        String getUnitTypeQuery
                = "SELECT MODEL_NAME FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet unitModelRs = MainApp.dbUtility.
                databaseQuerying(con, getUnitTypeQuery);
        try {
            while (unitModelRs.next()) {
                unitModel = unitModelRs.getString(1);
            }
            unitModelRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return unitModel;
    }
    
    public int getModelArea(String modelName, int buildingId){
        int modelArea = 0;
        String getUnitTypeQuery
                = "SELECT AREA FROM MODELS_F"
                + " WHERE BUILDING_ID = " + buildingId
                + " AND NAME = '" + modelName + "'" ;
        
        ResultSet unitCodeRs = MainApp.dbUtility.
                databaseQuerying(con, getUnitTypeQuery);
        try {
            while (unitCodeRs.next()) {
                modelArea = unitCodeRs.getInt(1);
            }
            unitCodeRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return modelArea;
    }
    
    public int getBuildingId(int unitId) {
        
        int buildingId = 0;
        String getBuilindIdQuery
                = "SELECT BUILDING_ID FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet buildingIdRs = MainApp.dbUtility.
                databaseQuerying(con, getBuilindIdQuery);
        try {
            while (buildingIdRs.next()) {
                buildingId = buildingIdRs.getInt(1);
            }
            buildingIdRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return buildingId;
    }
    
    public Date getContractDate(int unitId){
        
        Date conrtactDate = null;
        String getUnitTypeQuery
                = "SELECT CONTRACT_DATE FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet unitCodeRs = MainApp.dbUtility.
                databaseQuerying(con, getUnitTypeQuery);
        try {
            while (unitCodeRs.next()) {
                conrtactDate = unitCodeRs.getDate(1);
            }
            unitCodeRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return conrtactDate;
    }
    
    public Date getHandingOverDate(int unitId){
        
        Date handingOverDate = null;
        String getUnitTypeQuery
                = "SELECT RECEPTION_DATE FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet unitCodeRs = MainApp.dbUtility.
                databaseQuerying(con, getUnitTypeQuery);
        try {
            while (unitCodeRs.next()) {
                handingOverDate = unitCodeRs.getDate(1);
            }
            unitCodeRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return handingOverDate;
    }
    
    public Date getRestorationDate(int unitId, int clientId){
        Date resotrationDate = null;
        String getRestorationDateQuery
                = "SELECT RESTORATION_DATE FROM RESTORATION_HISTORY_F "
                + "WHERE UNIT_ID = " + unitId + " AND CLIENT_ID = " + clientId;
        
        ResultSet restorationDateRs = MainApp.dbUtility.
                databaseQuerying(con, getRestorationDateQuery);
        try {
            while (restorationDateRs.next()) {
                resotrationDate = restorationDateRs.getDate(1);
            }
            restorationDateRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return resotrationDate;
    }
    
    public Date getContractDateFromHistory(int unitId, int clientId){
        Date contractDate = null;
        String getContractDateQuery
                = "SELECT CONTRACT_DATE FROM RESTORATION_HISTORY_F "
                + "WHERE UNIT_ID = " + unitId + " AND CLIENT_ID = " + clientId;
        
        ResultSet contractDateRs = MainApp.dbUtility.
                databaseQuerying(con, getContractDateQuery);
        try {
            while (contractDateRs.next()) {
                contractDate = contractDateRs.getDate(1);
            }
            contractDateRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return contractDate;
    }
    
    public float getUnitPrice(int unitId,int buildingId ,String modelName){
        float unitPrice = isItSpecial(unitId);
        if(unitPrice != 0.0){
            return unitPrice; 
        }
        String unitPriceQuery = "SELECT PRICE FROM MODELS_F"
                + " WHERE BUILDING_ID = " + buildingId
                + " AND NAME = '"+ modelName+"'";
        ResultSet unitPriceRs = MainApp.dbUtility.
                databaseQuerying(con, unitPriceQuery);
        try {
            while (unitPriceRs.next()) {
                unitPrice = unitPriceRs.getFloat("PRICE");
            }
            unitPriceRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return unitPrice;
    }
    
    public float isItSpecial(int unitId){
        float specialPrice = 0;
        String specialPriceQuery = "SELECT SPECIAL_PRICE FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet specialPriceRs = MainApp.dbUtility.
                databaseQuerying(con, specialPriceQuery);
        try {
            while (specialPriceRs.next()) {
                specialPrice = specialPriceRs.getFloat(1);
            }
            specialPriceRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return specialPrice;
    }
    
    public float getDiscountRatio(int unitId){
        float discountRatio = 0;
        String specialPriceQuery = "SELECT DISCOUNT_RATIO FROM UNITS_F "
                + "WHERE ID = " + unitId;
        ResultSet discountRatioRs = MainApp.dbUtility.
                databaseQuerying(con, specialPriceQuery);
        try {
            while (discountRatioRs.next()) {
                discountRatio = discountRatioRs.getFloat(1);
            }
            discountRatioRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return discountRatio;
    }
    
    public float getTotalInstallmentsValues(int unitId, String installmentType, 
            int clientId){
        
        float totalValue = 0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_VALUE) FROM"
                + " UNITS_INSTALLMENTS1"
                + " WHERE UNIT_ID = " + unitId 
                + " AND TYPE = '" + installmentType+"'"
                + " AND CLIENT_ID = " + clientId;
        ResultSet totalValueRs = MainApp.dbUtility.
                databaseQuerying(con, totalValueQuery);
        try {
            while (totalValueRs.next()) {
                totalValue = totalValueRs.getFloat(1);
            }
            totalValueRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return totalValue ;
    }
    
    public float getTotalInstallmentsValues(int unitId, int clientId){
        float totalValue = 0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_VALUE) FROM"
                + " UNITS_INSTALLMENTS1 WHERE UNIT_ID = " + unitId
                + " AND CLIENT_ID = " + clientId;
        ResultSet totalValueRs = MainApp.dbUtility.
                databaseQuerying(con, totalValueQuery);
        try {
            while (totalValueRs.next()) {
                totalValue = totalValueRs.getFloat(1);
            }
            totalValueRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return totalValue ;
    }
    
    public float getTotalPaidInstallments(int unitId,
            String installmentType, int clientId){
        float totalPaid=0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_PAID_VALUE) FROM"
                + " UNITS_INSTALLMENTS1 WHERE UNIT_ID = " + unitId
                + " AND TYPE = '" + installmentType+"'"
                + " AND CLIENT_ID = " + clientId;
        ResultSet totalValueRs = MainApp.dbUtility.
                databaseQuerying(con, totalValueQuery);
        try {
            while (totalValueRs.next()) {
                totalPaid = totalValueRs.getFloat(1);
            }
            totalValueRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return totalPaid;
    }
    
    public float getTotalResotredInstallments(int unitId, int clientId){
        float totalPaid=0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_PAID_VALUE) FROM"
                + " UNITS_INSTALLMENTS1 WHERE UNIT_ID = " + unitId
                + " AND INSTALLMENT_PAID_VALUE < 0"
                + " AND CLIENT_ID = " + clientId;
        ResultSet totalValueRs = MainApp.dbUtility.
                databaseQuerying(con, totalValueQuery);
        try {
            while (totalValueRs.next()) {
                totalPaid = totalValueRs.getFloat(1);
            }
            totalValueRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return totalPaid;
    }
    
    public float getTotalPaidInstallments(int unitId, int clientId){
        float totalPaid=0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_PAID_VALUE) FROM"
                + " UNITS_INSTALLMENTS1 WHERE UNIT_ID = " + unitId
                + " AND CLIENT_ID = " + clientId;
        ResultSet totalValueRs = MainApp.dbUtility.
                databaseQuerying(con, totalValueQuery);
        try {
            while (totalValueRs.next()) {
                totalPaid = totalValueRs.getFloat(1);
            }
            totalValueRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return totalPaid;
    }
    
    public boolean isPaymentInitiated(int unitId){
        boolean isInitiated = false;
        String isInitiatedQuery = "SELECT IS_PAYMENT_INITIATED FROM UNITS_F"
                + " WHERE ID = " + unitId ;
        ResultSet isInitiatedRs = MainApp.dbUtility.
                databaseQuerying(con, isInitiatedQuery);
        try {
            while (isInitiatedRs.next()) {
                isInitiated = isInitiatedRs.getBoolean(1);
            }
            isInitiatedRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return isInitiated;
    }
    
    public void saveInstallments(List<HBox> installmentsList, 
            int unitId, int buildingId, int clientId){
        
        installmentsList.forEach((installmentHBox) -> {
            ComboBox installmentNameCB = (ComboBox) installmentHBox.
                    getChildren().get(1);
            String paymentName = String.valueOf(installmentNameCB.getValue());
            HBox dateHbox = (HBox) installmentHBox.getChildren().get(2);
            TextField dayTxtFld = (TextField) dateHbox.getChildren().get(0),
                    monthTxtFld = (TextField) dateHbox.getChildren().get(1),
                    yearTxtFld = (TextField) dateHbox.getChildren().get(2);
            String dayStr = dayTxtFld.getText(),
                    monthStr = monthTxtFld.getText(),
                    yearStr = yearTxtFld.getText(),
                    instlmntDateStr = yearStr + "-" + monthStr + "-" +dayStr;
            Date installmentDate = Date.valueOf(instlmntDateStr);
            TextField paymentValueTxtFld = (TextField) 
                    installmentHBox.getChildren().get(3);
            float paymentValue = Float.valueOf(paymentValueTxtFld.getText());
            String installmentType = ESSENTIAL_INSTALLMENT; // because it's the unit price.
            addInstallment(unitId, buildingId, clientId, paymentName,
                    installmentDate, paymentValue, installmentType);
        });
        
    }
    
    public void setPaymentInitiated(int unitId, float discountRatio){
        String paymentInitiatedQuery = "UPDATE UNITS_F SET"
                + " IS_PAYMENT_INITIATED = true,"
                + " DISCOUNT_RATIO = "+discountRatio
                + " WHERE ID = "+unitId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con,
                paymentInitiatedQuery);
    }
    
    public void addInstallment(int unidId, int buildingId, int clientId,
            String installmentName, Date installmentDate,
            float installmentValue, String type) {
        
        String insertInstallmentQuery = "INSERT INTO UNITS_INSTALLMENTS1 ("
                + "INSTALLMENT_NAME,"
                + "INSTALLMENT_DATE,"
                + "INSTALLMENT_VALUE,"
                + "TYPE,"
                + "UNIT_ID,"
                + "BUILDING_ID,"
                + "CLIENT_ID) "
                + "VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(insertInstallmentQuery);

            preparedStmt.setString(1, installmentName);
            preparedStmt.setDate(2, installmentDate);
            preparedStmt.setFloat(3, installmentValue);
            preparedStmt.setString(4, type);
            preparedStmt.setInt(5, unidId);
            preparedStmt.setInt(6, buildingId);
            preparedStmt.setInt(7, clientId);

            preparedStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public List<InstallmentModel> getInstallments(int unitId,
            List<InstallmentModel> installmentsList, int clientId){
        
        String getInstallmentsQuery =
                "SELECT ID, INSTALLMENT_NAME, INSTALLMENT_VALUE,"
                + " INSTALLMENT_PAID_VALUE, INSTALLMENT_DATE, FINISHED"
                + " FROM UNITS_INSTALLMENTS1 WHERE UNIT_ID = " + unitId 
                + " AND CLIENT_ID = " + clientId;
        
        ResultSet installmentsRs = MainApp.dbUtility.
                databaseQuerying(con, getInstallmentsQuery);
        try {
            while (installmentsRs.next()) {
                InstallmentModel installment = new InstallmentModel();
                installment.setId(installmentsRs.getInt(1));
                installment.setPaymentName(installmentsRs.getString(2));
                installment.setInstallmentValue(installmentsRs.getFloat(3));
                installment.setInstallmentPaidValue(installmentsRs.getFloat(4));
                installment.setInstallmentDate(installmentsRs.getDate(5));
                installment.setFinished(installmentsRs.getBoolean(6));
                installment.setClientId(clientId);
                installmentsList.add(installment);
            }
            installmentsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return installmentsList;
    }
    
    public InstallmentModel getInstallmentData(int installmentId, int unitId){
        InstallmentModel installment = new InstallmentModel();
        String installmentQuery = "SELECT INSTALLMENT_NAME, INSTALLMENT_VALUE,"
                + " INSTALLMENT_DATE, INSTALLMENT_PAID_VALUE "
                + " FROM UNITS_INSTALLMENTS1 WHERE ID = "+ installmentId
                + " AND UNIT_ID = "+ unitId;
        
        ResultSet installmentRs = MainApp.dbUtility.
                databaseQuerying(con, installmentQuery);
        
        try {
            while(installmentRs.next()){
                installment.setPaymentName(installmentRs.getString(1));
                installment.setInstallmentValue(installmentRs.getFloat(2));
                installment.setInstallmentDate(installmentRs.getDate(3));
                installment.setInstallmentPaidValue(installmentRs.getFloat(4));
            }
            installmentRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return installment;
    }
    
    public boolean checkInstallment(int installmentId, int unitId){
        String installmentChecking = "SELECT ID "
                + " FROM UNITS_INSTALLMENTS1 WHERE ID = "+ installmentId
                + " AND UNIT_ID = "+ unitId;
        
        ResultSet checkingRs = MainApp.dbUtility.
                databaseQuerying(con, installmentChecking);
        boolean checkingFlag = false;
        try {
            while(checkingRs.next()){
                checkingFlag = true;
            }
            checkingRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return checkingFlag;
    }
    
    public boolean BankedUpB4(int installmentId, String checkId){
        
        String query = "SELECT ID "
                + " FROM INSTALLMENTS_RECEIPTS_IDS"
                + " WHERE INSTALLMENT_ID = "+ installmentId
                + " AND CHECK_ID = '"+ checkId + "' AND (RECEIPT_VALUE > 0 OR"
                + " 0 > RECEIPT_VALUE)";
        
        ResultSet idRs = MainApp.dbUtility.
                databaseQuerying(con, query);
        
        boolean checkingFlag = false;
        try {
            while(idRs.next()){
                checkingFlag = true;
            }
            idRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return checkingFlag;
    }
    
    public int getCheckReceiptId(int installmentId, String checkId){
        String query = "SELECT ID "
                + " FROM INSTALLMENTS_RECEIPTS_IDS"
                + " WHERE INSTALLMENT_ID = "+ installmentId
                + " AND CHECK_ID = '"+ checkId + "'";
        
        ResultSet idRs = MainApp.dbUtility.
                databaseQuerying(con, query);
        int recieptId = 0;
        try {
            while(idRs.next()){
                recieptId = idRs.getInt(1);
            }
            idRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return recieptId;
    }
    
    public String getBankName(int installmentId, String checkId){
        String query = "SELECT CHECK_BANK "
                + " FROM INSTALLMENTS_RECEIPTS_IDS"
                + " WHERE INSTALLMENT_ID = "+ installmentId
                + " AND CHECK_ID = '"+ checkId + "'";
        
        ResultSet idRs = MainApp.dbUtility.
                databaseQuerying(con, query);
        String bank = "";
        try {
            while(idRs.next()){
                bank = idRs.getString(1);
            }
            idRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return bank;
    }
    
    public Date getReceiptDate(int installmentId, String checkId){
        String query = "SELECT RECEIPT_DATE "
                + " FROM INSTALLMENTS_RECEIPTS_IDS"
                + " WHERE INSTALLMENT_ID = "+ installmentId
                + " AND CHECK_ID = '"+ checkId + "'";
        
        ResultSet idRs = MainApp.dbUtility.
                databaseQuerying(con, query);
        Date receiptDate = null;
        try {
            while(idRs.next()){
                receiptDate = idRs.getDate(1);
            }
            idRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return receiptDate;
    }
    
    public void addMulct(float newInstallmentValue, int installmentId){
        String query = "UPDATE UNITS_INSTALLMENTS1 SET"
                + " INSTALLMENT_VALUE = " + newInstallmentValue
                + " WHERE ID = " + installmentId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, query);
    }
    
    public void editingInstallment(int installmentId, 
            String installmentName, float installmentValue,
            Date installmentDate, float paidValue, String type){
        
        String updateInstallmentQuery = "UPDATE UNITS_INSTALLMENTS1 SET"
                + " INSTALLMENT_NAME = ?, INSTALLMENT_VALUE = ?,"
                + " INSTALLMENT_DATE = ?, INSTALLMENT_PAID_VALUE = ?,"
                + " TYPE = ? WHERE ID = "+ installmentId;
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(updateInstallmentQuery);
            
            preparedStmt.setString(1, installmentName);
            preparedStmt.setFloat(2, installmentValue);
            preparedStmt.setDate(3, installmentDate);
            preparedStmt.setFloat(4, paidValue);
            preparedStmt.setString(5, type);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public String getClientName(int clientId){
        String clientName = "";
        String clientNameQuery = "SELECT NAME FROM CLIENTS_F"
                + " WHERE ID = "+clientId;
        
        ResultSet clientNameRs = MainApp.dbUtility.
                databaseQuerying(con, clientNameQuery);
        try {
            while(clientNameRs.next()){
                clientName = clientNameRs.getString(1);
            }
            clientNameRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return clientName;
    }
    
    public String getClientPersonalId(int clientId){
        String clientPerosnalId = "";
        String clientPersonalIdQuery = "SELECT PERSONAL_ID FROM CLIENTS_F"
                + " WHERE ID = "+clientId;
        
        ResultSet clientPersonalIdRs = MainApp.dbUtility.
                databaseQuerying(con, clientPersonalIdQuery);
        try {
            while(clientPersonalIdRs.next()){
                clientPerosnalId = clientPersonalIdRs.getString(1);
            }
            clientPersonalIdRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return clientPerosnalId;
    }
    
    public String getClientPhone(int clientId){
        String phone = "";
        
        String clientMobileNoQuery = "SELECT MOBILE_1 FROM CLIENTS_F"
                + " WHERE ID = "+clientId;
        
        ResultSet clientMobileNoRs = MainApp.dbUtility.
                databaseQuerying(con, clientMobileNoQuery);
        try {
            while(clientMobileNoRs.next()){
                phone = clientMobileNoRs.getString(1);
            }
            clientMobileNoRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return phone;
    }
    
    public void payInstallment(float paidValue, String receiptId,
            boolean isItCheck, Date paymentDate, int installmentId,
            boolean finished, float receiptValue, String bank, String checkId){
        String payingQuery= "UPDATE UNITS_INSTALLMENTS1 SET"
                + " INSTALLMENT_PAID_VALUE = ?, FINISHED = ?"
                + " WHERE ID = "+ installmentId;
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(payingQuery);
            
            preparedStmt.setFloat(1, paidValue);
            preparedStmt.setBoolean(2, finished);
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        setReceiptData(installmentId, receiptId, paymentDate, isItCheck,
                receiptValue, bank, checkId);
    }
    
    public void setReceiptData(int installmentId, String receiptId, 
            Date paymentDate, boolean isItCheck, float receiptValue,
            String bank, String checkId){
        String updatingQuery = "INSERT INTO INSTALLMENTS_RECEIPTS_IDS ("
                + " INSTALLMENT_ID,"
                + " RECEIPT_ID,"
                + " RECEIPT_DATE,"
                + " IS_IT_CHECK,"
                + " CHECK_BANK,"
                + " CHECK_ID,"
                + " RECEIPT_VALUE)"
                + " VALUES(?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(updatingQuery);
            
            preparedStmt.setInt(1, installmentId);
            preparedStmt.setString(2, receiptId);
            preparedStmt.setDate(3, paymentDate);
            preparedStmt.setBoolean(4, isItCheck);
            preparedStmt.setString(5, bank);
            preparedStmt.setString(6, checkId);
            preparedStmt.setFloat(7, receiptValue);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public List<ReceiptModel> getReceipts(int installmentId){
        String getReceiptsQuery = "SELECT RECEIPT_ID, IS_IT_CHECK, RECEIPT_DATE,"
                + " ID, CHECK_ID, CHECK_BANK, BANK_UP_DATE, RECEIPT_VALUE"
                + " FROM INSTALLMENTS_RECEIPTS_IDS"
                + " WHERE INSTALLMENT_ID = " + installmentId;
        
        List<ReceiptModel> receipts = new ArrayList();
        
        ResultSet receiptsRs = MainApp.dbUtility.
                databaseQuerying(con, getReceiptsQuery);
        
        try {
            while(receiptsRs.next()){
                ReceiptModel receipt = new ReceiptModel();
                receipt.setReceiptId(receiptsRs.getString(1));
                receipt.setIsItCheck(receiptsRs.getBoolean(2));
                receipt.setReceiptDate(receiptsRs.getDate(3));
                receipt.setId(receiptsRs.getInt(4));
                receipt.setCheckId(receiptsRs.getString(5));
                receipt.setBank(receiptsRs.getString(6));
                receipt.setBankingUpDate(receiptsRs.getDate(7));
                receipt.setReceiptValue(receiptsRs.getFloat(8));
                receipts.add(receipt);
            }
            receiptsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return receipts;
    }
    
    public void editReceipt(String receiptId, int id, Date receiptDate,
            boolean isItCheck, String bank, String checkId, float receiptNewVal){
        String query = "UPDATE INSTALLMENTS_RECEIPTS_IDS SET"
                + " RECEIPT_ID = ?, IS_IT_CHECK = ?, RECEIPT_DATE = ?,"
                + " CHECK_BANK = ?, CHECK_ID = ?, RECEIPT_VALUE = ? WHERE ID = ?";
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(query);
            
            preparedStmt.setString(1, receiptId);
            preparedStmt.setBoolean(2, isItCheck);
            preparedStmt.setDate(3, receiptDate);
            preparedStmt.setString(4, bank);
            preparedStmt.setString(5, checkId);
            preparedStmt.setFloat(6, receiptNewVal);
            preparedStmt.setInt(7, id);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void addDiffrenceValue2Installment(int installmentId,
            float differenceVal){
        
        String query = "UPDATE UNITS_INSTALLMENTS1 SET"
                + " INSTALLMENT_PAID_VALUE ="
                + " INSTALLMENT_PAID_VALUE + " + differenceVal 
                + " WHERE ID = " + installmentId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, query);
    }
    
    public float getPaidFromInstallment(int installmentId){
        
        String paidValueQuery = "SELECT INSTALLMENT_PAID_VALUE FROM"
                + " UNITS_INSTALLMENTS1 WHERE ID = "+installmentId;
        
        float paidValue = 0;
        
        ResultSet paidValueRs = MainApp.dbUtility.
                databaseQuerying(con, paidValueQuery);
        
        try {
            while(paidValueRs.next()){
                paidValue = paidValueRs.getFloat(1);
            }
            paidValueRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return paidValue;
    }
    
    public float getInstallmentValue(int installmentId){
        
        String installmentValueQuery = "SELECT INSTALLMENT_VALUE FROM"
                + " UNITS_INSTALLMENTS1 WHERE ID = " + installmentId;
        
        float installmentValue = 0;
        
        ResultSet installmentValueRs = MainApp.dbUtility.
                databaseQuerying(con, installmentValueQuery);
        
        try {
            while(installmentValueRs.next()){
                installmentValue = installmentValueRs.getFloat(1);
            }
            installmentValueRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return installmentValue;
    }
    
    public boolean checkInsallmentExistance(int installmentId, int unitId){
        
        String checkInstallmentQuery = "SELECT ID FROM UNITS_INSTALLMENTS1"
                + " WHERE ID = "+installmentId + "AND UNIT_ID = "+unitId;
        boolean flag = false;
        
        ResultSet checkRs = MainApp.dbUtility.
                databaseQuerying(con, checkInstallmentQuery);
        
        try {
            while(checkRs.next()){
                flag = true;
            }
            checkRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    public void handOverUnit(int unitId, Date handingOverDate){
        
        String handingQuery = "UPDATE UNITS_F SET RECEPTION_DATE = ?"
                + " WHERE ID = "+ unitId;
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(handingQuery);

            preparedStmt.setDate(1, handingOverDate);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public void transferUnit(int clientId, int unitId){
        String transferingUnit = "UPDATE UNITS_F SET CLIENT_ID = " + clientId
                + " WHERE ID = " + unitId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con,transferingUnit);
    }
    
    public void add2Transitions(int unitId, int buildingId, int clientId,
            int xclientId){
        String transitionQuery = "INSERT INTO TRANSITIONS_F("
                + "UNIT_ID,"
                + "BUILDING_ID,"
                + "CLIENT_ID,"
                + "XCLIENT_ID,"
                + "TRANSITION_DATE) "
                + "VALUES(?,?,?,?,?)";
        LocalDate transtionLocalDate = LocalDate.now();
        Date transtionDate = Date.valueOf(transtionLocalDate);
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(transitionQuery);
            
            preparedStmt.setInt(1, unitId);
            preparedStmt.setInt(2, buildingId);
            preparedStmt.setInt(3, clientId);
            preparedStmt.setInt(4, xclientId);
            preparedStmt.setDate(5, transtionDate);
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }       
    }
    
    public int getPreviuosClientId(int unitId, int clientId){
        
        String query = "SELECT XCLIENT_ID FROM TRANSITIONS_F"
                + " WHERE UNIT_ID = " + unitId 
                + " AND CLIENT_ID = " + clientId;
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        int xclientId = 0;
        try {
            while (rs.next()) {
                xclientId = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return xclientId;
    }
    
    public void addClient(String name, String career, String personalId,
            String mobile1, String mobile2, String address, String email){
        
        String clientInsertionQuery
                = "INSERT INTO CLIENTS_F(NAME,"
                + "CAREER,"
                + "PERSONAL_ID,"
                + "MOBILE_1,"
                + "MOBILE_2,"
                + "ADDRESS,"
                + "EMAIL)"
                + " VALUES(?,?,?,?,?,?,?)";
        
         try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(clientInsertionQuery);

            preparedStmt.setString(1, name);
            preparedStmt.setString(2, career);
            preparedStmt.setString(3, personalId);
            preparedStmt.setString(4, mobile1);
            preparedStmt.setString(5, mobile2);
            preparedStmt.setString(6, address);
            preparedStmt.setString(7, email);

            preparedStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public int getClientId(){
    
        String query = "SELECT ID FROM CLIENTS_F ORDER BY ID DESC FETCH"
                + " FIRST ROW ONLY";
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        int clientId = 0;
        try {
            while (rs.next()) {
                clientId = rs.getInt("id");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return clientId;
    }
    
    public void transferUnpaidInstallments(int clientId, int unitId){
        String transferInstallmentsQuery = "UPDATE UNITS_INSTALLMENTS1 SET"
                + " CLIENT_ID = "+clientId+" WHERE UNIT_ID = "+unitId
                +" AND INSTALLMENT_PAID_VALUE = 0 ";
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con,
                transferInstallmentsQuery);
    }
    
    public void deleteUnpaidInstallments(int unitId){
        
        String deletionQuery = "DELETE FROM UNITS_INSTALLMENTS1"
                + " WHERE UNIT_ID = "+unitId+" AND INSTALLMENT_PAID_VALUE = 0 ";
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, deletionQuery);
        
        paymentNotInitiated(unitId);
    }
    
    private void paymentNotInitiated(int unitId){
        String paymentNotInitiatedQuery = "UPDATE UNITS_F SET"
                + " IS_PAYMENT_INITIATED = FALSE WHERE ID = " + unitId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con,
                paymentNotInitiatedQuery);
    }
    
    public void payCash(float cashValue, Date cashPaymentDate, String receiptId,
            int unitId, int buildingId, int clientId, String bank,
            String checkId, Date receiptDate, boolean isItCheck){
        String payCashQuery = "INSERT INTO UNITS_INSTALLMENTS1("
                + " INSTALLMENT_NAME,"
                + " INSTALLMENT_VALUE,"
                + " INSTALLMENT_PAID_VALUE,"
                + " INSTALLMENT_DATE,"
                + " TYPE,"
                + " FINISHED,"
                + " UNIT_ID,"
                + " BUILDING_ID,"
                + " CLIENT_ID)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(payCashQuery);

            preparedStmt.setString(1, "كاش");
            preparedStmt.setFloat(2, cashValue);
            preparedStmt.setFloat(3, cashValue);
            preparedStmt.setDate(4, cashPaymentDate);
            preparedStmt.setString(5, ESSENTIAL_INSTALLMENT);
            preparedStmt.setBoolean(6, true);
            preparedStmt.setInt(7, unitId);
            preparedStmt.setInt(8, buildingId);
            preparedStmt.setInt(9, clientId);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        int installmentId = getLastInsertedInslmntId();
        setReceiptData(installmentId, receiptId, receiptDate, isItCheck, 
                cashValue, bank, checkId);
        if(isItCheck){
            int receiptDBId =  getCheckReceiptId(installmentId, checkId);
            bankUpCheck(cashValue, receiptDate, receiptDBId);
        }
    }
    
    public void bankUpCheck(float checkValue, Date bankingUpDate,
            int receiptId){
        String query = "UPDATE INSTALLMENTS_RECEIPTS_IDS SET"
                + " RECEIPT_VALUE = ?, BANK_UP_DATE = ?"
                + " WHERE ID = " + receiptId;
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(query);
            
            preparedStmt.setFloat(1, checkValue);
            preparedStmt.setDate(2, bankingUpDate);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void addCheck2Installment(int installmentId,
            float valueAfterInstallment){
        String query = "UPDATE UNITS_INSTALLMENTS1 SET"
                + " INSTALLMENT_PAID_VALUE = " + valueAfterInstallment
                + " WHERE ID = " + installmentId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, query);
    }
    
    private int getLastInsertedInslmntId(){
        String query = "SELECT ID FROM UNITS_INSTALLMENTS1 "
                + "ORDER BY ID DESC FETCH FIRST ROW ONLY";
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        int installmentId = 0;
        try {
            while (rs.next()) {
                installmentId = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return installmentId;
    }
    
    public void restorPayment(float cashValue, Date cashPaymentDate, 
            String receiptId, int unitId, int buildingId, int clientId,
            String type, String bank, String checkId, Date receiptDate,
            boolean isItCheck){
        
        String payCashQuery = "INSERT INTO UNITS_INSTALLMENTS1("
                + " INSTALLMENT_NAME,"
                + " INSTALLMENT_VALUE,"
                + " INSTALLMENT_PAID_VALUE,"
                + " INSTALLMENT_DATE,"
                + " TYPE,"
                + " FINISHED,"
                + " UNIT_ID,"
                + " BUILDING_ID,"
                + " CLIENT_ID)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(payCashQuery);

            preparedStmt.setString(1, "دفعه مسترده");
            preparedStmt.setFloat(2, 0);
            preparedStmt.setFloat(3, cashValue * -1 );
            preparedStmt.setDate(4, cashPaymentDate);
            preparedStmt.setString(5, type);
            preparedStmt.setBoolean(6, true);
            preparedStmt.setInt(7, unitId);
            preparedStmt.setInt(8, buildingId);
            preparedStmt.setInt(9, clientId);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        
        int installmentId = getLastInsertedInslmntId();
        setReceiptData(installmentId, receiptId, receiptDate, isItCheck,
                cashValue * -1, bank, checkId);
    }
    
    public void restorUnit(int unitId, int clientId, Date restorationDate){
        
        Date contractDate = getContractDate(unitId),
                handOveringDate = getHandingOverDate(unitId);
        submitToRestorationHistory(unitId, clientId, restorationDate,
                contractDate, handOveringDate);
        
        String freeTheUnit = "UPDATE UNITS_F SET CLIENT_ID = 0," 
                + " AVAILABILITY_STATUS = TRUE, IS_PAYMENT_INITIATED = FALSE,"
                + " RECEPTION_DATE = NULL, CONTRACT_DATE = NULL,"
                + " DISCOUNT_RATIO = 0 WHERE ID = " + unitId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, freeTheUnit);
        
    }

    public void setUnitFree(int unitId){
        String freeTheUnit = "UPDATE UNITS_F SET CLIENT_ID = 0," 
                + " AVAILABILITY_STATUS = TRUE, IS_PAYMENT_INITIATED = FALSE,"
                + " CONTRACT_DATE = NULL, RECEPTION_DATE = NULL,"
                + " DISCOUNT_RATIO = 0 WHERE ID = " + unitId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, freeTheUnit);
    }
    
    void submitToRestorationHistory(int unitId, int clientId,
            Date restorationDate, Date contractDate, Date handOveringDate){
        
        String submitRestoration = "INSERT INTO RESTORATION_HISTORY_F("
                + " RESTORATION_DATE,"
                + " CONTRACT_DATE,"
                + " RECEPTION_DATE,"
                + " UNIT_ID,"
                + " CLIENT_ID)"
                + " VALUES(?,?,?,?,?)";
        
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(submitRestoration);
            preparedStmt.setDate(1, restorationDate);
            preparedStmt.setDate(2, contractDate);
            preparedStmt.setDate(3, handOveringDate);
            preparedStmt.setInt(4, unitId);
            preparedStmt.setInt(5, clientId);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public Map<String, String>  getInstallmentsType(){
        
        String installmentsTypeQuery = "SELECT NAME, TYPE FROM"
                + " INSTALLMENT_NAMES_F";
        
        ResultSet installmentsTypeRs = MainApp.dbUtility.
                databaseQuerying(con, installmentsTypeQuery);
      
        Map<String, String> installmentsTypeMap = new HashMap();

        try {
            while (installmentsTypeRs.next()) {

                installmentsTypeMap.put(installmentsTypeRs.getString(1),
                        installmentsTypeRs.getString(2));
            }
            installmentsTypeRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return installmentsTypeMap;
    }
    
    public void deleteFinInfo(int unitId){
        
        String deletionQuery = "DELETE FROM UNITS_INSTALLMENTS1"
                + " WHERE UNIT_ID = " + unitId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, deletionQuery);
        
        paymentNotInitiated(unitId);
    }
}
