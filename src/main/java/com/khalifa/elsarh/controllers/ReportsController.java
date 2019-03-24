/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.controllers;

import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import com.khalifa.elsarh.modules.ReportsModule;
import com.khalifa.elsarh.utilities.ReportsUtility;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class ReportsController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @FXML
    private ToggleButton projectsTBtn, unitsTBtn, clientsTBtn, financeTBtn;
    
    @FXML
    private TextField clientNameTxtFld, clientIdTxtFld;
    
    @FXML
    private ComboBox projectNameCB;
    
    @FXML
    private AnchorPane reportscontainerAncPn;
    
    @FXML
    private HBox projectNameHB, clientsFilterHB;
    
    
    private int buildingId, clientId;
    private String buildingName;
    
    private ReportsModule module ;
    ReportsUtility reportsUtilityObj;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportsUtilityObj = new ReportsUtility();
        module = new ReportsModule();
        
        projectNameHB.setVisible(false);
        clientsFilterHB.setVisible(false);
        reportscontainerAncPn.setStyle("-fx-background-color: transparent;"
                + "-fx-border-style: hidden;");
        
        AnchorPane.setTopAnchor(projectNameHB, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(clientsFilterHB, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(reportscontainerAncPn, scenesHeight * 0.2);
        AnchorPane.setRightAnchor(projectNameHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(clientsFilterHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(reportscontainerAncPn, scenesWidth * 0);
        projectNameCB.getItems().addAll(module.getBuildingNames());
    }    
    
    @FXML
    private void projectsReports(MouseEvent event){
        reportscontainerAncPn.getChildren().clear();
        if(projectsTBtn.isSelected()){
            FadeTransition fadeTransition = new FadeTransition(Duration
                    .seconds(1.0), projectNameHB);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
            projectNameHB.setVisible(true);
            clientsFilterHB.setVisible(false);
        }else{
            projectNameHB.setVisible(false);
        }
    }
    @FXML
    private void unitsReports(MouseEvent event){
        reportscontainerAncPn.getChildren().clear();
        if(unitsTBtn.isSelected()){
           clientsFilterHB.setVisible(false);
           projectNameHB.setVisible(false);
           UnitsReportsUIProviding();
           
        }else{
            reportscontainerAncPn.getChildren().clear();
        }
    }
    @FXML
    private void clientsReports(MouseEvent event){
        reportscontainerAncPn.getChildren().clear();
        if (clientsTBtn.isSelected()) {
            FadeTransition fadeTransition = new FadeTransition(Duration
                    .seconds(1.0),
                clientsFilterHB);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
            clientsFilterHB.setVisible(true);
            projectNameHB.setVisible(false);
        }else{
            clientsFilterHB.setVisible(false);
            
        }
    }
    @FXML
    private void financeReports(MouseEvent event){
        reportscontainerAncPn.getChildren().clear();
        if(financeTBtn.isSelected()){
           clientsFilterHB.setVisible(false);
           projectNameHB.setVisible(false);
           financialReportsUIProviding();
        }
    }
    @FXML
    private void clientInfoById(ActionEvent event){
        String clientIdStr = clientIdTxtFld.getText();
        clientId = Integer.valueOf(clientIdStr);
        if(!clientIdStr.isEmpty() ){
            clientIdTxtFld.setStyle("-fx-border-color: green;"
                    + "-fx-border-width: 2;");
            clientReportsUIProviding();
        }else{
            clientIdTxtFld.setStyle("-fx-border-color: red;"
                    + "-fx-border-width: 2;");
            reportscontainerAncPn.getChildren().clear();
        }
    } 
    
    @FXML
    private void projectInfo(ActionEvent event){
        
        buildingName = String.valueOf(projectNameCB.getValue());
        buildingId = module.getBuildingId(buildingName) ;
        
        projectReportsUIProviding();
    }
    
    private void financialReportsUIProviding(){
        
        HBox FR_lateInstlmntsHB = new HBox();
        hboxStyling(FR_lateInstlmntsHB, 0, 0.1, 0.1);
        
        Text FR_lateInstlmntsTxt = new Text();
        FR_lateInstlmntsTxt.setText("كــشـف الاقـســاط الـــمتـأخـره");
        FR_lateInstlmntsTxt.setStyle("-fx-font-size:1.2em;");
        
        Button FR_lateInstlmntsBtn = createStyledButton("yellowBtn");
        FR_lateInstlmntsBtn.setText(" اسـتـخـراج ");
        FR_lateInstlmntsBtn.setOnAction((event) -> {
            reportsUtilityObj.lateInstallments();
        });
        
        FR_lateInstlmntsHB.getChildren().addAll(FR_lateInstlmntsTxt,
                FR_lateInstlmntsBtn);
        
        HBox FR_restoredReservationsHB = new HBox(); 
        hboxStyling(FR_restoredReservationsHB, 0.08, 0.1, 0.1);
        
        Text FR_restoredReservationsTxt = 
                new Text("كـشـف الـــحـجـــوز الــــملـغـاه");
        FR_restoredReservationsTxt.setStyle("-fx-font-size:1.2em;");
        
        Button FR_restoredReservationsBtn = createStyledButton("darkBtn");
        FR_restoredReservationsBtn.setOnAction((event) -> {
            reportsUtilityObj.restoredClientsRepo();
            FR_restoredReservationsBtn.
                    setStyle("-fx-border-color: #92ff84;");
        });
        
        FR_restoredReservationsHB.getChildren().
                addAll(FR_restoredReservationsTxt,FR_restoredReservationsBtn);
        
        HBox FR_soldUnitsIncomeHB = new HBox();
        hboxStyling(FR_soldUnitsIncomeHB, 0.16, 0.1, 0.1);
        
        Text FR_soldUnitsIncomeTxt = new Text("كـشـف ايـرادات وحـدات مبـاعــه");
        FR_soldUnitsIncomeTxt.setStyle("-fx-font-size:1.2em;");
        
        Button FR_soldUnitsIncomeBtn = createStyledButton("yellowBtn");
        FR_soldUnitsIncomeBtn.setOnAction((event) -> {
            reportsUtilityObj.incomeFromSoldUnits();
            FR_soldUnitsIncomeBtn.setStyle("-fx-border-color: #92ff84;");
        });
        
        FR_soldUnitsIncomeHB.getChildren().addAll(FR_soldUnitsIncomeTxt,
                FR_soldUnitsIncomeBtn);
        
        HBox FR_checksHB = new HBox();
        hboxStyling(FR_checksHB, 0.24, 0.1, 0.1);
        
        Text checksRepoTxt = new Text("تــقــــــريـــــر  الشيـــــــــكات");
        checksRepoTxt.setStyle("-fx-font-size:1.2em;");
        
        Button checksRepoBtn = createStyledButton("darkBtn");
        checksRepoBtn.setOnAction((event) -> {
            reportsUtilityObj.cashReports();
        });
        
        FR_checksHB.getChildren().addAll(checksRepoTxt,
                checksRepoBtn);
        
        HBox FR_salesInPeriodHB = new HBox();
        hboxStyling(FR_salesInPeriodHB, 0.32, 0.1, 0.015);
        
        Text FR_salesInPeriodTxt = new Text("كشف المبيعات من");
        FR_salesInPeriodTxt.setStyle("-fx-font-size:1.2em;");
        
        TextField fromMonthTxtFld = new TextField();
        fromMonthTxtFld.setPrefWidth(scenesWidth * 0.05);
        fromMonthTxtFld.setPromptText("شهر");
        TextField fromYearTxtFld = new TextField();
        fromYearTxtFld.setPrefWidth(scenesWidth * 0.05);
        fromYearTxtFld.setPromptText("سنه");
        
        Text FR_salesInPeriodWordTxt = new Text();
        FR_salesInPeriodWordTxt.setText("الـي");
        FR_salesInPeriodWordTxt.setStyle("-fx-font-size:1.2em;");
        
        TextField toMonthTxtFld = new TextField();
        toMonthTxtFld.setPrefWidth(scenesWidth * 0.05);
        toMonthTxtFld.setPromptText("شهر");
        TextField toYearTxtFld = new TextField();
        toYearTxtFld.setPrefWidth(scenesWidth * 0.05);
        toYearTxtFld.setPromptText("سنه");
        
        Button FR_salesInPeriodBtn = createStyledButton("yellowBtn");
        FR_salesInPeriodBtn.setOnAction((event) -> {
            String salesFromMonthStr = fromMonthTxtFld.getText(),
                    salesFromYearStr = fromYearTxtFld.getText(),
                    salesToMonthStr = toMonthTxtFld.getText(),
                    salesToYearStr = toYearTxtFld.getText();
            if(salesFromMonthStr.isEmpty()){
                fromMonthTxtFld.
                        setStyle("-fx-border-color: #f44256;");
            }else if (salesFromYearStr.isEmpty()){
                fromYearTxtFld.
                        setStyle("-fx-border-color: #f44256;");
            }else if (salesToMonthStr.isEmpty()){
                toMonthTxtFld.setStyle("-fx-border-color: #f44256;");
            }else if (salesToYearStr.isEmpty()){
                toYearTxtFld.setStyle("-fx-border-color: #f44256;");
            }else{
                reportsUtilityObj
                        .salesInSpecPeriod(salesFromMonthStr,
                                salesFromYearStr, salesToMonthStr,
                                salesToYearStr);
                FR_salesInPeriodBtn.setStyle("-fx-border-color: #92ff84;");
            }
        });
        
        fadeInTranstion(FR_lateInstlmntsHB);
        fadeInTranstion(FR_restoredReservationsHB);
        fadeInTranstion(FR_soldUnitsIncomeHB);
        fadeInTranstion(FR_checksHB);
        fadeInTranstion(FR_salesInPeriodHB);
        
        FR_salesInPeriodHB.getChildren().addAll(FR_salesInPeriodTxt,
                fromMonthTxtFld,fromYearTxtFld,FR_salesInPeriodWordTxt,
                toMonthTxtFld,toYearTxtFld,FR_salesInPeriodBtn);
        
        reportscontainerAncPn.getChildren().addAll(FR_lateInstlmntsHB,
                FR_restoredReservationsHB, FR_salesInPeriodHB,
                FR_soldUnitsIncomeHB, FR_checksHB);
    }
    
    private void UnitsReportsUIProviding(){
        
        HBox UR_allReceivedUnitsHB = new HBox();
        hboxStyling(UR_allReceivedUnitsHB, 0, 0.1, 0.1);
        
        Text UR_allReceivedUnitsTxt = new Text("كـشـف وحــدات مـــســلـمـه ");
        UR_allReceivedUnitsTxt.setStyle("-fx-font-size:1.2em;");
        
        Button UR_allReceivedUnitsBtn = createStyledButton("yellowBtn");
        UR_allReceivedUnitsBtn.setOnAction((event) -> {
            reportsUtilityObj.allReceivedUnits();
            UR_allReceivedUnitsBtn.setStyle("-fx-border-color: #92ff84;");
        });
        
        UR_allReceivedUnitsHB.getChildren().addAll(UR_allReceivedUnitsTxt,
                UR_allReceivedUnitsBtn);
        
        HBox UR_allReservedUnitsHB = new HBox();
        hboxStyling(UR_allReservedUnitsHB, 0.08, 0.1, 0.099);
        
        Text UR_allReservedUnitsTxt = new Text("كـشـف الـوحـدات الـمـحـجـوزه");
        UR_allReservedUnitsTxt.setStyle("-fx-font-size:1.2em;");
        
        Button UR_allReservedUnitsBtn = createStyledButton("darkBtn");
        UR_allReservedUnitsBtn.setOnAction((event) -> {
            reportsUtilityObj.allReservedUnits();
            UR_allReservedUnitsBtn.setStyle("-fx-border-color: #92ff84;");
        });
        
        fadeInTranstion(UR_allReservedUnitsHB);
        fadeInTranstion(UR_allReceivedUnitsHB);
            
        UR_allReservedUnitsHB.getChildren().addAll(UR_allReservedUnitsTxt,
                UR_allReservedUnitsBtn);
        
        reportscontainerAncPn.getChildren().addAll(UR_allReceivedUnitsHB,
                UR_allReservedUnitsHB);
    }
    
    private void clientReportsUIProviding(){
        
        reportscontainerAncPn.getChildren().clear();
        
        HBox CR_clientFinancialStatusHB = new HBox();
        hboxStyling(CR_clientFinancialStatusHB, 0.0, 0.1, 0.1);
        
        Text CR_clientFinancialStatusTxt = new Text("كشف الموقف المالى للعميل");
        CR_clientFinancialStatusTxt.setStyle("-fx-font-size:1.2em;");
        
        Button CR_clientFinancialStatusBtn = createStyledButton("yellowBtn");
        CR_clientFinancialStatusBtn.setOnAction((event) -> {
            reportsUtilityObj.clientFinancialState(clientId);
            CR_clientFinancialStatusBtn.setStyle("-fx-border-color: #92ff84;");
        });
        
        CR_clientFinancialStatusHB.getChildren()
              .addAll(CR_clientFinancialStatusTxt, CR_clientFinancialStatusBtn);
        
        fadeInTranstion(CR_clientFinancialStatusHB);
        
        reportscontainerAncPn.getChildren().add(CR_clientFinancialStatusHB);
    }
    
    private void projectReportsUIProviding(){
        reportscontainerAncPn.getChildren().clear();
        
        HBox PR_reservedClientsHB = new HBox();
        hboxStyling(PR_reservedClientsHB, 0.0, 0.1, 0.1);
        
        Text PR_reservedClientsTxt = new Text("كـشـف الـعـمـلاء بـالــمشـــروع");
        PR_reservedClientsTxt.setStyle("-fx-font-size:1.2em;");
        
        Button PR_reservedClientsTBtn = createStyledButton("yellowBtn");
        PR_reservedClientsTBtn.setOnAction((event) -> {
            reportsUtilityObj.projectClientsReport(buildingId, buildingName);
            PR_reservedClientsTBtn.setStyle("-fx-border-color: #92ff84;");
        });
        
        PR_reservedClientsHB.getChildren().addAll(PR_reservedClientsTxt,
                PR_reservedClientsTBtn);
        
        HBox PR_reservedUnitsHB = new HBox();
        hboxStyling(PR_reservedUnitsHB, 0.08, 0.1, 0.099);
        
        Text PR_reservedUnitsTxt = new Text("كـشـف الـوحـدات الـمـحـجـوزه");
        PR_reservedUnitsTxt.setStyle("-fx-font-size:1.2em;");
        
        Button PR_reservedUnitsTBtn = createStyledButton("darkBtn");
        PR_reservedUnitsTBtn.setOnAction((event) -> {
            reportsUtilityObj.projectReservedUnits(buildingId, buildingName);
            PR_reservedUnitsTBtn.setStyle("-fx-border-color: #92ff84;");
        });
        
        PR_reservedUnitsHB.getChildren().addAll(PR_reservedUnitsTxt,
                PR_reservedUnitsTBtn);
        
        
        HBox PR_nonReservedUnitsHB = new HBox();
        hboxStyling(PR_nonReservedUnitsHB, 0.16, 0.1, 0.09);
        
        Text PR_nonReservedUnitsTxt = new Text("كـشـف وحـدات غـيـر مـحـجـوزه");
        PR_nonReservedUnitsTxt.setStyle("-fx-font-size:1.2em;");
        
        Button PR_nonReservedUnitsTBtn = createStyledButton("yellowBtn");
        PR_nonReservedUnitsTBtn.setOnAction((event) -> {
            reportsUtilityObj.projectNonReservedUnits(buildingId);
            PR_nonReservedUnitsTBtn.setStyle("-fx-border-color: #92ff84;");
        });
        
        
        PR_nonReservedUnitsHB.getChildren().addAll(PR_nonReservedUnitsTxt,
                PR_nonReservedUnitsTBtn);
        
        HBox PR_FinancePerMonthHB = new HBox();
        hboxStyling(PR_FinancePerMonthHB, 0.24, 0.1, 0.015);
        
        Text PR_FinancePerMOnthTxt = new Text("كـشـف المتحصلات لــ ");
        PR_FinancePerMOnthTxt.setStyle("-fx-font-size:1.2em;");
        
        TextField monthTxtFld = new TextField();
        monthTxtFld.setPrefWidth(scenesWidth * 0.05);
        monthTxtFld.setPromptText("شهر");
        TextField yearTxtFld = new TextField();
        yearTxtFld.setPrefWidth(scenesWidth * 0.05);
        yearTxtFld.setPromptText("سنه");
        
        Button PR_FinancePerMonthBtn = createStyledButton("yellowBtn");
        PR_FinancePerMonthBtn.setOnAction((event) -> {
            String month = monthTxtFld.getText(),
                    year = yearTxtFld.getText();
            if (!month.isEmpty() && !year.isEmpty()) {
                reportsUtilityObj.
                        projectIncome4Month(buildingId, month, year);
            } else {
                monthTxtFld.setStyle("-fx-border-color: #f44256;");
                yearTxtFld.setStyle("-fx-border-color: #f44256;");
            }
        });
        
        PR_FinancePerMonthHB.getChildren().addAll(PR_FinancePerMOnthTxt,
                monthTxtFld, yearTxtFld, PR_FinancePerMonthBtn);
        
        HBox PR_unitsStatisticsHB = new HBox();
        PR_unitsStatisticsHB.setId("reportsFilteringParamHB");
        PR_unitsStatisticsHB.setAlignment(Pos.CENTER);
        PR_unitsStatisticsHB.setSpacing(scenesWidth * 0.09);
        PR_unitsStatisticsHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        PR_unitsStatisticsHB.setPrefWidth(scenesWidth * 0.8);
        PR_unitsStatisticsHB.setPrefHeight(scenesHeight * 0.05);
        AnchorPane.setTopAnchor(PR_unitsStatisticsHB, scenesHeight * 0.32);
        AnchorPane.setRightAnchor(PR_unitsStatisticsHB, scenesWidth * 0.1);
        
        Text PR_unitsstatisticsTxt = 
                new Text("احـــصـــائـيـــــة الـــوحــــدات");
        PR_unitsstatisticsTxt.setStyle("-fx-font-size:1.2em;");
        
        Button PR_unitsStatisticsBtn = createStyledButton("darkBtn");
        PR_unitsStatisticsBtn.setOnAction((event) -> {
            reportsUtilityObj.projectUnitsStatistics(buildingId);
            PR_unitsStatisticsBtn.setStyle("-fx-border-color: #92ff84;");
        });
        
        PR_unitsStatisticsHB.getChildren().addAll(PR_unitsstatisticsTxt,
                PR_unitsStatisticsBtn);
        
        fadeInTranstion(PR_reservedClientsHB);
        fadeInTranstion(PR_FinancePerMonthHB);
        fadeInTranstion(PR_unitsStatisticsHB);
        fadeInTranstion(PR_reservedUnitsHB);
        fadeInTranstion(PR_nonReservedUnitsHB);
            
        reportscontainerAncPn.getChildren().addAll(PR_reservedClientsHB,
                PR_reservedUnitsHB, PR_nonReservedUnitsHB, PR_FinancePerMonthHB,
                PR_unitsStatisticsHB);
    }
    
    
    
    private void fadeInTranstion(HBox hbox){
        FadeTransition fadeInTransition = new FadeTransition(Duration
                    .seconds(1.0), hbox);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
    }
    
    private void hboxStyling(HBox hbox, double hGap, double wGap,
            double spacing){
        
        hbox.setId("reportsFilteringParamHB");
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(scenesWidth * spacing);
        hbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        hbox.setPrefWidth(scenesWidth * 0.8);
        hbox.setPrefHeight(scenesHeight * 0.05);
        AnchorPane.setTopAnchor(hbox, scenesHeight * hGap);
        AnchorPane.setRightAnchor(hbox, scenesWidth * wGap);
        
    }
    
    private Button createStyledButton(String idStr){
        
        Button styledBtn = new Button(" اسـتـخـراج ");
        styledBtn.setId(idStr);
        styledBtn.setPrefWidth(scenesWidth * 0.1);
        
        return styledBtn;
    }
    
}
