/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.pojos;

import java.sql.Date;

/**
 *
 * @author khalifa
 */
public class UnitModel {
    
    private int id, floorNumber, unitCode, buildingId, clientId;
    private float specialPrice, discountRatio;
    private String modelName;
    private boolean availabilityStatus;
    private char type;
    private Date receptionDate, contructDate;
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
     * @return the floorNumber
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * @param floorNumber the floorNumber to set
     */
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    /**
     * @return the unitCode
     */
    public int getUnitCode() {
        return unitCode;
    }

    /**
     * @param unitCode the unitCode to set
     */
    public void setUnitCode(int unitCode) {
        this.unitCode = unitCode;
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
     * @return the specialPrice
     */
    public float getSpecialPrice() {
        return specialPrice;
    }

    /**
     * @param specialPrice the specialPrice to set
     */
    public void setSpecialPrice(float specialPrice) {
        this.specialPrice = specialPrice;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * @return the availabilityStatus
     */
    public boolean isAvailable() {
        return availabilityStatus;
    }

    /**
     * @param availabilityStatus the availabilityStatus to set
     */
    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
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

    /**
     * @return the discountRatio
     */
    public float getDiscountRatio() {
        return discountRatio;
    }

    /**
     * @param discountRatio the discountRatio to set
     */
    public void setDiscountRatio(float discountRatio) {
        this.discountRatio = discountRatio;
    }

    /**
     * @return the receptionDate
     */
    public Date getReceptionDate() {
        return receptionDate;
    }

    /**
     * @param receptionDate the receptionDate to set
     */
    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
    }

    /**
     * @return the contructDate
     */
    public Date getContructDate() {
        return contructDate;
    }

    /**
     * @param contructDate the contructDate to set
     */
    public void setContructDate(Date contructDate) {
        this.contructDate = contructDate;
    }
    
    
}
