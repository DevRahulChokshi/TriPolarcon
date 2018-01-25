package com.example.ebc003.tripolarcon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.LogData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by EBC003 on 1/13/2018.
 */

public class ShowDailyPlanAdapter extends RecyclerView.Adapter<ShowDailyPlanAdapter.LogHolder>{

    private String TAG=ShowDailyPlanAdapter.class.getSimpleName ();

    private Context context;
    private List<LogData> mLogList;
    private LayoutInflater layoutInflater;


    public ShowDailyPlanAdapter (Context context, List<LogData> mLogList) {
        this.context=context;
        layoutInflater=LayoutInflater.from (context);
        this.mLogList=mLogList;
    }

    @Override
    public LogHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate (R.layout.list_daily_plan,parent,false);
        LogHolder logHolder=new LogHolder (view);
        return logHolder;
    }

    @Override
    public void onBindViewHolder (LogHolder holder, int position) {
        if (mLogList!=null){
            LogData logData=mLogList.get (position);
            if (logData!=null){
                holder.mTxtCompanyLatter.setText (logData.getLogUserLatter ());
                holder.mTxtCompanyName.setText (logData.getLogUserLatter ());
                String mLogData=logData.getLogCompanyTime ();
                holder.mTxtScheduleType.setText (logData.getLogScheduleType ());
                holder.mTxtLocation.setText (logData.getLogScheduleType ());
                holder.mTxtScheduleTime.setText (mLogData);
            }
            else {
                Log.i (TAG,"DATA NULL");
            }
        }
        else {
            Log.i (TAG,"DATA NULL");
        }
    }

    @Override
    public int getItemCount () {
        return mLogList.size ();
    }

    class LogHolder extends RecyclerView.ViewHolder {

        TextView mTxtCompanyLatter;
        TextView mTxtCompanyName;
        TextView mTxtScheduleTime;
        TextView mTxtScheduleType;
        TextView mTxtLocation;

        public LogHolder (View itemView) {
            super (itemView);
            mTxtCompanyLatter=itemView.findViewById (R.id.txtPlanLatter);
            mTxtCompanyName=itemView.findViewById (R.id.txtEnquiryNameData);
            mTxtScheduleType=itemView.findViewById (R.id.txtScheduleTypeData);
            mTxtScheduleTime =itemView.findViewById (R.id.txtEnquiryTimeData);
            mTxtLocation =itemView.findViewById (R.id.txtScheduleLocationData);
        }
    }
}
