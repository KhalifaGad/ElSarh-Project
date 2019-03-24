/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.modules;

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
import com.khalifa.elsarh.pojos.ClientModel;
import com.khalifa.elsarh.pojos.InstallmentModel;
import com.khalifa.elsarh.pojos.ReceiptModel;
import com.khalifa.elsarh.pojos.RestorationModel;
import com.khalifa.elsarh.pojos.TransitionsModel;
import com.khalifa.elsarh.pojos.UnitModel;
import com.khalifa.elsarh.utilities.ReportsUtility;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khalifa
 */
public class ReportsModule {
    
    public int getBuildingId(String buildingName){
        
        String buildingIdQuery = "SELECT ID FROM BUILDINGS_F"
                + " WHERE NAME = '" + buildingName + "'";
        int buildingId = 0;
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, buildingIdQuery);
        
        try {
            while(rs.next()){
                buildingId = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return buildingId ;
    }
    
    public List<String> getBuildingNames(){
        
        List<String> buildingNamesList = new ArrayList();
        
        String builingNamesQuery = "SELECT NAME FROM BUILDINGS_F ";
        
        ResultSet buildingNameRs = MainApp.dbUtility.
                databaseQuerying(con, builingNamesQuery);
        
        try {
            while (buildingNameRs.next()) {
                buildingNamesList.add(buildingNameRs.getString(1));
            }
            buildingNameRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return buildingNamesList;
    }
    
    public String getBuildingName(int buildingId){
        
        String query = "SELECT NAME FROM BUILDINGS_F"
                + " WHERE ID = " + buildingId ,
                buildingName = "";
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        
        try {
            while(rs.next()){
                buildingName = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return buildingName ;
    }
    
    public List<Integer> getClientsOfBuilding(int buildingId){
        
        String clientsIdsQuery = "SELECT CLIENT_ID FROM UNITS_F"
                + " WHERE BUILDING_ID = " + buildingId 
                +" AND AVAILABILITY_STATUS = FALSE" ;
        
        List<Integer> clientsIds = new ArrayList();
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, clientsIdsQuery);
        
        try {
            while(rs.next()){
                int clientId = rs.getInt(1);
                if(!clientsIds.contains(clientId)){
                    clientsIds.add(clientId);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return clientsIds;
    }
    
    public void getClientsData(List<Integer> clientsIds,
            Map<Integer, ClientModel> clientIdClientObjMap){
        
        clientsIds.forEach((clientId) -> { //CLIENTS_F
            ClientModel client = new ClientModel();
            String clientDataQuery = "SELECT NAME, MOBILE_1, PERSONAL_ID"
                    + " FROM CLIENTS_F WHERE ID = " + clientId;
            ResultSet rs = MainApp.dbUtility.
                    databaseQuerying(con, clientDataQuery);
            
            try {
                while (rs.next()) {
                    client.setId(clientId);
                    client.setName(rs.getString(1));
                    client.setMobileNumber(rs.getInt(2));
                    client.setPersonalId(rs.getString(3));
                    clientIdClientObjMap.put(clientId, client);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReportsModule.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public String getClientName(int clientId) {
        String clientNameQuery = "SELECT NAME FROM"
                + " CLIENTS_F WHERE ID = " + clientId,
                clientName = "";
        ResultSet rs = MainApp.dbUtility.
                databaseQuerying(con, clientNameQuery);
        try {
            while (rs.next()) {
                clientName = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return clientName;
    }
    
    public String getClientPhone(int clientId) {
        String clientNameQuery = "SELECT MOBILE_1 FROM"
                + " CLIENTS_F WHERE ID = " + clientId,
                clientName = "";
        ResultSet rs = MainApp.dbUtility.
                databaseQuerying(con, clientNameQuery);
        try {
            while (rs.next()) {
                clientName = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return clientName;
    }
    
    public Map<String, Float> getModelsPriceMap(int buildingId){ //MODELS_F
        String modelsQuery = "SELECT NAME, PRICE FROM MODELS_F"
                + " WHERE BUILDING_ID = " + buildingId;
        
        Map<String, Float> modelPriceMap = new HashMap();
        
        ResultSet rs = MainApp.dbUtility.
                databaseQuerying(con, modelsQuery);
        try {
            while (rs.next()) {
                modelPriceMap.put(rs.getString(1), rs.getFloat(2));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return modelPriceMap;
    }
    
    public List<UnitModel> getUnitsData(List<Integer> clientsIds,
            int buildingId){
        
        List<UnitModel> units = new ArrayList();
        
        clientsIds.forEach((clientId) -> {
            String clientDataQuery = "SELECT UNIT_CODE, CONTRACT_DATE,"
                    + " RECEPTION_DATE, TYPE, ID"
                    + " FROM UNITS_F WHERE CLIENT_ID = " + clientId
                    + " AND BUILDING_ID = "+buildingId;
            ResultSet rs = MainApp.dbUtility.
                    databaseQuerying(con, clientDataQuery);
            
            try {
                while (rs.next()) {
                    UnitModel unit = new UnitModel();
                    int unitCode = rs.getInt(1);
                    unit.setUnitCode(unitCode);
                    unit.setContructDate(rs.getDate(2));
                    unit.setReceptionDate(rs.getDate(3));
                    if("h".equals(rs.getString(4))){
                        unit.setType('h');
                    } else {
                        unit.setType('c');
                    }
                    unit.setId(rs.getInt(5));
                    unit.setClientId(clientId);
                    units.add(unit);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReportsModule.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        });
        return units;
    }
    
    public List<UnitModel> getReservedUnits(int buildingId){
        
        List<UnitModel> units = new ArrayList();

        String clientDataQuery = "SELECT RECEPTION_DATE, MODEL_NAME,"
                + " FLOOR_NUMBER, TYPE, CLIENT_ID, SPECIAL_PRICE"
                + " FROM UNITS_F WHERE AVAILABILITY_STATUS = FALSE" 
                + " AND BUILDING_ID = " + buildingId;
        ResultSet rs = MainApp.dbUtility.
                databaseQuerying(con, clientDataQuery);

        try {
            while (rs.next()) {
                UnitModel unit = new UnitModel();
                unit.setReceptionDate(rs.getDate(1));
                unit.setModelName(rs.getString(2));
                unit.setFloorNumber(rs.getInt(3));
                if ("h".equals(rs.getString(4))) {
                    unit.setType('h');
                } else {
                    unit.setType('c');
                    unit.setSpecialPrice(rs.getFloat(6));
                }
                unit.setClientId(rs.getInt(5));
                units.add(unit);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return units;
    }
    
    public List<UnitModel> getFreeUnits(int buildingId){
        
        List<UnitModel> units = new ArrayList();

        String clientDataQuery = "SELECT MODEL_NAME,"
                + " FLOOR_NUMBER, TYPE, SPECIAL_PRICE FROM UNITS_F WHERE"
                + " AVAILABILITY_STATUS = TRUE AND BUILDING_ID = " + buildingId;
        ResultSet rs = MainApp.dbUtility.
                databaseQuerying(con, clientDataQuery);

        try {
            while (rs.next()) {
                UnitModel unit = new UnitModel();
                unit.setModelName(rs.getString(1));
                unit.setFloorNumber(rs.getInt(2));
                if ("h".equals(rs.getString(3))) {
                    unit.setType('h');
                } else {
                    unit.setType('c');
                    unit.setSpecialPrice(rs.getFloat(4));
                }
                units.add(unit);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return units;
    }
    
    public float getTotalInstallmentsValues(int unitId){
        float totalValue = 0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_VALUE) FROM "
                + "UNITS_INSTALLMENTS1 WHERE UNIT_ID = "+unitId;
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
    
    public List<InstallmentModel> getInstallmentsData(int buildingId,
            Date startDate, Date endDate){
        
        List<InstallmentModel> installmentsList = new ArrayList();
        
        String installmentsQuery = "SELECT INSTALLMENT_VALUE, CLIENT_ID, "
                + "INSTALLMENT_NAME, INSTALLMENT_PAID_VALUE, "
                + "INSTALLMENT_DATE FROM UNITS_INSTALLMENTS1 "
                + "WHERE INSTALLMENT_DATE >= ? AND INSTALLMENT_DATE <= ? "
                + "AND BUILDING_ID = "+buildingId;
        PreparedStatement pStmt;
       
        try {
            pStmt = con.prepareStatement(installmentsQuery);
            pStmt.setDate(1, startDate);
            pStmt.setDate(2, endDate);
            try (ResultSet rs = pStmt.executeQuery()) {
                while(rs.next()){
                    InstallmentModel installment = new InstallmentModel();
                    installment.setInstallmentValue(rs.getFloat(1));
                    installment.setClientId(rs.getInt(2));
                    installment.setPaymentName(rs.getString(3));
                    installment.setInstallmentPaidValue(rs.getFloat(4));
                    installment.setInstallmentDate(rs.getDate(5));
                    installmentsList.add(installment);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return installmentsList;
    }
    
    public float getTotalPaidInstallments(int unitId){
        float totalPaid=0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_PAID_VALUE) FROM "
                + "UNITS_INSTALLMENTS1 WHERE UNIT_ID = "+unitId;
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
    
    public int getAvailablesCount(int buildingId){
        String query = "SELECT AVAILABILITY_STATUS FROM UNITS_F WHERE"
                + " AVAILABILITY_STATUS = TRUE AND BUILDING_ID = "+buildingId;
        int availables = 0;
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        try {
            while(rs.next()){
                availables ++;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsUtility.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return availables;
    }
    
    public int getUnAvailablesCount(int buildingId){
        String query = "SELECT AVAILABILITY_STATUS FROM UNITS_F WHERE"
                + " AVAILABILITY_STATUS = FALSE AND BUILDING_ID = "+buildingId;
        int unavailables = 0;
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        try {
            while(rs.next()){
                unavailables ++;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsUtility.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return unavailables;
    }
    
    public List<UnitModel> getHandedOverUnits(){
        String query = "SELECT CLIENT_ID, BUILDING_ID, TYPE, MODEL_NAME,"
                + " FLOOR_NUMBER, RECEPTION_DATE FROM UNITS_F"
                + " WHERE RECEPTION_DATE IS NOT NULL";
        List<UnitModel> units = new ArrayList();
        
        ResultSet rs = MainApp.dbUtility.
                databaseQuerying(con, query);

        try {
            while (rs.next()) {
                UnitModel unit = new UnitModel();
                unit.setClientId(rs.getInt(1));
                unit.setBuildingId(rs.getInt(2));
                unit.setModelName(rs.getString(4));
                unit.setFloorNumber(rs.getInt(5));
                if ("h".equals(rs.getString(3))) {
                    unit.setType('h');
                } else {
                    unit.setType('c');
                }
                unit.setReceptionDate(rs.getDate(6));
                units.add(unit);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return units;
    }
    
    public List<UnitModel> getJustBusyUnits(){
        String query = "SELECT CLIENT_ID, BUILDING_ID, TYPE, MODEL_NAME,"
                + " FLOOR_NUMBER FROM UNITS_F"
                + " WHERE RECEPTION_DATE IS NULL AND CLIENT_ID > 0";
        List<UnitModel> units = new ArrayList();
        
        ResultSet rs = MainApp.dbUtility.
                databaseQuerying(con, query);

        try {
            while (rs.next()) {
                UnitModel unit = new UnitModel();
                unit.setClientId(rs.getInt(1));
                unit.setBuildingId(rs.getInt(2));
                unit.setModelName(rs.getString(4));
                unit.setFloorNumber(rs.getInt(5));
                if ("h".equals(rs.getString(3))) {
                    unit.setType('h');
                } else {
                    unit.setType('c');
                }
                units.add(unit);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return units;
    }
    
    public List<InstallmentModel> getClientInstallments(int clientId,
            List<Integer> unitsIds){
        String installmentsQuery = "SELECT BUILDING_ID, UNIT_ID,"
                + " INSTALLMENT_NAME, INSTALLMENT_VALUE, INSTALLMENT_DATE,"
                + " INSTALLMENT_PAID_VALUE, ID"
                + " FROM UNITS_INSTALLMENTS1 WHERE CLIENT_ID = " + clientId
                + " ORDER BY UNIT_ID ";
        
        List<InstallmentModel> installments = new ArrayList();
        
        ResultSet rs = MainApp.dbUtility.
                databaseQuerying(con, installmentsQuery);
        
        try {
            while(rs.next()){
                InstallmentModel installment = new InstallmentModel();
                installment.setBuildingId(rs.getInt(1));
                int unitId = rs.getInt(2);
                installment.setUnitId(unitId);
                if(!unitsIds.contains(unitId)){
                    unitsIds.add(unitId);
                }
                installment.setPaymentName(rs.getString(3));
                installment.setInstallmentValue(rs.getFloat(4));
                installment.setInstallmentDate(rs.getDate(5));
                installment.setInstallmentPaidValue(rs.getFloat(6));
                installment.setId(rs.getInt(7));
                installments.add(installment);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return installments;
    }
    
    public Map<Integer, UnitModel> getUnitIdUnitDataMap(List<Integer> unitsIds){
        
        Map<Integer, UnitModel> unitIdUnitDataMap = new HashMap();
        
        unitsIds.forEach((unitId) -> {
            String query = "SELECT FLOOR_NUMBER, TYPE, MODEL_NAME, BUILDING_ID"
                    + " FROM UNITS_F WHERE ID = " + unitId;
            ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
            try {
                while(rs.next()){
                    UnitModel unit = new UnitModel();
                    unit.setFloorNumber(rs.getInt(1));
                    if ("h".equals(rs.getString(2))) {
                        unit.setType('h');
                    } else {
                        unit.setType('c');
                    }
                    unit.setModelName(rs.getString(3));
                    unit.setBuildingId(rs.getInt(4));
                    unitIdUnitDataMap.put(unitId, unit);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReportsModule.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        });
        return unitIdUnitDataMap;
    }
    
    public List<TransitionsModel> getTransitionedUnits(int xclientId,
            List<Integer> unitsIds){
        String query = "SELECT UNIT_ID, BUILDING_ID, CLIENT_ID,"
                + " TRANSITION_DATE FROM TRANSITIONS_F"
                + " WHERE XCLIENT_ID = " +xclientId;
        
        List<TransitionsModel> clientTransitions = new ArrayList();
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        
        try {
            while(rs.next()){
                TransitionsModel transition = new TransitionsModel();
                int unitId = rs.getInt(1);
                transition.setUnitId(unitId);
                transition.setBuildingId(rs.getInt(2));
                transition.setClientId(rs.getInt(3));
                transition.setxClientId(xclientId);
                transition.setTransitionDate(rs.getDate(4));
                unitsIds.add(unitId);
                clientTransitions.add(transition);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return clientTransitions;
    }
    
    public List<RestorationModel> getRestoredUnits(int clientId,
            List<Integer> restoredUnitsIds){
        String query = "SELECT UNIT_ID, RESTORATION_DATE"
                + " FROM RESTORATION_HISTORY_F WHERE CLIENT_ID = " + clientId;
        List<RestorationModel> restoredUnits = new ArrayList();
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        
        try {
            while (rs.next()) {
                RestorationModel restoredUnit = new RestorationModel();
                int unitId = rs.getInt(1);
                restoredUnit.setUnitId(unitId);
                restoredUnitsIds.add(unitId);
                restoredUnit.setRestorationDate(rs.getDate(2));
                restoredUnit.setClientId(clientId);
                restoredUnits.add(restoredUnit);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return restoredUnits;
    }
    
    public List<RestorationModel> getRestoredUnits(
            List<Integer> restoredUnitsIds){
        String query = "SELECT UNIT_ID, RESTORATION_DATE, CLIENT_ID"
                + " FROM RESTORATION_HISTORY_F ";
        List<RestorationModel> restoredUnits = new ArrayList();
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        
        try {
            while (rs.next()) {
                RestorationModel restoredUnit = new RestorationModel();
                int unitId = rs.getInt(1);
                restoredUnit.setUnitId(unitId);
                restoredUnitsIds.add(unitId);
                restoredUnit.setRestorationDate(rs.getDate(2));
                restoredUnit.setClientId(rs.getInt(3));
                restoredUnits.add(restoredUnit);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return restoredUnits;
    }
    
    public List<InstallmentModel> getLateInstallments(Date todaysDate){
        
        String unaidInstlmntsQuery = "SELECT INSTALLMENT_NAME, "
                + " INSTALLMENT_VALUE, CLIENT_ID, BUILDING_ID,"
                + " INSTALLMENT_DATE FROM UNITS_INSTALLMENTS1"
                + " WHERE INSTALLMENT_DATE <= ? AND INSTALLMENT_PAID_VALUE = 0";
        
        List<InstallmentModel> installmentList = new ArrayList();
        
        PreparedStatement pStmt;
        try {
            pStmt = con.prepareStatement(unaidInstlmntsQuery);
            pStmt.setDate(1, todaysDate);
            
            try (ResultSet installmentsRs = pStmt.executeQuery()) {
                while (installmentsRs.next()) {
                    InstallmentModel installment = new InstallmentModel();
                    installment.setPaymentName(installmentsRs.getString(1));
                    installment.setInstallmentValue(installmentsRs.getFloat(2));
                    installment.setClientId(installmentsRs.getInt(3));
                    installment.setBuildingId(installmentsRs.getInt(4));
                    installment.setInstallmentDate(installmentsRs.getDate(5));
                    installmentList.add(installment);
                }
                installmentsRs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyPaymentsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return installmentList;
    }
    
    public List<UnitModel> getBusyUnits(){
        String query = "SELECT ID, BUILDING_ID, CLIENT_ID, FLOOR_NUMBER,"
                + " MODEL_NAME, TYPE FROM UNITS_F"
                + " WHERE AVAILABILITY_STATUS = FALSE";
        
        List<UnitModel> units = new ArrayList();
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        
        try {
            while(rs.next()){
                UnitModel unit = new UnitModel();
                unit.setId(rs.getInt(1));
                unit.setBuildingId(rs.getInt(2));
                unit.setClientId(rs.getInt(3));
                unit.setFloorNumber(rs.getInt(4));
                unit.setModelName(rs.getString(5));
                if ("h".equals(rs.getString(6))) {
                    unit.setType('h');
                } else {
                    unit.setType('c');
                }
                units.add(unit);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return units;
    }
    
    public float getTotalPaidInstallments(int unitId, int clientId){
        float totalPaid=0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_PAID_VALUE) FROM "
                + "UNITS_INSTALLMENTS1 WHERE UNIT_ID = " + unitId 
                + "AND CLIENT_ID = " + clientId;
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
    
    public float getTotalPaidInstallments(int clientId, Date fromDate,
            Date toDate){
        
        float totalPaid = 0;
        String totalValueQuery = "SELECT SUM(INSTALLMENT_PAID_VALUE) FROM "
                + "UNITS_INSTALLMENTS1 JOIN INSTALLMENTS_RECEIPTS_IDS "
                + "ON CLIENT_ID = ? AND "
                + "UNITS_INSTALLMENTS1.ID = "
                + "INSTALLMENTS_RECEIPTS_IDS.INSTALLMENT_ID AND" 
                + "PAYMENT_DATE BETWEEN ? AND ? ";
        
        PreparedStatement pStmt;
        try {
            pStmt = con.prepareStatement(totalValueQuery);
            pStmt.setInt(1, clientId);
            pStmt.setDate(2, fromDate);
            pStmt.setDate(3, toDate);
            
            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    totalPaid = rs.getFloat(1);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyPaymentsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return totalPaid;
    }
    
    public List<Integer> getClientsPaidInPeriod(Date fromDate, Date toDate){
        
        String query = "SELECT CLIENT_ID FROM UNITS_INSTALLMENTS1"
                + " JOIN INSTALLMENTS_RECEIPTS_IDS ON"
                + " UNITS_INSTALLMENTS1.ID = "
                + " INSTALLMENTS_RECEIPTS_IDS.INSTALLMENT_ID AND"
                + " PAYMENT_DATE BETWEEN ? AND ? ";
        
        List<Integer> clientsPaidInPeriodIds = new ArrayList();
        
        PreparedStatement pStmt;
        try {
            pStmt = con.prepareStatement(query);
            pStmt.setDate(1, fromDate);
            pStmt.setDate(2, toDate);
            
            try (ResultSet installmentsRs = pStmt.executeQuery()) {
                while (installmentsRs.next()) {
                    int clientId = installmentsRs.getInt(1);
                    if(!clientsPaidInPeriodIds.contains(clientId)){
                        clientsPaidInPeriodIds.add(clientId);
                    }
                }
                installmentsRs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyPaymentsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return clientsPaidInPeriodIds;
    }
    
    public List<ReceiptModel> getCashReceipts(Map <Integer, InstallmentModel> 
            IdInstallmentMap){
        
        String gettingCashReceiptQuery = 
                  " SELECT INSTALLMENTS_RECEIPTS_IDS.ID,"
                + " INSTALLMENT_ID, INSTALLMENTS_RECEIPTS_IDS.RECEIPT_ID,"
                + " RECEIPT_DATE, RECEIPT_VALUE,"
                + " INSTALLMENT_NAME, UNITS_INSTALLMENTS1.ID, CLIENT_ID,"
                + " BUILDING_ID FROM INSTALLMENTS_RECEIPTS_IDS"
                + " JOIN UNITS_INSTALLMENTS1"
                + " ON UNITS_INSTALLMENTS1.ID ="
                + " INSTALLMENTS_RECEIPTS_IDS.INSTALLMENT_ID"
                + " AND INSTALLMENTS_RECEIPTS_IDS.IS_IT_CHECK = TRUE "
                + " ORDER BY CLIENT_ID";
       
        List<ReceiptModel> receipts = new ArrayList();
        
        ResultSet cashReceiptsRs = MainApp.dbUtility.
                databaseQuerying(con, gettingCashReceiptQuery);
        try {
            while (cashReceiptsRs.next()) {
                ReceiptModel receipt = new ReceiptModel();
                InstallmentModel installment = new InstallmentModel();
                int receiptId = cashReceiptsRs.getInt(1);
                receipt.setId(receiptId);
                receipt.setInstallmentId(cashReceiptsRs.getInt(2));
                receipt.setReceiptId(cashReceiptsRs.getString(3));
                receipt.setReceiptDate(cashReceiptsRs.getDate(4));
                receipt.setReceiptValue(cashReceiptsRs.getFloat(5));
                receipts.add(receipt);
                installment.setPaymentName(cashReceiptsRs.getString(6));
                installment.setId(cashReceiptsRs.getInt(7));
                installment.setClientId(cashReceiptsRs.getInt(8));
                installment.setBuildingId(cashReceiptsRs.getInt(9));
                IdInstallmentMap.put(receiptId, installment);
            }
            cashReceiptsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientUnitsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return receipts;
    }
    
    public List<UnitModel> getClientUnits(int clientId){
        
        List<UnitModel> units = new ArrayList();
        
        String clientUnitsQuery = "SELECT ID, BUILDING_ID, CONTRACT_DATE,"
                + " RECEPTION_DATE, UNIT_CODE, DISCOUNT_RATIO FROM UNITS_F "
                + " WHERE CLIENT_ID = " + clientId;
        
        ResultSet unitsRs = MainApp.dbUtility.
                databaseQuerying(con, clientUnitsQuery);
        
        try {
            while(unitsRs.next()){
                UnitModel unit = new UnitModel();
                unit.setId(unitsRs.getInt(1));
                unit.setBuildingId(unitsRs.getInt(2));
                unit.setContructDate(unitsRs.getDate(3));
                unit.setReceptionDate(unitsRs.getDate(4));
                unit.setUnitCode(unitsRs.getInt(5));
                unit.setDiscountRatio(unitsRs.getFloat(6));
                units.add(unit);
            }
            unitsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return units;
    }
    
}
