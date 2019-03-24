/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.modules;

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
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
public class UnitBookingModule {
    
    public int getBuildingId(String buildingName){
        
        int buildingId = 0;
        String gettingBuildingIdQuery
                = "SELECT ID FROM BUILDINGS_F WHERE"
                + " NAME = '" + buildingName + "'";
        ResultSet buildingRs = MainApp.dbUtility.
                databaseQuerying(con, gettingBuildingIdQuery);

        try {
            while (buildingRs.next()) {
                buildingId = buildingRs.getInt("ID");
            }
            buildingRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return buildingId;
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
    
    public List<String> getAvailableUnits(int buildingId){
        List<String> availableUnitsCode = new ArrayList();
        String unitsCodeQuery = "SELECT UNIT_CODE FROM UNITS_F"
                + " WHERE BUILDING_ID = "+ buildingId 
                + " AND AVAILABILITY_STATUS = TRUE ORDER BY UNIT_CODE";
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, unitsCodeQuery);
        
         try {
            while (rs.next()) {
                availableUnitsCode.add(""+ rs.getInt(1));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return availableUnitsCode;
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
   
    public void makeItBusy(int unitCode, int buildingId, int clientId, 
            Date contractDate){
        
        String updatingAvailabliltyQuery = "UPDATE UNITS_F"
                + " SET CLIENT_ID = ?, AVAILABILITY_STATUS = ?,"
                + " CONTRACT_DATE = ?"
                + " WHERE UNIT_CODE = ? AND BUILDING_ID = ?";
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(updatingAvailabliltyQuery);
            
            preparedStmt.setInt(1, clientId);
            preparedStmt.setBoolean(2, false);
            preparedStmt.setDate(3, contractDate);
            preparedStmt.setInt(4, unitCode);
            preparedStmt.setInt(5, buildingId);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(UnitBookingModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public String getClientName(int clientId){
        String clientNameQuery = "SELECT NAME FROM CLIENTS_F "
                + "WHERE ID = "+clientId 
                ,clientName = "";
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, clientNameQuery);
        
        try {
            while (rs.next()) {
                clientName = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return clientName;
    }
    
    public String getClientPhone(int clientId){
        String clientNameQuery = "SELECT MOBILE_1 FROM CLIENTS_F "
                + "WHERE ID = "+clientId;
        
        String mobileNumber = "";
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, clientNameQuery);
        
        try {
            while (rs.next()) {
                mobileNumber = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UnitBookingModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return mobileNumber;
    }
    
}
