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
public class ReceiptModel {

    private int id, installmentId;
    private String receiptId, checkId, bank;
    private Date receiptDate, bankingUpDate;
    private boolean isItCheck;
    private float receiptValue;

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
     * @return the installmentId
     */
    public int getInstallmentId() {
        return installmentId;
    }

    /**
     * @param installmentId the installmentId to set
     */
    public void setInstallmentId(int installmentId) {
        this.installmentId = installmentId;
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
     * @return the receiptDate
     */
    public Date getReceiptDate() {
        return receiptDate;
    }

    /**
     * @param receiptDate the receiptDate to set
     */
    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    /**
     * @return the isItCheck
     */
    public boolean isIsItCheck() {
        return isItCheck;
    }

    /**
     * @param isItCheck the isItCheck to set
     */
    public void setIsItCheck(boolean isItCheck) {
        this.isItCheck = isItCheck;
    }

    /**
     * @return the receiptValue
     */
    public float getReceiptValue() {
        return receiptValue;
    }

    /**
     * @param receiptValue the receiptValue to set
     */
    public void setReceiptValue(float receiptValue) {
        this.receiptValue = receiptValue;
    }

    /**
     * @return the checkId
     */
    public String getCheckId() {
        return checkId;
    }

    /**
     * @param checkId the checkId to set
     */
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    /**
     * @return the bank
     */
    public String getBank() {
        return bank;
    }

    /**
     * @param bank the bank to set
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * @return the bankingUpDate
     */
    public Date getBankingUpDate() {
        return bankingUpDate;
    }

    /**
     * @param bankingUpDate the bankingUpDate to set
     */
    public void setBankingUpDate(Date bankingUpDate) {
        this.bankingUpDate = bankingUpDate;
    }

}
