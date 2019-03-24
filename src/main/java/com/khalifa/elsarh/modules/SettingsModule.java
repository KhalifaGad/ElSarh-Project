
package com.khalifa.elsarh.modules;

import static com.khalifa.elsarh.MainApp.con;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khalifa
 */
public class SettingsModule {
    
    public void addInstallmentType(String installmentName,
            String installmentType){
        
        String addInstallmentQuery = "INSERT INTO INSTALLMENT_NAMES_F("
                + " NAME,"
                + " TYPE)"
                + " VALUES(?,?)";
        
        try {
            PreparedStatement preparedStmt =
                    con.prepareStatement(addInstallmentQuery);
            
            preparedStmt.setString(1, installmentName);
            preparedStmt.setString(2, installmentType);
            
            preparedStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(SettingsModule.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
}
