package com.example.ebc003.tripolarcon.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.LeadListAdapter;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LeadListData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EBC003 on 12/9/2017.
 */

public class FragmentNotification extends Fragment{

    private String TAG=FragmentNotification.class.getSimpleName ();
    List<LeadListData> listData;

    @BindView(R.id.progressBarNotificationList) ProgressBar progressBar;
    @BindView  (R.id.recyclerNotificationList) RecyclerView convertedLeadList;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        listData=new ArrayList<> ();
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_reminder,container,false);
        ButterKnife.bind (this,view);
        setUpToolbar ();

        layoutManager=new LinearLayoutManager (view.getContext());
        convertedLeadList.setLayoutManager (layoutManager);
        return view;
    }

    private void setUpToolbar () {
        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.strNotification);
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest (Request.Method.POST, Constants.URL_SHOW_LEAD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            jsonArray = new JSONArray (response);

                            if (!response.equals (Constants.RESPONSE_STATUS_FAIL)){
                                for (int i=0;i<=jsonArray.length ();i++){
                                    try{
                                        LeadListData leadListData=new LeadListData ();

                                        String CompanyID=jsonArray.getJSONObject (i).getString ("id");
                                        Log.i (TAG,"COMPANY ID:"+CompanyID);
                                        String CompanyName=jsonArray.getJSONObject (i).getString ("name");
                                        String CompanyEmail=jsonArray.getJSONObject (i).getString ("email");
                                        String OfficePhoneNumber=jsonArray.getJSONObject (i).getString ("cust_comp_phn");
                                        String Address=jsonArray.getJSONObject (i).getString ("invoicing_address");
                                        String FaxNumber=jsonArray.getJSONObject (i).getString ("cust_comp_fax");
                                        String ContactPersonName=jsonArray.getJSONObject (i).getString ("contact_person");
                                        String ContactPersonNumber=jsonArray.getJSONObject (i).getString ("con_per_no");
                                        String ContactPersonDesignation=jsonArray.getJSONObject (i).getString ("con_per_des");
                                        String Note=jsonArray.getJSONObject (i).getString ("cust_note");

                                        String fistLatter=CompanyName;
                                        char first=fistLatter.charAt (0);
                                        String firstData= String.valueOf (first);

                                        leadListData.setTxtUserEmail (CompanyEmail);
                                        leadListData.setTxtCompanyId (CompanyID);
                                        leadListData.setTxtLeadTitle (CompanyName);
                                        leadListData.setTxtUser (firstData);
                                        leadListData.setStrOfficeNumber (OfficePhoneNumber);
                                        leadListData.setStrAddress (Address);
                                        leadListData.setStrFaxNumber (FaxNumber);
                                        leadListData.setStrPersonName (ContactPersonName);
                                        leadListData.setStrPersonNumber (ContactPersonNumber);
                                        leadListData.setStrPersonDesignation (ContactPersonDesignation);
                                        leadListData.setStrNote (Note);
                                        listData.add (leadListData);
                                    }catch (JSONException e){
                                        e.printStackTrace ();
                                    }
                                }
                            }
                            else {
                                FragmentNoData fragmentNoData=new FragmentNoData ();
                                FragmentManager fragmentManager=getFragmentManager ();
                                FragmentTransaction transaction=fragmentManager.beginTransaction ();
                                transaction.replace (R.id.fragment_container,fragmentNoData);
                                transaction.commit ();
                            }
                            setRecycler();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> stringMap=new HashMap<> ();
                return stringMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext ());
        requestQueue.add(stringRequest);
    }

    private void setRecycler () {
        LeadListAdapter leadListAdapter=new LeadListAdapter (getContext (),listData);
        convertedLeadList.setAdapter (leadListAdapter);
        progressBar.setVisibility (View.GONE);
    }
}
