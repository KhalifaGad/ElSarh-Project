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
public class RestorationModel {
    private int clientId, unitId;
    private Date restorationDate;

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
     * @return the restorationDate
     */
    public Date getRestorationDate() {
        return restorationDate;
    }

    /**
     * @param restorationDate the restorationDate to set
     */
    public void setRestorationDate(Date restorationDate) {
        this.restorationDate = restorationDate;
    }
}
