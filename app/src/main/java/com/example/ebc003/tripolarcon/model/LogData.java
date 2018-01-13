package com.example.ebc003.tripolarcon.model;

import android.util.Log;

/**
 * Created by EBC003 on 1/13/2018.
 */

public class LogData {

    private String TAG=LogData.class.getSimpleName ();

    private String logCompanyDate;
    private String logCompanyTime;
    private String logCompanyRemark;
    private String logUserLatter;
    private String logCompanyName;

    public String getLogCompanyDate () {
        return logCompanyDate;
    }

    public void setLogCompanyDate (String logCompanyDate) {
        this.logCompanyDate = logCompanyDate;
        Log.i (TAG,"COMPANY DATE:-"+logCompanyDate);
    }

    public String getLogCompanyTime () {
        return logCompanyTime;
    }

    public void setLogCompanyTime (String logCompanyTime) {
        this.logCompanyTime = logCompanyTime;
        Log.i (TAG,"COMPANY TIME:-"+logCompanyTime);
    }

    public String getLogCompanyRemark () {
        return logCompanyRemark;
    }

    public void setLogCompanyRemark (String logCompanyRemark) {
        this.logCompanyRemark = logCompanyRemark;
        Log.i (TAG,"COMPANY REMARK:-"+logCompanyRemark);
    }

    public String getLogUserLatter () {
        return logUserLatter;
    }

    public void setLogUserLatter (String logUserLatter) {
        this.logUserLatter = logUserLatter;
    }

    public String getLogCompanyName () {
        return logCompanyName;
    }

    public void setLogCompanyName (String logCompanyName) {
        this.logCompanyName = logCompanyName;
        Log.i (TAG,"COMPANY REMARK:-"+logCompanyName);
    }
}
