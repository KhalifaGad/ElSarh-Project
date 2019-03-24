/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.modules;

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
import com.khalifa.elsarh.pojos.ModelTemplate;
import com.khalifa.elsarh.pojos.UnitModel;
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
public class ProjectProfileModule {
    
    public String getBuildingStreet(int buildingId){
        String getBuildingLocationQuery
                = "SELECT STREET FROM BUILDINGS_F "
                + "WHERE ID = " + buildingId +"",
                street="";
        ResultSet locationRs = MainApp.dbUtility.
                databaseQuerying(con, getBuildingLocationQuery);
        
        try {
            while (locationRs.next()) {
                street = locationRs.getString(1);
            }
            locationRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return street;
    }
    
    public String getBuildingArea(int buildingId){
        String getBuildingLocationQuery
                = "SELECT AREA FROM BUILDINGS_F "
                + "WHERE ID = " + buildingId ,
                area = "";
        ResultSet locationRs = MainApp.dbUtility.
                databaseQuerying(con, getBuildingLocationQuery);
        
        try {
            while (locationRs.next()) {
                area = locationRs.getString(1);
            }
            locationRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return area;
    }
    
    public String getBuildingCountry(int buildingId){
        String getBuildingLocationQuery
                = "SELECT COUNTRY FROM BUILDINGS_F "
                + "WHERE ID = " + buildingId ,
                country = "";
        ResultSet locationRs = MainApp.dbUtility.
                databaseQuerying(con, getBuildingLocationQuery);
        
        try {
            while (locationRs.next()) {
                country = locationRs.getString(1);
            }
            locationRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return country;
    }
    
    public int getBuildingId(String buildingName){
        
        int buidlingId = 0; 
        String getBuildingIdQuery= "SELECT ID FROM BUILDINGS_F "
                + "WHERE NAME = '" + buildingName +"'";
        ResultSet idRs = MainApp.dbUtility.
                databaseQuerying(con, getBuildingIdQuery);
        try {
            while (idRs.next()) {
                buidlingId = idRs.getInt(1);
            }
            idRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return buidlingId;
    }
    
    public int getBuildingFloorsCount(int buildingId){
        int floorsCount = 0;
        String getfloorsCountQuery = "SELECT FLOORS FROM BUILDINGS_F "
                + "WHERE ID = " + buildingId;
        ResultSet floorsRs = MainApp.dbUtility.
                databaseQuerying(con, getfloorsCountQuery);
        try {
            while(floorsRs.next()){
                floorsCount = floorsRs.getInt(1);
            }
            floorsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return floorsCount;
    }
    
    public List<ModelTemplate> getBuildingModels(int buildingId){
        String getModelsQuery = "SELECT ID, NAME, AREA, PRICE FROM MODELS_F "
                + "WHERE BUILDING_ID = "+ buildingId;
        List<ModelTemplate> models = new ArrayList();
        
        ResultSet modelsInfoRs = MainApp.dbUtility.
                databaseQuerying(con, getModelsQuery);
        
        try {
            while(modelsInfoRs.next()){
                ModelTemplate modelTemplateObj = new ModelTemplate();
                modelTemplateObj.setId(modelsInfoRs.getInt("ID"));
                modelTemplateObj.setName(modelsInfoRs.getString("NAME" ));
                modelTemplateObj.setArea(modelsInfoRs.getInt("AREA" ));
                modelTemplateObj.setPrice(modelsInfoRs.getFloat("PRICE"));
                models.add(modelTemplateObj);
            }
            modelsInfoRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return models;
    }
    
    public int getBuildingModelsCount(int buildingId){
        String getModelsQuery = "SELECT ID FROM MODELS_F "
                + "WHERE BUILDING_ID = "+ buildingId;
        int modelsCount = 0;
        
        ResultSet modelsInfoRs = MainApp.dbUtility.
                databaseQuerying(con, getModelsQuery);
        
        try {
            while(modelsInfoRs.next()){
                modelsCount ++;
            }
            modelsInfoRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return modelsCount;
    }
    
    public boolean checkModelUnits(int buildingId, String modelName, int from,
            int to){
        
        for (int i = from; i <= to; i++) {
            String checkingQuery = "SELECT AVAILABILITY_STATUS FROM UNITS_F"
                    + " WHERE BUILDING_ID = " + buildingId
                    + " AND FLOOR_NUMBER = " + i
                    + " AND MODEL_NAME = '" + modelName + "'";

            ResultSet checkingRs = MainApp.dbUtility.
                    databaseQuerying(con, checkingQuery);

            try {
                while (checkingRs.next()) {
                    if (checkingRs.getBoolean(1) == false) {
                        return false;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProjectProfileModule.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
    
    public boolean whatAboutMedia(int buildingId){
        String mediaCheckQuery = "SELECT MEDIA_CHECK FROM BUILDINGS_F WHERE"
                + " ID = "+ buildingId;
        boolean mediaCheck = false;
        ResultSet mediaCheckRs = MainApp.dbUtility.
                databaseQuerying(con, mediaCheckQuery);
        try {
            while(mediaCheckRs.next()){
                mediaCheck = mediaCheckRs.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return mediaCheck;
    }
    
    public void buildingHasMedia(int buildingId) {
        String altringBuilding = "UPDATE BUILDINGS_F SET "
                + "MEDIA_CHECK = TRUE WHERE ID = " + buildingId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, altringBuilding);
    }
    
    public void seveImages(List<String> imgsPaths, int buildingId){
        for(String imgPath : imgsPaths){
            
            String savingImgPathQry
                    = "INSERT INTO MEDIA_F(PIC_PATH,"
                    + "TYPE,"
                    + "BUILDING_ID)"
                    + "VALUES('" + imgPath + "',"
                    + "'b',"
                    + buildingId + ")";

            MainApp.dbUtility.databaseQueryingThatDiffer(con, savingImgPathQry);
        }
    }
    
    public String getUnitOwner(int clientId){
       
        String getUnitOwnerQuery = "SELECT NAME FROM CLIENTS_F WHERE"
                + " ID = "+clientId;
        ResultSet unitOwnerRs = MainApp.dbUtility.
                databaseQuerying(con, getUnitOwnerQuery);
        String name= "";
        try {
            while(unitOwnerRs.next()){
                name = unitOwnerRs.getString("NAME");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return name;
    }
    
    public List<String> getImgsPaths(int id){
        List imgsPathes = new ArrayList() ;
        String imagesQuerying = "SELECT PIC_PATH FROM MEDIA_F "
                + "WHERE BUILDING_ID = "+ id +" AND TYPE = 'b'";
        ResultSet imagePathesRs = MainApp.dbUtility.
                databaseQuerying(con, imagesQuerying);
        try {
            while(imagePathesRs.next()){
                String imgPath = imagePathesRs.getString("PIC_PATH");
                imgsPathes.add(imgPath);
            }
            imagePathesRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return imgsPathes;
    }
    
    public void itsSpecial(int unitId, float specialPrice) {
        String addPriceQuery
                = "UPDATE UNITS_F SET SPECIAL_PRICE = "
                + specialPrice + " WHERE ID = " + unitId;
        MainApp.dbUtility.databaseQueryingThatDiffer(con, addPriceQuery);
    }

    public List<UnitModel> getCommUnits(int buildingId){
        String getCommUnitsQuery = "SELECT AVAILABILITY_STATUS, FLOOR_NUMBER,"
                + " MODEL_NAME, UNIT_CODE,SPECIAL_PRICE, CLIENT_ID"
                + " FROM UNITS_F WHERE BUILDING_ID = "+buildingId
                + " AND TYPE = 'c'";
        boolean availabilityStatus = true;
        List<UnitModel> commUnits = new ArrayList();
        ResultSet commUnitsRs = MainApp.dbUtility.
                databaseQuerying(con, getCommUnitsQuery);
        try {
            while(commUnitsRs.next()){
                UnitModel unit =
                        new UnitModel();
                unit.setModelName(commUnitsRs.getString("MODEL_NAME"));
                availabilityStatus = commUnitsRs.
                        getBoolean("AVAILABILITY_STATUS");
                unit.setAvailabilityStatus(availabilityStatus);
                if(!availabilityStatus){
                    unit.setClientId(commUnitsRs.getInt("CLIENT_ID"));
                }
                unit.setUnitCode(commUnitsRs.getInt("UNIT_CODE"));
                unit.setFloorNumber(commUnitsRs.getInt("FLOOR_NUMBER"));
                unit.setSpecialPrice(commUnitsRs.getFloat("SPECIAL_PRICE"));
                unit.setBuildingId(buildingId);
                commUnits.add(unit);
            }
            commUnitsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return commUnits;
    }
    
    public List<UnitModel> getHousingUnits(int buildingId){
        
        String unitsQuerying = "SELECT ID, FLOOR_NUMBER, MODEL_NAME, "
                + "AVAILABILITY_STATUS, CLIENT_ID, UNIT_CODE, SPECIAL_PRICE "
                + "FROM UNITS_F WHERE BUILDING_ID = "+buildingId
                +" AND TYPE = 'h' ORDER BY UNIT_CODE ";
        boolean availabilityStatus = true;
        List<UnitModel> housingUnits = new ArrayList();
        ResultSet unitsRs = MainApp.dbUtility.
                databaseQuerying(con, unitsQuerying);
        try {
            while(unitsRs.next()){
                UnitModel unit = new UnitModel();
                unit.setId(unitsRs.getInt("ID"));
                unit.setModelName(unitsRs.getString("MODEL_NAME"));
                availabilityStatus = unitsRs.getBoolean("AVAILABILITY_STATUS");
                unit.setAvailabilityStatus(availabilityStatus);
                if(!availabilityStatus){
                    unit.setClientId(unitsRs.getInt("CLIENT_ID"));
                }
                unit.setFloorNumber(unitsRs.getInt("FLOOR_NUMBER"));
                unit.setUnitCode(unitsRs.getInt("UNIT_CODE"));
                unit.setSpecialPrice(unitsRs.getFloat("SPECIAL_PRICE"));
                housingUnits.add(unit);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return housingUnits;
    }
    
    public void editCommUnit(int buildingId, int unitCode, String unitName,
            int unitArea, float unitPrice){
        
        String editCommUnitQuery = "UPDATE UNITS_F SET"
                + " MODEL_NAME = '"+unitName+"'"
                + " ,FLOOR_NUMBER = "+unitArea
                + " ,SPECIAL_PRICE = "+unitPrice
                + " WHERE BUILDING_ID = "+ buildingId 
                + " AND UNIT_CODE = "+unitCode;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, editCommUnitQuery);
    }
    
    public void addCommUnit(int buildingId, int unitArea, float unitPrice,
            String unitName){
        int i = getCommUnitsCount(buildingId);
        int multiplyFactor = 100 ;
        if (buildingId < 100){ // to make sure that the commerial unit code doesn't match any housing model code 
            multiplyFactor = 1000;
        }
        int unitCode = buildingId * multiplyFactor  + i + 1;
        
        String addCommUnit = "INSERT INTO UNITS_F("
                + " FLOOR_NUMBER,"
                + " UNIT_CODE,"
                + " TYPE,"
                + " AVAILABILITY_STATUS,"
                + " BUILDING_ID,"
                + " SPECIAL_PRICE,"
                + " MODEL_NAME)"
                + " VALUES("
                + unitArea + ","
                + unitCode + ","
                + "'c',"
                + true + ","
                + buildingId + ","
                + unitPrice + ","
                + "'" + unitName + "')";
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, addCommUnit);
    }
    
    public int getCommUnitsCount(int buildingId){
        String getCommUnitsQuery = "SELECT ID FROM UNITS_F"
                + " WHERE BUILDING_ID = "+buildingId + " AND TYPE = 'c'";
        int i = 0;
        ResultSet commUnitsRs = MainApp.dbUtility.
                databaseQuerying(con, getCommUnitsQuery);
        try {
            while(commUnitsRs.next()){
                i++;
            }
            commUnitsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public int getHousingUnitsCount(int buildingId){
        String getHousingUnitsQuery = "SELECT ID FROM UNITS_F"
                + " WHERE BUILDING_ID = "+buildingId + " AND TYPE = 'h'";
        int counter = 0;
        ResultSet housingUnitsRs = MainApp.dbUtility.
                databaseQuerying(con, getHousingUnitsQuery);
        try {
            while(housingUnitsRs.next()){
                counter++;
            }
            housingUnitsRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return counter;
    }
    
    public void deleteModel(int buildingId, String modelName, int from, int to){
        
        deleteUnits(buildingId, modelName, from, to);
      
        if (!checkUnitsModelExistance(buildingId, modelName)) {
            String deleteModelQuery = "DELETE FROM MODELS_F"
                    + " WHERE BUILDING_ID = " + buildingId
                    + " AND NAME = '" + modelName + "'";

            MainApp.dbUtility.databaseQueryingThatDiffer(con, deleteModelQuery);
        }
    }
    // true if exist 
    boolean checkUnitsModelExistance(int buildingId, String modelName){
        
        String checkModelExistanceQuery = "SELECT ID FROM UNITS_F"
                + " WHERE BUILDING_ID = "+buildingId
                + " AND MODEL_NAME = '"+modelName+"'";
        ResultSet checkingRs = MainApp.dbUtility.
                databaseQuerying(con, checkModelExistanceQuery);
        try {
            while(checkingRs.next()){
                checkingRs.close();
                return true;
            }
            checkingRs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean isCommUnitAvailable(int unitCode, int buildingId){
        String unitAvailablityQuery = "SELECT AVAILABILITY_STATUS FROM UNITS_F"
                + " WHERE BUILDING_ID = "+buildingId
                + " AND UNIT_CODE = "+unitCode;
        
        boolean availablity = false;
        
        ResultSet checkingRS = MainApp.dbUtility.
                databaseQuerying(con, unitAvailablityQuery);
        
         try {
            while(checkingRS.next()){
                availablity = checkingRS.getBoolean(1);
            }
            checkingRS.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectProfileModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return availablity; // false in case of not exist or not available.
    } 
    
    public void deleteCommUnit(int buildingId, int unitCode){
        String deleteUnitsQuery = "DELETE FROM UNITS_F"
                    + " WHERE BUILDING_ID = " + buildingId
                    + " AND UNIT_CODE = " + unitCode;
        
            MainApp.dbUtility.databaseQueryingThatDiffer(con, deleteUnitsQuery);
    }
    
    public void deleteUnits(int buildingId, String modelName, int from, int to){
      
        for (int i = from; i <= to; i++) {
            String deleteUnitsQuery = "DELETE FROM UNITS_F"
                    + " WHERE BUILDING_ID = " + buildingId
                    + " AND FLOOR_NUMBER = " + i
                    + " AND MODEL_NAME = '" + modelName + "'";
            MainApp.dbUtility.databaseQueryingThatDiffer(con, deleteUnitsQuery);
        }   
    }
    
    public void addModel(int buildingId, String name, int area, float price,
            int modelsCount, int from, int to){
        
            String addModelQry
                    = "INSERT INTO MODELS_F("
                    + "NAME,"
                    + "PRICE,"
                    + "AREA,"
                    + "BUILDING_ID)"
                    + "VALUES('" + name + "',"
                    + price + ","
                    + area + ","
                    + buildingId + ")";
            
            MainApp.dbUtility.databaseQueryingThatDiffer(con, addModelQry);
            createUnits(name, buildingId, modelsCount, from, to);
    }
    
    private void createUnits(String modelName, int buildingId, int modelsCount,
            int from, int to){
        modelsCount++;
        for(int i = from; i <= to; i++){
            String addUnitQry
                    = "INSERT INTO UNITS_F("
                    + "MODEL_NAME,"
                    + "FLOOR_NUMBER,"
                    + "UNIT_CODE,"
                    + "TYPE,"
                    + "AVAILABILITY_STATUS,"
                    + "BUILDING_ID)"
                    + "VALUES('" + modelName + "',"
                    + i + ","
                    + ((i * 100) + modelsCount) + ","
                    + "'h',"
                    + true + ","
                    + buildingId + ")";
            MainApp.dbUtility.databaseQueryingThatDiffer(con, addUnitQry);
        }
        
    }
    
    public void deleteImage(int buildingId, String imgPath){
        
        String deleteImgQuery = "DELETE FROM MEDIA_F"
                + " WHERE BUILDING_ID = " + buildingId 
                + " AND PIC_PATH = '" + imgPath + "'" ;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, deleteImgQuery);
    }
    
    public void updateMediaCheck(int buildingId){
        String updatingQuery = "UPDATE BUILDINGS_F SET MEDIA_CHECK = FALSE "
                + "WHERE ID = "+ buildingId;
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, updatingQuery);
    }
    
}
