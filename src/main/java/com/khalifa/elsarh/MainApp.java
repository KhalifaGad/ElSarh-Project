package com.khalifa.elsarh;

import com.khalifa.elsarh.pojos.InsertUnit2ApiModel;
import com.khalifa.elsarh.utilities.DataBaseUtility;
import com.khalifa.elsarh.utilities.JerseyClient;
import java.io.File;
import java.sql.Connection;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    public static DataBaseUtility dbUtility ;
    public static Connection con;
    public static String DirPath;
    @Override
    public void init(){
        File gettingPathFileTrick = new File("");
        DirPath = gettingPathFileTrick.getAbsolutePath();
        
        dbUtility = new DataBaseUtility();
        con = dbUtility.derbyDBConnection("jdbc:derby:"+
                DirPath+"/elsarhEmbDB;create=true;");
        System.setProperty("derby.language.sequence.preallocator", "1");
        dbUtility.createDatabase(con);
        
        /*
        String updatingQuery = "ALTER TABLE RESTORATION_HISTORY_F ADD COLUMN CONTRACT_DATE DATE";
        dbUtility.databaseQueryingThatDiffer(con, updatingQuery);
        
        String updatingQuery1 = "ALTER TABLE RESTORATION_HISTORY_F ADD COLUMN RECEPTION_DATE DATE";
        dbUtility.databaseQueryingThatDiffer(con, updatingQuery1);
        
        updatingQuery = "ALTER TABLE INSTALLMENTS_RECEIPTS_IDS ADD COLUMN CHECK_BANK VARCHAR(50)";
        dbUtility.databaseQueryingThatDiffer(con, updatingQuery);
        */
        
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().
                getResource("/fxml/sideBar.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        
        stage.setTitle("El Sarh Management System");
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
//        InsertUnit2ApiModel unitModel = new InsertUnit2ApiModel();
//        unitModel.setAdd_unit("add_unit");
//        unitModel.setUnit_instal("yes");
//        unitModel.setUnit_name("unit_name");
//        unitModel.setUnit_type("commercial");
//        unitModel.setUnit_status("semi-finished");
//        unitModel.setUnit_area(200);
//        unitModel.setUnit_area_land(200);
//        unitModel.setUnit_price(2000000);
//        unitModel.setUnit_village("test test");
//        JerseyClient jerseyClient = new JerseyClient();
//        jerseyClient.postRequestUsingGson(unitModel, stage);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
