/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.pojos;

/**
 *
 * @author khalifa
 */
public class RestoredFinanceModule {
    
    private String clientName, buildingName;
    
    private int cId, bId, mobileNumber;
    
    private float totalPaidCash, totalRestoredCash, totalRemaind;
    
    private java.sql.Date restorationDate, contractDate;

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return the buildingName
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * @param buildingName the buildingName to set
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     * @return the cId
     */
    public int getcId() {
        return cId;
    }

    /**
     * @param cId the cId to set
     */
    public void setcId(int cId) {
        this.cId = cId;
    }

    /**
     * @return the bId
     */
    public int getbId() {
        return bId;
    }

    /**
     * @param bId the bId to set
     */
    public void setbId(int bId) {
        this.bId = bId;
    }

    /**
     * @return the mobileNumber
     */
    public int getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobileNumber to set
     */
    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the totalPaidCash
     */
    public float getTotalPaidCash() {
        return totalPaidCash;
    }

    /**
     * @param totalPaidCash the totalPaidCash to set
     */
    public void setTotalPaidCash(float totalPaidCash) {
        this.totalPaidCash = totalPaidCash;
    }

    /**
     * @return the totalRestoredCash
     */
    public float getTotalRestoredCash() {
        return totalRestoredCash;
    }

    /**
     * @param totalRestoredCash the totalRestoredCash to set
     */
    public void setTotalRestoredCash(float totalRestoredCash) {
        this.totalRestoredCash = totalRestoredCash;
    }

    /**
     * @return the totalRemaind
     */
    public float getTotalRemaind() {
        return totalRemaind;
    }

    /**
     * @param totalRemaind the totalRemaind to set
     */
    public void setTotalRemaind(float totalRemaind) {
        this.totalRemaind = totalRemaind;
    }

    /**
     * @return the restorationDate
     */
    public java.sql.Date getRestorationDate() {
        return restorationDate;
    }

    /**
     * @param restorationDate the restorationDate to set
     */
    public void setRestorationDate(java.sql.Date restorationDate) {
        this.restorationDate = restorationDate;
    }

    /**
     * @return the contractDate
     */
    public java.sql.Date getContractDate() {
        return contractDate;
    }

    /**
     * @param contractDate the contractDate to set
     */
    public void setContractDate(java.sql.Date contractDate) {
        this.contractDate = contractDate;
    }
    
}
