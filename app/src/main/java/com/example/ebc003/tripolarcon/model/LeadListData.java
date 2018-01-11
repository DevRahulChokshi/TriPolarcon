package com.example.ebc003.tripolarcon.model;

import android.util.Log;

/**
 * Created by EBC003 on 12/20/2017.
 */

public class LeadListData {

    public String txtCompanyId;
    public String txtUser;
    public String txtLeadTitle;
    public String txtUserEmail;
    public Boolean isCheckState;
    public String strWebsite;
    public String strOfficeNumber;
    public String strAddress;
    public String strFaxNumber;
    public String strPersonName;
    public String strPersonNumber;
    public String strPersonDesignation;
    public String strNote;

    public LeadListData () {
    }

//    public LeadListData (String txtUser, String txtLeadTitle, String txtUserEmail, Boolean isCheckState) {
//        this.txtUser = txtUser;
//        this.txtLeadTitle = txtLeadTitle;
//        this.txtUserEmail = txtUserEmail;
//        this.isCheckState = isCheckState;
//    }

    public String getTxtCompanyId () {
        return txtCompanyId;
    }

    public void setTxtCompanyId (String txtCompanyId) {
        this.txtCompanyId = txtCompanyId;
    }

    public void setTxtUser (String txtUser) {
        this.txtUser = txtUser;
    }

    public void setTxtLeadTitle (String txtLeadTitle) {
        this.txtLeadTitle = txtLeadTitle;
    }

    public void setTxtUserEmail (String txtUserEmail) {
        this.txtUserEmail = txtUserEmail;
    }

    public void setCheckState (Boolean checkState) {
        isCheckState = checkState;
    }

    public String getTxtUser () {
        Log.i (LeadListData.class.getSimpleName (),"List Data:-"+txtUser);
        return txtUser;
    }

    public String getTxtLeadTitle () {
        Log.i (LeadListData.class.getSimpleName (),"List Data:-"+txtLeadTitle);
        return txtLeadTitle;
    }

    public String getTxtUserEmail () {
        Log.i (LeadListData.class.getSimpleName (),"List Data:-"+txtUserEmail);
        return txtUserEmail;
    }

    public Boolean getCheckState () {
        Log.i (LeadListData.class.getSimpleName (),"List Data:-"+isCheckState);
        return isCheckState;
    }

    public String getStrWebsite () {
        return strWebsite;
    }

    public void setStrWebsite (String strWebsite) {
        this.strWebsite = strWebsite;
    }

    public String getStrOfficeNumber () {
        return strOfficeNumber;
    }

    public void setStrOfficeNumber (String strOfficeNumber) {
        this.strOfficeNumber = strOfficeNumber;
    }

    public String getStrAddress () {
        return strAddress;
    }

    public void setStrAddress (String strAddress) {
        this.strAddress = strAddress;
    }

    public String getStrFaxNumber () {
        return strFaxNumber;
    }

    public void setStrFaxNumber (String strFaxNumber) {
        this.strFaxNumber = strFaxNumber;
    }

    public String getStrPersonName () {
        return strPersonName;
    }

    public void setStrPersonName (String strPersonName) {
        this.strPersonName = strPersonName;
    }

    public String getStrPersonNumber () {
        return strPersonNumber;
    }

    public void setStrPersonNumber (String strPersonNumber) {
        this.strPersonNumber = strPersonNumber;
    }

    public String getStrPersonDesignation () {
        return strPersonDesignation;
    }

    public void setStrPersonDesignation (String strPersonDesignation) {
        this.strPersonDesignation = strPersonDesignation;
    }

    public String getStrNote () {
        return strNote;
    }

    public void setStrNote (String strNote) {
        this.strNote = strNote;
    }

}
