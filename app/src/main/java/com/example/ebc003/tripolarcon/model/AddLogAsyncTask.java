package com.example.ebc003.tripolarcon.model;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.app.activities.ActivityContainer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by EBC003 on 1/11/2018.
 */

public class AddLogAsyncTask  extends AsyncTask<String,Void,Boolean>{

    private String TAG=AddLogAsyncTask.class.getSimpleName ();

    ProgressBar progressBar;
    private Context context;

    public AddLogAsyncTask (Context context,ProgressBar progressBar){
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

        String mSchedule=strings[0];
        String mLogDate=strings[1];
        String mLogTime=strings[2];
        String mCall_type=strings[3];
        String mStatus=strings[4];
        String mRemark=strings[5];
        String mScheduleDate=strings[6];
        String mScheduleTime=strings[7];
        String mGeneratedBy=strings[8];
        String mCustomer=strings[9];
        String mFilePath=strings[10];
        String mDateTime=strings[11];

        List<NameValuePair> listData=new ArrayList<> ();
        listData.add (new BasicNameValuePair (Constants.LOG_SCHEDULE,mSchedule));
        listData.add (new BasicNameValuePair (Constants.LOG_TIME,mLogTime));
        listData.add (new BasicNameValuePair (Constants.LOG_DATE,mLogDate));
        listData.add (new BasicNameValuePair (Constants.LOG_CALL_TYPE,mCall_type));
        listData.add (new BasicNameValuePair (Constants.LOG_STATUS,mStatus));
        listData.add (new BasicNameValuePair (Constants.LOG_REMARK,mRemark));
        listData.add (new BasicNameValuePair (Constants.LOG_SCHEDULE_DATE,mScheduleDate));
        listData.add (new BasicNameValuePair (Constants.LOG_SCHEDULE_TIME,mScheduleTime));
        listData.add (new BasicNameValuePair (Constants.GENERATED_BY,mGeneratedBy));
        listData.add (new BasicNameValuePair (Constants.COMPANY_NAME,mCustomer));
        listData.add (new BasicNameValuePair (Constants.LOG_IMG_PATH,mFilePath));
        listData.add (new BasicNameValuePair (Constants.LOG_DATETIME,mDateTime));

        Log.i (TAG,"Spinner Schedule:-"+mSchedule);
        Log.i (TAG,"Current Date:-"+mLogDate);
        Log.i (TAG,"Current Time:-"+mLogTime);
        Log.i (TAG,"Remark:-"+mRemark);
        Log.i (TAG,"Schedule Date:-"+mScheduleDate);
        Log.i (TAG,"Schedule Time:-"+mScheduleTime);
        Log.i (TAG,"Customer ID:-"+mCustomer);
        Log.i (TAG,"User ID:-"+mGeneratedBy);
        Log.i (TAG,"File Path:-"+mFilePath);
        Log.i (TAG,"Call type:-"+mCall_type);
        Log.i (TAG,"Status:-"+mStatus);
        Log.i (TAG,"DATETIME:-"+mDateTime);

        // Set your file path here
        FileInputStream fstrm = null;
        try {
            fstrm = new FileInputStream (mFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Set your server page url (and the file title/description)
        HttpFileUpload hfu = new HttpFileUpload("http://www.ebusinesscanvas.com/tripolarcon/android_app/file_upload_log_generate.php", "ftitle", "fdescription",mFilePath);
        hfu.Send_Now(fstrm);

        JSONParser parser=new JSONParser ();
        JSONObject response=parser.makeHttpRequest (Constants.URL_ADD_LOG,Constants.METHOD_POST,listData);

        if (!response.isNull (Constants.REQUEST_STATUS)){
            try {
                String success=response.getString (Constants.REQUEST_STATUS);
                Log.i (AddLogAsyncTask.class.getSimpleName ()," "+success);
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

