package com.example.ebc003.tripolarcon.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.LeadListData;

import java.util.List;

/**
 * Created by EBC003 on 4/2/2018.
 */

public class LeadNotificationAdapter extends RecyclerView.Adapter<LeadNotificationAdapter.MyViewHolder>{

    Context context;
    List<LeadListData> listData;
    LayoutInflater layoutInflater;

    private String strCompanyName;
    private String strCompanyEmail;
    private String strCompanyWebsite;
    private String strOfficeNumber;
    private String strCompanyAddress;
    private String strFaxNumber;
    private String strPersonDesignation;
    private String strPersonName;
    private String strPersonNumber;
    private String strNote;

    private static final String TAG=LeadListAdapter.class.getSimpleName ();

    public LeadNotificationAdapter (Context context, List<LeadListData> listData) {
        this.context=context;
        layoutInflater=LayoutInflater.from (context);
        this.listData = listData;
    }

    @Override
    public LeadNotificationAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate (R.layout.notification_card_view,parent,false);

        LeadNotificationAdapter.MyViewHolder myViewHolder=new LeadNotificationAdapter.MyViewHolder (view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {
        if (listData !=null){
            LeadListData leadListData=listData.get (position);
            if (leadListData!=null){
                holder.txtNotificationTitle.setText (leadListData.getTxtUser ());
                holder.txtNotificationDescription.setText (leadListData.getStrNote ());
                holder.txtNotificationDateTime.setText (leadListData.getStrPersonNumber ());

                Typeface regularFont=Typeface.createFromAsset (context.getAssets (),"fonts/OpenSansCondensed-Light.ttf");
                Typeface boldFont=Typeface.createFromAsset (context.getAssets (),"fonts/OpenSansCondensed-Bold.ttf");
                holder.txtNotificationTitle.setTypeface (boldFont);
                holder.txtNotificationDescription.setTypeface (regularFont);
                holder.txtNotificationDateTime.setTypeface (boldFont);
            }
            else {
                Toast.makeText (context.getApplicationContext (),"Data List is null",Toast.LENGTH_LONG).show ();
            }
        }
        else {
            Toast.makeText (context.getApplicationContext (),"Data is null", Toast.LENGTH_LONG).show ();
        }
    }

    @Override
    public int getItemCount () {
        return listData.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNotificationTitle;
        TextView txtNotificationDescription;
        TextView txtNotificationDateTime;


        public MyViewHolder (final View itemView) {
            super (itemView);
            txtNotificationTitle=itemView.findViewById (R.id.notificationTitle);
            txtNotificationDescription=itemView.findViewById (R.id.notificationDescription);
            txtNotificationDateTime=itemView.findViewById (R.id.notification_datetime);
        }
    }
}