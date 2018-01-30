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
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LogData;

import java.util.List;

/**
 * Created by EBC003 on 1/13/2018.
 */

public class ShowTomorrowPlanAdapter extends RecyclerView.Adapter<ShowTomorrowPlanAdapter.LogHolder>{

    private String TAG=ShowTomorrowPlanAdapter.class.getSimpleName ();

    Context context;
    List<LogData> mLogList;
    LayoutInflater layoutInflater;

    public ShowTomorrowPlanAdapter (Context context, List<LogData> mLogList) {
        this.context=context;
        layoutInflater=LayoutInflater.from (context);
        this.mLogList=mLogList;
    }

    @Override
    public LogHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            View view=layoutInflater.inflate (R.layout.show_today_plan_list,parent,false);
        LogHolder logHolder=new LogHolder (view);
        return logHolder;
    }

    @Override
    public void onBindViewHolder (final LogHolder holder, int position) {
        if (mLogList!=null){
            LogData logData=mLogList.get (position);
            if (logData!=null){
                holder.mTxtCompanyLatter.setText (logData.getLogUserLatter ());
                holder.mTxtCompanyName.setText (logData.getLogUserLatter ());
                String mLogData=logData.getLogCompanyDate ()+" "+logData.getLogCompanyTime ()+" "+logData.getLogScheduleType ();
                holder.mTxtTodayPlanDetails.setText (mLogData);
            }
            else {
                Log.i (TAG,"DATA NULL");
            }
        }
        else {
            Log.i (TAG,"DATA NULL");
        }
        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                String strLeadTitle=(String)holder.mTxtCompanyName.getText ();
                Intent intent=new Intent (context.getApplicationContext (),ActivityLeadInformation.class);
                intent.putExtra (Constants.USER_ID,strLeadTitle);
                context.startActivity (intent);
                Log.i (TAG,"COMPANY_ID:-:"+strLeadTitle);
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
        TextView mTxtTodayPlanDetails;

        public LogHolder (View itemView) {
            super (itemView);
            mTxtCompanyLatter=itemView.findViewById (R.id.txtTodayPlanLatter);
            mTxtCompanyName=itemView.findViewById (R.id.txtTodayPlanCompanyName);
            mTxtTodayPlanDetails =itemView.findViewById (R.id.txtToDayPlanDetails);
        }
    }
}
