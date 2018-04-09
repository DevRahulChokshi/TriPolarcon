package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.EditTradingServicesDetailsAsyncTask;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by developer on 11-03-2018.
 */

public class FragmentEditTradingServiceDetails extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String TAG=FragmentEditTradingServiceDetails.class.getSimpleName ();

    @BindView(R.id.tradingServicesEditProgressbar) ProgressBar progressBar;
    @BindView(R.id.fabTradingService) FloatingActionButton actionButton;
    @BindView(R.id.edtOrderDescriptionData) EditText mEdtOrderDescriptionData;
    @BindView(R.id.edtProductAreaData) EditText mEdtProductAreaData;
    @BindView(R.id.spnProductUnitData) Spinner mSpnProductUnitData;
    @BindView(R.id.spnServiceSourceTypeData) Spinner mSpnServiceSourceTypeData;
    @BindView(R.id.spnTradingServicesStatus) Spinner mSpnTradingServicesStatus;
    @BindView(R.id.spnTradingServicesAction) Spinner mSpnTradingServicesAction;
    @BindView(R.id.spnTradingServicesFollowUp) Spinner mSpnTradingServicesFollowUp;
    @BindView(R.id.edtTradingServiceRemark) EditText mEdtTradingServiceRemark;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_edit_trading_service_details,container,false);

        ButterKnife.bind (this,view);

        progressBar.setVisibility (View.GONE);

        setUpSpinnerListener ();

        setSpinnerItem ();

        checkShredPreference ();

        Intent intent=getActivity().getIntent ();

        if (intent!=null){
            mStrCompanyName=intent.getStringExtra (Constants.COMPANY_NAME);
            mStrAssignPersonId=intent.getStringExtra (Constants.ASSIGN_TO);
            mStrCompanyId=intent.getStringExtra (Constants.USER_ID);

            Log.i (TAG,"CompanyName:-"+mStrCompanyName);
            Log.i (TAG,"AssignPersonId:-"+mStrAssignPersonId);
            Log.i (TAG,"CompanyId:-"+mStrCompanyId);
        }

        actionButton.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick (View v) {
                getStringData();
            }
        });


        return view;
    }

    private void setUpSpinnerListener () {
        mSpnProductUnitData.setOnItemSelectedListener (this);
        mSpnServiceSourceTypeData.setOnItemSelectedListener (this);
        mSpnTradingServicesStatus.setOnItemSelectedListener (this);
        mSpnTradingServicesAction.setOnItemSelectedListener (this);
        mSpnTradingServicesFollowUp.setOnItemSelectedListener (this);
    }

    private void setSpinnerItem () {
        //Spinner 2
        String [] mListProductUnitData={"SQFT","KG","LTR","KIT","BOX","Packet","Ton","ML","NOS","Rnmtr","Rnft","SQMTR"};
        ArrayAdapter<String> adapterListProductUnitData=new ArrayAdapter<String> (getContext(),android.R.layout.simple_spinner_dropdown_item,mListProductUnitData);
        mSpnProductUnitData.setAdapter (adapterListProductUnitData);

        String [] mListServiceSourceTypeData={"Email","India Mart","Just Dial","Offline","Reference","sulekha","Trade India","Exp India","Walk-in","By Call"};
        ArrayAdapter<String> adapterListServiceSourceTypeData=new ArrayAdapter<String> (getContext(),android.R.layout.simple_spinner_dropdown_item,mListServiceSourceTypeData);
        mSpnServiceSourceTypeData.setAdapter (adapterListServiceSourceTypeData);

        String [] mListServicesStatus={"Call back","No response","Not Interested","Not Related","Order closed","Directly forwarded","Number not reachable","Others","Quotation","Order finalise","Sampling","Site visit","Send to contractor","FCD","Order Finalized"};
        ArrayAdapter<String> adapterListServicesStatus=new ArrayAdapter<String> (getContext(),android.R.layout.simple_spinner_dropdown_item,mListServicesStatus);
        mSpnTradingServicesStatus.setAdapter (adapterListServicesStatus);

        String [] mListServicesAction={"Incoming","Qualified","Lost","Data Store"};
        ArrayAdapter<String> adapterListServicesAction=new ArrayAdapter<String> (getContext(),android.R.layout.simple_spinner_dropdown_item,mListServicesAction);
        mSpnTradingServicesAction.setAdapter (adapterListServicesAction);

        String [] mListServicesFollowUp={"Re-Followup","Reminder"};
        ArrayAdapter<String> adapterListServicesFollowUp=new ArrayAdapter<String> (getContext(),android.R.layout.simple_spinner_dropdown_item,mListServicesFollowUp);
        mSpnTradingServicesFollowUp.setAdapter (adapterListServicesFollowUp);
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");

        Log.i (TAG,"User id:-"+userID);
        Log.i (TAG,"User name:-"+userName);
    }

    private void getStringData () {
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

        EditTradingServicesDetailsAsyncTask addLTask=new EditTradingServicesDetailsAsyncTask (getContext (),progressBar);
        addLTask.execute (mStrProductUnitData,mStrServiceSourceTypeData,mStrTradingServicesStatus,mStrTradingServicesAction,mStrTradingServicesFollowUp,StrOrderDescriptionData,StrProductAreaData,StrTradingServiceRemark,CurrentDate,CurrentTime,mStrCompanyId,mStrAssignPersonId,userID,mStrCompanyName,userName,userName);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        {
            ((TextView) view).setTextColor(getResources().getColor(R.color.textColorEarthquakeLocation));
        }
        int viewId = adapterView.getId();

        switch (viewId) {

            case R.id.spnProductUnitData:{
                mStrProductUnitData = (String) adapterView.getItemAtPosition (i);
                Log.i (TAG,"SpinnerSchedule:-"+mStrProductUnitData);
                break;
            }
            case R.id.spnServiceSourceTypeData:{
                mStrServiceSourceTypeData = (String) adapterView.getItemAtPosition (i);
                Log.i (TAG,"SpinnerSchedule:-"+mStrServiceSourceTypeData);
                break;
            }
            case R.id.spnTradingServicesStatus:{
                mStrTradingServicesStatus = (String) adapterView.getItemAtPosition (i);
                Log.i (TAG,"SpinnerSchedule:-"+mStrTradingServicesStatus);
                break;
            }
            case R.id.spnTradingServicesAction:{
                mStrTradingServicesAction = (String) adapterView.getItemAtPosition (i);
                Log.i (TAG,"SpinnerSchedule:-"+mStrTradingServicesAction);
                break;
            }
            case R.id.spnTradingServicesFollowUp:{
                mStrTradingServicesFollowUp = (String) adapterView.getItemAtPosition (i);
                Log.i (TAG,"SpinnerSchedule:-"+mStrTradingServicesFollowUp);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
