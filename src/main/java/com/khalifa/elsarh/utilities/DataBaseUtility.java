/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khalifa
 */
public class DataBaseUtility {

    private final static String INSTALLMENT_NAMES_TABLE_STR = 
              "CREATE TABLE INSTALLMENT_NAMES_F("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "NAME VARCHAR(30),"
            + "TYPE VARCHAR(20))";// essential, not_essential, from_unit_price, restored

    private final static String BUILDINGS_TABLE_STR = 
              "CREATE TABLE BUILDINGS_F("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "NAME VARCHAR(30) UNIQUE,"
            + "STREET VARCHAR(30),"
            + "AREA VARCHAR(30),"
            + "COUNTRY VARCHAR(30),"
            + "FLOORS INTEGER,"
            + "MEDIA_CHECK BOOLEAN)";

    private final static String CLIENTS_TABLE_STR = 
            
              "CREATE TABLE CLIENTS_F("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "NAME VARCHAR(50),"
            + "CAREER VARCHAR(30),"
            + "PERSONAL_ID VARCHAR(30),"
            + "MOBILE_1 VARCHAR(30),"
            + "MOBILE_2 VARCHAR(30),"
            + "ADDRESS VARCHAR(300),"
            + "EMAIL VARCHAR(100),"
            + "MEDIA_CHECK BOOLEAN)";

    private final static String CLIENTS_AGENTS_TABLE_STR = 
            
              "CREATE TABLE CLIENTS_AGENTS("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "NAME VARCHAR(50),"
            + "CAREER VARCHAR(30),"
            + "PERSONAL_ID VARCHAR(30),"
            + "MOBILE_NUMBER VARCHAR(30),"
            + "MOBILE_NUMBER_2 VARCHAR(30),"
            + "ADDRESS VARCHAR(300),"
            + "EMAIL VARCHAR(100),"
            + "CLIENT_ID INTEGER)";
    
    private final static String MEDIA_TABLE_STR = 
              "CREATE TABLE MEDIA_F("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "PIC_PATH VARCHAR(4000),"
            + "TYPE CHAR,"
            + "BUILDING_ID INTEGER,"
            + "CLIENT_ID INTEGER)";

    private final static String MODELS_TABLE_STR = 
              "CREATE TABLE MODELS_F("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "NAME VARCHAR(30),"
            + "PRICE REAL,"
            + "AREA INTEGER,"
            + "BUILDING_ID INTEGER)";

    private final static String UNITS_TABLE_STR =
        // floor number in the case of commercial unit is the area .
        // model name in the case of commercial unit is the unit name .
              "CREATE TABLE UNITS_F("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "FLOOR_NUMBER INTEGER,"
            + "UNIT_CODE INTEGER,"
            + "TYPE CHAR," // h housing, c commercial.
            + "AVAILABILITY_STATUS BOOLEAN,"
            + "IS_PAYMENT_INITIATED BOOLEAN NOT NULL WITH DEFAULT FALSE,"
            + "CLIENT_ID INTEGER," 
            + "BUILDING_ID INTEGER,"
            + "CONTRACT_DATE DATE,"
            + "RECEPTION_DATE DATE,"
            + "DISCOUNT_RATIO REAL NOT NULL WITH DEFAULT 0," 
            + "SPECIAL_PRICE REAL NOT NULL WITH DEFAULT 0,"
            + "MODEL_NAME VARCHAR(20))"; 
    
    private final static String TRANSITIONS_TABLE_STR =
              "CREATE TABLE TRANSITIONS_F("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "UNIT_ID INTEGER,"
            + "BUILDING_ID INTEGER,"
            + "XCLIENT_ID INTEGER," 
            + "CLIENT_ID INTEGER,"// the new client
            + "TRANSITION_DATE DATE)";
    
    private final static String UNITS_INSTALLMENTS_TABLE_STR =
              "CREATE TABLE UNITS_INSTALLMENTS1("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "INSTALLMENT_NAME VARCHAR(30),"
            + "INSTALLMENT_VALUE REAL,"
            + "INSTALLMENT_PAID_VALUE REAL NOT NULL WITH DEFAULT 0,"
            + "INSTALLMENT_DATE DATE,"
            + "PAYMENT_DATE DATE,"
            + "TYPE VARCHAR(20)," // essential, not_essential, from_unit_price
            + "FINISHED BOOLEAN," // new
            + "UNIT_ID INTEGER,"
            + "BUILDING_ID INTEGER,"
            + "CLIENT_ID INTEGER)";
    
    private final static String INSTALLMENTS_RECEIPTS_IDS_STR = 
              "CREATE TABLE INSTALLMENTS_RECEIPTS_IDS("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "INSTALLMENT_ID INTEGER,"
            + "RECEIPT_ID VARCHAR(150),"
            + "CHECK_ID VARCHAR(150),"
            + "CHECK_BANK VARCHAR(50),"
            + "IS_IT_CHECK BOOLEAN,"
            + "RECEIPT_VALUE REAL,"
            + "RECEIPT_DATE DATE,"
            + "BANK_UP_DATE DATE)";
    
    private final static String RESTORED_UNITS_TABLE_STR =
              "CREATE TABLE RESTORATION_HISTORY_F("
            + "ID INTEGER NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1),"
            + "RESTORATION_DATE DATE,"
            + "CONTRACT_DATE DATE,"
            + "RECEPTION_DATE DATE,"
            + "UNIT_ID INTEGER,"
            + "CLIENT_ID INTEGER)";
    
    public Connection derbyDBConnection(String strUrl) {
        Connection con = null;

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(strUrl);
            
        } catch (ClassNotFoundException | SQLException
                | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DataBaseUtility.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public boolean createDatabase(Connection dbConnection) {
        
        boolean bCreatedTables = false;
        Statement statement ;
        try {
          
            statement = dbConnection.createStatement();
        
            if (!checkExistance(dbConnection, "MEDIA_F")) {
                statement.execute(MEDIA_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "CLIENTS_F")) {
                statement.execute(CLIENTS_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "CLIENTS_AGENTS")) {
                statement.execute(CLIENTS_AGENTS_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "MODELS_F")) {
                statement.execute(MODELS_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "UNITS_F")) {
                statement.execute(UNITS_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "INSTALLMENT_NAMES_F")) {
                statement.execute(INSTALLMENT_NAMES_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "BUILDINGS_F")) {
                statement.execute(BUILDINGS_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "TRANSITIONS_F")) {
                statement.execute(TRANSITIONS_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "UNITS_INSTALLMENTS1")) {
                statement.execute(UNITS_INSTALLMENTS_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "RESTORATION_HISTORY_F")) {
                statement.execute(RESTORED_UNITS_TABLE_STR);
            }
            if (!checkExistance(dbConnection, "INSTALLMENTS_RECEIPTS_IDS")) {
                statement.execute(INSTALLMENTS_RECEIPTS_IDS_STR);
            }
            bCreatedTables = true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseUtility.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return bCreatedTables;
    }
    private boolean checkExistance(Connection dbConnection, String name){
        
        boolean check = false;
        try {
            ResultSet rs = dbConnection.getMetaData()
                    .getTables(null, "APP", name, null);
            while(rs.next()){
                check = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseUtility.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return check;
    }

    public ResultSet databaseQuerying(Connection con, String query) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preStmt = con.prepareStatement(query);
            resultSet = preStmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseUtility.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public int databaseQueryingThatDiffer(Connection con, String query) {

        int resultSet = 0;
        try {
            Statement stmt = con.createStatement();
            resultSet = stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseUtility.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
}
