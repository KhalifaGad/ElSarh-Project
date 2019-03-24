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


public class ClientModel {

    
    private int id, mobileNumber;
    private String name, career, personalId, mobile1, mobile2, address, email;
    private boolean mediaCheck;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the career
     */
    public String getCareer() {
        return career;
    }

    /**
     * @param career the career to set
     */
    public void setCareer(String career) {
        this.career = career;
    }

    /**
     * @return the personalId
     */
    public String getPersonalId() {
        return personalId;
    }

    /**
     * @param personalId the personalId to set
     */
    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    /**
     * @return the mediaCheck
     */
    public boolean isMediaExist() {
        return mediaCheck;
    }

    /**
     * @param mediaCheck the mediaCheck to set
     */
    public void setMediaCheck(boolean mediaCheck) {
        this.mediaCheck = mediaCheck;
    }

    /**
     * @return the mobile1
     */
    public String getMobile1() {
        return mobile1;
    }

    /**
     * @param mobile1 the mobile1 to set
     */
    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    /**
     * @return the mobile2
     */
    public String getMobile2() {
        return mobile2;
    }

    /**
     * @param mobile2 the mobile2 to set
     */
    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }
    
}
