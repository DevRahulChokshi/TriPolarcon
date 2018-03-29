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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EBC003 on 1/11/2018.
 */

public class EditTradingServicesDetailsAsyncTask extends AsyncTask<String,Void,Boolean>{

    private String TAG=EditTradingServicesDetailsAsyncTask.class.getSimpleName ();

    ProgressBar progressBar;
    private Context context;

    public EditTradingServicesDetailsAsyncTask (Context context, ProgressBar progressBar){
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

        String mStrProductUnitData=strings[0];
        String mStrServiceSourceTypeData=strings[1];
        String mStrTradingServicesStatus=strings[2];
        String mStrTradingServicesAction=strings[3];
        String mStrTradingServicesFollowUp=strings[4];
        String StrOrderDescriptionData=strings[5];
        String StrProductAreaData=strings[6];
        String StrTradingServiceRemark=strings[7];
        String CurrentDate=strings[8];
        String CurrentTime=strings[9];
        String mStrCompanyId=strings[10];
        String mStrAssignPersonId=strings[11];
        String userID=strings[12];
        String mStrCompanyName=strings[13];
        String assignToName=strings[14];
        String userName=strings[15];

        List<NameValuePair> listData=new ArrayList<> ();
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_UNIT,mStrProductUnitData));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_SOURCE_TYPE,mStrServiceSourceTypeData));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_ENQUIRY_STATUS,mStrTradingServicesStatus));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_ENQUIRY_ACTION,mStrTradingServicesAction));
        listData.add (new BasicNameValuePair (Constants.TRADING_SERVICE_FOLLOWUP,mStrTradingServicesFollowUp));
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

        Log.i (TAG,"mStrProductUnitData:-"+mStrProductUnitData);
        Log.i (TAG,"mStrServiceSourceTypeData:-"+mStrServiceSourceTypeData);
        Log.i (TAG,"mStrTradingServicesStatus:-"+mStrTradingServicesStatus);
        Log.i (TAG,"mStrTradingServicesAction:-"+mStrTradingServicesAction);
        Log.i (TAG,"mStrTradingServicesFollowUp:-"+mStrTradingServicesFollowUp);
        Log.i (TAG,"StrOrderDescriptionData:-"+StrOrderDescriptionData);
        Log.i (TAG,"StrProductAreaData:-"+StrProductAreaData);
        Log.i (TAG,"StrTradingServiceRemark:-"+StrTradingServiceRemark);
        Log.i (TAG,"CurrentDate:-"+CurrentDate);
        Log.i (TAG,"CurrentTime:-"+CurrentTime);
        Log.i (TAG,"mStrCompanyId:-"+mStrCompanyId);
        Log.i (TAG,"mStrAssignPersonId:-"+mStrAssignPersonId);
        Log.i (TAG,"userID:-"+userID);
        Log.i (TAG,"mStrCompanyName:-"+mStrCompanyName);
        Log.i (TAG,"assignToName:-"+assignToName);
        Log.i (TAG,"userName:-"+userName);

        JSONParser parser=new JSONParser ();
        JSONObject response=parser.makeHttpRequest (Constants.URL_EDIT_TRADING_SERVICES_DETAILS,Constants.METHOD_POST,listData);

        if (!response.isNull (Constants.REQUEST_STATUS)){
            try {
                String success=response.getString (Constants.REQUEST_STATUS);
                Log.i (EditTradingServicesDetailsAsyncTask.class.getSimpleName ()," "+success);
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

