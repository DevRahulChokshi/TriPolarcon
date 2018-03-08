package com.example.ebc003.tripolarcon.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EBC003 on 1/11/2018.
 */

public class EditTradingDetailsAsyncTask extends AsyncTask<String,Void,Boolean>{

    private String TAG=EditTradingDetailsAsyncTask.class.getSimpleName ();

    ProgressBar progressBar;
    private Context context;

    public EditTradingDetailsAsyncTask (Context context, ProgressBar progressBar){
        this.context=context;
        this.progressBar=progressBar;
    }

    @Override
    protected void onPreExecute () {
        progressBar.setVisibility (View.VISIBLE);
        super.onPreExecute ();
    }

    @Override
    protected Boolean doInBackground (String... strings) {

        String mStrBrandNameData=strings[0];
        String mStrProductNameData=strings[1];
        String mStrSourceTypeData=strings[2];
        String strTradingStatus=strings[3];
        String mStrTradingAction=strings[4];
        String mStrTradingFollowup=strings[5];
        String mStrProductUnitData=strings[6];
        String mStrServiceSourceTypeData=strings[7];
        String mStrTradingServicesStatus=strings[8];
        String mStrTradingServicesAction=strings[9];
        String mStrTradingServicesFollowUp=strings[10];
        String StrRemark=strings[11];
        String StrOrderDescriptionData=strings[12];
        String StrProductAreaData=strings[13];
        String StrTradingServiceRemark=strings[14];
        String CurrentDate=strings[15];
        String CurrentTime=strings[16];
        String mStrCompanyId=strings[17];
        String mStrAssignPersonId=strings[18];
        String userID=strings[19];
        String mStrCompanyName=strings[20];
        String assignToName=strings[21];
        String userName=strings[22];

        List<NameValuePair> listData=new ArrayList<> ();
        listData.add (new BasicNameValuePair (Constants.TRADING_BRAND_NAME,mStrBrandNameData));
        listData.add (new BasicNameValuePair (Constants.TRADING_PRODUCT_NAME,mStrProductNameData));
        listData.add (new BasicNameValuePair (Constants.TRADING_SOURCE_TYPE,mStrSourceTypeData));
        listData.add (new BasicNameValuePair (Constants.TRADING_ENQUIRY_STATUS,strTradingStatus));
        listData.add (new BasicNameValuePair (Constants.TRADING_ENQUIRY_ACTION,mStrTradingAction));
        listData.add (new BasicNameValuePair (Constants.TRADING_FOLLOWUP,mStrTradingFollowup));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_UNIT,mStrProductUnitData));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_SOURCE_TYPE,mStrServiceSourceTypeData));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_ENQUIRY_STATUS,mStrTradingServicesStatus));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_ENQUIRY_ACTION,mStrTradingServicesAction));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_FOLLOWUP,mStrTradingServicesFollowUp));
        listData.add (new BasicNameValuePair (Constants.TRADING_REMARK,StrRemark));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_ORDER_DESCRIPTION,StrOrderDescriptionData));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_AREA,StrProductAreaData));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_REMARK,StrTradingServiceRemark));
        listData.add (new BasicNameValuePair (Constants.SHOW_PLAN_DATE,CurrentDate));
        listData.add (new BasicNameValuePair (Constants.SHOW_PLAN_TIME,CurrentTime));
        listData.add (new BasicNameValuePair (Constants.USER_ID,mStrCompanyId));
        listData.add (new BasicNameValuePair (Constants.ASSIGN_TO,mStrAssignPersonId));
        listData.add (new BasicNameValuePair (Constants.EMP_ID,userID));
        listData.add (new BasicNameValuePair (Constants.COMPANY_NAME,mStrCompanyName));
        listData.add (new BasicNameValuePair (Constants.ASSIGN_TO_NAME,assignToName));
        listData.add (new BasicNameValuePair (Constants.USER_ID_NAME,userName));

        Log.i (TAG,"Spinner Schedule:-"+mStrBrandNameData);
        Log.i (TAG,"Current Date:-"+mStrProductNameData);
        Log.i (TAG,"Current Time:-"+mStrSourceTypeData);
        Log.i (TAG,"Remark:-"+strTradingStatus);
        Log.i (TAG,"Schedule Date:-"+mStrTradingAction);
        Log.i (TAG,"Schedule Time:-"+mStrTradingFollowup);
        Log.i (TAG,"Customer ID:-"+mStrProductUnitData);
        Log.i (TAG,"User ID:-"+mStrServiceSourceTypeData);
        Log.i (TAG,"File Path:-"+mStrTradingServicesStatus);
        Log.i (TAG,"Call type:-"+mStrTradingServicesAction);
        Log.i (TAG,"Status:-"+mStrTradingServicesFollowUp);
        Log.i (TAG,"DATETIME:-"+StrRemark);
        Log.i (TAG,"CompanyName:-"+StrOrderDescriptionData);
        Log.i (TAG,"UserName:-"+StrProductAreaData);
        Log.i (TAG,"UserName:-"+StrTradingServiceRemark);
        Log.i (TAG,"UserName:-"+CurrentDate);
        Log.i (TAG,"UserName:-"+CurrentTime);
        Log.i (TAG,"UserName:-"+mStrCompanyId);
        Log.i (TAG,"UserName:-"+mStrAssignPersonId);
        Log.i (TAG,"UserName:-"+userID);
        Log.i (TAG,"UserName:-"+mStrCompanyName);
        Log.i (TAG,"UserName:-"+assignToName);
        Log.i (TAG,"UserName:-"+userName);

        JSONParser parser=new JSONParser ();
        JSONObject response=parser.makeHttpRequest (Constants.URL_EDIT_TRADING_DETAILS,Constants.METHOD_POST,listData);

        if (!response.isNull (Constants.REQUEST_STATUS)){
            try {
                String success=response.getString (Constants.REQUEST_STATUS);
                Log.i (EditTradingDetailsAsyncTask.class.getSimpleName ()," "+success);
            }
            catch (JSONException e) {
                e.printStackTrace ();
            }
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute (Boolean aBoolean) {
        if (aBoolean){
            progressBar.setVisibility (View.GONE);
        }
        else {
            Toast.makeText (context,"Something Wrong",Toast.LENGTH_LONG).show ();
        }
        super.onPostExecute (aBoolean);
    }
}

