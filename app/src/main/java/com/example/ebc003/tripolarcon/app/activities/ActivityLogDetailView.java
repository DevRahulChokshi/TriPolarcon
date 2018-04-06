package com.example.ebc003.tripolarcon.app.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityLogDetailView extends AppCompatActivity {

    private static final String TAG=ActivityLogDetailView.class.getSimpleName ();

    @BindView (R.id.logViewSchedule) TextView mLogSchedule;
    @BindView (R.id.logViewDate) TextView mLogDate;
    @BindView (R.id.logViewCallType) TextView mLogCallType;
    @BindView (R.id.logViewCallBack) TextView mLogCallBack;
    @BindView (R.id.logViewRemark) TextView mLogRemark;
    @BindView (R.id.logViewGeneratedBy) TextView mLogGenerated;
    @BindView (R.id.notificationImg) ImageView imageView;
    @BindView (R.id.progressBarLogView) ProgressBar progressBar;

    String mCompanyName;
    String mLogDateTime;
    String mScheduleTime;
    String mRemark;
    String mCallType;
    String mStatus;
    String mGeneratedName;
    String mFilePath;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_log_detail_view);
        ButterKnife.bind (this);
        checkIntentData();
    }

    private void checkIntentData () {
        Intent intent=getIntent ();
        if (intent!=null){
            Bundle bundle=intent.getExtras ();
            if (bundle!=null){
                mCompanyName=bundle.getString (Constants.COMPANY_NAME,"N/A");
                mLogDateTime=bundle.getString (Constants.LOG_SCHEDULE_DATE,"N/A");
                mScheduleTime=bundle.getString (Constants.LOG_SCHEDULE,"N/A");
                mRemark=bundle.getString (Constants.LOG_REMARK,"N/A");
                mCallType=bundle.getString (Constants.LOG_CALL_TYPE,"N/A");
                mStatus=bundle.getString (Constants.LOG_STATUS,"N/A");
                mGeneratedName=bundle.getString (Constants.GENERATED_BY_NAME,"N/A");
                mFilePath=bundle.getString (Constants.LOG_IMG_FILE_PATH,"N/A");

                Log.i (TAG,mCompanyName);
                Log.i (TAG,mLogDateTime);
                Log.i (TAG,mScheduleTime);
                Log.i (TAG,mRemark);
                Log.i (TAG,mCallType);
                Log.i (TAG,mStatus);
                Log.i (TAG,mGeneratedName);
                Log.i (TAG,mFilePath);
            }else {
                Log.i (TAG,"Bundle is null");
            }
        }else{
            Log.i (TAG,"Intent is null");
        }

        Picasso.get().load(mFilePath).into(imageView);
        progressBar.setVisibility (View.GONE);
        mLogSchedule.setText (mScheduleTime);
        mLogDate.setText (mLogDateTime);
        mLogCallType.setText (mCallType);
        mLogCallBack.setText (mStatus);
        mLogRemark.setText (mRemark);
        mLogGenerated.setText (mGeneratedName);

        Typeface boldFont=Typeface.createFromAsset (getAssets (),"fonts/OpenSansCondensed-Bold.ttf");
        mLogSchedule.setTypeface (boldFont);
        mLogDate.setTypeface (boldFont);
        mLogCallType.setTypeface (boldFont);
        mLogCallBack.setTypeface (boldFont);
        mLogRemark.setTypeface (boldFont);
        mLogGenerated.setTypeface (boldFont);
    }
}
