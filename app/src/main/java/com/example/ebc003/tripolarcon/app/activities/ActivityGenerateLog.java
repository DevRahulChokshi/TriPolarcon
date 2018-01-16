package com.example.ebc003.tripolarcon.app.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.app.MyDatePicker;
import com.example.ebc003.tripolarcon.app.MyTimePicker;
import com.example.ebc003.tripolarcon.model.AddLogAsyncTask;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.DatePickerDialogExample;
import com.example.ebc003.tripolarcon.model.TimePickerDialogExample;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityGenerateLog extends AppCompatActivity implements View.OnClickListener,MyDatePicker,MyTimePicker,AdapterView.OnItemSelectedListener{

    private static final String TAG=ActivityGenerateLog.class.getSimpleName ();

    @BindView (R.id.spinAddLogSchedule) Spinner mSpinnerSchedule;
    @BindView (R.id.spinAddLogCallType) Spinner mSpinnerCallType;
    @BindView (R.id.spinAddLogStatus) Spinner mSpinnerStatus;
    @BindView (R.id.add_log_toolbar) Toolbar toolbar;
    @BindView (R.id.btnDtn) Button mBtnDate;
    @BindView (R.id.btnTime) Button mBtnTime;
    @BindView (R.id.editAddLogDtn) EditText mEdtDate;
    @BindView (R.id.editAddLogTime) EditText mEdtTime;
    @BindView (R.id.editAddLogRemark) EditText mEdtRemark;
    @BindView (R.id.progressBarAddLog) ProgressBar progressBar;


    String company_name;
    String userID;
    String companyId;
    String mSpinnserSchedule;
    String mSpinnserCallType;
    String mSpinnserStaus;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_generate_log);

        ButterKnife.bind (this);
        //setUp Toolbar
        setToolbar();
        //Add Spinner mSpinnserSchedule
        setSpinner();

        mBtnDate.setOnClickListener (this);
        mBtnTime.setOnClickListener (this);
        mSpinnerSchedule.setOnItemSelectedListener (this);
        mSpinnerCallType.setOnItemSelectedListener (this);
        mSpinnerStatus.setOnItemSelectedListener (this);

        checkShredPreference ();

        Intent intent=getIntent ();
        if (intent!=null){
            company_name=intent.getStringExtra (Constants.COMPANY_NAME);
            companyId=intent.getStringExtra (Constants.USER_ID);

            Log.i (TAG,company_name);
            Log.i (TAG,companyId);
        }
        progressBar.setVisibility (View.GONE);

    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        Log.i (TAG,"User id:-"+userID);
    }

    private void setSpinner () {
        String [] mListSchedule={"Meeting","Site Visit","Phone Call","Reminder","Follow up"};
        ArrayAdapter<String> adapterSchedule=new ArrayAdapter<String> (getApplicationContext (),android.R.layout.simple_spinner_dropdown_item,mListSchedule);
        mSpinnerSchedule.setAdapter (adapterSchedule);

        String [] mListCallType={"Phone call","Enquiry call","Reminder Call","Follow up","Payment"};
        ArrayAdapter<String> adapterCallType=new ArrayAdapter<String> (getApplicationContext (),android.R.layout.simple_spinner_dropdown_item,mListCallType);
        mSpinnerCallType.setAdapter (adapterCallType);

        String [] mListStatus={"Call back","No response","No related","Order closed","Directly forwarded","Number not reachable","Others","Quotation","Order finalise","Sampling","Site visit","Send to contractor","FCD"};
        ArrayAdapter<String> adapterStatus=new ArrayAdapter<String> (getApplicationContext (),android.R.layout.simple_spinner_dropdown_item,mListStatus);
        mSpinnerStatus.setAdapter (adapterStatus);

    }

    private void setToolbar () {
        setSupportActionBar (toolbar);
        ActionBar actionBar=getSupportActionBar ();
        if (actionBar!=null){
            actionBar.setTitle (R.string.add_log_detail);
            actionBar.setDisplayHomeAsUpEnabled (true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.add_log_menu,menu);
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id=item.getItemId ();
        switch (id){
            case R.id.addLog:{
                addLogDetails();
                break;
            }
            case R.id.showLog:{
                    Intent intent=new Intent (this,ActivityShowLogDetail.class);
                    intent.putExtra (Constants.USER_ID,userID);
                    startActivity (intent);
                break;
            }
        }
        return super.onOptionsItemSelected (item);
    }

    private void addLogDetails () {

        String mRemark=mEdtRemark.getText ().toString ();
        String mDate=mEdtDate.getText ().toString ();
        String mTime=mEdtTime.getText ().toString ();

        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int day = myCalender.get(Calendar.DAY_OF_MONTH);

        String CurrentDate=year+"/"+(month)+1+"/"+day;
        String CurrentTime=hour+"/"+minute;

        AddLogAsyncTask addLogAsyncTask=new AddLogAsyncTask (getApplicationContext (),progressBar);
        addLogAsyncTask.execute (mSpinnserSchedule,CurrentDate,CurrentTime,mSpinnserCallType,mSpinnserStaus,mRemark,mDate,mTime,companyId,userID);
    }

    @Override
    public void onClick (View v) {
       int id= v.getId ();

       switch (id){
           case R.id.btnDtn:{
               DialogFragment dialogFragment=new DatePickerDialogExample ();
               dialogFragment.show (getFragmentManager (),"date");
               break;
           }
           case R.id.btnTime:{
               DialogFragment dialogFragment=new TimePickerDialogExample ();
               dialogFragment.show (getFragmentManager (),"time");
               break;
           }
       }
    }

    @Override
    public void getItem (int day, int month, int years) {
        String myDate=years+"/"+(month+1)+"/"+day;
        mEdtDate.setText (myDate);
    }

    @Override
    public void getItem (int hours, int minuets) {
        String myTime=hours+":"+minuets;
        mEdtTime.setText (myTime);
    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
        mSpinnserSchedule = (String) parent.getItemAtPosition (position);
        mSpinnserCallType = (String) parent.getItemAtPosition (position);
        mSpinnserStaus = (String) parent.getItemAtPosition (position);
    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {}

}
