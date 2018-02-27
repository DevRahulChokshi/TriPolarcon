package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.LeadListAdapter;
import com.example.ebc003.tripolarcon.app.activities.ActivityGenerateLead;
import com.example.ebc003.tripolarcon.app.activities.ActivityGenerateLog;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LeadListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.android.volley.VolleyLog.TAG;

/**
 * Created by EBC003 on 12/9/2017.
 */

public class FragmentLead extends Fragment {

    private static final String TAG=FragmentLead.class.getSimpleName ();

    @BindView (R.id.fab) FloatingActionButton fab;
    @BindView (R.id.recyclerLeadDataList) RecyclerView mRecyclerDataList;

    private RecyclerView.LayoutManager  layoutManager;
    private LeadListAdapter leadListAdapter;
    List<LeadListData> listData;

    private String userID;
    private String userName;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setUpToolbar ();
        checkShredPreference ();
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate (R.layout.fragment_lead,container,false);

        ButterKnife.bind (this,view);

        fab.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getActivity (), ActivityGenerateLead.class);
                getActivity ().startActivity (intent);
            }
        });

        layoutManager=new LinearLayoutManager (view.getContext ());
        mRecyclerDataList.setLayoutManager (layoutManager);


//        listData=new ArrayList<> ();
//        listData.add (new LeadListData ("R","Google Visit","dev.rahulchokshi@gmail.com",true));
//        listData.add (new LeadListData ("S","Microsoft Visit","dev.rahulchokshi@gmail.com",false));
//        listData.add (new LeadListData ("L","Apple Visit","dev.rahulchokshi@gmail.com",false));
//        listData.add (new LeadListData ("O","Linux Visit","dev.rahulchokshi@gmail.com",false));
//        listData.add (new LeadListData ("Z","Jetbrain Visit","dev.rahulchokshi@gmail.com",false));
//        listData.add (new LeadListData ("A","Persistent Visit","dev.rahulchokshi@gmail.com",false));
//        listData.add (new LeadListData ("B","Camp Gemini Visit","dev.rahulchokshi@gmail.com",false));
//        listData.add (new LeadListData ("C","OPPO Visit","dev.rahulchokshi@gmail.com",false));
//        listData.add (new LeadListData ("G","JIO Visit","dev.rahulchokshi@gmail.com",false));
//        listData.add (new LeadListData ("J","Airtel Visit","dev.rahulchokshi@gmail.com",false));
//
//        leadListAdapter=new LeadListAdapter (getContext (),listData);
//        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration (getContext (),DividerItemDecoration.HORIZONTAL);
//        mRecyclerDataList.addItemDecoration (dividerItemDecoration);
//        mRecyclerDataList.setAdapter (leadListAdapter);

        return view;
    }

    private void setUpToolbar () {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.strEnquiry);
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getActivity ().getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");

        Log.i (TAG,"User id:-"+userID);
        Log.i (TAG,"User name:-"+userName);
    }
}

