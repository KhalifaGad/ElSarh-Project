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
public class TransitionsModel {
    private int clientId, xClientId, unitId, buildingId;
    private Date transitionDate ;

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
     * @return the xClientId
     */
    public int getxClientId() {
        return xClientId;
    }

    /**
     * @param xClientId the xClientId to set
     */
    public void setxClientId(int xClientId) {
        this.xClientId = xClientId;
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
     * @return the transitionDate
     */
    public Date getTransitionDate() {
        return transitionDate;
    }

    /**
     * @param transitionDate the transitionDate to set
     */
    public void setTransitionDate(Date transitionDate) {
        this.transitionDate = transitionDate;
    }
}
