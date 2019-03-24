package com.khalifa.elsarh.controllers;

import static com.khalifa.elsarh.controllers.ClientProfileController.clientUnits;
import static com.khalifa.elsarh.controllers.ClientProfileController.clientRestoredUnits;
import static com.khalifa.elsarh.controllers.ClientProfileController.clientTransitionedUnits;
import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import com.khalifa.elsarh.modules.ClientUnitsModule;
import com.khalifa.elsarh.pojos.InstallmentModel;
import static com.khalifa.elsarh.pojos.InstallmentModel.ADDITIONAL_INSTALLMENT;
import static com.khalifa.elsarh.pojos.InstallmentModel.ADJUSTMENT_INSTALLMENT;
import static com.khalifa.elsarh.pojos.InstallmentModel.ESSENTIAL_INSTALLMENT;
import static com.khalifa.elsarh.pojos.InstallmentModel.FACILITIES_INSTALLMENT;
import static com.khalifa.elsarh.pojos.InstallmentModel.MAINTENANCE_INSTALLMENT;
import com.khalifa.elsarh.pojos.ReceiptModel;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class ClientUnitsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane editingAnchorPn, showingAnchorPn, installmentAnchPn,
            createdInstlmntsAnchPn, savedInstlmntsAnchPn, paymentsEditingAnchPn,
            cashPaymentAnchPn, unitOptionsPane, transformationPane,
            payInstlmntPane, editInstlmntPane, editReceiptsAnchPn;

    @FXML
    private ToggleGroup clientUnitsToggleGroup, generalDealingToggleGroup,
            editingToggleGroup, showingToggleGroup, restorationToggleGroup;

    @FXML
    private HBox unitsHB, buildingNameHBox, financialHBox, bankUpCheckHB,
            generalDealingHBox, editingHBox, showingHBox, unitFinInfoHB,
            essentialPaymentHB, additionalPaymentHB, totalRequiredHB,
            adjustmentPaymentHB, facilitiesPaymentHB, maintenancePaymentHB,
            financialDealingHB, reservationPaymentHB, installmentHB,
            unitPriceHB, editingOptionsHB, PaymentCode4PayingHB, payingHB,
            addInstallmentHB, cashOptionsHB, cashRecieptHB, cashUnitPriceHB,
            cashAfterDiscountHB, cashDateHB, receptUnitHB, restorationHB,
            existedClientInfoHB, existedClientHB, clientInfoTopLayerHB,
            clientInfoBottomLayerHB, payInstlmntFirstHB, payInstlmntSecondHB,
            payInstlmntThirdHB, editInstlmntFirstHB, editInstlmntSecondHB,
            editInstallmentOptionsHB, transferLayerHB, reinstallationHB,
            restorInstallmentHB, cancelReservationHB, firstHead2HB,  
            secondHead2HB, restorInstallment2LayerHB, restorInstallment3LayerHB,
            restorationCheckHB, restorationCheckDateHB, cashCheckDataHB,
            cashCheckDateHB, checkInputsHB, checkDataHB;

    @FXML
    private VBox unitDetailsVBox, cashPaymentVB, payInstallmentVB, bankingUpVB,
            editInstallmentVB;

    @FXML
    private ScrollPane instalmntScPn, instalmntShowingScPn, editReceiptsScPn;

    @FXML
    private Text buildingLocationTxt, unitTypeTxt, unitFloorTxt, bankTxt,
            unitCodeTxt, unitModelTxt, showUnitPriceTxt, discountRatioTxt,
            unitPriceAfterDiscountTxt, essentialPaymentValueTxt, payingDateTxt,
            paidEssentialCashTxt, ModelAreaTxt, remainedEssentialTxt,
            additionalPaymentValueTxt, adjustmentPaymentValueTxt, 
            paidAdjustmentCashTxt, paidAdditionalCashTxt, remainedAdditionalTxt,
            contractDateTxt, totalRequiredCashTxt, totalPaidCashTxt,
            totalRemainedTxt, unitPriceTxt, paidFromUnitPriceTxt, 
            remainedFromUnitPriceTxt, priceAfterDiscountTxt, unitPriceCashTxt,
            unitRemainedCashTxt, existedClientNameTxt, paidFacilitiesCashTxt,
            existedClientPersonalIdTxt, cashPriceTxt, existedClientPhoneNoTxt,
            transformationHeadTxt, installmentDateTxt, paymentName4PayingTxt,
            paymentValue4PayingTxt, restorationDateTxt, delayDays, delayMonths,
            remainedInstlmntCashTxt, mulctValueTxt, installmentedValueTxt,
            remainedAdjustmentTxt, facilitiesPaymentValueTxt, handingOverDateTxt, 
            remainedFacilitiesTxt, maintenancePaymentValueTxt, paymentDateStr,
            paidMaintenanceCashTxt, remainedMaintenanceTxt, setUnitFreeTxt;
    
    @FXML
    private ToggleButton showFinInfoTB, showingTB, showEditingTabsTB,
            financialDealingTB, installmentTB, showInstlmntsTB,
            installmentsEditingTB, cashTB, unitDealingTB, transformationTB;

    @FXML
    private RadioButton editPaymentRB, payPaymentRB, addPaymentRB,
            restorInstallmentRB, newClientRB, paymentTypeRB, cachCheckRB,
            restorationFromPrimaryRB, restorationFromAdditionalRB,
            restorationFromFacilitiesRB, restorationFromMaintenanceRB,
            restorationFromAdjustmentRB, restorationCheckRB;

    @FXML
    private ComboBox addPaymentCB, paymentName4editingCB, reservationCB;

    @FXML
    private TextField startInstlmntDayTxtFld, startInstlmntMonthTxtFld,
            milestonePeriodTxtFld, discountRationTxtFld, reservationValueTxtFld,
            reservationDayTxtFld, reservationMonthTxtFld, reservationYearTxtFld,
            receptionDayTxtFld, addInstallmentValueTxtFld, receptionMonthTxtFld,
            receptionYearTxtFld, newClientNameTxtFld, newClientPersonalIdTxtFld,
            editInstallmentDayTxtFld, editPaidValueTxtFld, bankTxtFld,
            instlmntsCountTxtFld, addInstlmntMonthTxtFld, addInstlmntYearTxtFld,
            paymentCode4EditingTxtFld, startInstlmntYearTxtFld, payingDayTxtFld,
            cashPaymentDayTxtFld, cashPaymentMonthTxtFld, cashPaymentYearTxtFld,
            restorationDayTxtFld, restorationMonthTxtFld, restorationYearTxtFld,
            restorInstlmntDayTxtFld, cashDiscountRetioTxtFld, clientCodeTxtFld,
            paymentCode4PayingTxtFld, paidValueTxtFld, recieptNo4PayingTxtFld,
            newClientMobileNumberTxtFld, newClientCareerTxtFld, recieptTxtFld,
            restoredValueTxtFld, paymentValue4editingTxtFld, payingYearTxtFld,
            payingMonthTxtFld, restorationReceiptTxtFld, addInstlmntDayTxtFld,
            editInstallmentMonthTxtFld, editInstallmentYearTxtFld,
            restorInstlmntMonthTxtFld, restorInstlmntYearTxtFld, checkIdTxtFld,
            mulctRatioTxtFld, resotationBankTxtFld, restoredCheckIdTxtFld,
            resotationCheckDayTxtFld, resotationCheckMonTxtFld, checkValueTxtFld,
            resotationCheckYearTxtFld, cashCheckBankTxtFld, cashCheckIdTxtFld,
            DayTxtFld, MonTxtFld, YearTxtFld, checkIdValidationTxtFld,
            installmentIdValidationTxtFld, bankUpDayTxtFld, bankUpMonthTxtFld,
            bankUpYearTxtFld;

    @FXML
    private Button addReservInstlmntBtn, payCashBtn, addCashCheckBtn,
            restorationBtn, transformationBtn, cancelReservationBtn,
            receptionBtn, reinstallationBtn;

    private float selectedUnitPrice = 0, paidCashFromUnitPrice,
            discoutRatio = 0, installmentValue4Paying = 0,
            priceAfterDiscount = 0, totalPreviousClientEssentials,
            totalPreviousClientAdjustments, totalPreviousClientFacilities,
            totalPreviousClientMaintenance, previousTotalRequired,
            totalPreviousClientPaidfromEssentials,
            totalPreviousClientAdditionals, previousTotalPaid,
            totalPreviousClientPaidfromAdditionals,
            totalPreviousClientPaidfromAdjustments,
            totalPreviousClientPaidfromFacilities,
            totalPreviousClientPaidfromMaintenance;

    private String selectedUnitType, selectedUnitModel;

    private ClientUnitsModule module;
    private int selectedUnitId, clientId, selectedBuildingId, selectedUnitCode;

    private Map<String, String> installmentsTypeMap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        module = new ClientUnitsModule();
        installmentsTypeMap = module.getInstallmentsType();
        
        unitFinInfoHB.setSpacing(scenesWidth * 0.03);
        essentialPaymentHB.setSpacing(scenesWidth * 0.1);
        adjustmentPaymentHB.setSpacing(scenesWidth * 0.1);
        facilitiesPaymentHB.setSpacing(scenesWidth * 0.1);
        maintenancePaymentHB.setSpacing(scenesWidth * 0.1);
        additionalPaymentHB.setSpacing(scenesWidth * 0.1);
        totalRequiredHB.setSpacing(scenesWidth * 0.1);
        reservationPaymentHB.setSpacing(scenesWidth * 0.05);
        installmentHB.setSpacing(scenesWidth * 0.05);
        unitPriceHB.setSpacing(scenesWidth * 0.04);
        editingOptionsHB.setSpacing(scenesWidth * 0.08);
        cashOptionsHB.setSpacing(scenesWidth * 0.1);
        cashRecieptHB.setSpacing(scenesWidth * 0.05);
        cashAfterDiscountHB.setSpacing(scenesWidth * 0.05);
        cashDateHB.setSpacing(scenesWidth * 0.05);
        cashCheckDataHB.setSpacing(scenesWidth * 0.03);
        checkDataHB.setSpacing(scenesWidth * 0.05);
        checkInputsHB.setSpacing(scenesWidth * 0.05);
        PaymentCode4PayingHB.setSpacing(scenesWidth * 0.02);
        bankUpCheckHB.setSpacing(scenesWidth * 0.02);
        payingHB.setSpacing(scenesWidth * 0.05);
        addInstallmentHB.setSpacing(scenesWidth * 0.03);
        cashPaymentVB.setSpacing(scenesHeight * 0.02);
        receptUnitHB.setSpacing(scenesWidth * 0.03);
        reinstallationHB.setSpacing(scenesWidth * 0.12);
        cancelReservationHB.setSpacing(scenesWidth * 0.16);
        restorationHB.setSpacing(scenesWidth * 0.03);
        restorInstallmentHB.setSpacing(scenesWidth * 0.03);
        restorInstallment2LayerHB.setSpacing(scenesWidth * 0.03);
        restorationCheckHB.setSpacing(scenesWidth * 0.03);
        existedClientHB.setSpacing(scenesWidth * 0.03);
        existedClientInfoHB.setSpacing(scenesWidth * 0.05);
        clientInfoTopLayerHB.setSpacing(scenesWidth * 0.05);
        clientInfoBottomLayerHB.setSpacing(scenesWidth * 0.05);
        transferLayerHB.setSpacing(scenesWidth * 0.03);
        payInstlmntFirstHB.setSpacing(scenesWidth * 0.1);
        payInstlmntSecondHB.setSpacing(scenesWidth * 0.05);
        payInstlmntThirdHB.setSpacing(scenesWidth * 0.03);
        editInstlmntFirstHB.setSpacing(scenesWidth * 0.1);
        editInstlmntSecondHB.setSpacing(scenesWidth * 0.1);
        firstHead2HB.setSpacing(scenesWidth * 0.03);
        secondHead2HB.setSpacing(scenesWidth * 0.03);
        payInstallmentVB.setSpacing(scenesHeight * 0.03);
        bankingUpVB.setSpacing(scenesHeight * 0.03);
        editInstallmentVB.setSpacing(scenesHeight * 0.03);
        cashUnitPriceHB.setSpacing(scenesWidth * 0.05);

        addPaymentCB.getItems().addAll(installmentsTypeMap.keySet());
        paymentName4editingCB.getItems().addAll(installmentsTypeMap.keySet());
        reservationCB.getItems().addAll(installmentsTypeMap.keySet());

        AnchorPane.setTopAnchor(buildingNameHBox, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(unitDetailsVBox, scenesHeight * 0.15);
        AnchorPane.setTopAnchor(financialHBox, scenesHeight * 0.4);
        AnchorPane.setTopAnchor(generalDealingHBox, scenesHeight * 0.46);
        AnchorPane.setTopAnchor(editingHBox, scenesHeight * 0.5);
        AnchorPane.setTopAnchor(showingHBox, scenesHeight * 0.5);
        AnchorPane.setTopAnchor(editingAnchorPn, scenesHeight * 0.56);
        AnchorPane.setTopAnchor(unitOptionsPane, scenesHeight * 0.56);
        AnchorPane.setTopAnchor(transformationPane, scenesHeight * 0.56);
        AnchorPane.setTopAnchor(showingAnchorPn, scenesHeight * 0.56);
        AnchorPane.setTopAnchor(unitFinInfoHB, scenesHeight * 0.05);
        AnchorPane.setTopAnchor(essentialPaymentHB, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(additionalPaymentHB, scenesHeight * 0.14);
        AnchorPane.setTopAnchor(adjustmentPaymentHB, scenesHeight * 0.18);
        AnchorPane.setTopAnchor(facilitiesPaymentHB, scenesHeight * 0.22);
        AnchorPane.setTopAnchor(maintenancePaymentHB, scenesHeight * 0.26);
        AnchorPane.setTopAnchor(totalRequiredHB, scenesHeight * 0.3);
        AnchorPane.setTopAnchor(financialDealingHB, scenesHeight * 0.006);
        AnchorPane.setTopAnchor(installmentAnchPn, scenesHeight * 0.06);
        AnchorPane.setTopAnchor(paymentsEditingAnchPn, scenesHeight * 0.06);
        AnchorPane.setTopAnchor(cashPaymentAnchPn, scenesHeight * 0.06);
        AnchorPane.setTopAnchor(installmentHB, scenesHeight * 0.06);
        AnchorPane.setTopAnchor(unitPriceHB, scenesHeight * 0.11);
        AnchorPane.setTopAnchor(instalmntScPn, scenesHeight * 0.155);
        AnchorPane.setTopAnchor(instalmntShowingScPn, scenesHeight * 0.56);
        AnchorPane.setTopAnchor(editingOptionsHB, scenesHeight * 0.02);
        AnchorPane.setTopAnchor(payingHB, scenesHeight * 0.07);
        AnchorPane.setTopAnchor(addInstallmentHB, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(cashPaymentVB, scenesHeight * 0.0);
        AnchorPane.setTopAnchor(reinstallationHB, scenesHeight * 0.07);
        AnchorPane.setTopAnchor(cancelReservationHB, scenesHeight * 0.15);
        AnchorPane.setTopAnchor(receptUnitHB, scenesHeight * 0.3);
        AnchorPane.setTopAnchor(restorationHB, scenesHeight * 0.23);
        AnchorPane.setTopAnchor(existedClientHB, scenesHeight * 0.05);
        AnchorPane.setTopAnchor(existedClientInfoHB, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(transformationHeadTxt, scenesHeight * 0.17);
        AnchorPane.setTopAnchor(clientInfoTopLayerHB, scenesHeight * 0.22);
        AnchorPane.setTopAnchor(clientInfoBottomLayerHB, scenesHeight * 0.27);
        AnchorPane.setTopAnchor(transferLayerHB, scenesHeight * 0.33);
        AnchorPane.setTopAnchor(payInstlmntPane, scenesHeight * 0.15);
        AnchorPane.setTopAnchor(editInstlmntPane, scenesHeight * 0.06);
        AnchorPane.setTopAnchor(payInstallmentVB, scenesHeight * 0.0);
        AnchorPane.setTopAnchor(bankingUpVB, scenesHeight * 0.0);
        AnchorPane.setTopAnchor(paymentCode4EditingTxtFld, scenesHeight * 0.0);
        AnchorPane.setTopAnchor(editInstallmentOptionsHB, scenesHeight * 0.05);
        AnchorPane.setTopAnchor(editInstallmentVB, scenesHeight * 0.12);
        AnchorPane.setTopAnchor(editReceiptsScPn, scenesHeight * 0.12);
        AnchorPane.setTopAnchor(restorInstallmentHB, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(restorInstallment2LayerHB, scenesHeight * 0.16);
        AnchorPane.setTopAnchor(restorationCheckHB, scenesHeight * 0.22);
        AnchorPane.setTopAnchor(restorInstallment3LayerHB, scenesHeight * 0.28);

        AnchorPane.setRightAnchor(unitDetailsVBox, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(generalDealingHBox, scenesWidth * 0.12);
        AnchorPane.setRightAnchor(editingHBox, scenesWidth * 0.14);
        AnchorPane.setRightAnchor(showingHBox, scenesWidth * 0.14);
        AnchorPane.setRightAnchor(editingAnchorPn, scenesWidth * 0.14);
        AnchorPane.setRightAnchor(unitOptionsPane, scenesWidth * 0.14);
        AnchorPane.setRightAnchor(transformationPane, scenesWidth * 0.14);
        AnchorPane.setRightAnchor(showingAnchorPn, scenesWidth * 0.14);
        AnchorPane.setRightAnchor(unitFinInfoHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(essentialPaymentHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(adjustmentPaymentHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(facilitiesPaymentHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(maintenancePaymentHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(additionalPaymentHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(totalRequiredHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(financialDealingHB, scenesWidth * 0.001);
        AnchorPane.setRightAnchor(installmentAnchPn, scenesWidth * 0.0);
        AnchorPane.setRightAnchor(paymentsEditingAnchPn, scenesWidth * 0.0);
        AnchorPane.setRightAnchor(cashPaymentAnchPn, scenesWidth * 0.0);
        AnchorPane.setRightAnchor(reservationPaymentHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(installmentHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(unitPriceHB, scenesWidth * 0.02);
        AnchorPane.setRightAnchor(instalmntShowingScPn, scenesWidth * 0.14);
        AnchorPane.setRightAnchor(editingOptionsHB, scenesWidth * 0.17);
        AnchorPane.setRightAnchor(addInstallmentHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(cashPaymentVB, scenesWidth * 0.13);
        AnchorPane.setRightAnchor(reinstallationHB, scenesWidth * 0.24);
        AnchorPane.setRightAnchor(cancelReservationHB, scenesWidth * 0.24);
        AnchorPane.setRightAnchor(receptUnitHB, scenesWidth * 0.24);
        AnchorPane.setRightAnchor(restorationHB, scenesWidth * 0.24);
        AnchorPane.setRightAnchor(existedClientHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(existedClientInfoHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(transformationHeadTxt, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(clientInfoTopLayerHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(clientInfoBottomLayerHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(transferLayerHB, scenesWidth * 0.07);
        AnchorPane.setRightAnchor(payInstallmentVB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(bankingUpVB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(paymentCode4EditingTxtFld, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(editInstallmentVB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(restorInstallmentHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(restorInstallment2LayerHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(restorationCheckHB, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(restorInstallment3LayerHB, scenesWidth * 0.1);

        paymentTypeRB.setOnAction((event) -> {
            if(paymentTypeRB.isSelected()){
                paymentDateStr.setText("");
                paymentDateStr.setText("تاريخ استحقاق: ");
                paidValueTxtFld.setDisable(true);
            } else {
                paymentDateStr.setText("");
                paymentDateStr.setText("تاريخ الدفع: ");
                paidValueTxtFld.setDisable(false);
            }
        });
        
        showUnitPriceTxt.setCache(false);
        hideUI();
        initUnitsToggles();
        visiblityOptions();
    }

    private void visiblityOptions() {
        showingHBox.visibleProperty().bind(showingTB.selectedProperty());
        editingHBox.visibleProperty()
                .bind(showEditingTabsTB.selectedProperty());
        showingAnchorPn.visibleProperty()
                .bind(showFinInfoTB.selectedProperty());
        editingAnchorPn.visibleProperty()
                .bind(financialDealingTB.selectedProperty());
        installmentAnchPn.visibleProperty()
                .bind(installmentTB.selectedProperty());
        reservationPaymentHB.visibleProperty()
                .bind(installmentAnchPn.visibleProperty());
        installmentHB.visibleProperty()
                .bind(installmentAnchPn.visibleProperty());
        unitPriceHB.visibleProperty()
                .bind(installmentAnchPn.visibleProperty());
        instalmntShowingScPn.visibleProperty()
                .bind(showInstlmntsTB.selectedProperty());
        paymentsEditingAnchPn.visibleProperty()
                .bind(installmentsEditingTB.selectedProperty());
        editingOptionsHB.visibleProperty()
                .bind(paymentsEditingAnchPn.visibleProperty());
        payingHB.visibleProperty()
                .bind(payPaymentRB.selectedProperty());
        addInstallmentHB.visibleProperty()
                .bind(addPaymentRB.selectedProperty());
        restorInstallmentHB.visibleProperty()
                .bind(restorInstallmentRB.selectedProperty());
        restorInstallment2LayerHB.visibleProperty()
                .bind(restorInstallmentRB.selectedProperty());
        restorationCheckHB.visibleProperty()
                .bind(restorInstallmentRB.selectedProperty());
        restorInstallment3LayerHB.visibleProperty()
                .bind(restorInstallmentRB.selectedProperty());
        cashPaymentAnchPn.visibleProperty()
                .bind(cashTB.selectedProperty());
        cashPaymentVB.visibleProperty()
                .bind(cashPaymentAnchPn.visibleProperty());
        unitOptionsPane.visibleProperty()
                .bind(unitDealingTB.selectedProperty());
        transformationPane.visibleProperty()
                .bind(transformationTB.selectedProperty());
        existedClientHB.visibleProperty()
                .bind(transformationPane.visibleProperty());
        existedClientInfoHB.visibleProperty()
                .bind(transformationPane.visibleProperty());
        transformationHeadTxt.visibleProperty()
                .bind(transformationPane.visibleProperty());
        clientInfoTopLayerHB.visibleProperty()
                .bind(transformationPane.visibleProperty());
        clientInfoBottomLayerHB.visibleProperty()
                .bind(transformationPane.visibleProperty());
        transferLayerHB.visibleProperty()
                .bind(transformationPane.visibleProperty());
        payInstlmntPane.visibleProperty()
                .bind(payPaymentRB.selectedProperty());
        editInstlmntPane.visibleProperty()
                .bind(editPaymentRB.selectedProperty());
        resotationBankTxtFld.visibleProperty()
                .bind(restorationCheckRB.selectedProperty());
        restoredCheckIdTxtFld.visibleProperty()
                .bind(restorationCheckRB.selectedProperty());
        restorationCheckDateHB.visibleProperty()
                .bind(restorationCheckRB.selectedProperty());
        bankTxtFld.visibleProperty().bind(paymentTypeRB.selectedProperty());
        checkIdTxtFld.visibleProperty().bind(paymentTypeRB.selectedProperty());
        cashCheckBankTxtFld.visibleProperty().
                bind(cachCheckRB.selectedProperty());
        cashCheckIdTxtFld.visibleProperty().
                bind(cachCheckRB.selectedProperty());
        cashCheckDateHB.visibleProperty().bind(cachCheckRB.selectedProperty());
    }

    private void hideUI() {
        buildingNameHBox.setVisible(false);
        unitDetailsVBox.setVisible(false);
        financialHBox.setVisible(false);
        instalmntScPn.setVisible(false);
        payInstallmentVB.setVisible(false);
        bankingUpVB.setVisible(false);
        generalDealingHBox.setVisible(false);
        editInstallmentVB.setVisible(false);
        editReceiptsScPn.setVisible(false);
        
        generalDealingToggleGroup.selectToggle(null);
        editingToggleGroup.selectToggle(null);
        showingToggleGroup.selectToggle(null);
    }

    private void showUI() {
        hideUI();
        buildingNameHBox.setVisible(true);
        unitDetailsVBox.setVisible(true);
        financialHBox.setVisible(true);
        generalDealingHBox.setVisible(true);
        generalDealingHBox.setVisible(true);
        cashTB.setDisable(false);
        installmentTB.setDisable(false);
        addPaymentRB.setDisable(false);
        payPaymentRB.setDisable(false);
        transformationTB.setDisable(false);
        unitDealingTB.setDisable(false);
        restorationDateTxt.setText("");
        remainedEssentialTxt.setVisible(true);
        remainedAdditionalTxt.setVisible(true);
        remainedAdjustmentTxt.setVisible(true);
        remainedFacilitiesTxt.setVisible(true);
        remainedMaintenanceTxt.setVisible(true);
        totalRemainedTxt.setVisible(true);
    }

    private void showUI4RestoredUnit(int resoterdUnitId) {
        hideUI();
        buildingNameHBox.setVisible(true);
        unitDetailsVBox.setVisible(true);
        financialHBox.setVisible(true);
        generalDealingHBox.setVisible(true);
        generalDealingHBox.setVisible(true);
        cashTB.setDisable(true);
        installmentTB.setDisable(true);
        addPaymentRB.setDisable(true);
        payPaymentRB.setDisable(true);
        transformationTB.setDisable(true);
        unitDealingTB.setDisable(true);
        cashTB.setDisable(true);
        installmentTB.setDisable(true);
        remainedEssentialTxt.setVisible(false);
        remainedAdditionalTxt.setVisible(false);
        remainedAdjustmentTxt.setVisible(false);
        remainedFacilitiesTxt.setVisible(false);
        remainedMaintenanceTxt.setVisible(false);
        totalRemainedTxt.setVisible(false);
        
        Date restorationDate = module.getRestorationDate(resoterdUnitId,
                clientId),
                contractDate = module.getContractDateFromHistory(resoterdUnitId,
                        clientId);
        contractDateTxt.setText("ت.التعاقد" + contractDate);
        restorationDateTxt.setText(" ت.استرجاع" + restorationDate);
    }

    private void initUnitsToggles() {
        
        int i = 0;
        // this list due to Concurrent Modification Exception
        List<Integer> tempRestoredUnits = new ArrayList();
        List<Integer> tempTransitionedUnits = new ArrayList();

        
        for (Integer restoredUnitId : clientTransitionedUnits) {
            if (clientRestoredUnits.contains(restoredUnitId)) {
                tempTransitionedUnits.add(restoredUnitId);
            }
        }
        clientTransitionedUnits.removeAll(tempTransitionedUnits);
        
        //this for loop made for : if the client restore a unit and buy it again
        for (Integer restoredUnitId : clientRestoredUnits) {
            if (clientUnits.contains(restoredUnitId)) {
                tempRestoredUnits.add(restoredUnitId);
            }
        }

        clientRestoredUnits.removeAll(tempRestoredUnits);
        
        clientUnits.removeAll(clientTransitionedUnits);

        for (final Integer unitId : clientUnits) {
            i++;
            final ToggleButton toggleButton = new ToggleButton();
            toggleButton.setText("" + i);
            toggleButton.setPrefWidth((scenesWidth / 0.799)
                    / (clientUnits.size() + clientRestoredUnits.size()
                    + clientTransitionedUnits.size()));
            toggleButton.setToggleGroup(clientUnitsToggleGroup);
            unitsHB.getChildren().add(toggleButton);
            toggleButton.setOnAction((ActionEvent event) -> {
                if (toggleButton.isSelected()) {
                    updateUI(unitId);
                    showUI();
                } else {
                    hideUI();
                }
            });
        }

        for (final Integer restoredUnitId : clientRestoredUnits) {
            i++;
            final ToggleButton toggleButton = new ToggleButton();
            toggleButton.setText("" + i);
            toggleButton.setStyle("-fx-background-color: #ffb7b7;"
                    + "-fx-text-fill: #7a0006;");
            toggleButton.setPrefWidth((scenesWidth / 0.799)
                    / (clientUnits.size() + clientRestoredUnits.size()
                    + clientTransitionedUnits.size()));
            toggleButton.setToggleGroup(clientUnitsToggleGroup);
            unitsHB.getChildren().add(toggleButton);
            toggleButton.setOnAction((ActionEvent event) -> {
                if (toggleButton.isSelected()) {
                    updateUI(restoredUnitId);
                    showUI4RestoredUnit(restoredUnitId);
                } else {
                    hideUI();
                }
            });
        }

        for (final Integer transitionedUnitId : clientTransitionedUnits) {
            i++;
            final ToggleButton toggleButton = new ToggleButton();
            System.out.println(" transitionedUnitId " + transitionedUnitId);
            toggleButton.setText("" + i);
            toggleButton.setStyle("-fx-background-color: #adffd7;"
                    + "-fx-text-fill: #7a0006;");
            toggleButton.setPrefWidth((scenesWidth / 0.799)
                    / (clientUnits.size() + clientRestoredUnits.size()
                    + clientTransitionedUnits.size()));
            toggleButton.setToggleGroup(clientUnitsToggleGroup);
            unitsHB.getChildren().add(toggleButton);
            toggleButton.setOnAction((ActionEvent event) -> {
                if (toggleButton.isSelected()) {
                    updateUI4Transitioned(transitionedUnitId);
                    showUI();
                } else {
                    hideUI();
                }
            });
        }
    }

    private void updateUI(int unitId) {
        selectedUnitId = unitId;
        clientId = ClientProfileController.clientId;
        selectedBuildingId = module.getBuildingId(unitId);
        
        totalPreviousClientEssentials = 0;
        totalPreviousClientPaidfromEssentials = 0;
        previousTotalRequired = 0;
        totalPreviousClientAdditionals = 0;
        totalPreviousClientAdjustments = 0;
        totalPreviousClientFacilities = 0;
        totalPreviousClientMaintenance = 0;
        previousTotalPaid = 0;
        totalPreviousClientPaidfromAdditionals = 0;
        totalPreviousClientPaidfromAdjustments = 0;
        totalPreviousClientPaidfromFacilities = 0;
        totalPreviousClientPaidfromMaintenance = 0;

        providBuildingName(selectedBuildingId);
        providUnitDetails(selectedUnitId, selectedBuildingId);
    }

    private void updateUI4Transitioned(int unitId) {
        selectedUnitId = unitId;
        clientId = module.getClientId(unitId);
        selectedBuildingId = module.getBuildingId(unitId);

        int previousClientId = module.getPreviuosClientId(unitId, clientId);

        totalPreviousClientEssentials
                = module.getTotalInstallmentsValues(selectedUnitId,
                        ESSENTIAL_INSTALLMENT, previousClientId);

        totalPreviousClientPaidfromEssentials
                = module.getTotalPaidInstallments(selectedUnitId,
                        ESSENTIAL_INSTALLMENT, previousClientId);

        totalPreviousClientAdditionals
                = module.getTotalInstallmentsValues(selectedUnitId,
                        ADDITIONAL_INSTALLMENT, previousClientId);
        
        totalPreviousClientAdjustments
                = module.getTotalInstallmentsValues(selectedUnitId,
                        ADJUSTMENT_INSTALLMENT, previousClientId);
        
        totalPreviousClientFacilities
                = module.getTotalInstallmentsValues(selectedUnitId,
                        FACILITIES_INSTALLMENT, previousClientId);
        
        totalPreviousClientMaintenance
                = module.getTotalInstallmentsValues(selectedUnitId,
                        MAINTENANCE_INSTALLMENT, previousClientId);
                      
        totalPreviousClientPaidfromAdditionals
                = module.getTotalPaidInstallments(selectedUnitId,
                        ADDITIONAL_INSTALLMENT, previousClientId);
        
        totalPreviousClientPaidfromAdjustments
                = module.getTotalPaidInstallments(selectedUnitId,
                        ADJUSTMENT_INSTALLMENT, previousClientId);
        
        totalPreviousClientPaidfromFacilities
                = module.getTotalPaidInstallments(selectedUnitId,
                        FACILITIES_INSTALLMENT, previousClientId);
        
        totalPreviousClientPaidfromMaintenance
                = module.getTotalPaidInstallments(selectedUnitId,
                        MAINTENANCE_INSTALLMENT, previousClientId);

        previousTotalRequired = module.getTotalInstallmentsValues(
                selectedUnitId, previousClientId);

        previousTotalPaid = module.getTotalPaidInstallments(selectedUnitId,
                previousClientId);

        providBuildingName(selectedBuildingId);
        providUnitDetails(selectedUnitId, selectedBuildingId);

    }

    private void providBuildingName(int buildingId) {
        Text buildingNameTxt = new Text();
        buildingNameTxt.setId("headTxt");
        String buildingName = module.getBuildingName(buildingId);
        buildingNameTxt.setText(buildingName);
        buildingNameHBox.getChildren().clear();
        buildingNameHBox.getChildren().add(buildingNameTxt);
    }

    private void providUnitDetails(int unitId, int buildingId) {

        // to prevent showing the same text twice when calling the function
        buildingLocationTxt.setText("");
        unitModelTxt.setText("");
        unitTypeTxt.setText("");
        unitFloorTxt.setText("");
        ModelAreaTxt.setText("");
        contractDateTxt.setText("");
        handingOverDateTxt.setText("");
        setUnitFreeTxt.setText("");
        unitCodeTxt.setText("");
        //                      *******
        
        ArrayList<String> location = module.getBuildingLocation(buildingId);
        String locationStr = location.get(0) + " - " + location.get(1)
                + " - " + location.get(2);
        buildingLocationTxt.setText(locationStr);
        selectedUnitModel = module.getUnitModel(unitId);
        unitModelTxt.setText(selectedUnitModel);
        int modelArea = module.getModelArea(selectedUnitModel, buildingId);
        selectedUnitType = module.getUnitType(unitId);
        if ("h".equals(selectedUnitType)) {
            unitTypeTxt.setText("سكنى");
            unitFloorTxt.setText(" رقم الدور: " + module.getUnitFloor(unitId));
            ModelAreaTxt.setText("بمساحه: " + modelArea);
        } else {
            unitTypeTxt.setText("تجارى");
            // floor number in the commercial unit is the unit area
            ModelAreaTxt.setText("بمساحه: " + module.getUnitFloor(unitId));
        }
        Date contractDate = module.getContractDate(unitId),
                handingOverDate = module.getHandingOverDate(unitId);
        if (contractDate == null) {
            //contractDateTxt.setText("");
        } else {
            contractDateTxt.setText(" ت.التعاقد" + contractDate.toString());
        }
        if (handingOverDate == null) {
            handingOverDateTxt.setText("");
            setUnitFreeTxt.setText("تسليم الوحده: ");
        } else {
            handingOverDateTxt.setText(" ت.التسليم" +
                    handingOverDate.toString());
            setUnitFreeTxt.setText("تعديل ت.تسليم");
            receptionBtn.setGraphic(null);
            receptionBtn.setText("تعديل");
        }
        selectedUnitCode = module.getUnitCode(unitId);
        unitCodeTxt.setText(" كود الوحده : " + selectedUnitCode);
    }

    @FXML
    private void showEditPrimaryInstlmntVB() {
        editPaidValueTxtFld.setDisable(true);
        String installmentIdStr = paymentCode4EditingTxtFld.getText();
        if (installmentIdStr.isEmpty()) {
            paymentCode4EditingTxtFld.setStyle("-fx-border-color: red;");
        } else {
            int installmentId = Integer.valueOf(installmentIdStr);
            if (module.checkInsallmentExistance(installmentId, selectedUnitId)) {
                paymentCode4EditingTxtFld.
                        setStyle("-fx-border-color:transparent;");
                editReceiptsScPn.setVisible(false);
                editInstallmentVB.setVisible(true);
                InstallmentModel installment = module.
                        getInstallmentData(installmentId, selectedUnitId);
                paymentName4editingCB.getSelectionModel().
                        select(installment.getPaymentName());
                paymentValue4editingTxtFld.setText(String.format("%.1f",
                        installment.getInstallmentValue()));
                editPaidValueTxtFld.setText(String.format("%.1f",
                        installment.getInstallmentPaidValue()));
                editInstallmentDayTxtFld.setText(installment.
                        getInstallmentDate().toLocalDate().getDayOfMonth() + "");
                editInstallmentMonthTxtFld.setText(installment.
                        getInstallmentDate().toLocalDate().getMonthValue() + "");
                editInstallmentYearTxtFld.setText(installment.
                        getInstallmentDate().toLocalDate().getYear() + "");
            } else {
                paymentCode4EditingTxtFld.setStyle("-fx-border-color: red;");
            }
        }
    }

    @FXML
    private void showEditReceiptsScPn() {
        String installmentIdStr = paymentCode4EditingTxtFld.getText();
        if (installmentIdStr.isEmpty()) {
            paymentCode4EditingTxtFld.setStyle("-fx-border-color: red;");
        } else {
            int installmentId = Integer.valueOf(installmentIdStr);
            if (module.checkInsallmentExistance(installmentId, selectedUnitId)){
                editReceiptsAnchPn.getChildren().clear();
                paymentCode4EditingTxtFld.setStyle("-fx-border-color:green;");
                editInstallmentVB.setVisible(false);
                editReceiptsScPn.setVisible(true);
                String buleStyle = "-fx-background-color:#cce0ff;",
                        transparentStyle = "-fx-background-color:transparent;";
                List<ReceiptModel> receipts = module.getReceipts(installmentId);
                int i = 0;
                double incrementedGap = 0;
                for (ReceiptModel receipt : receipts) {
                    i++;
                    HBox hbox = new HBox();
                    if (i % 2 > 0) {
                        hbox.setStyle(buleStyle);
                    } else {
                        hbox.setStyle(transparentStyle);
                    }
                    AnchorPane.setTopAnchor(hbox, incrementedGap);
                    incrementedGap += scenesHeight * 0.045;
                    hbox.setPrefWidth(editReceiptsAnchPn.getPrefWidth());
                    hbox.setPrefHeight(scenesHeight * 0.045);
                    hbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(scenesWidth * 0.05);
                    
                    HBox receiptValueHB = new HBox();
                    receiptValueHB.
                            setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    receiptValueHB.setAlignment(Pos.CENTER);
                    
                    Text receiptValueTxt = new Text("قيمة الايصال");
                    TextField receiptValueTxtFld = new TextField();
                    float oldReceiptValue = receipt.getReceiptValue();
                    receiptValueTxtFld.setText(oldReceiptValue + "");
                    
                    receiptValueHB.getChildren().addAll(receiptValueTxt,
                            receiptValueTxtFld);
                    
                    RadioButton checkRB = new RadioButton();
                    checkRB.setId("BlkRadioButton");
                    checkRB.setText("شيك");
                    checkRB.setSelected(receipt.isIsItCheck());
                    
                    HBox insideHb = new HBox();
                    insideHb.setAlignment(Pos.CENTER);

                    Text text = new Text("ايصال:");

                    TextField editReceiptIdTxtFld = new TextField();
                    editReceiptIdTxtFld.setPrefWidth(scenesWidth * 0.09);
                    editReceiptIdTxtFld.setText(receipt.getReceiptId());
                    editReceiptIdTxtFld.
                            setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                    insideHb.getChildren().addAll(text, editReceiptIdTxtFld);

                    HBox dateHB = new HBox();
                    Text dateInfoTxt = new Text();
                    if(checkRB.isSelected()){
                        dateInfoTxt.setText("ت.استحقاق");
                        receiptValueTxtFld.setDisable(true);
                    } else {
                        dateInfoTxt.setText("ت.دفع");
                    }
                    TextField editReceiptDayTxtFld = new TextField();
                    editReceiptDayTxtFld.setText(receipt.getReceiptDate().
                            toLocalDate().getDayOfMonth() + "");
                    editReceiptDayTxtFld.setPrefWidth(scenesWidth * 0.03);
                    TextField editReceiptMonthTxtFld = new TextField();
                    editReceiptMonthTxtFld.setText(receipt.getReceiptDate().
                            toLocalDate().getMonthValue() + "");
                    editReceiptMonthTxtFld.setPrefWidth(scenesWidth * 0.03);
                    TextField editReceiptYearTxtFld = new TextField();
                    editReceiptYearTxtFld.setText(receipt.getReceiptDate().
                            toLocalDate().getYear() + "");
                    editReceiptYearTxtFld.setPrefWidth(scenesWidth * 0.06);

                    dateHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    dateHB.setAlignment(Pos.CENTER);
                    dateHB.getChildren().addAll(dateInfoTxt,
                            editReceiptDayTxtFld, editReceiptMonthTxtFld,
                            editReceiptYearTxtFld);
                    
                    EditingReceiptParams editingReceiptParams =
                            new EditingReceiptParams();
                    checkRB.setOnAction((event)->{
                        if(checkRB.isSelected()){
                            TextInputDialog bankNameDialog =
                                    new TextInputDialog();
                            bankNameDialog.setHeaderText("ادخال بيانات الشيك");
                            bankNameDialog.setContentText("اسم البنك : ");
                            bankNameDialog.getDialogPane().
                                    setNodeOrientation(NodeOrientation.
                                            RIGHT_TO_LEFT);
                            Optional<String> bankNameRes =
                                    bankNameDialog.showAndWait();
                            if (bankNameRes.isPresent()){
                                editingReceiptParams.bank = bankNameRes.get();
                                TextInputDialog checkIdDialog =
                                    new TextInputDialog();
                                checkIdDialog.
                                        setHeaderText("ادخال بيانات الشيك");
                                checkIdDialog.setContentText("رقم الشيك: ");
                                checkIdDialog.getDialogPane().
                                    setNodeOrientation(NodeOrientation.
                                            RIGHT_TO_LEFT);
                                Optional<String> checkIdRes =
                                    checkIdDialog.showAndWait();
                                if(checkIdRes.isPresent()){
                                    editingReceiptParams.checkId = 
                                           checkIdRes.get();
                                    dateInfoTxt.setText("ت.استحقاق");
                                    receiptValueTxtFld.setText("0.0");
                                    receiptValueTxtFld.setDisable(true);
                                } else {
                                    checkRB.setSelected(false);
                                    dateInfoTxt.setText("ت.دفع");
                                    receiptValueTxtFld.setDisable(false);
                                }
                            } else {
                                checkRB.setSelected(false);
                                receiptValueTxtFld.setDisable(false);
                            }
                        } else {
                            receiptValueTxtFld.setDisable(false);
                            receiptValueTxtFld.setText(oldReceiptValue + "");
                        } 
                    });
                    
                    Button editReceiptBtn = new Button("تعديل");
                    editReceiptBtn.setPrefWidth(scenesWidth * 0.05);
                    editReceiptBtn.setId("BtnStyle");

                    editReceiptBtn.setOnMouseClicked((MouseEvent event) -> {
                        String editedReceiptId = editReceiptIdTxtFld.getText(),
                                editedReceiptDayStr = editReceiptDayTxtFld.
                                        getText(),
                                editedReceiptMonthStr = editReceiptMonthTxtFld.
                                        getText(),
                                editedReceiptYearStr = editReceiptYearTxtFld.
                                        getText(),
                                receiptValueStr = receiptValueTxtFld.getText();
                        if (editedReceiptId.isEmpty()) {
                            editReceiptIdTxtFld.
                                    setStyle("-fx-border-color:red;");
                            return;
                        } else {
                            editReceiptIdTxtFld.
                                    setStyle("-fx-border-color:transparent;");
                        }
                        if (editedReceiptDayStr.isEmpty()) {
                            editReceiptDayTxtFld.
                                    setStyle("-fx-border-color:red;");
                            return;
                        } else {
                            editReceiptDayTxtFld.
                                    setStyle("-fx-border-color:transparent;");
                        }
                        if (editedReceiptMonthStr.isEmpty()) {
                            editReceiptMonthTxtFld.
                                    setStyle("-fx-border-color:red;");
                            return;
                        } else {
                            editReceiptMonthTxtFld.
                                    setStyle("-fx-border-color:transparent;");
                        }
                        if (editedReceiptYearStr.isEmpty()) {
                            editReceiptYearTxtFld.
                                    setStyle("-fx-border-color:red;");
                            return;
                        } else {
                            editReceiptYearTxtFld.
                                    setStyle("-fx-border-color:transparent;");
                        } if (receiptValueStr.isEmpty()){
                            editReceiptMonthTxtFld.
                                    setStyle("-fx-border-color:red;");
                            return;
                        } else {
                            editReceiptMonthTxtFld.
                                    setStyle("-fx-border-color:transparent;");
                        }
                        String editedDateStr = editedReceiptYearStr + "-"
                                + editedReceiptMonthStr + "-"
                                + editedReceiptDayStr;

                        Date editedDate = Date.valueOf(editedDateStr);

                        if (confirmProcess(null, "تأكيد تعديل بيانات الايصال")) {
                            
                            float receiptNewValue = Float.
                                    parseFloat(receiptValueStr),
                                    changedValue = receiptNewValue -
                                    oldReceiptValue;
                            
                            module.addDiffrenceValue2Installment(installmentId,
                                    changedValue);
                            
                            module.editReceipt(editedReceiptId, receipt.getId(),
                                    editedDate, checkRB.isSelected(),
                                    editingReceiptParams.bank,
                                    editingReceiptParams.checkId,
                                    receiptNewValue);
                            
                            if(checkRB.isSelected()){
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("تنبيه");
                                alert.setHeaderText(null);
                                alert.setContentText("هذا الشيك لم يتم تحصيله!");
                                alert.showAndWait();
                            }
                        }
                        editReceiptBtn.setStyle("-fx-background-color: green;");
                    });
                    hbox.getChildren().addAll(insideHb, checkRB, dateHB,
                            receiptValueHB, editReceiptBtn);
                    editReceiptsAnchPn.getChildren().add(hbox);
                }
            } else {
                paymentCode4EditingTxtFld.setStyle("-fx-border-color: red;");
            }
        }
    }
    
    class EditingReceiptParams{
        String bank, checkId;
    }
    
    @FXML
    private void showingOptions() {
        editingToggleGroup.selectToggle(null);
    }

    @FXML
    private void showEditingTabs() {
        showingToggleGroup.selectToggle(null);
    }

    @FXML
    private void cancelReservation() {
        if (confirmProcess("تأكيد الغاء حجز العميل", "اتمام عملية الاغاء")) {
            module.setUnitFree(selectedUnitId);
            module.deleteFinInfo(selectedUnitId);
            cancelReservationBtn.setStyle("-fx-background-color: green;");
            cancelReservationBtn.setDisable(true);
            receptionBtn.setDisable(true);
            reinstallationBtn.setDisable(true);
            restorationBtn.setDisable(true);            
        }
    }

    @FXML
    private void prepareCashPane() {
        if (selectedUnitPrice == 0) {
            if ("h".equals(selectedUnitType)) {
                selectedUnitPrice = module.getUnitPrice(selectedUnitId,
                        selectedBuildingId, selectedUnitModel);
            } else {
                selectedUnitPrice = module.isItSpecial(selectedUnitId);
            }
        }
        float previousInstallmentsValues = module.
                getTotalInstallmentsValues(selectedUnitId,
                        ESSENTIAL_INSTALLMENT, clientId);

        unitPriceCashTxt.setText("سعر الوحده : "
                + String.format("%.1f", selectedUnitPrice));
        
        unitRemainedCashTxt.setText("المتبقى: "
                + String.format("%.1f", selectedUnitPrice
                        - previousInstallmentsValues
                        - totalPreviousClientPaidfromEssentials));
    }

    @FXML
    private void financialDealing() {

        if (module.isPaymentInitiated(selectedUnitId)) {
            System.out.println("is initiated : " + true);
            installmentTB.setDisable(true);
            cashTB.setDisable(true);
        } else {
            installmentTB.setDisable(false);
            cashTB.setDisable(false);
        }
    }

    @FXML
    private void getExistedClient() {
        String clientCodeTxt = clientCodeTxtFld.getText();
        int clientCode = Integer.parseInt(clientCodeTxt);
        String clientName = module.getClientName(clientCode);
        String clientPersonalId = module.getClientPersonalId(clientCode);
        String clientPhone = module.getClientPhone(clientCode);

        existedClientNameTxt.setText(clientName);
        existedClientPersonalIdTxt.setText(clientPersonalId);
        existedClientPhoneNoTxt.setText(clientPhone);
        existedClientInfoHB.setVisible(true);
    }

    @FXML
    private void prepareInstallmentPane() {

        if (selectedUnitPrice == 0) {
            if ("h".equals(selectedUnitType)) {
                selectedUnitPrice = module.getUnitPrice(selectedUnitId,
                        selectedBuildingId, selectedUnitModel);
            } else {
                selectedUnitPrice = module.isItSpecial(selectedUnitId);
            }
        }
        unitPriceTxt.setText("ث.الوحده : "
                + String.format("%.1f", selectedUnitPrice));

        paidCashFromUnitPrice = module.getTotalPaidInstallments(selectedUnitId,
                ESSENTIAL_INSTALLMENT, clientId);
        paidFromUnitPriceTxt.setText("المدفوع: "
                + String.format("%.1f", paidCashFromUnitPrice
                        + totalPreviousClientPaidfromEssentials));

        remainedFromUnitPriceTxt.setText("المتبقى: "
                + String.format("%.1f",
                        (selectedUnitPrice - paidCashFromUnitPrice
                        - totalPreviousClientPaidfromEssentials)));
    }

    @FXML
    private void createInstallments() {

        String statringDayStr = startInstlmntDayTxtFld.getText(),
                statringMonthStr = startInstlmntMonthTxtFld.getText(),
                statringYearStr = startInstlmntYearTxtFld.getText();

        if (statringDayStr.isEmpty()) {
            startInstlmntDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            startInstlmntDayTxtFld.setStyle("-fx-border-color:green;");
        }
        if (statringMonthStr.isEmpty()) {
            startInstlmntMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            startInstlmntMonthTxtFld.setStyle("-fx-border-color:green;");
        }
        if (statringYearStr.isEmpty()) {
            startInstlmntYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            startInstlmntYearTxtFld.setStyle("-fx-border-color:green;");
        }
        String instlmntsCountStr = instlmntsCountTxtFld.getText();
        if (instlmntsCountStr.isEmpty()) {
            instlmntsCountTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            instlmntsCountTxtFld.setStyle("-fx-border-color:green;");
        }
        String milestonePeriodStr = milestonePeriodTxtFld.getText();
        if (milestonePeriodStr.isEmpty()) {
            milestonePeriodTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            milestonePeriodTxtFld.setStyle("-fx-border-color:green;");
        }
        String discountRationStr = discountRationTxtFld.getText();
        if (discountRationStr.isEmpty()) {
            discoutRatio = 0;
            priceAfterDiscount = 0;
        } else {
            discountRationTxtFld.setStyle("-fx-border-color:green;");
            try {
                discoutRatio = Float.parseFloat(discountRationStr);
            } catch (NumberFormatException e) {
                discountRationTxtFld.setStyle("-fx-border-color:red;");
                return;
            }
        }

        if (discoutRatio == 0) {
            priceAfterDiscountTxt.
                    setText("0.0");
            priceAfterDiscount = selectedUnitPrice;
        } else {
            priceAfterDiscount = selectedUnitPrice
                    - (selectedUnitPrice * (discoutRatio / 100));
            priceAfterDiscountTxt.
                    setText(String.format("%.1f", (selectedUnitPrice
                            - priceAfterDiscount)));
        }

        String statringDateStr
                = statringYearStr + "-" + statringMonthStr + "-"
                + statringDayStr;
        Date statringDate = Date.valueOf(statringDateStr);
        LocalDate statringLocalDate = statringDate.toLocalDate();
        int milestonePeriod, instlmntsCount;
        try {
            milestonePeriod = Integer.parseInt(milestonePeriodStr);
        } catch (NumberFormatException e) {
            milestonePeriodTxtFld.setStyle("-fx-border-color:red;");
            return;
        }
        try {
            instlmntsCount = Integer.parseInt(instlmntsCountStr);
        } catch (NumberFormatException e) {
            instlmntsCountTxtFld.setStyle("-fx-border-color:red;");
            return;
        }

        float previousInstallmentsValues = module.
                getTotalInstallmentsValues(selectedUnitId, clientId);
        installmentedValueTxt.setText(String.format("%.1f",
                ((priceAfterDiscount - previousInstallmentsValues
                - totalPreviousClientPaidfromEssentials))));

        installmentsUIProviding(statringLocalDate, milestonePeriod,
                instlmntsCount, (priceAfterDiscount
                - previousInstallmentsValues
                - totalPreviousClientPaidfromEssentials));

        instalmntScPn.setVisible(true);
    }

    private void installmentsUIProviding(LocalDate installmentDate,
            int milestoneTime, final int installmentsCount,
            final float installmentedValue) {
        createdInstlmntsAnchPn.getChildren().clear();
        final List<HBox> installmentsList = new ArrayList();
        double incrementedGap = 0;
        String buleStyle = "-fx-background-color:#cce0ff;",
                transparentStyle = "-fx-background-color:transparent;";
        float valuePerInstallment = installmentedValue / installmentsCount;

        double fractions = 0;
        for (int i = 1; installmentsCount >= i; i++) {
            HBox installmentHbox = new HBox();
            if (i % 2 > 0) {
                installmentHbox.setStyle(buleStyle);
            } else {
                installmentHbox.setStyle(transparentStyle);
            }
            AnchorPane.setTopAnchor(installmentHbox, incrementedGap);
            incrementedGap += scenesHeight * 0.04;
            installmentHbox.setPrefWidth(createdInstlmntsAnchPn.getPrefWidth());
            installmentHbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            installmentHbox.setAlignment(Pos.CENTER);
            installmentHbox.setSpacing(scenesWidth * 0.05);

            Text text = new Text();
            text.setText("دفعه رقم : " + i);

            ComboBox comboBox = new ComboBox();
            comboBox.getItems().addAll(installmentsTypeMap.keySet());
            comboBox.getSelectionModel().selectFirst();

            TextField paymentDayTxtFld = new TextField(),
                    paymentMonthTxtFld = new TextField(),
                    paymentYearTxtFld = new TextField(),
                    paymentValueTxtFld = new TextField();

            comboBox.setPrefWidth(scenesWidth * 0.2);
            paymentDayTxtFld.setPrefWidth(scenesWidth * 0.05);
            paymentMonthTxtFld.setPrefWidth(scenesWidth * 0.05);
            paymentYearTxtFld.setPrefWidth(scenesWidth * 0.075);
            paymentValueTxtFld.setPrefWidth(scenesWidth * 0.1);

            fractions += valuePerInstallment - (long) valuePerInstallment;
            if (i == installmentsCount) {
                valuePerInstallment += fractions;
                fractions = valuePerInstallment - (long) valuePerInstallment;
                if (fractions > 0.5) {
                    valuePerInstallment++;
                }
            }
            paymentValueTxtFld.setText("" + (long) valuePerInstallment);

            if (i != 1) {
                installmentDate = installmentDate.plusMonths(milestoneTime);
            }

            paymentDayTxtFld.setText("" + installmentDate.getDayOfMonth());
            paymentMonthTxtFld.setText("" + installmentDate.getMonthValue());
            paymentYearTxtFld.setText("" + installmentDate.getYear());

            HBox dateHbox = new HBox();
            dateHbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            dateHbox.getChildren().addAll(paymentDayTxtFld, paymentMonthTxtFld,
                    paymentYearTxtFld);

            installmentHbox.getChildren().addAll(text, comboBox,
                    dateHbox, paymentValueTxtFld);
            installmentsList.add(installmentHbox);
            createdInstlmntsAnchPn.getChildren().add(installmentHbox);
        }
        HBox controllersHbox = new HBox();
        controllersHbox.setStyle("-fx-background-color:#fcf96f;");
        controllersHbox.setPrefWidth(createdInstlmntsAnchPn.getPrefWidth());
        controllersHbox.setSpacing(scenesWidth * 0.05);
        controllersHbox.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(controllersHbox, incrementedGap);

        Button clearButton = new Button();
        clearButton.setId("BtnStyle");
        clearButton.setText("الغاء");
        clearButton.setOnMouseClicked((MouseEvent event) -> {
            createdInstlmntsAnchPn.getChildren().clear();
            installmentsList.clear();
        });

        Button confirmationButton = new Button();
        confirmationButton.setId("BtnStyle");
        confirmationButton.setText("حفظ");
        confirmationButton.setOnMouseClicked((MouseEvent event) -> {
            boolean areEqual = confirmeInstallmentedValue(installmentsList,
                    installmentedValue);
            if (areEqual) {
                String confirmationStr
                        = "تأكيد تقسيط قيمة : "
                        + String.format("%.2f", installmentedValue)
                        + " على عدد : " + installmentsCount + " قسط"
                        + System.lineSeparator()
                        + "بنسبة خصم : " + discoutRatio + "%";
                if (confirmProcess(null, confirmationStr)) {
                    module.saveInstallments(installmentsList,
                            selectedUnitId, selectedBuildingId, clientId);
                    module.setPaymentInitiated(
                            selectedUnitId, discoutRatio);
                    instalmntScPn.setVisible(false);
                    startInstlmntDayTxtFld.setText("");
                    startInstlmntMonthTxtFld.setText("");
                    startInstlmntYearTxtFld.setText("");
                    discountRationTxtFld.setText("");
                    milestonePeriodTxtFld.setText("");
                    instlmntsCountTxtFld.setText("");
                }
            } else {
                alertError("خطأ فى قيمة الاقساط !",
                        "من فضلك اعد حساب قيمة الاقساط");
            }
        });
        controllersHbox.getChildren().addAll(clearButton, confirmationButton);
        createdInstlmntsAnchPn.getChildren().add(controllersHbox);
    }

    private boolean confirmProcess(String headerStr, String confirmationStr) {
        Alert confirmationAlert
                = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.getDialogPane().setNodeOrientation(
                NodeOrientation.RIGHT_TO_LEFT);
        confirmationAlert.setTitle("تأكيد");
        confirmationAlert.setHeaderText(headerStr);
        confirmationAlert.setContentText(confirmationStr);
        Optional<ButtonType> result = confirmationAlert
                .showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void alertError(String headerStr, String contentStr) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.getDialogPane().setNodeOrientation(
                NodeOrientation.RIGHT_TO_LEFT);
        errorAlert.setTitle("خطأ ! ");
        errorAlert.setHeaderText(headerStr);
        errorAlert.setContentText(contentStr);
        errorAlert.showAndWait();
    }

    private boolean confirmeInstallmentedValue(List<HBox> installmentsList,
            float installmentedValue) {
        float paymentsValue = 0;
        TextField installmentValueTxtFld;
        String installmentValueStr;
        for (HBox installmentHBox : installmentsList) {
            installmentValueTxtFld = (TextField) installmentHBox.
                    getChildren().get(3);
            installmentValueStr = installmentValueTxtFld.getText();
            paymentsValue += Float.parseFloat(installmentValueStr);
        }

        return Math.abs(installmentedValue - paymentsValue) < 2;
    }

    @FXML
    private void addReservationInstallment() {

        String reservationValueStr = reservationValueTxtFld.getText(),
                reservationDayStr = reservationDayTxtFld.getText(),
                reservationMonthStr = reservationMonthTxtFld.getText(),
                reservationYearStr = reservationYearTxtFld.getText(),
                reservationPaymentName = String.
                        valueOf(reservationCB.getValue());

        if (reservationPaymentName.isEmpty()) {
            reservationCB.setStyle("-fx-border-color:red;");
            return;
        } else {
            reservationCB.setStyle("-fx-border-color:green;");
        }

        if (reservationValueStr.isEmpty()) {
            reservationValueTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            reservationValueTxtFld.setStyle("-fx-border-color:green;");
        }

        if (reservationDayStr.isEmpty()) {
            reservationDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            reservationDayTxtFld.setStyle("-fx-border-color:green;");
        }

        if (reservationMonthStr.isEmpty()) {
            reservationMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            reservationMonthTxtFld.setStyle("-fx-border-color:green;");
        }

        if (reservationYearStr.isEmpty()) {
            reservationYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            reservationYearTxtFld.setStyle("-fx-border-color:green;");
        }

        float reservationValue = Float.parseFloat(reservationValueStr);

        String reservationDateStr = reservationYearStr + "-"
                + reservationMonthStr + "-" + reservationDayStr;

        Date reservationDate = Date.valueOf(reservationDateStr);

        String confrimationBodyStr = "اضافة دفعة حجز بقيمة: " + reservationValue
                + System.lineSeparator() + "اسم الدفعه: " + reservationPaymentName
                + System.lineSeparator() + "تاريخ الدفعه: " + reservationDateStr,
                confirmationHeaderStr = "تأكيد اضافة دفعة حجز";
        if (confirmProcess(confirmationHeaderStr, confrimationBodyStr)) {

            module.addInstallment(selectedUnitId, selectedBuildingId, clientId,
                    reservationPaymentName, reservationDate, reservationValue,
                    installmentsTypeMap.get(reservationPaymentName));
            addReservInstlmntBtn.setStyle("-fx-background-color:green;");
            addReservInstlmntBtn.setDisable(true);
            
        }
    }

    @FXML
    private void calculateDiscount() {

        discoutRatio = 0;
        String discountRationStr = discountRationTxtFld.getText();
        try {
            discoutRatio = Float.parseFloat(discountRationStr);
        } catch (NumberFormatException e) {
            discountRationTxtFld.setStyle("-fx-border-color:red;");
            return;
        }
        if (discoutRatio == 0) {
            priceAfterDiscountTxt.
                    setText("0.0");
            priceAfterDiscount = selectedUnitPrice;
        } else {
            priceAfterDiscount = selectedUnitPrice
                    - (selectedUnitPrice * (discoutRatio / 100));
            priceAfterDiscountTxt.
                    setText(String.format("%.1f", (selectedUnitPrice
                            - priceAfterDiscount)));
        }
    }

    @FXML
    private void calculateCashDiscount() {

        String discountRatioStr = cashDiscountRetioTxtFld.getText();
        float discountRatio = Float.parseFloat(discountRatioStr);
        if (discountRatio == 0) {
            priceAfterDiscount = selectedUnitPrice;
        } else {
            priceAfterDiscount = selectedUnitPrice
                    - (selectedUnitPrice * (discountRatio / 100));
        }
        cashPriceTxt.setText(String.format("%.1f", priceAfterDiscount));
    }

    @FXML
    private void showFinInfo() {
        
        String paidStr = "المدفوع : ",
                remainedStr = "المتبقى : ";
        if ("h".equals(selectedUnitType)) {
            selectedUnitPrice = module.getUnitPrice(selectedUnitId,
                    selectedBuildingId, selectedUnitModel);
        } else {
            selectedUnitPrice = module.isItSpecial(selectedUnitId);
        }

        showUnitPriceTxt.setText("سعر الوحده : "
                + String.format("%.1f", selectedUnitPrice));

        
        float selectedUnitDiscount = module.getDiscountRatio(selectedUnitId);
        discountRatioTxt.setText("نسبة الخصم : " + selectedUnitDiscount + "%");

        if (selectedUnitDiscount != 0) {
            priceAfterDiscount
                    = selectedUnitPrice
                    - (selectedUnitPrice * (selectedUnitDiscount / 100));
            unitPriceAfterDiscountTxt.setText("بعد الخصم : "
                    + String.format("%.1f", priceAfterDiscount));
        } else {
            priceAfterDiscount = selectedUnitPrice;
            unitPriceAfterDiscountTxt.setText("بعد الخصم : "
                    + String.format("%.1f", priceAfterDiscount));
        }

        float essentialPayment = module.getTotalInstallmentsValues(selectedUnitId,
                ESSENTIAL_INSTALLMENT, clientId)
                + totalPreviousClientEssentials;

        essentialPaymentValueTxt.setText("المستحقات الاساسيه : "
                + String.format("%.1f", essentialPayment));
        
        float paidEssentialCash = module.getTotalPaidInstallments(selectedUnitId,
                ESSENTIAL_INSTALLMENT, clientId)
                + totalPreviousClientPaidfromEssentials;

        paidEssentialCashTxt.setText(paidStr
                + String.format("%.1f", paidEssentialCash));

        remainedEssentialTxt.setText(remainedStr
                + String.format("%.1f", (essentialPayment - paidEssentialCash)));

        float additionalPayment = module.
                getTotalInstallmentsValues(selectedUnitId, 
                        ADDITIONAL_INSTALLMENT, clientId)
                + totalPreviousClientAdditionals;

        additionalPaymentValueTxt.setText("المستحقات الاضافيه : "
                + String.format("%.1f", additionalPayment));

        float paidAdditionalCash = module.getTotalPaidInstallments(selectedUnitId,
                ADDITIONAL_INSTALLMENT, clientId)
                + totalPreviousClientPaidfromAdditionals;

        paidAdditionalCashTxt.setText(paidStr
                + String.format("%.1f", paidAdditionalCash));

        remainedAdditionalTxt.setText(remainedStr + String.format("%.1f",
                        (additionalPayment - paidAdditionalCash)));
        
        float adjustmentPayment = module.
                getTotalInstallmentsValues(selectedUnitId, 
                        ADJUSTMENT_INSTALLMENT, clientId)
                + totalPreviousClientAdjustments;
        
        adjustmentPaymentValueTxt.setText("التعديلات : "
                + String.format("%.1f", adjustmentPayment));

        float paidAdjustmentCash = module.
                getTotalPaidInstallments(selectedUnitId, ADJUSTMENT_INSTALLMENT,
                        clientId) + totalPreviousClientPaidfromAdjustments;

        paidAdjustmentCashTxt.setText(paidStr
                + String.format("%.1f", paidAdjustmentCash));
        
        remainedAdjustmentTxt.setText(remainedStr + String.format("%.1f",
                        (adjustmentPayment - paidAdjustmentCash)));
        
        float facilitiesPayment = module.
                getTotalInstallmentsValues(selectedUnitId, 
                        FACILITIES_INSTALLMENT, clientId)
                + totalPreviousClientFacilities;
        
        facilitiesPaymentValueTxt.setText("المرافق : "
                + String.format("%.1f", facilitiesPayment));

        float paidFacilitiesCash = module.
                getTotalPaidInstallments(selectedUnitId, FACILITIES_INSTALLMENT,
                        clientId) + totalPreviousClientPaidfromFacilities;

        paidFacilitiesCashTxt.setText(paidStr
                + String.format("%.1f", paidFacilitiesCash));
        
        remainedFacilitiesTxt.setText(remainedStr + String.format("%.1f",
                        (facilitiesPayment - paidFacilitiesCash)));
        
        float maintenancePayment = module.
                getTotalInstallmentsValues(selectedUnitId, 
                        MAINTENANCE_INSTALLMENT, clientId)
                + totalPreviousClientMaintenance;
        
        maintenancePaymentValueTxt.setText("الصيانه : "
                + String.format("%.1f", maintenancePayment));

        float paidMaintenanceCash = module.
                getTotalPaidInstallments(selectedUnitId, MAINTENANCE_INSTALLMENT,
                        clientId) + totalPreviousClientPaidfromMaintenance;

        paidMaintenanceCashTxt.setText(paidStr
                + String.format("%.1f", paidMaintenanceCash));
        
        remainedMaintenanceTxt.setText(remainedStr + String.format("%.1f",
                        (maintenancePayment - paidMaintenanceCash)));
        
        
        float totalRequiredCash = module.
                getTotalInstallmentsValues(selectedUnitId, clientId) +
                previousTotalRequired;

        totalRequiredCashTxt.setText("اجمالى المستحقات : "
                + String.format("%.1f", totalRequiredCash));

        float totalPaid = module.getTotalPaidInstallments(selectedUnitId,
                clientId) + previousTotalPaid;

        totalPaidCashTxt.setText(paidStr + String.format("%.1f", totalPaid));

        totalRemainedTxt.setText(remainedStr
                + String.format("%.1f", (totalRequiredCash - totalPaid)));
    }

    @FXML
    private void showInstallments() {
        savedInstlmntsAnchPn.getChildren().clear();
        List<InstallmentModel> installmentsList = new ArrayList();
        installmentsList = module.getInstallments(selectedUnitId,
                installmentsList, clientId);

        String buleStyle = "-fx-background-color:#cce0ff;",
                transparentStyle = "-fx-background-color:transparent;",
                textSyle = "-fx-font-size:1.05em;-fx-font-wight: bold";
        int i = 0, finishedCount = 0;
        double incrementedGap = 0;
        
        for (InstallmentModel installment : installmentsList) {
            i++;
            VBox installmentVB = new VBox();
            installmentVB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            installmentVB.setAlignment(Pos.CENTER_RIGHT);
            if (i % 2 > 0) {
                installmentVB.setStyle(buleStyle);
            } else {
                installmentVB.setStyle(transparentStyle);
            }
            HBox topHB = new HBox(),
                    bottomHB = new HBox();
            topHB.setStyle(transparentStyle);
            bottomHB.setStyle(transparentStyle);
            AnchorPane.setTopAnchor(installmentVB, incrementedGap);
            incrementedGap += scenesHeight * 0.08;

            topHB.setPrefWidth(savedInstlmntsAnchPn.getPrefWidth());
            bottomHB.setPrefWidth(savedInstlmntsAnchPn.getPrefWidth());
            topHB.setPrefHeight(scenesHeight * 0.04);
            bottomHB.setPrefHeight(scenesHeight * 0.04);
            topHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            bottomHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            topHB.setAlignment(Pos.CENTER_LEFT);
            bottomHB.setAlignment(Pos.CENTER_LEFT);
            topHB.setPadding(new Insets(0, 0, 0, scenesWidth * 0.02));
            bottomHB.setPadding(new Insets(0, 0, 0, scenesWidth * 0.02));
            topHB.setSpacing(scenesWidth * 0.05);
            bottomHB.setSpacing(scenesWidth * 0.05);

            float installmentValue = installment.getInstallmentValue(),
                    paidValue = installment.getInstallmentPaidValue(),
                    remainedValue = installmentValue - paidValue;

            int installmentOwnerId = installment.getClientId();

            Text paymentCodeTxt = new Text(),
                    paymentNameTxt = new Text(),
                    installmentValueTxt = new Text(),
                    paidValueTxt = new Text(),
                    remainedValueTxt = new Text(),
                    installmentDateTxtF = new Text(),
                    clientIdTxt = new Text();

            paymentCodeTxt.setText("كود الدفعه : " + installment.getId());
            paymentCodeTxt.setStyle(textSyle);
            paymentNameTxt.setText(installment.getPaymentName());
            paymentNameTxt.setStyle(textSyle);
            installmentValueTxt.setText("بقيمة : "
                    + String.format("%.1f", installmentValue));
            installmentValueTxt.setStyle(textSyle);
            paidValueTxt.setText("مدفوع : " + String.format("%.1f", paidValue));
            paidValueTxt.setStyle(textSyle);
            remainedValueTxt.setText("متبقى : " + String.format("%.1f",
                    remainedValue));
            remainedValueTxt.setStyle(textSyle);
            installmentDateTxtF.setText("ت.استحقاق : "
                    + installment.getInstallmentDate().toString());
            installmentDateTxtF.setStyle(textSyle);
            clientIdTxt.setText("لعميل: " + installmentOwnerId);
            clientIdTxt.setStyle(textSyle);

            if (installment.isFinished()) {
                finishedCount++;
            }

            Button showReceiptsBtn = new Button("عرض الايصالات");
            showReceiptsBtn.setId("lightBtn");

            showReceiptsBtn.setOnMouseClicked((event) -> {
                List<ReceiptModel> receipts = module.getReceipts(installment.
                        getId());
                VBox layout = new VBox();
                layout.setAlignment(Pos.CENTER);
                layout.setSpacing(scenesHeight * 0.02);
                for (ReceiptModel receipt : receipts) {
                    Text receiptId = new Text(receipt.getReceiptId());
                    String paymentType, chekcIdStr, bankNameStr,
                            bankingUpDateStr;
                    if (receipt.isIsItCheck()) {
                        paymentType = "شيك";
                        bankNameStr = "بنك: " + receipt.getBank();
                        chekcIdStr = "رقم الشيك: " + receipt.getCheckId();
                        bankingUpDateStr = "تحصيل: " 
                                + receipt.getBankingUpDate();
                    } else {
                        paymentType = "نقدى";
                        bankNameStr = "";
                        chekcIdStr = "";
                        bankingUpDateStr = "";
                    }
                    Text isItCheck = new Text(paymentType);
                    Text receiptDate = new Text(receipt.getReceiptDate().
                            toString());
                    Text checkId = new Text(chekcIdStr);
                    Text bankingUpDateTxt = new Text(bankingUpDateStr);
                    Text checkBank = new Text(bankNameStr);

                    HBox layer = new HBox();
                    layer.setSpacing(scenesWidth * 0.05);
                    layer.setAlignment(Pos.CENTER);
                    layer.getChildren().addAll(bankingUpDateTxt, checkId,
                            checkBank, receiptDate, isItCheck, receiptId);
                    layout.getChildren().add(layer);
                }
                Stage popupwindow = new Stage();

                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("ايصالات دفعه رقم: " + installment.getId());
                Scene scene1 = new Scene(layout, 800, 400);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });

            topHB.getChildren().addAll(paymentCodeTxt, paymentNameTxt,
                    installmentDateTxtF, showReceiptsBtn);

            bottomHB.getChildren().addAll(installmentValueTxt, paidValueTxt,
                    remainedValueTxt, clientIdTxt);

            installmentVB.getChildren().addAll(topHB, bottomHB);
            savedInstlmntsAnchPn.getChildren().add(installmentVB);
        }
        Text installmentsCountTxt = new Text(),
                finishedInstallmentsTxt = new Text();

        installmentsCountTxt.setText("عدد الدفعات : " + i);
        finishedInstallmentsTxt.setText("المنتهى : " + finishedCount);

        HBox infoHbox = new HBox();
        infoHbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        infoHbox.setStyle("-fx-background-color:#fcf96f;");
        infoHbox.setPrefWidth(savedInstlmntsAnchPn.getPrefWidth());
        infoHbox.setPrefHeight(scenesHeight * 0.05);
        infoHbox.setSpacing(scenesWidth * 0.05);
        infoHbox.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(infoHbox, incrementedGap);

        infoHbox.getChildren().addAll(installmentsCountTxt,
                finishedInstallmentsTxt);
        savedInstlmntsAnchPn.getChildren().add(infoHbox);
    }

    @FXML
    private void preparePayingVBs() {
        payInstallmentVB.setVisible(false);
        bankingUpVB.setVisible(false);
    }
    
    @FXML
    private void prepareBankingVB() {
        if (paymentCode4PayingTxtFld.getText().isEmpty()) {
            bankingUpVB.setVisible(false);
        }
    }

    @FXML
    private void showPayingVB() {

        String installmentIdStr = paymentCode4PayingTxtFld.getText();
        if (installmentIdStr.isEmpty()) {
            paymentCode4PayingTxtFld.setStyle("-fx-border-color:red;");
            payInstallmentVB.setVisible(false);
        } else {
            int installmentId = Integer.parseInt(installmentIdStr);

            paymentCode4PayingTxtFld.setStyle("-fx-border-color:green;");
            InstallmentModel installment
                    = module.getInstallmentData(installmentId, selectedUnitId);
            if (installment.getPaymentName() == null) {
                paymentCode4PayingTxtFld.setStyle("-fx-border-color:red;");
                payInstallmentVB.setVisible(false);
            } else {
                paymentName4PayingTxt.setText(installment.getPaymentName());
                paymentValue4PayingTxt.setText(
                        String.format("%.1f",
                                installment.getInstallmentValue()));
                installmentValue4Paying = installment.getInstallmentValue();
                Date installmentDate = installment.getInstallmentDate();
                LocalDate installmentLocalDate = installmentDate.toLocalDate(),
                        todaysDate = LocalDate.now();

                int durationDays = Period.between(installmentLocalDate,
                        todaysDate).getDays(),
                        durationMonths = Period.between(installmentLocalDate,
                                todaysDate).getMonths(),
                        durationYears = Period.between(installmentLocalDate,
                                todaysDate).getYears();
                delayDays.setText("" + durationDays);
                delayMonths.setText("" + (durationMonths + (durationYears * 12)));
                installmentDateTxt.setText(installmentDate.toString());
                remainedInstlmntCashTxt.setText(
                        String.format("%.1f",
                                (installment.getInstallmentValue()
                                - installment.getInstallmentPaidValue())));
                bankingUpVB.setVisible(false);
                payInstallmentVB.setVisible(true);
            }
        }
    }
    
    @FXML
    private void showBankingUpVB(){
        String checkId = checkIdValidationTxtFld.getText(),
                installmentIdStr = installmentIdValidationTxtFld.getText();
        if(checkId.isEmpty() || installmentIdStr.isEmpty()){
            checkIdValidationTxtFld.setStyle("-fx-border-color: red;");
            installmentIdValidationTxtFld.setStyle("-fx-border-color: red;");
            bankingUpVB.setVisible(false);
        } else {
            int installmentId = Integer.parseInt(installmentIdStr);
            if(!module.checkInstallment(installmentId, selectedUnitId)){
                installmentIdValidationTxtFld.
                        setStyle("-fx-border-color: red;");
                bankingUpVB.setVisible(false);
            } else{
                int receiptId = module.getCheckReceiptId(installmentId,
                        checkId);
                if(receiptId == 0){
                    checkIdValidationTxtFld.setStyle("-fx-border-color: red;");
                    installmentIdValidationTxtFld.
                            setStyle("-fx-border-color: red;");
                    bankingUpVB.setVisible(false);
                } else if(module.BankedUpB4(installmentId, checkId)){
                    alertError("خطأ فى تحصيل القسط",
                            "هذا القسط تم تحصيله مسبقا");
                } else {
                    bankTxt.setText(module.getBankName(installmentId, checkId));
                    payingDateTxt.setText("تاريخ استلام الشيك: " +
                            module.getReceiptDate(installmentId, checkId));
                    payInstallmentVB.setVisible(false);
                    bankingUpVB.setVisible(true);
                }
            }
        }
    }
    
    @FXML
    private void bankUpCheck(){
        
        String checkValueStr = checkValueTxtFld.getText(),
                bankUpDayStr = bankUpDayTxtFld.getText(),
                bankUpMonthStr = bankUpMonthTxtFld.getText(),
                bankUpYearStr = bankUpYearTxtFld.getText(),
                checkId = checkIdValidationTxtFld.getText();
        if(checkValueStr.isEmpty()){
            checkValueTxtFld.setStyle("-fx-border-color: red;");
            return;
        } else {
            checkValueTxtFld.setStyle("-fx-border-color: transparent;");
        }
        if(bankUpDayStr.isEmpty()){
            bankUpDayTxtFld.setStyle("-fx-border-color: red;");
            return;
        } else {
            bankUpDayTxtFld.setStyle("-fx-border-color: transparent;");
        }
        if(bankUpMonthStr.isEmpty()){
            bankUpMonthTxtFld.setStyle("-fx-border-color: red;");
            return;
        } else {
            bankUpMonthTxtFld.setStyle("-fx-border-color: transparent;");
        }
        if(bankUpYearStr.isEmpty()){
            bankUpYearTxtFld.setStyle("-fx-border-color: red;");
            return;
        } else {
            bankUpYearTxtFld.setStyle("-fx-border-color: transparent;");
        }
        String installmentIdStr = installmentIdValidationTxtFld.getText();
        int installmentId = Integer.parseInt(installmentIdStr);
        int receiptId = module.getCheckReceiptId(installmentId,
                        checkId);
        float paidFromInstallment = module.
                getPaidFromInstallment(installmentId);
        float remaindValue = module.getInstallmentValue(installmentId) - 
                paidFromInstallment;
        
        float checkValue = Float.parseFloat(checkValueStr);
        
        if(checkValue > remaindValue ){
            alertError("خطأبالمبلغ المدفوع","المبلغ المدفوع اكبر من المتبقى");
            return;
        }
        
        String bankUpDateStr = bankUpYearStr + "-"
                + bankUpMonthStr + "-" + bankUpDayStr;
        Date bankingUpDate = Date.valueOf(bankUpDateStr);
        
        if(confirmProcess(null, "تأكيد تحصيل الشيك")){
            module.addCheck2Installment(installmentId,
                    paidFromInstallment + checkValue );
            module.bankUpCheck(checkValue, bankingUpDate, receiptId);
            checkValueTxtFld.setText("");
            bankUpDayTxtFld.setText("");
            bankUpMonthTxtFld.setText("");
            bankUpYearTxtFld.setText("");
            bankUpYearTxtFld.setText("");
            checkIdValidationTxtFld.setText("");
            installmentIdValidationTxtFld.setText("");
            bankingUpVB.setVisible(false);
        }
    }
    
    @FXML
    private void calculateMulct(ActionEvent event) {
        String installmentValueStr = paymentValue4PayingTxt.getText(),
                mulctRatioStr = mulctRatioTxtFld.getText();
        if (mulctRatioStr.isEmpty()) {
            mulctRatioTxtFld.setStyle("-fx-border-color: red;");
            return;
        } else {
            mulctRatioTxtFld.setStyle("-fx-border-color: transparent;");
        }
        String delayMonStr = delayMonths.getText(),
                delayDayStr = delayDays.getText();
        float delayedDays = Integer.valueOf(delayDayStr),
                delayedMonthes = Integer.valueOf(delayMonStr);
        if (delayedDays > 15) {
            delayedMonthes++;
        }

        float installmentValue = Float.valueOf(installmentValueStr),
                mulctRatio = Float.valueOf(mulctRatioStr),
                mulctValue = (installmentValue * (mulctRatio / 100))
                * (delayedMonthes / 12);
        mulctValueTxt.setText(String.format("%.1f", mulctValue));
    }

    @FXML
    private void payInstallment() {
        String paidValueStr = paidValueTxtFld.getText(),
                installmentIdStr = paymentCode4PayingTxtFld.getText(),
                recieptIdStr = recieptNo4PayingTxtFld.getText(),
                payingDayStr = payingDayTxtFld.getText(),
                payingMonthStr = payingMonthTxtFld.getText(),
                payingYearStr = payingYearTxtFld.getText();
        float paidValue = 0;
        boolean isItCheck = paymentTypeRB.isSelected();
        String checkId, bankName;
        if (isItCheck) {
            bankName = bankTxtFld.getText();
            checkId = checkIdTxtFld.getText();
            if (bankName.isEmpty()) {
                bankTxtFld.setStyle("-fx-border-color: red;");
                return;
            } else {
                bankTxtFld.setStyle("-fx-border-color: transparent;");
            }
            if (checkId.isEmpty()) {
                checkIdTxtFld.setStyle("-fx-border-color: red;");
                return;
            } else {
                checkIdTxtFld.setStyle("-fx-border-color: transparent;");
            }

        } else {
            bankName = "";
            checkId = "";
            paidValue = Float.parseFloat(paidValueStr);
        }
        
        String remainedInstlmntCashStr = remainedInstlmntCashTxt.getText();
        
        float remainedInstlmntCash = Float.valueOf(remainedInstlmntCashStr);
        if(paidValue > remainedInstlmntCash){
            alertError("خطأبالمبلغ المدفوع","المبلغ المدفوع اكبر من المتبقى");
            return;
        }
        int installmentId = Integer.parseInt(installmentIdStr);

        if (recieptIdStr.isEmpty()) {
            recieptNo4PayingTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            recieptNo4PayingTxtFld.setStyle("-fx-border-color:green;");
        }
        if (payingDayStr.isEmpty()) {
            payingDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            payingDayTxtFld.setStyle("-fx-border-color:green;");
        }
        if (payingMonthStr.isEmpty()) {
            payingMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            payingMonthTxtFld.setStyle("-fx-border-color:green;");
        }
        if (payingYearStr.isEmpty()) {
            payingYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            payingYearTxtFld.setStyle("-fx-border-color:green;");
        }

        String payingDateStr = payingYearStr + "-"
                + payingMonthStr + "-" + payingDayStr;

        Date reservationDate = Date.valueOf(payingDateStr);

        if (confirmProcess("تأكيد الدفع", "اتمام عملية الدفع بنجاح")) {
            String mulctRatioStr = mulctRatioTxtFld.getText();
            if (!mulctRatioStr.isEmpty()) {
                if (confirmProcess("تأكيد !", "تأكيد اضافة غرامه")) {
                    String mulctValueStr = mulctValueTxt.getText(),
                            installmentValueStr = paymentValue4PayingTxt.
                                    getText();
                    float mulctValue = Float.valueOf(mulctValueStr),
                            installmentValue = Float.
                                    valueOf(installmentValueStr),
                            newInstallmentValue = installmentValue + mulctValue;
                    module.addMulct(newInstallmentValue, installmentId);
                }
            }
            

            boolean finished = ((remainedInstlmntCash - paidValue) <= 0);
            float totalPaidFromInstallment = paidValue
                    + module.getPaidFromInstallment(installmentId);
            module.payInstallment(totalPaidFromInstallment, recieptIdStr,
                    isItCheck, reservationDate, installmentId, finished,
                    paidValue, bankName, checkId);

            paidValueTxtFld.setText("");
            paymentCode4PayingTxtFld.setText("");
            recieptNo4PayingTxtFld.setText("");
            payingDayTxtFld.setText("");
            payingMonthTxtFld.setText("");
            payingYearTxtFld.setText("");
            mulctRatioTxtFld.setText("");
            mulctValueTxt.setText("");
            bankTxtFld.setText("");
            checkIdTxtFld.setText("");

            payInstallmentVB.setVisible(false);
        }
    }

    @FXML
    private void editInstallment() {
        String installmentIdStr = paymentCode4EditingTxtFld.getText();
        int installmentId = Integer.parseInt(installmentIdStr);

        if (installmentIdStr.isEmpty()) {
            paymentCode4EditingTxtFld.setStyle("-fx-border-color:red;");
        } else {
            module.checkInsallmentExistance(installmentId, selectedUnitId);
        }
        String paymentName = String.valueOf(paymentName4editingCB.getValue());

        String installmentDayStr = editInstallmentDayTxtFld.getText(),
                installmentMonthStr = editInstallmentMonthTxtFld.getText(),
                installmentYearStr = editInstallmentYearTxtFld.getText(),
                installmentValueStr = paymentValue4editingTxtFld.getText(),
                paidValueStr = editPaidValueTxtFld.getText();

        if (installmentDayStr.isEmpty()) {
            editInstallmentDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            editInstallmentDayTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (installmentMonthStr.isEmpty()) {
            editInstallmentMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            editInstallmentMonthTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (installmentYearStr.isEmpty()) {
            editInstallmentYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            editInstallmentYearTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (installmentValueStr.isEmpty()) {
            paymentValue4editingTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            paymentValue4editingTxtFld.setStyle("-fx-border-color:transparent;");
        }

        String installmentDateStr = installmentYearStr + "-"
                + installmentMonthStr + "-" + installmentDayStr;

        Date installmentDate = Date.valueOf(installmentDateStr);

        String confirmationStr = "اسم الدفعه: " + paymentName
                + System.lineSeparator() + "بتاريخ استحقاق: " + installmentDateStr
                + System.lineSeparator() + "بقيمة: " + installmentValueStr
                + System.lineSeparator() + "القيمه المدفوعه: " + paidValueStr,
                headerStr = "تاكيد تعديل البيانات التاليه للدفعه بكود : "
                + installmentIdStr;
        if (confirmProcess(headerStr, confirmationStr)) {
            try {
                float installmentValue = Float.parseFloat(installmentValueStr),
                        paidValue = Float.parseFloat(paidValueStr);

                module.editingInstallment(installmentId, paymentName,
                        installmentValue, installmentDate, paidValue,
                        installmentsTypeMap.get(paymentName));

                paymentCode4EditingTxtFld.setText("");
                editInstallmentDayTxtFld.setText("");
                editInstallmentMonthTxtFld.setText("");
                editInstallmentYearTxtFld.setText("");
                paymentValue4editingTxtFld.setText("");
                editPaidValueTxtFld.setText("");

            } catch (NumberFormatException ex) {
                alertError("خطأ فى القيم المدخله!",
                        "من فضلك قم بأدخال القيم صحيحه");
            }
        }
    }

    @FXML
    private void addInstallment() {

        String paymentName = String.valueOf(addPaymentCB.getValue()),
                installmentDayStr = addInstlmntDayTxtFld.getText(),
                installmentMonthStr = addInstlmntMonthTxtFld.getText(),
                installmentYearStr = addInstlmntYearTxtFld.getText(),
                installmentValueStr = addInstallmentValueTxtFld.getText();

        if (installmentDayStr.isEmpty()) {
            addInstlmntDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            addInstlmntDayTxtFld.setStyle("-fx-border-color:green;");
        }
        if (installmentMonthStr.isEmpty()) {
            addInstlmntMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            addInstlmntMonthTxtFld.setStyle("-fx-border-color:green;");
        }
        if (installmentYearStr.isEmpty()) {
            addInstlmntYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            addInstlmntYearTxtFld.setStyle("-fx-border-color:green;");
        }
        if (installmentValueStr.isEmpty()) {
            addInstallmentValueTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            addInstallmentValueTxtFld.setStyle("-fx-border-color:green;");
        }

        String installmentDateStr = installmentYearStr + "-"
                + installmentMonthStr + "-" + installmentDayStr;

        Date installmentDate = Date.valueOf(installmentDateStr);

        String confirmationStr = "اسم الدفعه : " + paymentName
                + System.lineSeparator() + "بتاريخ استحقاق: " + installmentDateStr
                + System.lineSeparator() + "و قيمة: " + installmentValueStr,
                confirmationHeader = "تأكيد اضفة دفعه";

        if (confirmProcess(confirmationHeader, confirmationStr)) {
            Float installmentValue = Float.parseFloat(installmentValueStr);
            module.addInstallment(selectedUnitId, selectedBuildingId, clientId,
                    paymentName, installmentDate, installmentValue,
                    installmentsTypeMap.get(paymentName));

            addInstlmntDayTxtFld.setText("");
            addInstlmntMonthTxtFld.setText("");
            addInstlmntYearTxtFld.setText("");
            addInstallmentValueTxtFld.setText("");

        }
    }

    @FXML
    private void handOverUnit() {

        String recepDayStr = receptionDayTxtFld.getText(),
                recepMonthStr = receptionMonthTxtFld.getText(),
                recepYearStr = receptionYearTxtFld.getText();

        if (recepDayStr.isEmpty()) {
            receptionDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            receptionDayTxtFld.setStyle("-fx-border-color:green;");
        }
        if (recepMonthStr.isEmpty()) {
            receptionMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            receptionMonthTxtFld.setStyle("-fx-border-color:green;");
        }
        if (recepYearStr.isEmpty()) {
            receptionYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            receptionYearTxtFld.setStyle("-fx-border-color:green;");
        }

        String handOverDateStr = recepYearStr + "-"
                + recepMonthStr + "-" + recepDayStr;

        Date handOverDate = Date.valueOf(handOverDateStr);
        String confirmationBodyStr
                = "اتمام تسليم الوحده بكود : " + selectedUnitCode
                + System.lineSeparator() + "الى العميل بكود : " + clientId;
        String confirmationHeader = "تاكيد تسليم الوحده";
        if (confirmProcess(confirmationHeader, confirmationBodyStr)) {
            module.handOverUnit(selectedUnitId, handOverDate);
            receptionBtn.setStyle("-fx-background-color: green;");
            providUnitDetails(selectedUnitId, selectedBuildingId);
        }
    }

    @FXML
    private void transferUnit() {
        boolean transferingOptions = newClientRB.isSelected();
        if (transferingOptions) {

            String newClientNewName = newClientNameTxtFld.getText(),
                    newClientPersonalId = newClientPersonalIdTxtFld.getText(),
                    newClientCareer = newClientCareerTxtFld.getText(),
                    newClientPhone = newClientMobileNumberTxtFld.getText();

            String confirmationBodyStr = "تأكيد نقل الوحده من عميل: " + clientId
                    + System.lineSeparator() + "الى عميل: " + newClientNewName,
                    confirmationHeaderStr = "تأكيد نقل وحده",
                    confirmationBodyStr2
                    = "تأكيد نقل الاقساط الغير مدفوعه الى المالك الجديد",
                    confirmationHeaderStr2 = "تأكيد نقل اقساط";

            if (confirmProcess(confirmationHeaderStr, confirmationBodyStr)) {

                module.addClient(newClientNewName, newClientCareer,
                        newClientPersonalId, newClientPhone, "", "", "");
                int newClientId = module.getClientId();

                module.add2Transitions(selectedUnitId, selectedBuildingId,
                        newClientId, clientId);

                clientId = newClientId;
                module.transferUnit(clientId, selectedUnitId);

                transformationBtn.setDisable(true);
                if (confirmProcess(confirmationHeaderStr2,
                        confirmationBodyStr2)) {
                    module.transferUnpaidInstallments(clientId, selectedUnitId);
                }
            }
        } else {
            String existedClientIdStr = clientCodeTxtFld.getText();
            int existedClientId = Integer.parseInt(existedClientIdStr);

            String confirmationBodyStr = "تأكيد نقل الوحده من عميل: " + clientId
                    + System.lineSeparator() + "الى عميل: " + existedClientId,
                    confirmationHeaderStr = "تأكيد نقل وحده";

            if (confirmProcess(confirmationHeaderStr, confirmationBodyStr)) {
                module.add2Transitions(selectedUnitId, selectedBuildingId,
                        existedClientId, clientId);
                module.transferUnit(existedClientId, selectedUnitId);
                clientId = existedClientId;
                transformationBtn.setDisable(true);

                String confirmationBodyStr2
                        = "تأكيد نقل الاقساط الغير مدفوعه الى المالك الجديد",
                        confirmationHeaderStr2 = "تأكيد نقل اقساط";

                if (confirmProcess(confirmationHeaderStr2,
                        confirmationBodyStr2)) {
                    module.transferUnpaidInstallments(clientId, selectedUnitId);
                }
            }

        }
    }

    @FXML
    private void deleteUnpaidInstallment() {
        String confirmationBodyStr = "تأكيد مسح الاقساط الغير مدفوعه";
        if (confirmProcess(null, confirmationBodyStr)) {
            module.deleteUnpaidInstallments(selectedUnitId);
        }
    }

    @FXML
    private void payCash() {

        String cashPaymentDayStr = cashPaymentDayTxtFld.getText(),
                cashPaymentMonthStr = cashPaymentMonthTxtFld.getText(),
                CashPaymentYearStr = cashPaymentYearTxtFld.getText(),
                recieptId = recieptTxtFld.getText();

        if (cashPaymentDayStr.isEmpty()) {
            cashPaymentDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashPaymentDayTxtFld.setStyle("-fx-border-color:green;");
        }
        if (cashPaymentMonthStr.isEmpty()) {
            cashPaymentMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashPaymentMonthTxtFld.setStyle("-fx-border-color:green;");
        }
        if (CashPaymentYearStr.isEmpty()) {
            cashPaymentYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashPaymentYearTxtFld.setStyle("-fx-border-color:green;");
        }
        if (recieptId.isEmpty()) {
            recieptTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            recieptTxtFld.setStyle("-fx-border-color:green;");
        }

        String cashPaymentDateStr = CashPaymentYearStr + "-"
                + cashPaymentMonthStr + "-" + cashPaymentDayStr;

        Date cashPaymentDate = Date.valueOf(cashPaymentDateStr);

        String discountRatioStr = cashDiscountRetioTxtFld.getText();
        float discountRatio;
        if (discountRatioStr.isEmpty()) {
            discountRatio = 0;
        } else {
            discountRatio = Float.parseFloat(discountRatioStr);
        }

        float previousInstallmentsValues = module.
                getTotalInstallmentsValues(selectedUnitId, clientId);

        float paidValue = selectedUnitPrice
                - (selectedUnitPrice * (discountRatio / 100))
                - previousInstallmentsValues;

        String confirmationHeaderStr = "تاكيد دفع كاش",
                confirmationBodyStr = "تأكيد دفع دفعة كاش بقيمة : "
                + String.format("%.1f", paidValue)
                + System.lineSeparator() + "بنسبة خصم : " + discountRatioStr
                + System.lineSeparator() + "رقم الايصال: " + recieptId
                + System.lineSeparator() + "بتاريخ: " + cashPaymentDateStr
                + System.lineSeparator() + "طريقة التحصيل : نقدى";

        if (confirmProcess(confirmationHeaderStr, confirmationBodyStr)) {
            module.payCash(paidValue, cashPaymentDate, recieptId,
                    selectedUnitId, selectedBuildingId, clientId, "", "",
                    cashPaymentDate, false);
            module.setPaymentInitiated(selectedUnitId, discountRatio);
            payCashBtn.setStyle("-fx-background-color:green;");
            payCashBtn.setDisable(true);
            addCashCheckBtn.setDisable(true);
        }
    }

    @FXML
    private void cashInstallment() {
        String cashPaymentDayStr = cashPaymentDayTxtFld.getText(),
                cashPaymentMonthStr = cashPaymentMonthTxtFld.getText(),
                CashPaymentYearStr = cashPaymentYearTxtFld.getText(),
                recieptId = recieptTxtFld.getText(),
                bank = cashCheckBankTxtFld.getText(),
                chckId = cashCheckIdTxtFld.getText(), 
                checkDay = DayTxtFld.getText(),
                checkMon = MonTxtFld.getText(),
                checkYear = YearTxtFld.getText();
        
        if (cashPaymentDayStr.isEmpty()) {
            cashPaymentDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashPaymentDayTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (cashPaymentMonthStr.isEmpty()) {
            cashPaymentMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashPaymentMonthTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (CashPaymentYearStr.isEmpty()) {
            cashPaymentYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashPaymentYearTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (bank.isEmpty()) {
            cashCheckBankTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashCheckBankTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (bank.isEmpty()) {
            cashCheckBankTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashCheckBankTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (chckId.isEmpty()) {
            cashCheckIdTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            cashCheckIdTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (checkDay.isEmpty()) {
            DayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            DayTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (checkMon.isEmpty()) {
            MonTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            MonTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (checkYear.isEmpty()) {
            YearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            YearTxtFld.setStyle("-fx-border-color:transparent;");
        }
        if (recieptId.isEmpty()) {
            recieptTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            recieptTxtFld.setStyle("-fx-border-color:transparent;");
        }

        String cashPaymentDateStr = CashPaymentYearStr + "-"
                + cashPaymentMonthStr + "-" + cashPaymentDayStr,
                checkDateStr = checkYear + "-" + checkMon + "-" + checkDay;

        Date cashPaymentDate = Date.valueOf(cashPaymentDateStr),
                checkDate = Date.valueOf(checkDateStr);

        String discountRatioStr = cashDiscountRetioTxtFld.getText();

        float discountRatio;
        if (discountRatioStr.isEmpty()) {
            discountRatio = 0;
        } else {
            discountRatio = Float.parseFloat(discountRatioStr);
        }

        float previousInstallmentsValues = module.
                getTotalInstallmentsValues(selectedUnitId, clientId);

        float paidValue = selectedUnitPrice
                - (selectedUnitPrice * (discountRatio / 100))
                - previousInstallmentsValues;

        String confirmationHeaderStr = "تاكيد دفع كاش",
                confirmationBodyStr = "تأكيد اضافة دفعة كاش"
                + System.lineSeparator() + " بقيمة: "
                + String.format("%.1f", paidValue)
                + System.lineSeparator() + "بتاريخ: " + cashPaymentDateStr
                + System.lineSeparator() + "طريقة التحصيل : شيك";
        if (confirmProcess(confirmationHeaderStr, confirmationBodyStr)) {
            module.payCash(paidValue, cashPaymentDate, recieptId, selectedUnitId,
                    selectedBuildingId, clientId, bank, chckId, checkDate, true);
            module.setPaymentInitiated(selectedUnitId, discountRatio);
            addCashCheckBtn.setStyle("-fx-background-color:green;");
            payCashBtn.setDisable(true);
            addCashCheckBtn.setDisable(true);
        }
    }

    @FXML
    private void restorePayment() {

        String restorPaymentDayStr = restorInstlmntDayTxtFld.getText(),
                restorPaymentMonthStr = restorInstlmntMonthTxtFld.getText(),
                restorPaymentYearStr = restorInstlmntYearTxtFld.getText(),
                restorationReceipt = restorationReceiptTxtFld.getText(),
                restoredValueStr = restoredValueTxtFld.getText();

        if (restorPaymentDayStr.isEmpty()) {
            restorInstlmntDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            restorInstlmntDayTxtFld.setStyle("-fx-border-color:green;");
        }
        if (restorPaymentMonthStr.isEmpty()) {
            restorInstlmntMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            restorInstlmntMonthTxtFld.setStyle("-fx-border-color:green;");
        }
        if (restorPaymentYearStr.isEmpty()) {
            restorInstlmntYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            restorInstlmntYearTxtFld.setStyle("-fx-border-color:green;");
        }
        if (restorationReceipt.isEmpty()) {
            restorationReceiptTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            restorationReceiptTxtFld.setStyle("-fx-border-color:green;");
        }
        if (restoredValueStr.isEmpty()) {
            restoredValueTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            restoredValueTxtFld.setStyle("-fx-border-color:green;");
        }

        String restorPaymentDateStr = restorPaymentYearStr + "-"
                + restorPaymentMonthStr + "-" + restorPaymentDayStr;

        Date restorPaymentDate = Date.valueOf(restorPaymentDateStr);

        float restoredValue = Float.parseFloat(restoredValueStr);

        RadioButton restoredFromRB = (RadioButton) restorationToggleGroup.
                getSelectedToggle();
        String installmentType = ESSENTIAL_INSTALLMENT,
                forConfirmation = " مستحقات اساسيه";

        if (restoredFromRB == restorationFromPrimaryRB) {
            
            installmentType = ESSENTIAL_INSTALLMENT;
            forConfirmation = "مستحقات الاساسيه";
            
        } else if(restoredFromRB == restorationFromAdditionalRB) {
            
            installmentType = ADDITIONAL_INSTALLMENT;
            forConfirmation = "مستحقات اضافيه";
            
        } else if(restoredFromRB == restorationFromFacilitiesRB) {
            
            installmentType = FACILITIES_INSTALLMENT;
            forConfirmation = "مستحقات مرافق";
            
        } else if(restoredFromRB == restorationFromMaintenanceRB) {
            
            installmentType = MAINTENANCE_INSTALLMENT;
            forConfirmation = "مستحقات صيانه";
            
        } else if(restoredFromRB == restorationFromAdjustmentRB) {
            
            installmentType = ADJUSTMENT_INSTALLMENT;
            forConfirmation = "مستحقات تعديلات";
            
        }
        String bank = "", checkId = "";
        Date checkDate = restorPaymentDate;
        boolean isItCheck = restorationCheckRB.isSelected();
        if (isItCheck) {
            bank = resotationBankTxtFld.getText();
            checkId = restoredCheckIdTxtFld.getText();
            String checkDayStr = resotationCheckDayTxtFld.getText(),
                    checkMonStr = resotationCheckMonTxtFld.getText(),
                    checkYearStr = resotationCheckYearTxtFld.getText();
            if (bank.isEmpty()) {
                resotationBankTxtFld.setStyle("-fx-border-color: red");
                return;
            }
            if (checkId.isEmpty()) {
                restoredCheckIdTxtFld.setStyle("-fx-border-color: red");
                return;
            }
            if (checkDayStr.isEmpty()) {
                resotationCheckDayTxtFld.setStyle("-fx-border-color: red");
                return;
            }
            if (checkMonStr.isEmpty()) {
                resotationCheckMonTxtFld.setStyle("-fx-border-color: red");
                return;
            }
            if (checkYearStr.isEmpty()) {
                resotationCheckYearTxtFld.setStyle("-fx-border-color: red");
                return;
            }
            
            String checkDateStr = checkYearStr + "-"
                + checkMonStr + "-" + checkDayStr;
            
            checkDate = Date.valueOf(checkDateStr);
        } 
        String confirmationHeaderStr = "تأكيد استرجاع مبلغ",
                confrimationBodyStr = "تأكيد استرجاع قيمة: " + restoredValue
                + System.lineSeparator() + " استرداد من : " + forConfirmation
                + System.lineSeparator() + " في تاريخ: " + restorPaymentDateStr
                + System.lineSeparator() + "رقم الايصال: " + restorationReceipt;
        if (confirmProcess(confirmationHeaderStr, confrimationBodyStr)) {
            module.restorPayment(restoredValue, restorPaymentDate,
                    restorationReceipt, selectedUnitId, selectedBuildingId,
                    clientId, installmentType, bank, checkId, checkDate,
                    isItCheck);

            restorInstlmntDayTxtFld.setText("");
            restorInstlmntMonthTxtFld.setText("");
            restorInstlmntYearTxtFld.setText("");
            restorationReceiptTxtFld.setText("");
            restoredValueTxtFld.setText("");
        }
    }

    @FXML
    private void restorUnit() {

        String restorationDayStr = restorationDayTxtFld.getText(),
                restorationMonthStr = restorationMonthTxtFld.getText(),
                restorationYearStr = restorationYearTxtFld.getText();

        if (restorationDayStr.isEmpty()) {
            restorationDayTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            restorationDayTxtFld.setStyle("-fx-border-color:green;");
        }
        if (restorationMonthStr.isEmpty()) {
            restorationMonthTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            restorationMonthTxtFld.setStyle("-fx-border-color:green;");
        }
        if (restorationYearStr.isEmpty()) {
            restorationYearTxtFld.setStyle("-fx-border-color:red;");
            return;
        } else {
            restorationYearTxtFld.setStyle("-fx-border-color:green;");
        }

        String restorationDateStr = restorationYearStr + "-"
                + restorationMonthStr + "-" + restorationDayStr;

        Date restorationDate = Date.valueOf(restorationDateStr);

        String confirmationHeaderStr = "تأكيد استرجاع الوحده",
                confirmationBodyStr = "تأكيد استرجاع الوحده : " + selectedUnitId
                + System.lineSeparator() + "من العميل : " + clientId
                + System.lineSeparator() + " فى تاريخ : " + restorationDateStr;
        if (confirmProcess(confirmationHeaderStr, confirmationBodyStr)) {
            module.restorUnit(selectedUnitId, clientId, restorationDate);
            restorationBtn.setStyle("-fx-background-color:green;");
            cancelReservationBtn.setDisable(true);
            receptionBtn.setDisable(true);
            reinstallationBtn.setDisable(true);
            restorationBtn.setDisable(true); 
        }
    }
}
