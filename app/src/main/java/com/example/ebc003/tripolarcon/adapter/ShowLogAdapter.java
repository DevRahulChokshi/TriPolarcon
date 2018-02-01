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

import java.util.List;

/**
 * Created by EBC003 on 1/13/2018.
 */

public class ShowLogAdapter extends RecyclerView.Adapter<ShowLogAdapter.LogHolder>{

    private String TAG=ShowLogAdapter.class.getSimpleName ();

    Context context;
    List<LogData> mLogList;
    LayoutInflater layoutInflater;

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
    public void onBindViewHolder (LogHolder holder, int position) {
        if (mLogList!=null){
            LogData logData=mLogList.get (position);
            if (logData!=null){
                holder.mTxtCompanyLatter.setText (logData.getLogUserLatter ());
                holder.mTxtCompanyName.setText (logData.getLogUserLatter ());
                String mLogData=logData.getLogCompanyDate ()+" "+logData.getLogCompanyTime ()+" "+logData.getLogCompanyRemark ();
                holder.mTxtScheduleDateTime.setText (mLogData);
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
        TextView mTxtScheduleDateTime;

        public LogHolder (View itemView) {
            super (itemView);
            mTxtCompanyLatter=itemView.findViewById (R.id.txtLogCompanyLatter);
            mTxtCompanyName=itemView.findViewById (R.id.txtLogCompanyName);
            mTxtScheduleDateTime=itemView.findViewById (R.id.txtLogDateTime);
        }
    }
}
