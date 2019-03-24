package com.khalifa.elsarh.modules;

import com.khalifa.elsarh.MainApp;
import static com.khalifa.elsarh.MainApp.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

/**
 *
 * @author khalifa
 */
public class AddProjectModule {
    
    public void addBuilding(String projectName, String street, String area,
            String country, int floorsCount, boolean mediaCheck){
        
        String query = 
                "INSERT INTO BUILDINGS_F(NAME,"
                + "STREET,"
                + "AREA,"
                + "COUNTRY,"
                + "FLOORS,"
                + "MEDIA_CHECK)"
                + " values('"+projectName+"',"
                + "'"+street +"',"
                + "'"+area+"',"
                + "'"+country+"',"
                + ""+floorsCount+","
                + ""+mediaCheck+")";
        
        MainApp.dbUtility.databaseQueryingThatDiffer(con, query);
    }
    
    public boolean checkBuildingName(String buildingName){
        boolean exist = false;
        String query = "SELECT NAME FROM BUILDINGS_F WHERE "
                + "NAME = '"+buildingName+"'";
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        
        
        try {
            exist = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(AddProjectModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return exist;
    }
    
    public int getLastBuildingId(){
        int buildingId = 1;
        String query = "SELECT ID FROM BUILDINGS_F ORDER BY "
                + "ID DESC FETCH FIRST ROW ONLY";
        
        ResultSet rs = MainApp.dbUtility.databaseQuerying(con, query);
        
        try {
            while (rs.next()) {
                
                buildingId = rs.getInt("id");
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(AddProjectModule.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return buildingId;
    }
    
    public void createHousingUnits(Scene projectScene, int floorsCount, 
            int modelsCount, int buildingId){
        
        int unitCode;
        TextField nameTxtFld;
        String modelNameStr;
        for (int floorsIterator = 1; floorsCount >= floorsIterator;
                floorsIterator++) {
            for (int modelsIterator = 0; modelsCount >= modelsIterator;
                    modelsIterator++) {
                unitCode = (modelsIterator+1) + (floorsIterator * 100);
                nameTxtFld  = 
                    getSpecificTextField("modelName", modelsIterator
                            , projectScene);
                modelNameStr = nameTxtFld.getText();
                String unitsCreationQuery =
                          "INSERT INTO UNITS_F(FLOOR_NUMBER,"
                        + "UNIT_CODE,"
                        + "AVAILABILITY_STATUS,"
                        + "BUILDING_ID,"
                        + "TYPE,"
                        + "MODEL_NAME) "
                        + "VALUES(?,?,?,?,'h',?)";
                
                try {
                    PreparedStatement preparedStmt =
                            con.prepareStatement(unitsCreationQuery);
                    
                    preparedStmt.setInt(1, floorsIterator);
                    preparedStmt.setInt(2, unitCode);
                    preparedStmt.setBoolean(3, true);
                    preparedStmt.setInt(4, buildingId);
                    preparedStmt.setString(5, modelNameStr);
                    
                    preparedStmt.execute();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(AddProjectModule.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void createModles(Scene projectScene, int modelsCount,
            int buildingId){
        
        TextField nameTxtFld, areaTxtFld, priceTxtFld;
        float modelPrice;
        for(int i=0; modelsCount >= i; i++){
            nameTxtFld  = 
                    getSpecificTextField("modelName" , i, projectScene);
            areaTxtFld = 
                    getSpecificTextField("modelArea", i, projectScene);
            priceTxtFld = 
                    getSpecificTextField("modelPrice", i, projectScene);
            modelPrice = Float.valueOf(priceTxtFld.getText());
            String query = 
                      "INSERT INTO MODELS_F(NAME,"
                    + "PRICE,"
                    + "AREA,"
                    + "BUILDING_ID) "
                    + "VALUES('"+nameTxtFld.getText()+"',"
                    + modelPrice+","
                    + Integer.valueOf(areaTxtFld.getText())+","
                    + buildingId+")";
            MainApp.dbUtility.databaseQueryingThatDiffer(con, query);
        }   
    }
    
    public void createCommercialsUnits(Scene projectScene, int buildingId, 
            int commercialsCount){
        // add unit code , financial account
        TextField nameTxtFld, priceTxtFld, areaTxtFld ;
        float commercialUnitPrice;
        int commercialUnitArea;
        int multiplyFactor = 100 ;
        if (buildingId < 100){ // to make sure that the commerial unit code doesn't match any housing model code 
            multiplyFactor = 1000;
        }
        for(int i = 1; commercialsCount >= i; i++){
            nameTxtFld  = 
                   getSpecificTextField("commercialUnitName" , i, projectScene);
            priceTxtFld = 
                   getSpecificTextField("commercialUnitPrice", i, projectScene);
            areaTxtFld  = 
                   getSpecificTextField("commercialUnitArea", i, projectScene);
            commercialUnitPrice = Float.valueOf(priceTxtFld.getText());
            commercialUnitArea = Integer.valueOf(areaTxtFld.getText());
            int unitCode = buildingId * multiplyFactor + i;
            String query=
                      "INSERT INTO UNITS_F(MODEL_NAME,"
                    + "AVAILABILITY_STATUS,"
                    + "BUILDING_ID,"
                    + "SPECIAL_PRICE,"
                    + "TYPE,"
                    + "UNIT_CODE,"
                    + "FLOOR_NUMBER)" 
                    + "values('"+nameTxtFld.getText()+"',"
                    + "true,"
                    + buildingId+","
                    + commercialUnitPrice+","
                    + "'c',"
                    + unitCode + ","
                    + commercialUnitArea+")";
            MainApp.dbUtility.databaseQueryingThatDiffer(con, query);
        }
    }
    
    public void saveBuildingImgs(List<String> projectImagesPathsList,
            int buildingId){
         for(int i=0 ;projectImagesPathsList.size() > i ; i++){
            
        String savingImgPathsQry = 
                "INSERT INTO MEDIA_F(PIC_PATH,"
                + "TYPE,"
                + "BUILDING_ID)"
                + "VALUES('"+projectImagesPathsList.get(i)+"',"
                + "'b'," // b for building
                + buildingId +")";
           
        MainApp.dbUtility.databaseQueryingThatDiffer(con, savingImgPathsQry);
}
    }
    
    TextField getSpecificTextField(String baseStr, Integer Iterator,
            Scene scene){
         String textFiledIdStr = baseStr + Iterator.toString();
         TextField txtFld = (TextField) scene.lookup("#" + textFiledIdStr);
         return txtFld;
    }
}