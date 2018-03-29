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
import com.example.ebc003.tripolarcon.app.activities.ActivityGenerateLead;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.EditTradingDetailsAsyncTask;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by developer on 11-03-2018.
 */

public class FragmentEditTradingDetails extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String TAG=FragmentEditTradingDetails.class.getSimpleName();

    @BindView(R.id.fabTradingEdit) FloatingActionButton actionButton;
    @BindView(R.id.tradingEditProgressbar) ProgressBar progressBar;
    @BindView(R.id.spnBrandNameData) Spinner mSpnBrandNameData;
    @BindView(R.id.spnProductNameData) Spinner mSpnProductNameData;
    @BindView(R.id.spnSourceTypeData) Spinner mSpnSourceTypeData;
    @BindView(R.id.spnTradingStatus) Spinner spnTradingStatus;
    @BindView(R.id.spnTradingAction) Spinner mSpnTradingAction;
    @BindView(R.id.spnTradingFollowup) Spinner mSpnTradingFollowup;
    @BindView(R.id.edtRemark) EditText mEdtRemark;

    String mStrBrandNameData;
    String mStrProductNameData;
    String mStrSourceTypeData;
    String strTradingStatus;
    String mStrTradingAction;
    String mStrTradingFollowup;

    String userName;
    String userID;

    private String mStrCompanyId;
    private String mStrAssignPersonId;
    private String mStrCompanyName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frgment_edit_trading_details,container,false);

        ButterKnife.bind (this,view);

        setUpSpinnerListener ();

        setSpinnerItem ();

        checkShredPreference ();

        progressBar.setVisibility (View.GONE);

        Intent intent=getActivity().getIntent ();
        if (intent!=null){
            mStrCompanyName=intent.getStringExtra (Constants.COMPANY_NAME);
            mStrAssignPersonId=intent.getStringExtra (Constants.ASSIGN_TO);
            mStrCompanyId=intent.getStringExtra (Constants.USER_ID);

            Log.i (TAG,"CompanyName:-"+mStrCompanyName);
            Log.i (TAG,"AssignPersonId:-"+mStrAssignPersonId);
            Log.i (TAG,"CompanyId:-"+mStrCompanyId);
        }

        actionButton.setOnClickListener ( new View.OnClickListener () {

            @Override
            public void onClick (View v) {
                getStringData();
            }
        });

        return view;
    }

    private void setUpSpinnerListener () {
            mSpnBrandNameData.setOnItemSelectedListener(this);
            mSpnProductNameData.setOnItemSelectedListener(this);
            mSpnSourceTypeData.setOnItemSelectedListener(this);
            spnTradingStatus.setOnItemSelectedListener(this);
            mSpnTradingAction.setOnItemSelectedListener(this);
            mSpnTradingFollowup.setOnItemSelectedListener(this);
    }

    private void setSpinnerItem () {
        //Spinner 1
        String[] mListSchedule = {"TRIKOL", "TRIFLOR", "TRIPLAST", "TRIGUARD"};
        ArrayAdapter<String> adapterSchedule = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mListSchedule);
        mSpnBrandNameData.setAdapter(adapterSchedule);

        String[] mListCallType = {"Triflor Diamond Hard", "Triflor EP25", "Triflor ESD Black", "Triflor EPWB", "Triflor PUC SC", "Triflor Diamond Hard", "Triflor SL 500", "Triflor SL 1000", "Triflor ESD SL 2000", "Triflor  EPU SL",
                "Triflor PU CRETE", "Triflor PU WB", "Triflor PU FLEX", "Triflor HVI 2000", "Triflor MTL", "Triflor ECC", "Triflor GR"};
        ArrayAdapter<String> adapterCallType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mListCallType);
        mSpnProductNameData.setAdapter(adapterCallType);

        String[] mListStatus = {"Email", "India Mart", "Just Dial", "Offline", "Reference", "Sulekha", "Trade India", "Exp India", "Walk-in", "By Call"};
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mListStatus);
        mSpnSourceTypeData.setAdapter(adapterStatus);

        String[] mListTradingStatus = {"Call back", "No response", "Not Interested", "Not Related", "Order closed", "Directly forwarded", "Number not reachable", "Others", "Quotation", "Order finalise", "Sampling", "Site visit", "Send to contractor", "FCD", "Order Finalized"};
        ArrayAdapter<String> adapterListTradingStatus = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mListTradingStatus);
        spnTradingStatus.setAdapter(adapterListTradingStatus);

        String[] mListTradingAction = {"Incoming", "Qualified", "Lost", "Data Store"};
        ArrayAdapter<String> adapterListTradingAction = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mListTradingAction);
        mSpnTradingAction.setAdapter(adapterListTradingAction);

        String[] mListTradingFollowup = {"Re-Followup", "Reminder"};
        ArrayAdapter<String> adapterListTradingFollowup = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mListTradingFollowup);
        mSpnTradingFollowup.setAdapter(adapterListTradingFollowup);
    }

        private void getStringData () {

        String StrRemark=mEdtRemark.getText ().toString ();

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

        EditTradingDetailsAsyncTask addLTask=new EditTradingDetailsAsyncTask (getContext (),progressBar);
        addLTask.execute (mStrBrandNameData,mStrProductNameData,mStrSourceTypeData,strTradingStatus,mStrTradingAction,mStrTradingFollowup,StrRemark,CurrentDate,CurrentTime,mStrCompanyId,mStrAssignPersonId,userID,mStrCompanyName,userName,userName);
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");

        Log.i (TAG,"User id:-"+userID);
        Log.i (TAG,"User name:-"+userName);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        {
            ((TextView) view).setTextColor(getResources().getColor(R.color.textColorEarthquakeLocation));
        }
        int viewId = adapterView.getId();

        switch (viewId) {
            case R.id.spnBrandNameData: {
                mStrBrandNameData = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, "SpinnerSchedule:-" + mStrBrandNameData);
                break;
            }
            case R.id.spnProductNameData: {
                mStrProductNameData = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, "SpinnerSchedule:-" + mStrProductNameData);
                break;
            }
            case R.id.spnSourceTypeData: {
                mStrSourceTypeData = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, "SpinnerSchedule:-" + mStrSourceTypeData);
                break;
            }
            case R.id.spnTradingStatus: {
                strTradingStatus = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, "SpinnerSchedule:-" + strTradingStatus);
                break;
            }
            case R.id.spnTradingAction: {
                mStrTradingAction = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, "SpinnerSchedule:-" + mStrTradingAction);
                break;
            }
            case R.id.spnTradingFollowup: {
                mStrTradingFollowup = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, "SpinnerSchedule:-" + mStrTradingFollowup);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
