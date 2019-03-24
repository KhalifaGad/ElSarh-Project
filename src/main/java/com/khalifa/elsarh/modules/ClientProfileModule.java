/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.modules;

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
import com.khalifa.elsarh.pojos.ClientAgentModel;
import com.khalifa.elsarh.pojos.ClientModel;
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
public class ClientProfileModule {
    
    public void updateEmail(int clientId, String email) {
        String altringClient = "UPDATE CLIENTS_F SET"
                + " EMAIL = '" + email+ "' WHERE ID = " + clientId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, altringClient);
    }
    
    public void updateAddress(int clientId, String address) {
        String altringClient = "UPDATE CLIENTS_F SET"
                + " ADDRESS = '" + address+ "' WHERE ID = " + clientId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, altringClient);
    }
    
    public void updateMobile1(int clientId, String mobile1) {
        String altringClient = "UPDATE CLIENTS_F SET "
                + "MOBILE_1 = '"+mobile1+"' WHERE ID = " + clientId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, altringClient);
    }
    
    public void updateMobile2(int clientId, String mobile2) {
        String altringClient = "UPDATE CLIENTS_F SET "
                + "MOBILE_2 = '" + mobile2 + "' WHERE ID = " + clientId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, altringClient);
    }
    
    public void updateCareer(int clientId,String career) {
        String altringClient = "UPDATE CLIENTS_F SET "
                + "CAREER = '" + career + "' WHERE ID = " + clientId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, altringClient);
    }
    
    public void updatePersonalId(int clientId, String personalId) {
        String altringClient = "UPDATE CLIENTS_F SET "
                + "PERSONAL_ID = '" + personalId + "' WHERE ID = " + clientId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, altringClient);
    }
    
    
    public void clientHasMedia(int clientId) {
        String altringClient = "UPDATE CLIENTS_F SET "
                + "MEDIA_CHECK = TRUE WHERE ID = " + clientId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, altringClient);
    }
    
    public ClientModel getClientInfo(int clientId) {
        
        ClientModel client = new ClientModel();
        String getProjectInfoQuery
                = "SELECT NAME, PERSONAL_ID, MOBILE_1, MOBILE_2, CAREER,"
                + "MEDIA_CHECK, ADDRESS, EMAIL "
                + "FROM CLIENTS_F WHERE ID = " + clientId;
        ResultSet clientInfoRs = MainApp.dbUtility.
                databaseQuerying(con, getProjectInfoQuery);

        try {
            while (clientInfoRs.next()) {
                client.setName(clientInfoRs.getString("NAME"));
                client.setPersonalId(clientInfoRs.getString("PERSONAL_ID"));
                client.setMobile1(clientInfoRs.getString("MOBILE_1"));
                client.setMobile2(clientInfoRs.getString("MOBILE_2"));
                client.setCareer(clientInfoRs.getString("CAREER"));
                client.setAddress(clientInfoRs.getString("ADDRESS"));
                client.setEmail(clientInfoRs.getString("EMAIL"));
                client.setMediaCheck(clientInfoRs.getBoolean("MEDIA_CHECK"));
            }
            clientInfoRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return client;
    }
    
    public void seveImages(List<String> imgsPaths, int clientId){
        for(String imgPath : imgsPaths){
            
            String savingImgPathQry
                    = "INSERT INTO MEDIA_F(PIC_PATH,"
                    + "TYPE,"
                    + "CLIENT_ID)"
                    + "VALUES('" + imgPath + "',"
                    + "'c'," // b for building
                    + clientId + ")";

            MainApp.dbUtility.databaseQueryingThatDiffer(con, savingImgPathQry);
        }
    }
    
    public List<String> getImages(int clientId){
        
        List<String> imgsPaths = new ArrayList();
        
        String gettingImagesQuery = "SELECT PIC_PATH FROM MEDIA_F "
                + "WHERE CLIENT_ID = " + clientId + " AND TYPE = 'c'";
        
        ResultSet imagePathRs = MainApp.dbUtility.
                databaseQuerying(con, gettingImagesQuery);
        try {
            while (imagePathRs.next()) {
                imgsPaths.add(imagePathRs.getString("PIC_PATH"));
            }
            imagePathRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return imgsPaths;
    }
    
    public List<Integer> getClientUnits(int clientId){
        
        List<Integer> unitsIds = new ArrayList();
        
        String clientUnitsQuery = "SELECT ID FROM UNITS_F "
                + "WHERE CLIENT_ID = "+clientId;
        
        ResultSet clientUnitsRs = MainApp.dbUtility.
                databaseQuerying(con, clientUnitsQuery);
        
        try {
            while(clientUnitsRs.next()){
                unitsIds.add(clientUnitsRs.getInt(1));
            }
            clientUnitsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return unitsIds;
    }
    
    public List<Integer> getClientRestoredUnits(int clientId){
        
        List<Integer> unitsIds = new ArrayList();
        
        String clientUnitsQuery = "SELECT UNIT_ID FROM RESTORATION_HISTORY_F"
                + " WHERE CLIENT_ID = " + clientId;
        
        ResultSet clientUnitsRs = MainApp.dbUtility.
                databaseQuerying(con, clientUnitsQuery);
        
        try {
            while(clientUnitsRs.next()){
                unitsIds.add(clientUnitsRs.getInt(1));
            }
            clientUnitsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return unitsIds;
    }
    
    public List<Integer> getClientTransitionedUnits(int clientId){
        
        List<Integer> unitsIds = new ArrayList();
        
        String clientUnitsQuery = "SELECT UNIT_ID FROM TRANSITIONS_F"
                + " WHERE CLIENT_ID = " + clientId;
        
        ResultSet clientUnitsRs = MainApp.dbUtility.
                databaseQuerying(con, clientUnitsQuery);
        
        try {
            while(clientUnitsRs.next()){
                unitsIds.add(clientUnitsRs.getInt(1));
            }
            clientUnitsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return unitsIds;
    }
    
    public void deleteImage(int clientId, String imgPath){
        
        String deleteImgQuery = "DELETE FROM MEDIA_F"
                + " WHERE CLIENT_ID = " + clientId 
                + " AND PIC_PATH = '" + imgPath + "'" ;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, deleteImgQuery);
    }
    
    public void updateMediaCheck(int clientId){
        String updatingQuery = "UPDATE CLIENTS_F SET MEDIA_CHECK = FALSE "
                + "WHERE ID = "+ clientId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, updatingQuery);
    }
    
    public boolean checkAgent(int clientId){
        String checkingQuery = "SELECT ID FROM CLIENTS_AGENTS"
                + " WHERE CLIENT_ID = " + clientId;
        boolean checking = false ;
        
        ResultSet checkAgentRs = MainApp.dbUtility.
                databaseQuerying(con, checkingQuery);
        
        try {
            while(checkAgentRs.next()){
                checking = true;
            }
            checkAgentRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        return checking;
    }
    
    public ClientAgentModel getAgentData(int clientId){
        String getAgentQuery = "SELECT NAME, CAREER, PERSONAL_ID,"
                + " MOBILE_NUMBER, MOBILE_NUMBER_2, ADDRESS, EMAIL"
                + " FROM CLIENTS_AGENTS WHERE CLIENT_ID = " + clientId;
        
        ClientAgentModel agent = new ClientAgentModel();
        
        ResultSet getAgentRs = MainApp.dbUtility.
                databaseQuerying(con, getAgentQuery);
        
        try {
            while(getAgentRs.next()){
                agent.setName(getAgentRs.getString(1));
                agent.setCareer(getAgentRs.getString(2));
                agent.setPersonalId(getAgentRs.getString(3));
                agent.setMobile1(getAgentRs.getString(4));
                agent.setMobile2(getAgentRs.getString(5));
                agent.setAddress(getAgentRs.getString(6));
                agent.setEmail(getAgentRs.getString(7));
            }
            getAgentRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        
        return agent;
    }
    
    public void addAgentData(int clientId, String name, String career,
            String personalId, String mobile1, String mobile2, String address,
            String email){
        
        String addAgentQuery = "INSERT INTO CLIENTS_AGENTS ("
                + "NAME,"
                + "CAREER,"
                + "PERSONAL_ID,"
                + "MOBILE_NUMBER,"
                + "MOBILE_NUMBER_2,"
                + "ADDRESS,"
                + "EMAIL,"
                + "CLIENT_ID) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        
       
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(addAgentQuery);

            preparedStmt.setString(1, name);
            preparedStmt.setString(2, career);
            preparedStmt.setString(3, personalId);
            preparedStmt.setString(4, mobile1);
            preparedStmt.setString(5, mobile2);
            preparedStmt.setString(6, address);
            preparedStmt.setString(7, email);
            preparedStmt.setInt(8, clientId);

            preparedStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAgentData(int clientId, String name, String career,
            String personalId, String mobile1, String mobile2, String address,
            String email){
        
        String addAgentQuery = "UPDATE CLIENTS_AGENTS SET "
                + "NAME = ?,"
                + "CAREER = ?,"
                + "PERSONAL_ID = ?,"
                + "MOBILE_NUMBER = ?,"
                + "MOBILE_NUMBER_2 = ?,"
                + "ADDRESS = ?,"
                + "EMAIL = ? "
                + "WHERE CLIENT_ID = ?";
        
       
        try {
            PreparedStatement preparedStmt
                    = con.prepareStatement(addAgentQuery);

            preparedStmt.setString(1, name);
            preparedStmt.setString(2, career);
            preparedStmt.setString(3, personalId);
            preparedStmt.setString(4, mobile1);
            preparedStmt.setString(5, mobile2);
            preparedStmt.setString(6, address);
            preparedStmt.setString(7, email);
            preparedStmt.setInt(8, clientId);

            preparedStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClientProfileModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteClient(int clientId){
        String deletionQuery = "DELETE FROM CLIENTS_F"
                + " WHERE ID = " + clientId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, deletionQuery);
        deleteAgent(clientId);
        setUnitFree(clientId);
        deleteFinInfo(clientId);
    }
    
    void deleteAgent(int clientId){
        String deletionQuery = "DELETE FROM CLIENTS_AGENTS"
                + " WHERE CLIENT_ID = " + clientId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, deletionQuery);
    }
    
    void setUnitFree(int clientId){
        String freeTheUnit = "UPDATE UNITS_F SET CLIENT_ID = 0," 
                + " AVAILABILITY_STATUS = TRUE WHERE CLIENT_ID = " + clientId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, freeTheUnit);
    }
    
    void deleteFinInfo(int clientId){
        
        String deletionQuery = "DELETE FROM UNITS_INSTALLMENTS1"
                + " WHERE CLIENT_ID = " + clientId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, deletionQuery);
    }
    
}
