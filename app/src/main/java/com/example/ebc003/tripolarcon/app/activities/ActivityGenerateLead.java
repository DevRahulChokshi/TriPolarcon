package com.example.ebc003.tripolarcon.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityGenerateLead extends AppCompatActivity {

    private static final String TAG=ActivityGenerateLead.class.getSimpleName ();
    private String userID;
    private String userName;

    @BindView (R.id.generateLeadToolbar) Toolbar toolbar;
    @BindView (R.id.progressBarGenerateLead) ProgressBar progressBar;
    @BindView (R.id.edtCompanyName) EditText mEdtCompanyName;
    @BindView (R.id.edtEmail) EditText mEdtEmail;
    @BindView (R.id.edtPhoneNo) EditText mEdtPhoneNo;
    @BindView (R.id.edtWebSite) EditText mEdtWebSite;
    @BindView (R.id.edtContactPerson) EditText mEdtContactPerson;
    @BindView (R.id.edtContactDesignation) EditText mEdtContactDesignation;
    @BindView (R.id.edtState) EditText mEdtState;
    @BindView (R.id.edtCity) EditText mEdtCity;
    @BindView (R.id.edtFaxNumber) EditText mEdtFAX_Number;
    @BindView (R.id.edt_PAN_Number) EditText mEdtPAN_Number;
    @BindView (R.id.edt_GST_Number) EditText mEdt_GST_Number;
    @BindView (R.id.edtAddress) EditText mEdtAddress;
    @BindView (R.id.edtNote) EditText mEdtNote;
    @BindView (R.id.radOnline) RadioButton radioOnline;
    @BindView (R.id.radOffline) RadioButton radioOffline;
    @BindView (R.id.radioGroup) RadioGroup radioGroup;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_generate_lead);

        ButterKnife.bind (this);

        setToolbar ();
        checkShredPreference();
        progressBar.setVisibility (View.GONE);

        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });
    }

    private void setToolbar () {
        setSupportActionBar (toolbar);
        ActionBar actionBar=getSupportActionBar ();
        if (actionBar!=null){
            actionBar.setTitle (R.string.generate_lead);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.add_log_menu,menu);
        MenuItem item = menu.findItem(R.id.showLog);
        item.setVisible(false);
        this.invalidateOptionsMenu();
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id=item.getItemId ();
        switch (id){
            case R.id.addLog:{
                    alertBox ();
                break;
            }
        }
        return super.onOptionsItemSelected (item);
    }

    private void alertBox() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityGenerateLead.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.generate_lead_title);
        dialog.setMessage(R.string.dialog_message);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                getStringData ();
                Intent intent=new Intent (getApplicationContext (),ActivityEditTradingDetailsTabView.class);
                startActivity (intent);
            }
        })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getStringData ();
                    }
                });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    private void getStringData () {

        String company_name= mEdtCompanyName.getText ().toString ();
        String address= mEdtAddress.getText ().toString ();
        String email= mEdtEmail.getText ().toString ();
        String phone_no= mEdtPhoneNo.getText ().toString ();
        String state= mEdtState.getText ().toString ();
        String website= mEdtWebSite.getText ().toString ();
        String ContactPerson=mEdtContactPerson.getText ().toString ();
        String ContactDesignation=mEdtContactDesignation.getText ().toString ();
        String City=mEdtCity.getText ().toString ();
        String FAX_Number=mEdtFAX_Number.getText ().toString ();
        String PAN_Number=mEdtPAN_Number.getText ().toString ();
        String GST_Number=mEdt_GST_Number.getText ().toString ();
        String Note=mEdtNote.getText ().toString ();

        final Calendar myCalender = Calendar.getInstance();
        myCalender.set (Calendar.AM_PM,Calendar.AM);
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);
        int seconds = myCalender.get(Calendar.SECOND);

        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int day = myCalender.get(Calendar.DAY_OF_MONTH);

        String CurrentDate=year+"-"+((month)+1)+"-"+day;
        String CurrentTime=hour+":"+minute+":"+seconds;

        int id=radioGroup.getCheckedRadioButtonId ();
        radioOnline=findViewById (id);
        radioOffline=findViewById (id);

        String radioString= (String) radioOffline.getText ();

        MyAsyncTask myAsyncTask=new MyAsyncTask ();
        myAsyncTask.execute (company_name,address,email,phone_no,state,website,ContactPerson,ContactDesignation,City,FAX_Number,PAN_Number,GST_Number,Note,userID,userName,CurrentDate,CurrentTime,radioString);
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");

        Log.i (TAG,"User id:-"+userID);
        Log.i (TAG,"User name:-"+userName);
    }

    class MyAsyncTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute () {
            progressBar.setVisibility (View.VISIBLE);
            super.onPreExecute ();
        }

        @Override
        protected Boolean doInBackground (String... strings) {

            String company_name= strings[0];
            String address=strings[1];
            String email=strings[2];
            String phone_no=strings[3];
            String state=strings[4];
            String website=strings[5];
            String contactPerson=strings[6];
            String contactDesignation=strings[7];
            String city=strings[8];
            String FAX_Number=strings[9];
            String PAN_Number=strings[10];
            String GST_Number=strings[11];
            String note=strings[12];
            String userID=strings[13];
            String userName=strings[14];
            String currentDate=strings[15];
            String currentTime=strings[16];
            String radioString=strings[17];

            List<NameValuePair> listData=new ArrayList<> ();

            listData.add (new BasicNameValuePair (Constants.COMPANY_NAME,company_name));
            listData.add (new BasicNameValuePair (Constants.ADDRESS,address));
            listData.add (new BasicNameValuePair (Constants.USER_EMAIL,email));
            listData.add (new BasicNameValuePair (Constants.PHONE_NO,phone_no));
            listData.add (new BasicNameValuePair (Constants.STATE,state));
            listData.add (new BasicNameValuePair (Constants.WEB_SITE,website));
            listData.add (new BasicNameValuePair (Constants.CONTACT_PERSON,contactPerson));
            listData.add (new BasicNameValuePair (Constants.CONTACT_DESIGNATION,contactDesignation));
            listData.add (new BasicNameValuePair (Constants.CITY,city));
            listData.add (new BasicNameValuePair (Constants.FAX_NUMBER,FAX_Number));
            listData.add (new BasicNameValuePair (Constants.PAN_NUMBER,PAN_Number));
            listData.add (new BasicNameValuePair (Constants.GST_NO,GST_Number));
            listData.add (new BasicNameValuePair (Constants.NOTE,note));
            listData.add (new BasicNameValuePair (Constants.USER_ID,userID));
            listData.add (new BasicNameValuePair (Constants.USER_NAME,userName));
            listData.add (new BasicNameValuePair (Constants.LOG_DATE,currentDate));
            listData.add (new BasicNameValuePair (Constants.LOG_TIME,currentTime));
            listData.add (new BasicNameValuePair (Constants.SOURCE,radioString));

            Log.i (TAG,company_name);
            Log.i (TAG,address);
            Log.i (TAG,email);
            Log.i (TAG,phone_no);
            Log.i (TAG,state);
            Log.i (TAG,website);
            Log.i (TAG,contactPerson);
            Log.i (TAG,contactDesignation);
            Log.i (TAG,city);
            Log.i (TAG,FAX_Number);
            Log.i (TAG,PAN_Number);
            Log.i (TAG,GST_Number);
            Log.i (TAG,note);
            Log.i (TAG,userID);
            Log.i (TAG,userName);
            Log.i (TAG,currentDate);
            Log.i (TAG,currentTime);
            Log.i (TAG,radioString);

            JSONParser parser=new JSONParser ();
            JSONObject response=parser.makeHttpRequest (Constants.URL_INSERT_LEAD,Constants.METHOD_POST,listData);

            Log.i (TAG," "+response.toString ());

            if (!response.isNull (Constants.REQUEST_STATUS)){
                try {
                    String status=response.getString (Constants.REQUEST_STATUS);

                    return status.equals (Constants.REQUEST_STATUS);
                } catch (JSONException e) {
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
            progressBar.setVisibility (View.GONE);
            super.onPostExecute (aBoolean);
        }
    }
}
