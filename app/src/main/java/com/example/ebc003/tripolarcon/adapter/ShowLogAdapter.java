package com.example.ebc003.tripolarcon.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.app.activities.ActivityLeadInformation;
import com.example.ebc003.tripolarcon.app.activities.ActivityLogDetailView;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LogData;

import java.util.List;

/**
 * Created by EBC003 on 1/13/2018.
 */

public class ShowLogAdapter extends RecyclerView.Adapter<ShowLogAdapter.LogHolder>{

    private String TAG=ShowLogAdapter.class.getSimpleName ();

    Context context;
    List<LogData> mLogList;
    LayoutInflater layoutInflater;

    String mScheduleTime;
    String mCallType;
    String mStatus;
    String mGeneratedName;
    String mFilePath;

    public ShowLogAdapter (Context context, List<LogData> mLogList) {
        this.context=context;
        layoutInflater=LayoutInflater.from (context);
        this.mLogList=mLogList;
    }

    @Override
    public LogHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate (R.layout.show_log_detail,parent,false);
        LogHolder logHolder=new LogHolder (view);
        return logHolder;
    }

    @Override
    public void onBindViewHolder (final LogHolder holder, int position) {
        if (mLogList!=null){
            LogData logData=mLogList.get (position);
            if (logData!=null){

                String companyName=logData.getLogUserLatter();
                char first= companyName.charAt (0);
                String firstData= String.valueOf (first);

                holder.mTxtCompanyLatter.setText (firstData);
                holder.mTxtCompanyName.setText (logData.getLogUserLatter ());
                String mLogData=logData.getLogCompanyDate ()+" "+logData.getLogCompanyTime ();
                holder.mTxtScheduleDateTime.setText (mLogData);
                holder.mTxtLogRemark.setText (logData.getLogCompanyRemark ());
                mScheduleTime=logData.getLogScheduleType ();
                mCallType=logData.getLogCallType ();
                mStatus=logData.getLogStatus ();
                mGeneratedName=logData.getLogGenerated ();
                mFilePath=logData.getLogImgPath ();
            }
            else {
                Log.i (TAG,"DATA NULL");
            }
        }
        else {
            Log.i (TAG, "DATA NULL");
        }

        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (context.getApplicationContext (),ActivityLogDetailView.class);
                intent.putExtra (Constants.COMPANY_NAME,holder.mTxtCompanyName.getText ());
                intent.putExtra (Constants.LOG_SCHEDULE_DATE,holder.mTxtScheduleDateTime.getText ());
                intent.putExtra (Constants.LOG_REMARK,holder.mTxtLogRemark.getText ());
                intent.putExtra (Constants.LOG_SCHEDULE,mScheduleTime);
                intent.putExtra (Constants.LOG_CALL_TYPE,mCallType);
                intent.putExtra (Constants.LOG_STATUS,mStatus);
                intent.putExtra (Constants.GENERATED_BY_NAME,mGeneratedName);
                intent.putExtra (Constants.LOG_IMG_FILE_PATH,mFilePath);
                context.startActivity (intent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return mLogList.size ();
    }

    class LogHolder extends RecyclerView.ViewHolder {

        TextView mTxtCompanyLatter;
        TextView mTxtCompanyName;
        TextView mTxtScheduleDateTime;
        TextView mTxtLogRemark;

        public LogHolder (View itemView) {
            super (itemView);
            mTxtCompanyLatter=itemView.findViewById (R.id.txtLogCompanyLatter);
            mTxtCompanyName=itemView.findViewById (R.id.txtLogCompanyName);
            mTxtScheduleDateTime=itemView.findViewById (R.id.txtLogDateTime);
            mTxtLogRemark=itemView.findViewById (R.id.txtLogRemark);
        }
    }
}
