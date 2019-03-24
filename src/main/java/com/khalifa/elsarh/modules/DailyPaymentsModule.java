/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.modules;

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
import com.khalifa.elsarh.pojos.InstallmentModel;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khalifa
 */
public class DailyPaymentsModule {
    
    public List<InstallmentModel> getInstallments(Date todaysDate){
        
        String unaidInstlmntsQuery = "SELECT INSTALLMENT_NAME, "
                + " INSTALLMENT_VALUE, IS_IT_CHECK, CLIENT_ID, BUILDING_ID,"
                + " UNIT_ID, INSTALLMENT_DATE FROM UNITS_INSTALLMENTS1"
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
                    installment.setIsItCheck(installmentsRs.getBoolean(3));
                    installment.setClientId(installmentsRs.getInt(4));
                    installment.setBuildingId(installmentsRs.getInt(5));
                    installment.setUnitId(installmentsRs.getInt(6));
                    installment.setInstallmentDate(installmentsRs.getDate(7));
                    installmentList.add(installment);
                }installmentsRs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyPaymentsModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return installmentList;
    }
    
    public String getClientName(int clientId){
        String clientNameQuery = "SELECT NAME FROM CLIENTS_F"
                + " WHERE ID = " + clientId,
                clientName = ""; 
        
        ResultSet clientNameRs = MainApp.dbUtility.
                databaseQuerying(con, clientNameQuery);
        try {
            while (clientNameRs.next()) {
                clientName = clientNameRs.getString(1);
            }
            clientNameRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyPaymentsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return clientName;
    }
    
    public String getClientPhone(int clientId){
        String clientNameQuery = "SELECT MOBILE_1 FROM CLIENTS_F"
                + " WHERE ID = " + clientId; 
        
        String phoneNumber = "";
        
        ResultSet clientNameRs = MainApp.dbUtility.
                databaseQuerying(con, clientNameQuery);
        try {
            while (clientNameRs.next()) {
                phoneNumber = clientNameRs.getString(1);
            }
            clientNameRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyPaymentsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return phoneNumber;
    }
    
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
            Logger.getLogger(DailyPaymentsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return buildingName;
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
            Logger.getLogger(DailyPaymentsModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return unitCode;
    }
    
}
