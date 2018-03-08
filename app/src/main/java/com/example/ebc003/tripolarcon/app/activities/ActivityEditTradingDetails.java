package com.example.ebc003.tripolarcon.app.activities;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.AddLogAsyncTask;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.EditTradingDetailsAsyncTask;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityEditTradingDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG=ActivityEditTradingDetails.class.getSimpleName ();

    @BindView(R.id.tradingEditDetailToolbar) Toolbar mToolbar;
    @BindView(R.id.progressEditTradingDetails) ProgressBar progressBar;

    @BindView(R.id.spnBrandNameData) Spinner mSpnBrandNameData;
    @BindView(R.id.spnProductNameData) Spinner mSpnProductNameData;
    @BindView(R.id.spnSourceTypeData) Spinner mSpnSourceTypeData;
    @BindView(R.id.spnTradingStatus) Spinner spnTradingStatus;
    @BindView(R.id.spnTradingAction) Spinner mSpnTradingAction;
    @BindView(R.id.spnTradingFollowup) Spinner mSpnTradingFollowup;
    @BindView(R.id.edtRemark) EditText mEdtRemark;

    @BindView(R.id.edtOrderDescriptionData) EditText mEdtOrderDescriptionData;
    @BindView(R.id.edtProductAreaData) EditText mEdtProductAreaData;
    @BindView(R.id.spnProductUnitData) Spinner mSpnProductUnitData;
    @BindView(R.id.spnServiceSourceTypeData) Spinner mSpnServiceSourceTypeData;
    @BindView(R.id.spnTradingServicesStatus) Spinner mSpnTradingServicesStatus;
    @BindView(R.id.spnTradingServicesAction) Spinner mSpnTradingServicesAction;
    @BindView(R.id.spnTradingServicesFollowUp) Spinner mSpnTradingServicesFollowUp;
    @BindView(R.id.edtTradingServiceRemark) EditText mEdtTradingServiceRemark;

    String mStrBrandNameData;
    String mStrProductNameData;
    String mStrSourceTypeData;
    String strTradingStatus;
    String mStrTradingAction;
    String mStrTradingFollowup;

    String mStrProductUnitData;
    String mStrServiceSourceTypeData;
    String mStrTradingServicesStatus;
    String mStrTradingServicesAction;
    String mStrTradingServicesFollowUp;

    String userName;
    String userID;

    private String mStrCompanyId;
    private String mStrAssignPersonId;
    private String mStrCompanyName;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_trading_details);

        ButterKnife.bind (this);

        setUPToolbar();

        progressBar.setVisibility (View.GONE);

        mToolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });

        setUpSpinnerListener ();

        setSpinnerItem ();

        checkShredPreference ();

        Intent intent=getIntent ();
        if (intent!=null){
            mStrCompanyName=intent.getStringExtra (Constants.COMPANY_NAME);
            mStrAssignPersonId=intent.getStringExtra (Constants.ASSIGN_TO);
            mStrCompanyId=intent.getStringExtra (Constants.USER_ID);

            Log.i (TAG,mStrCompanyId);
            Log.i (TAG,mStrAssignPersonId);
            Log.i (TAG,mStrCompanyName);
        }
    }

    private void setUpSpinnerListener () {
        mSpnBrandNameData.setOnItemSelectedListener (this);
        mSpnProductNameData.setOnItemSelectedListener (this);
        mSpnSourceTypeData.setOnItemSelectedListener (this);
        spnTradingStatus.setOnItemSelectedListener (this);
        mSpnTradingAction.setOnItemSelectedListener (this);
        mSpnTradingFollowup.setOnItemSelectedListener (this);

        mSpnProductUnitData.setOnItemSelectedListener (this);
        mSpnServiceSourceTypeData.setOnItemSelectedListener (this);
        mSpnTradingServicesStatus.setOnItemSelectedListener (this);
        mSpnTradingServicesAction.setOnItemSelectedListener (this);
        mSpnTradingServicesFollowUp.setOnItemSelectedListener (this);
    }

    private void setSpinnerItem () {
        //Spinner 1
        String [] mListSchedule={"TRIKOL","TRIFLOR","TRIPLAST","TRIGUARD"};
        ArrayAdapter<String> adapterSchedule=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListSchedule);
        mSpnBrandNameData.setAdapter (adapterSchedule);

        String [] mListCallType={"Triflor Diamond Hard","Triflor EP25","Triflor ESD Black","Triflor EPWB","Triflor PUC SC","Triflor Diamond Hard","Triflor SL 500","Triflor SL 1000","Triflor ESD SL 2000","Triflor  EPU SL",
                "Triflor PU CRETE","Triflor PU WB","Triflor PU FLEX","Triflor HVI 2000","Triflor MTL","Triflor ECC","Triflor GR"};
        ArrayAdapter<String> adapterCallType=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListCallType);
        mSpnProductNameData.setAdapter (adapterCallType);

        String [] mListStatus={"Email","India Mart","Just Dial","Offline","Reference","Sulekha","Trade India","Exp India","Walk-in","By Call"};
        ArrayAdapter<String> adapterStatus=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListStatus);
        mSpnSourceTypeData.setAdapter (adapterStatus);

        String [] mListTradingStatus={"Call back","No response","Not Interested","Not Related","Order closed","Directly forwarded","Number not reachable","Others","Quotation","Order finalise","Sampling","Site visit","Send to contractor","FCD","Order Finalized"};
        ArrayAdapter<String> adapterListTradingStatus=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListTradingStatus);
        spnTradingStatus.setAdapter (adapterListTradingStatus);

        String [] mListTradingAction={"Incoming","Qualified","Lost","Data Store"};
        ArrayAdapter<String> adapterListTradingAction=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListTradingAction);
        mSpnTradingAction.setAdapter (adapterListTradingAction);

        String [] mListTradingFollowup={"Re-Followup","Reminder"};
        ArrayAdapter<String> adapterListTradingFollowup=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListTradingFollowup);
        mSpnTradingFollowup.setAdapter (adapterListTradingFollowup);

        //Spinner 2
        String [] mListProductUnitData={"SQFT","KG","LTR","KIT","BOX","Packet","Ton","ML","NOS","Rnmtr","Rnft","SQMTR"};
        ArrayAdapter<String> adapterListProductUnitData=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListProductUnitData);
        mSpnProductUnitData.setAdapter (adapterListProductUnitData);

        String [] mListServiceSourceTypeData={"Email","India Mart","Just Dial","Offline","Reference","sulekha","Trade India","Exp India","Walk-in","By Call"};
        ArrayAdapter<String> adapterListServiceSourceTypeData=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListServiceSourceTypeData);
        mSpnServiceSourceTypeData.setAdapter (adapterListServiceSourceTypeData);

        String [] mListServicesStatus={"Call back","No response","Not Interested","Not Related","Order closed","Directly forwarded","Number not reachable","Others","Quotation","Order finalise","Sampling","Site visit","Send to contractor","FCD","Order Finalized"};
        ArrayAdapter<String> adapterListServicesStatus=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListServicesStatus);
        mSpnTradingServicesStatus.setAdapter (adapterListServicesStatus);

        String [] mListServicesAction={"Incoming","Qualified","Lost","Data Store"};
        ArrayAdapter<String> adapterListServicesAction=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListServicesAction);
        mSpnTradingServicesAction.setAdapter (adapterListServicesAction);

        String [] mListServicesFollowUp={"Re-Followup","Reminder"};
        ArrayAdapter<String> adapterListServicesFollowUp=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListServicesFollowUp);
        mSpnTradingServicesFollowUp.setAdapter (adapterListServicesFollowUp);
    }

    private void setUPToolbar () {
        setSupportActionBar (mToolbar);
        ActionBar actionBar=getSupportActionBar ();
        if (actionBar!=null){
            actionBar.setTitle (R.string.edit_trading_details);
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
                getStringData();
                break;
            }
        }
        return super.onOptionsItemSelected (item);
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");

        Log.i (TAG,"User id:-"+userID);
        Log.i (TAG,"User name:-"+userName);
    }

    private void getStringData () {

        String StrRemark=mEdtRemark.getText ().toString ();
        String StrOrderDescriptionData=mEdtOrderDescriptionData.getText ().toString ();
        String StrProductAreaData=mEdtProductAreaData.getText ().toString ();
        String StrTradingServiceRemark=mEdtTradingServiceRemark.getText ().toString ();

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

        EditTradingDetailsAsyncTask addLTask=new EditTradingDetailsAsyncTask (getApplicationContext (),progressBar);
        addLTask.execute (mStrBrandNameData,mStrProductNameData,mStrSourceTypeData,strTradingStatus,mStrTradingAction,mStrTradingFollowup,mStrProductUnitData,mStrServiceSourceTypeData,mStrTradingServicesStatus,mStrTradingServicesAction,mStrTradingServicesFollowUp,StrRemark,StrOrderDescriptionData,StrProductAreaData,StrTradingServiceRemark,CurrentDate,CurrentTime,mStrCompanyId,mStrAssignPersonId,userID,mStrCompanyName,userName,userName);
    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
        {
            ((TextView) view).setTextColor(getResources ().getColor (R.color.textColorEarthquakeLocation));
        }
        int viewId=parent.getId ();

        switch (viewId){
            case R.id.spnBrandNameData:{
                mStrBrandNameData = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrBrandNameData);
                break;
            }
            case R.id.spnProductNameData:{
                mStrProductNameData = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrProductNameData);
                break;
            }
            case R.id.spnSourceTypeData:{
                mStrSourceTypeData = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrSourceTypeData);
                break;
            }
            case R.id.spnTradingStatus:{
                strTradingStatus = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+strTradingStatus);
                break;
            }
            case R.id.spnTradingAction:{
                mStrTradingAction = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrTradingAction);
                break;
            }
            case R.id.spnTradingFollowup:{
                mStrTradingFollowup = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrTradingFollowup);
                break;
            }
            case R.id.spnProductUnitData:{
                mStrProductUnitData = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrProductUnitData);
                break;
            }
            case R.id.spnServiceSourceTypeData:{
                mStrServiceSourceTypeData = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrServiceSourceTypeData);
                break;
            }
            case R.id.spnTradingServicesStatus:{
                mStrTradingServicesStatus = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrTradingServicesStatus);
                break;
            }
            case R.id.spnTradingServicesAction:{
                mStrTradingServicesAction = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrTradingServicesAction);
                break;
            }
            case R.id.spnTradingServicesFollowUp:{
                mStrTradingServicesFollowUp = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mStrTradingServicesFollowUp);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {

    }
}
