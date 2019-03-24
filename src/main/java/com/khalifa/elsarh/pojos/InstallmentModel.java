/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.pojos;

import java.sql.Date;

/**
 *
 * @author muhammad
 */
public class InstallmentModel {
    public static final String ESSENTIAL_INSTALLMENT = "essential",
            ADDITIONAL_INSTALLMENT = "additional",
            ADJUSTMENT_INSTALLMENT = "adjustments",
            FACILITIES_INSTALLMENT = "facilities",
            MAINTENANCE_INSTALLMENT = "maintenance";
    
    private int id, unitId, buildingId, clientId;
    private String paymentName, receiptId, type; //type = essential || not_essential || from_unit_price
    private float installmentValue, installmentPaidValue;
    private Date installmentDate, paymentDate;
    private boolean Finished, isItCheck;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the paymentName
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * @param paymentName the paymentName to set
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    /**
     * @return the installmentDate
     */
    public Date getInstallmentDate() {
        return installmentDate;
    }

    /**
     * @param installmentDate the installmentDate to set
     */
    public void setInstallmentDate(Date installmentDate) {
        this.installmentDate = installmentDate;
    }

    /**
     * @return the paymentDate
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * @param paymentDate the paymentDate to set
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return the receiptId
     */
    public String getReceiptId() {
        return receiptId;
    }

    /**
     * @param receiptId the receiptId to set
     */
    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    /**
     * @return the installmentValue
     */
    public float getInstallmentValue() {
        return installmentValue;
    }

    /**
     * @param installmentValue the installmentValue to set
     */
    public void setInstallmentValue(float installmentValue) {
        this.installmentValue = installmentValue;
    }

    /**
     * @return the installmentPaidValue
     */
    public float getInstallmentPaidValue() {
        return installmentPaidValue;
    }

    /**
     * @param installmentPaidValue the installmentPaidValue to set
     */
    public void setInstallmentPaidValue(float installmentPaidValue) {
        this.installmentPaidValue = installmentPaidValue;
    }

    /**
     * @return the Finished
     */
    public boolean isFinished() {
        return Finished;
    }

    /**
     * @param Finished the Finished to set
     */
    public void setFinished(boolean Finished) {
        this.Finished = Finished;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the unitId
     */
    public int getUnitId() {
        return unitId;
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    /**
     * @return the buildingId
     */
    public int getBuildingId() {
        return buildingId;
    }

    /**
     * @param buildingId the buildingId to set
     */
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * @return the isItCheck
     */
    public boolean isItCheck() {
        return isItCheck;
    }

    /**
     * @param isItCheck the isItCheck to set
     */
    public void setIsItCheck(boolean isItCheck) {
        this.isItCheck = isItCheck;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
