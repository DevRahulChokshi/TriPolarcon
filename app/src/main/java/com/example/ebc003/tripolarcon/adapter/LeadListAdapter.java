package com.example.ebc003.tripolarcon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.app.activities.ActivityLeadInformation;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LeadListData;

import java.util.ArrayList;

/**
 * Created by EBC003 on 12/20/2017.
 */

public class LeadListAdapter extends RecyclerView.Adapter<LeadListAdapter.MyViewHolder> implements Filterable{

    Context context;
    ArrayList<LeadListData> listData,players;
    LayoutInflater layoutInflater;
    CustomFilter filter;

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

    public LeadListAdapter (Context context, ArrayList<LeadListData> players) {
        this.context=context;
        layoutInflater=LayoutInflater.from (context);
        this.listData = players;
        this.players = players;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {

        View view=layoutInflater.inflate (R.layout.lead_data_list_view,parent,false);

        MyViewHolder myViewHolder=new MyViewHolder (view);

        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder (final MyViewHolder holder, int position) {
        if (players !=null){
            LeadListData leadListData=players.get (position);
            if (leadListData!=null){
                holder.txtUser.setText (leadListData.getTxtUser ());
                holder.txtLeadTitle.setText (leadListData.getTxtLeadTitle ());
                holder.txtUserEmail.setText (leadListData.getTxtUserEmail ());
                holder.txtCompanyId.setText (leadListData.getTxtCompanyId ());

                Typeface regularFont=Typeface.createFromAsset (context.getAssets (),"fonts/OpenSansCondensed-Light.ttf");
                Typeface boldFont=Typeface.createFromAsset (context.getAssets (),"fonts/OpenSansCondensed-Bold.ttf");
                holder.txtUser.setTypeface (boldFont);
                holder.txtLeadTitle.setTypeface (boldFont);
                holder.txtUserEmail.setTypeface (regularFont);
                holder.txtCompanyId.setTypeface (regularFont);
                holder.txtUser.setBackground (context.getDrawable (R.drawable.circle_user));
            }
            else {
                Toast.makeText (context.getApplicationContext (),"Data List is null",Toast.LENGTH_LONG).show ();
            }
        }
        else {
            Toast.makeText (context.getApplicationContext (),"Data is null", Toast.LENGTH_LONG).show ();
        }
        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                String strLeadTitle=(String)holder.txtCompanyId.getText ();
                Intent intent=new Intent (context.getApplicationContext (),ActivityLeadInformation.class);
                intent.putExtra (Constants.USER_ID,strLeadTitle);
                context.startActivity (intent);
            }
        });
    }

    private void getBundleData (MyViewHolder holder) {
    }

    @Override
    public int getItemCount () {
        return players.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

       TextView txtUser;
       TextView txtUserEmail;
       TextView txtLeadTitle;
       TextView txtCompanyId;


        public MyViewHolder (final View itemView) {
            super (itemView);
            txtUser=itemView.findViewById (R.id.txtUser);
            txtUserEmail=itemView.findViewById (R.id.txtUserEmail);
            txtLeadTitle=itemView.findViewById (R.id.txtLeadTitle);
            txtCompanyId=itemView.findViewById (R.id.txtCompanyId);
        }
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter (listData,this);
        }

        return filter;
    }
}
