package com.khalifa.elsarh.utilities;

import com.khalifa.elsarh.MainApp;
import com.khalifa.elsarh.modules.ReportsModule;
import com.khalifa.elsarh.pojos.ClientModel;
import com.khalifa.elsarh.pojos.InstallmentModel;
import com.khalifa.elsarh.pojos.ReceiptModel;
import com.khalifa.elsarh.pojos.RestorationModel;
import com.khalifa.elsarh.pojos.TransitionsModel;
import com.khalifa.elsarh.pojos.UnitModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author khalifa
 */
public class ReportsUtility {
    
    private final ReportsModule module = new ReportsModule();
    
    public void projectClientsReport(int buildingId, String buildingName) { 
       
        String[] projClntsRprtColumns = {"المتبقي","المدفوع"," المستحقات"
                ,"النوع","ت تسليم","ت تعاقد","ك.الوحده","رقم الشخصيه"
                ,"الهاتف","الاسم","الكود"};
        
        List<Integer> clientsIds = module.getClientsOfBuilding(buildingId);
        
        Map<Integer, ClientModel> clientIdClientObjMap = new HashMap();
         
        module.getClientsData(clientsIds, clientIdClientObjMap);
        
        List<UnitModel> units = module.getUnitsData(clientsIds, buildingId);
        
        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("project'sClientsRepo");
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير العملاء بمشروع "+ buildingName);
        sheet.getHeader().setLeft(LocalDate.now().toString());
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.
                createDataFormat().getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setFont(headerFont);
        
        Row headerRow = sheet.createRow(4);
        for(int i = 0; i < projClntsRprtColumns.length ; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    projClntsRprtColumns[i], i);
        }
        int rowNum = 5;
        
        for(UnitModel unit : units){
            Row row = sheet.createRow(rowNum++);
            
            createStyledCell(row, alignCenterStyle, 
                            String.valueOf(unit.getClientId()), 10);
            
            createStyledCell(row, alignCenterStyle, clientIdClientObjMap.
                            get(unit.getClientId()).getName(), 9);
            
            createStyledCell(row, alignCenterStyle, "0" + clientIdClientObjMap.
                            get(unit.getClientId()).getMobileNumber(), 8);
            
            createStyledCell(row, alignCenterStyle, clientIdClientObjMap.
                            get(unit.getClientId()).getPersonalId(), 7);
            
            createStyledCell(row, alignCenterStyle,
                    String.valueOf(unit.getUnitCode()), 6);
           
            String contructDateStr = "";
            if(unit.getContructDate() != null){
                contructDateStr = unit.getContructDate().toString();
            }
            
            createStyledCell(row, dateCellStyle, contructDateStr, 5);
            
            String receiptionDateStr = "";
            if(unit.getReceptionDate() != null){
                receiptionDateStr = unit.getReceptionDate().toString();
            }
            
            createStyledCell(row, dateCellStyle, receiptionDateStr, 4);
            
            String unitType = "";
            switch(unit.getType()){
                case 'h':
                    unitType = "سكنى";
                    break;
                case 'c':
                    unitType = "تجارى";
                    break;
            }
            createStyledCell(row, alignCenterStyle, unitType, 3);
            
            float totalRequiredCash = 
                    module.getTotalInstallmentsValues(unit.getId()),
                    totalPaidCash = 
                    module.getTotalPaidInstallments(unit.getId());
            
            createStyledCell(row, alignCenterStyle, String.format("%.1f",
                    totalRequiredCash), 2);
            
            createStyledCell(row, alignCenterStyle, String.format("%.1f",
                    totalPaidCash), 1);
            
            createStyledCell(row, alignCenterStyle, String.format("%.1f",
                    (totalRequiredCash - totalPaidCash)), 0);  
        }
        
        for(int i = 0; i < projClntsRprtColumns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        FileOutputStream fileOut;
        String directoryName = "تقارير مشروع";
        File dir = new File(directoryName);
        dir.mkdir();
        String reportName = "تقارير العملاء بمشروع "+ buildingName + " - " +
                LocalDate.now().toString() + " .xlsx";
        File reportNameFile = new File(dir + "/" +reportName);
        
        try {
            reportNameFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        
        try {
            fileOut = new FileOutputStream(reportNameFile);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void projectReservedUnits(int buildingId, String buildingName){
        
        List<UnitModel> unitsList = module.getReservedUnits(buildingId);
        Map<String, Float> modelPriceMap = module.getModelsPriceMap(buildingId);
        
        String[] projReservedUnitsRepoColumns =
        {"ت تسليم","السعر","النموذج","الدور","النوع","العميل","كود العميل"};
        
        Workbook workbook = new XSSFWorkbook();
        
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("projectReservedUnits");
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير الوحدات المحجوزه بمشروع "
                + buildingName);
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        Row headerRow = sheet.createRow(4);
        
        for(int i = 0; i < projReservedUnitsRepoColumns.length ; i++) {
            Cell cell = headerRow.createCell(i+2);
            cell.setCellValue(projReservedUnitsRepoColumns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        
        int rowNum = 5;
        
        for(UnitModel unit : unitsList){
            Row row = sheet.createRow(rowNum++);
            createStyledCell(row, alignCenterStyle, 
                    String.valueOf(unit.getClientId()), 8);
            
            createStyledCell(row, alignCenterStyle, 
                    module.getClientName(unit.getClientId()), 7);
            
            String unitType = "";
            switch(unit.getType()){
                case 'h':
                    unitType = "سكنى";
                    createStyledCell(row, alignCenterStyle,
                            String.valueOf(unit.getFloorNumber()), 5);
                    createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", modelPriceMap.
                            get(unit.getModelName())), 3);
                    break;
                case 'c':
                    unitType = "تجارى";
                    createStyledCell(row, alignCenterStyle, "", 5);
                    createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", unit.getSpecialPrice()), 3);
                    break;
            }
            
            createStyledCell(row, alignCenterStyle, unitType, 6);
            
            createStyledCell(row, alignCenterStyle, unit.getModelName(), 4);
            
            String receiptionDateStr = "";
            if(unit.getReceptionDate() != null){
                receiptionDateStr = unit.getReceptionDate().toString();
            }
            
            createStyledCell(row, dateCellStyle, receiptionDateStr, 2);
        }
        
        for(int i = 0; i < projReservedUnitsRepoColumns.length; i++) {
            sheet.autoSizeColumn(i+2);
        }
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير الوحدات المحجوزه بمشروع.xlsx");
        
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void projectNonReservedUnits(int buildingId){
        List <UnitModel> unitsList = module.getFreeUnits(buildingId);
        Map<String, Float> modelPriceMap = module.getModelsPriceMap(buildingId);
        
        String[] prjNonRsrvdUnitsRprtColumns = 
                {"الـسـعـر","النموذج","الدور","النوع"};
        
        Workbook workbook = new XSSFWorkbook();
        
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("projectFreeUnits");
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير وحدات غير محجوزه بمشروع"
                + module.getBuildingName(buildingId));
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        Row headerRow = sheet.createRow(4);
        
        for(int i = 0; i < prjNonRsrvdUnitsRprtColumns.length ; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    prjNonRsrvdUnitsRprtColumns[i], i+4);
        }
        int rowNum = 5;
        for(UnitModel unit : unitsList){
            Row row = sheet.createRow(rowNum++);
            
            String unitType = "";
            switch(unit.getType()){
                case 'h':
                    unitType = "سكنى";
                    createStyledCell(row, alignCenterStyle, String.valueOf(unit.
                            getFloorNumber()), 6);
                    createStyledCell(row, alignCenterStyle,String.format("%.1f",
                            modelPriceMap.get(unit.getModelName())), 4);
                    break;
                case 'c':
                    unitType = "تجارى";
                    createStyledCell(row, alignCenterStyle, "", 6);
                    createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", unit.getSpecialPrice()), 4);
                    break;
            }
            createStyledCell(row, alignCenterStyle, unitType, 7);
            
            createStyledCell(row, alignCenterStyle, unit.getModelName(), 5);
        }
        for(int i = 0; i < prjNonRsrvdUnitsRprtColumns.length; i++) {
            sheet.autoSizeColumn(i+4);
        }
        
        FileOutputStream fileOut;
        try {
            String fileName = "تقرير الوحدات غير محجوزه بمشروع.xlsx";
            fileOut = new FileOutputStream(fileName);

            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    public void projectIncome4Month(int buildingId,String month,String year){
        
        String[] projectIncome4MonthColumn =
            {"متبقي","مدفوع","بتاريخ","اسم الدفعه","رقم الهاتف","العميل",
                "كود العميل"};
        
        String startDateStr = year+"-"+month+"-01";
        LocalDate startDate = LocalDate.parse(startDateStr);
        java.sql.Date startSqlDate = java.sql.Date.valueOf(startDate);
        
        int endDayInt = startDate.lengthOfMonth();
        String endDateStr = year+"-"+month+"-"+String.valueOf(endDayInt);
        LocalDate endDate = LocalDate.parse(endDateStr);
        java.sql.Date endSqlDate = java.sql.Date.valueOf(endDate);
        
        List<InstallmentModel> installmentsList = module.
                getInstallmentsData(buildingId, startSqlDate, endSqlDate);
        
        Workbook workbook = new XSSFWorkbook();
        
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("projectIncome4Month");
        
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير ارادات مشروع  "+
                module.getBuildingName(buildingId)+"لشهر "+year+"-"+month);
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        Row headerRow = sheet.createRow(4);
        
        for(int i = 0; i < projectIncome4MonthColumn.length ; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    projectIncome4MonthColumn[i], i+2);
        }
        
        int rowNum = 5;
        float totalIncome = 0, totalRequired = 0, paidValue, requiredValue,
                totalUnpaid = 0, unpaindValue;

        for (InstallmentModel installment : installmentsList) {

            Row row = sheet.createRow(rowNum++);
            
            createStyledCell(row, alignCenterStyle,
                    String.valueOf(installment.getClientId()), 8);
            
            createStyledCell(row, alignCenterStyle,
                    module.getClientName(installment.getClientId()), 7);
            
            createStyledCell(row, alignCenterStyle,
                    "0" + module.getClientPhone(installment.getClientId()), 6);

            createStyledCell(row, alignCenterStyle,
                    installment.getPaymentName(), 5);

            createStyledCell(row, dateCellStyle,
                    installment.getInstallmentDate().toString(), 4);

            paidValue = installment.getInstallmentPaidValue();
            createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", paidValue), 3);

            requiredValue = installment.getInstallmentValue();
            unpaindValue = requiredValue - paidValue;

            createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", unpaindValue), 2);

            totalIncome += paidValue;
            totalRequired += requiredValue;
            totalUnpaid += unpaindValue;

        }
        rowNum ++;
        Row row = sheet.createRow(rowNum++);
        createStyledCell(row, alignCenterStyle, "احمالى المستحقات", 7);
        createStyledCell(row, alignCenterStyle,
                String.format("%.1f", totalRequired), 3);
        
        rowNum ++;
        Row row1 = sheet.createRow(rowNum++);
         createStyledCell(row1, alignCenterStyle, "اجمالى المتحصلات", 7);
        createStyledCell(row1, alignCenterStyle,
                String.format("%.1f", totalIncome), 3);
        
        rowNum ++;
        Row row2 = sheet.createRow(rowNum++);
        createStyledCell(row2, alignCenterStyle, "اجمالى متبقى", 7);
        createStyledCell(row2, alignCenterStyle,
                String.format("%.1f", totalUnpaid), 3);
        
        for(int i = 0; i < projectIncome4MonthColumn.length; i++) {
            sheet.autoSizeColumn(i+2);
        }
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير المتحصلات لشهر بمشروع.xlsx");
        
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void projectUnitsStatistics(int buildingId){ 
        String[] projectUnitsStatColumns = 
        {"اجـمـالـى","بـنـسـبـة","مـحـجـوز","بـنـسـبـة","شـاغـر"};
        int unitsCount, 
                availableUnits = module.getAvailablesCount(buildingId),
                unavailableUnits = module.getUnAvailablesCount(buildingId);
        
        unitsCount = availableUnits + unavailableUnits;
        
        float availabiltyPrecentage = (availableUnits * 100) / unitsCount;
        float unavailableUnitsPrecentage = (unavailableUnits * 100) / unitsCount;
        
        Workbook workbook = new XSSFWorkbook();
        
        Sheet sheet = workbook.createSheet("projectUnitsStat");
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير احصائية وحدات مشروع "+
                module.getBuildingName(buildingId));
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        headerCellStyle.setFont(headerFont);
        
        Row headerRow = sheet.createRow(12);
        
        for(int i = 0; i < projectUnitsStatColumns.length ; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    projectUnitsStatColumns[i], i+3);
        }
        
        Row row = sheet.createRow(13);
        
        createStyledCell(row, alignCenterStyle, String.valueOf(availableUnits),
                7);
        
        createStyledCell(row, alignCenterStyle, "%"+availabiltyPrecentage, 6);
        
        createStyledCell(row, alignCenterStyle, String.valueOf(unavailableUnits)
                , 5);
        
        createStyledCell(row, alignCenterStyle, "%"+unavailableUnitsPrecentage,
                4);
        
        createStyledCell(row, alignCenterStyle, String.valueOf(unitsCount), 3);
 
        for(int i = 0; i < projectUnitsStatColumns.length; i++) {
            sheet.autoSizeColumn(i+3);
        }
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير احصائات الوحدات بمشروع.xlsx");
        
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void allReceivedUnits(){
        List <UnitModel> unitsList = module.getHandedOverUnits();
        
        String[] allRcvdUnitsRepoColumns = 
        {"ت التسليم","النموذج","الدور","النوع"," المشروع","العميل","كود العميل"};
        
        Workbook workbook = new XSSFWorkbook();
        
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("projectUnitsStat");
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير اجمالى الوحدات المسلمه ");
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        headerCellStyle.setFont(headerFont);
        
        Row headerRow = sheet.createRow(4);
        
        for(int i = 0; i < allRcvdUnitsRepoColumns.length ; i++) {
            createStyledCell(headerRow, headerCellStyle, 
                    allRcvdUnitsRepoColumns[i], i + 4);
        }
        
        int rowNum = 5;
        for(UnitModel unit : unitsList){
            Row row = sheet.createRow(rowNum++);
            
            createStyledCell(row, alignCenterStyle, 
                    String.valueOf(unit.getClientId()), 10);
            
            createStyledCell(row, alignCenterStyle, 
                    module.getClientName(unit.getClientId()), 9);
            
            createStyledCell(row, alignCenterStyle, 
                    module.getBuildingName(unit.getBuildingId()), 8);
            
            String unitType = "";
            switch(unit.getType()){
                case 'h':
                    unitType = "سكنى";
                    createStyledCell(row, alignCenterStyle,
                            String.valueOf(unit.getFloorNumber()), 6);
                    break;
                case 'c':
                    unitType = "تجارى";
                    createStyledCell(row, alignCenterStyle, "", 6);
                    break;
            }
            createStyledCell(row, alignCenterStyle, unitType, 7);
            
            createStyledCell(row, alignCenterStyle, unit.getModelName(), 5);
            
            String receiptionDateStr = "";
            if(unit.getReceptionDate() != null){
                receiptionDateStr = unit.getReceptionDate().toString();
            }
            
            createStyledCell(row, dateCellStyle, receiptionDateStr, 4);
        }
        for(int i = 0; i < allRcvdUnitsRepoColumns.length; i++) {
            sheet.autoSizeColumn(i + 4);
        }
        
         FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير الوحدات المسلمه.xlsx");
        
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void allReservedUnits(){
        List <UnitModel> unitsList = module.getJustBusyUnits();
        String[] allRsrvdUnitsRepoColumns = 
        {"النموذج","الدور","النواع","المشروع","العميل","كود العميل"};
        
        Workbook workbook = new XSSFWorkbook();
        
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("reserved Units");
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير اجمالى الوحدات المحجوزه ");
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        Row headerRow = sheet.createRow(4);
        
        for(int i = 0; i < allRsrvdUnitsRepoColumns.length ; i++) {
            createStyledCell(headerRow, headerCellStyle, 
                    allRsrvdUnitsRepoColumns[i], i+1);
        }
        
        int rowNum = 5;
        for(UnitModel unit : unitsList){
            Row row = sheet.createRow(rowNum++);
            
            createStyledCell(row, alignCenterStyle, 
                    String.valueOf(unit.getClientId()), 6);
            
            createStyledCell(row, alignCenterStyle, 
                    module.getClientName(unit.getClientId()), 5);
            
            createStyledCell(row, alignCenterStyle, 
                    module.getBuildingName(unit.getBuildingId()), 4);
            
            String unitType = "";
            switch(unit.getType()){
                case 'h':
                    unitType = "سكنى";
                    createStyledCell(row, alignCenterStyle,
                                String.valueOf(unit.getFloorNumber()), 2);
                    break;
                case 'c':
                    unitType = "تجارى";
                    createStyledCell(row, alignCenterStyle, "", 2);
                    break;
            }
            createStyledCell(row, alignCenterStyle, unitType, 3);
            
            createStyledCell(row, alignCenterStyle, unit.getModelName(), 1);
           
        }
        for(int i = 0; i < allRsrvdUnitsRepoColumns.length; i++) {
            sheet.autoSizeColumn(i+1);
        }
        
         FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير الوحدات محجوزه.xlsx");
        
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void clientFinancialState(int clientId) {
        
        List<Integer> unitsIds = new ArrayList();
        List<InstallmentModel> installments = module.
                getClientInstallments(clientId, unitsIds);
        Map<Integer, UnitModel> unitsIdDataMap = 
                module.getUnitIdUnitDataMap(unitsIds);
        
        String clientName = module.getClientName(clientId);
        
        Workbook workbook = new XSSFWorkbook();
        
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("FinStat4Client");
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير موقف مالى لعميل ");
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        List<Integer> transitionedUnitsIds = new ArrayList();
        List<TransitionsModel> clientTransitions = module.
                getTransitionedUnits(clientId, transitionedUnitsIds);
        Map<Integer, UnitModel> transitionedUnitsIdDataMap = module.
                getUnitIdUnitDataMap(transitionedUnitsIds);
        
        List<Integer> restoredUnitsIds = new ArrayList();
        List<RestorationModel> restoredUnits = module.
                getRestoredUnits(clientId, restoredUnitsIds);
        Map<Integer, UnitModel> RestoredUnitsIdDataMap = module.
                getUnitIdUnitDataMap(restoredUnitsIds);
        
        List<UnitModel> clientUnits = module.getClientUnits(clientId);
        
        String restoredStr = "مسترجعات",
                transitionStr = "تنازلات",
                clientStr = "العميل";
        String[] clientFinancialStatusRepo = 
        {"كود الدفعه","المبلغ المدفوع","بتاريخ","بقيمة","اسم الدفعه","النوع",
            "النموذج","الدور","المبنى"},
                clientRestoredUnits = {"ت.استرجاع","النموذج","الدور","المبنى"},
                clientTransitionsHeader = {"المتنازل له","ت.تنازل","النموذج",
                    "الدور","المبنى"},
                unitsHeader = {"المدفوع","المسحقات","نسبة الخصم","ت.التسليم",
                    "ت.التعاقد","كود الوحده","المبنى"};
        
        
        int rowNumber = 4;
        Row clientNameRow = sheet.createRow(rowNumber);
        
        createStyledCell(clientNameRow, headerCellStyle, clientStr, 10);
        createStyledCell(clientNameRow, headerCellStyle, clientName, 8);
        
        if (!transitionedUnitsIds.isEmpty()) {
            rowNumber += 2;
            Row trnasformationStrRow = sheet.createRow(rowNumber);
            
            createStyledCell(trnasformationStrRow, headerCellStyle,
                        transitionStr, 10);

            rowNumber++;
            Row transfomrationsHeaders = sheet.createRow(rowNumber);

            for (int i = 0; i < clientTransitionsHeader.length; i++) {
                createStyledCell(transfomrationsHeaders, headerCellStyle,
                        clientTransitionsHeader[i], i+5);
            }

            for (TransitionsModel transition : clientTransitions) {
                rowNumber++;
                Row row = sheet.createRow(rowNumber);
                
                createStyledCell(row, alignCenterStyle, module.
                        getBuildingName(transition.getBuildingId()), 9);

                switch (transitionedUnitsIdDataMap.
                        get(transition.getUnitId()).getType()) {
                    case 'h':
                        createStyledCell(row, alignCenterStyle,
                                String.valueOf(unitsIdDataMap.
                                get(transition.getUnitId()).getFloorNumber()),
                                8);
                        break;
                    case 'c':
                        createStyledCell(row, alignCenterStyle, "", 8);
                        break;
                }
                
                createStyledCell(row, alignCenterStyle,
                        transitionedUnitsIdDataMap.get(transition.getUnitId())
                                .getModelName(), 7);
                
                createStyledCell(row, dateCellStyle,
                    transition.getTransitionDate().toString(), 6);
                
                createStyledCell(row, alignCenterStyle,
                        module.getClientName(transition.getClientId()), 5);
            }
        }
        
        if (!restoredUnitsIds.isEmpty()) {
            rowNumber += 2;

            Row restorationStrRow = sheet.createRow(rowNumber);
            Cell restorationStrCell = restorationStrRow.createCell(10);
            restorationStrCell.setCellValue(restoredStr);
            restorationStrCell.setCellStyle(headerCellStyle);
            rowNumber++;

            Row restorationHeaderRow = sheet.createRow(rowNumber);

            for (int i = 0; i < clientRestoredUnits.length; i++) {
                createStyledCell(restorationHeaderRow, headerCellStyle,
                        clientRestoredUnits[i], i+6);
            }
            
            for (RestorationModel restoredUnit : restoredUnits) {
                rowNumber++;
                Row row = sheet.createRow(rowNumber);

                createStyledCell(row, alignCenterStyle, module.
                        getBuildingName(RestoredUnitsIdDataMap.get(restoredUnit.
                                getUnitId()).getBuildingId()), 9);
                
                switch (RestoredUnitsIdDataMap.
                        get(restoredUnit.getUnitId()).getType()) {
                    case 'h':
                        createStyledCell(row, alignCenterStyle,
                                String.valueOf(RestoredUnitsIdDataMap.
                                        get(restoredUnit.getUnitId()).
                                        getFloorNumber()), 8);
                        break;
                    case 'c':
                        createStyledCell(row, alignCenterStyle, "", 8);
                        break;
                }
                
                createStyledCell(row, alignCenterStyle, RestoredUnitsIdDataMap.
                        get(restoredUnit.getUnitId()).getModelName(), 7);

                createStyledCell(row, dateCellStyle,
                    restoredUnit.getRestorationDate().toString(), 6);
            }
        }
        
        rowNumber += 2;
        Row unitsHeaderRow = sheet.createRow(rowNumber);
        
        for(int i = 0; i < unitsHeader.length ; i++) {
            createStyledCell(unitsHeaderRow, headerCellStyle,
                    unitsHeader[i], i+1);
        }
        
        for(UnitModel unit : clientUnits){
            rowNumber ++;
            Row row = sheet.createRow(rowNumber);
            
            createStyledCell(row, alignCenterStyle,
                    module.getBuildingName(unit.getBuildingId()), 7);
            
            createStyledCell(row, alignCenterStyle,
                    String.valueOf(unit.getUnitCode()), 6);
            
            String contractDateStr = "";
            if(unit.getReceptionDate() != null){
                contractDateStr = unit.getContructDate().toString();
            }
            
            createStyledCell(row, dateCellStyle, contractDateStr, 5);
            
            String receiptionDateStr = "";
            if(unit.getReceptionDate() != null){
                receiptionDateStr = unit.getReceptionDate().toString();
            }
            
            createStyledCell(row, dateCellStyle, receiptionDateStr, 4);
            
            createStyledCell(row, dateCellStyle, 
                    String.valueOf(unit.getDiscountRatio()), 3);
            
            createStyledCell(row, dateCellStyle, String.format("%.1f",
                    module.getTotalInstallmentsValues(unit.getId())), 2);
            
            createStyledCell(row, dateCellStyle, String.format("%.1f",
                    module.getTotalPaidInstallments(unit.getId())), 1);
            
        }
        
        rowNumber += 2;
        Row headerRow = sheet.createRow(rowNumber);
        
        for(int i = 0; i < clientFinancialStatusRepo.length ; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    clientFinancialStatusRepo[i], i+1);
        }
        
        for(InstallmentModel installment: installments){
            rowNumber ++;
            Row row = sheet.createRow(rowNumber);
            
            createStyledCell(row, alignCenterStyle,
                    module.getBuildingName(installment.getBuildingId()), 9);
            
            createStyledCell(row, alignCenterStyle,
                    unitsIdDataMap.get(installment.getUnitId()).getModelName(),
                    7);
            
            String unitType = "";
            switch(unitsIdDataMap.
                            get(installment.getUnitId()).getType()){
                case 'h':
                    unitType = "سكنى";
                    createStyledCell(row, alignCenterStyle, 
                    String.valueOf(unitsIdDataMap.
                            get(installment.getUnitId()).getFloorNumber()), 8);
                    break;
                case 'c':
                    unitType = "تجارى";
                    createStyledCell(row, alignCenterStyle, "", 8);
                    break;
            }
            createStyledCell(row, alignCenterStyle, unitType, 6);
            
            createStyledCell(row, alignCenterStyle,
                    installment.getPaymentName(), 5);
            
            createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", installment.getInstallmentValue()),
                    4);
            
            createStyledCell(row, dateCellStyle,
                    installment.getInstallmentDate().toString(), 3);
            
            createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", installment.
                            getInstallmentPaidValue()), 2);
            
            createStyledCell(row, alignCenterStyle,
                    String.valueOf(installment.getId()), 1);
        }
        
        sheet.autoSizeColumn(11);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(0);
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير مفصل لعميل.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void lateInstallments() {
        String[] lateInstlmntsColumns
                = {"اسم المبني", "بقيمة", "بتاريخ", "اسم الدفعه", "رقم الهاتف",
                    "العميل","كود العميل"};

        LocalDate todayDate = LocalDate.now();
        java.sql.Date todaySqlDate = java.sql.Date.valueOf(todayDate);

        List<InstallmentModel> lateInstallments = module.
                getLateInstallments(todaySqlDate);

        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("projectIncome4Month");

        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);

        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير متاخرات ");
        sheet.getHeader().setLeft(LocalDate.now().toString());

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(4);

        for (int i = 0; i < lateInstlmntsColumns.length; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    lateInstlmntsColumns[i], i + 2);
        }

        int rowNum = 5;
        float totalIncome = 0;
        
        for (InstallmentModel installment : lateInstallments) {
            rowNum++;
            
            Row row = sheet.createRow(rowNum);

            int clientId = installment.getClientId();

            createStyledCell(row, alignCenterStyle,
                    String.valueOf(clientId), 8);

            createStyledCell(row, alignCenterStyle,
                    module.getClientName(clientId), 7);

            createStyledCell(row, alignCenterStyle,
                    "0"+module.getClientPhone(clientId), 6);

            createStyledCell(row, alignCenterStyle,
                    installment.getPaymentName(), 5);

            createStyledCell(row, dateCellStyle,
                    installment.getInstallmentDate().toString(), 4);

            float installmentValue = installment.getInstallmentValue();
            createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", installmentValue), 3);

            createStyledCell(row, alignCenterStyle,
                    module.getBuildingName(installment.getBuildingId()), 2);

            totalIncome += installmentValue;

        }
        Row row = sheet.createRow(rowNum+2);

        createStyledCell(row, alignCenterStyle, "الاجمالى", 7);
        createStyledCell(row, alignCenterStyle,
                String.format("%.1f", totalIncome), 3);

        for (int i = 0; i < lateInstlmntsColumns.length; i++) {
            sheet.autoSizeColumn(i + 2);
        }

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير المتاخرات.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.class.getName()).
                    log(Level.SEVERE, null, ex);
        }   
    }
   
    public void restoredClientsRepo(){
        
        List<Integer> restoredUnitsIds = new ArrayList();
        List<RestorationModel> restoredUnits = module.
                getRestoredUnits(restoredUnitsIds);
        Map<Integer, UnitModel> unitIdUnitDataMap = module.
                getUnitIdUnitDataMap(restoredUnitsIds);
        String[] restoredClientsRepoColumns = 
        {"ت الاغاء","النموذج","الدور","المبنى","الهاتف","العميل","كود العميل"};
        
        Workbook workbook = new XSSFWorkbook();
        
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("projectUnitsStat");
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير الحجوز الملغاه ");
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Row headerRow = sheet.createRow(4);
        
        for(int i = 0; i < restoredClientsRepoColumns.length ; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    restoredClientsRepoColumns[i], i+2);
        }
        
        int rowNum = 5;
        
        for(RestorationModel unit : restoredUnits){
            rowNum ++;
            Row row = sheet.createRow(rowNum);
            int clientId = unit.getClientId();
            
            createStyledCell(row, alignCenterStyle,
                    String.valueOf(clientId), 8);
            
            createStyledCell(row, alignCenterStyle,
                    module.getClientName(clientId), 7);
            
            createStyledCell(row, alignCenterStyle,
                    "0"+module.getClientPhone(clientId), 6);
            
            createStyledCell(row, alignCenterStyle,
                    module.getBuildingName(unitIdUnitDataMap.
                    get(unit.getUnitId()).getBuildingId()), 5);
            
            switch(unitIdUnitDataMap.get(unit.getUnitId()).getType()){
                case 'h':
                    createStyledCell(row, alignCenterStyle,
                    String.valueOf(unitIdUnitDataMap.
                            get(unit.getUnitId()).getFloorNumber()), 4);
                    break;
                case 'c':
                    createStyledCell(row, alignCenterStyle, "", 4);
                    break;
            }
            
            createStyledCell(row, alignCenterStyle, unitIdUnitDataMap.
                            get(unit.getUnitId()).getModelName(), 3);
            
            createStyledCell(row, dateCellStyle, 
                    unit.getRestorationDate().toString(), 2);
            
        }
         for(int i = 0; i < restoredClientsRepoColumns.length; i++) {
            sheet.autoSizeColumn(i+2);
        }
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير الحجوز الملغاه.xlsx");
        
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void incomeFromSoldUnits(){
        
        List <UnitModel> unitsList = module.getBusyUnits();
        
        String[] allRsrvdUnitsRepoColumns = 
        {"المتحصل","النموذج","الدور","نوع"," المشروع","العميل","كود العميل"};
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("projectIncome4Month");
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير اراداتوحدات مباعه ");
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        Row headerRow = sheet.createRow(4);
        
        for(int i = 0; i < allRsrvdUnitsRepoColumns.length ; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    allRsrvdUnitsRepoColumns[i], i);
        }
        int rowNum = 5;
        float totalIncome = 0;
        
        for(UnitModel unit : unitsList){
            
            
            Row row = sheet.createRow(rowNum++);
            
            createStyledCell(row, alignCenterStyle,
                    String.valueOf(unit.getClientId()), 6);
            
            createStyledCell(row, alignCenterStyle,
                    module.getClientName(unit.getClientId()), 5);
            
            createStyledCell(row, alignCenterStyle,
                    module.getBuildingName(unit.getBuildingId()), 4);
            
            String unitType = "";
            switch(unit.getType()){
                case 'h':
                    unitType = "سكنى";
                    createStyledCell(row, alignCenterStyle,
                            String.valueOf(unit.getFloorNumber()), 2);
                    break;
                case 'c':
                    unitType = "تجارى";
                    createStyledCell(row, alignCenterStyle, "", 2);
                    break;
            }
           
            createStyledCell(row, alignCenterStyle, unitType, 3);
            
            createStyledCell(row, alignCenterStyle, unit.getModelName(), 1);
            
            float income = module.getTotalPaidInstallments(unit.getId(),
                    unit.getClientId());
            
            createStyledCell(row, alignCenterStyle,
                    String.format("%.1f", income), 0);
            
            totalIncome += income;
            
        }
        rowNum ++;
        Row row = sheet.createRow(rowNum++);
        
        createStyledCell(row, alignCenterStyle, "الاجمالى", 5);
        createStyledCell(row, alignCenterStyle,
                String.format("%.1f", totalIncome), 0);
        
        for(int i = 0; i < allRsrvdUnitsRepoColumns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير ارادات وحدات مباعه.xlsx");
        
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void salesInSpecPeriod(String fromMon, String fromYear,
            String toMon, String toYear){
        
        String[] salesInPeriodRepoColumns = 
            {"بقيمة","العميل","كود العميل"};
        
        String fromDate = fromYear + "-" + fromMon + "-01",
                    toStartingDate = toYear + "-" + toMon + "-01";
        
        LocalDate toStartingLocalDate = LocalDate.parse(toStartingDate);
        int endDayInt = toStartingLocalDate.lengthOfMonth();
        String toDate = toYear + "-" + toMon + "-" + endDayInt;
        
        LocalDate fromLocalDate = LocalDate.parse(fromDate),
                toLocalDate = LocalDate.parse(toDate);
        
        java.sql.Date fromSqlDate = java.sql.Date.valueOf(fromLocalDate),
                        toSqlDate = java.sql.Date.valueOf(toLocalDate);
        
        List<Integer> clientsId = module.getClientsPaidInPeriod(fromSqlDate,
                toSqlDate);
        
        
        Workbook workbook = new XSSFWorkbook();
        
        Sheet sheet = workbook.createSheet("projectIncome4APeriod");
        
        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        
        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير مبيعات لفتره");
        sheet.getHeader().setLeft(LocalDate.now().toString());
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        Row headerRow = sheet.createRow(4);
        
        for(int i = 0; i < salesInPeriodRepoColumns.length ; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    salesInPeriodRepoColumns[i], i+5);
        }
        
        int rowNum = 5;
        float totalIncome = 0;
        for(Integer clientId : clientsId){
            Row row = sheet.createRow(rowNum++);
            
                    
            createStyledCell(row, alignCenterStyle,
                    String.valueOf(clientId), 7);
            
            createStyledCell(row, alignCenterStyle,
                    module.getClientName(clientId), 6);
            
            float income = module.getTotalPaidInstallments(clientId,
                    fromSqlDate, toSqlDate);
            
            createStyledCell(row, alignCenterStyle, 
                    String.format("%.1f", income), 5);
            
            totalIncome += income;
        }
        
        Row row = sheet.createRow(rowNum+2);
        createStyledCell(row, alignCenterStyle, "الاجمالى", 6);
        createStyledCell(row, alignCenterStyle,
                String.format("%.1f", totalIncome), 5);
        
        for(int iterator = 0; iterator < salesInPeriodRepoColumns.length;
                iterator++) {
            sheet.autoSizeColumn(iterator+2);
        }
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير مبيعات لفتره.xlsx");
        
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.
                    class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cashReports(){
        Map <Integer, InstallmentModel> IdInstallmentMap = new HashMap();
        List<ReceiptModel> receipts = module.getCashReceipts(IdInstallmentMap);
        String[] headerStrings = {"بتاريخ","بقيمة","ايصال","اسم الدفعه",
            "رقم الدفعه","المبنى","اسم العميل","كود العميل"};
        
        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("checksReport");

        CellStyle alignCenterStyle = workbook.createCellStyle();
        alignCenterStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat()
                .getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);

        sheet.getHeader().setCenter("شركة الصرح");
        sheet.getHeader().setRight("تقرير متاخرات ");
        sheet.getHeader().setLeft(LocalDate.now().toString());

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(4);

        for (int i = 0; i < headerStrings.length; i++) {
            createStyledCell(headerRow, headerCellStyle,
                    headerStrings[i], i+1);
        }
        int rowNum = 5;
        
        for(ReceiptModel receipt : receipts){
            rowNum++;
            Row row = sheet.createRow(rowNum);
            
            createStyledCell(row, alignCenterStyle,
                    String.valueOf(IdInstallmentMap.get(receipt.getId()).
                            getClientId()), 8);
            
            createStyledCell(row, alignCenterStyle,
                    module.getClientName(IdInstallmentMap.get(receipt.getId()).
                            getClientId()), 7);
            
            createStyledCell(row, alignCenterStyle,
                    module.getBuildingName(IdInstallmentMap.get(receipt.getId()).
                            getBuildingId()), 6);
            
            createStyledCell(row, alignCenterStyle, 
                    String.valueOf(receipt.getId()), 5);
            
            createStyledCell(row, alignCenterStyle,
                    IdInstallmentMap.get(receipt.getId()).getPaymentName(), 4);
            
            createStyledCell(row, alignCenterStyle, receipt.getReceiptId(), 3);
            
            createStyledCell(row, alignCenterStyle, String.format("%.1f",
                    receipt.getReceiptValue()), 2);
            
            createStyledCell(row, dateCellStyle, 
                    receipt.getReceiptDate().toString(), 1);
        }
        
        for (int i = 0; i < headerStrings.length; i++) {
            sheet.autoSizeColumn(i + 1);
        }

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("تقرير شيكات.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsUtility.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportsUtility.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    private void createStyledCell(Row row, CellStyle style,
            String cellValue, int cellNumber){
        
        Cell cell = row.createCell(cellNumber);
        cell.setCellValue(cellValue);
        cell.setCellStyle(style);
    }
}