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
        String StrRemark=strings[6];
        String CurrentDate=strings[7];
        String CurrentTime=strings[8];
        String mStrCompanyId=strings[9];
        String mStrAssignPersonId=strings[10];
        String userID=strings[11];
        String mStrCompanyName=strings[12];
        String assignToName=strings[13];
        String userName=strings[14];

        List<NameValuePair> listData=new ArrayList<> ();
        listData.add (new BasicNameValuePair (Constants.TRADING_BRAND_NAME,mStrBrandNameData));
        listData.add (new BasicNameValuePair (Constants.TRADING_PRODUCT_NAME,mStrProductNameData));
        listData.add (new BasicNameValuePair (Constants.TRADING_SOURCE_TYPE,mStrSourceTypeData));
        listData.add (new BasicNameValuePair (Constants.TRADING_ENQUIRY_STATUS,strTradingStatus));
        listData.add (new BasicNameValuePair (Constants.TRADING_ENQUIRY_ACTION,mStrTradingAction));
        listData.add (new BasicNameValuePair (Constants.TRADING_FOLLOWUP,mStrTradingFollowup));
        listData.add (new BasicNameValuePair (Constants.TRADING_REMARK,StrRemark));
        listData.add (new BasicNameValuePair (Constants.SHOW_PLAN_DATE,CurrentDate));
        listData.add (new BasicNameValuePair (Constants.SHOW_PLAN_TIME,CurrentTime));
        listData.add (new BasicNameValuePair (Constants.USER_ID,mStrCompanyId));
        listData.add (new BasicNameValuePair (Constants.ASSIGN_TO,mStrAssignPersonId));
        listData.add (new BasicNameValuePair (Constants.EMP_ID,userID));
        listData.add (new BasicNameValuePair (Constants.COMPANY_NAME,mStrCompanyName));
        listData.add (new BasicNameValuePair (Constants.ASSIGN_TO_NAME,assignToName));
        listData.add (new BasicNameValuePair (Constants.USER_ID_NAME,userName));

        Log.i (TAG,"Brand Name:-"+mStrBrandNameData);
        Log.i (TAG,"Product Name:-"+mStrProductNameData);
        Log.i (TAG,"Source Type:-"+mStrSourceTypeData);
        Log.i (TAG,"Status:-"+strTradingStatus);
        Log.i (TAG,"Action:-"+mStrTradingAction);
        Log.i (TAG,"FollowUp Time:-"+mStrTradingFollowup);
        Log.i (TAG,"Remark:-"+StrRemark);
        Log.i (TAG,"CurrentTime:-"+CurrentTime);
        Log.i (TAG,"CurrentDate:-"+CurrentDate);
        Log.i (TAG,"mStrCompanyId:-"+mStrCompanyId);
        Log.i (TAG,"mStrAssignPersonId:-"+mStrAssignPersonId);
        Log.i (TAG,"userID:-"+userID);
        Log.i (TAG,"mStrCompanyName:-"+mStrCompanyName);
        Log.i (TAG,"assignToName:-"+assignToName);
        Log.i (TAG,"userName:-"+userName);

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

