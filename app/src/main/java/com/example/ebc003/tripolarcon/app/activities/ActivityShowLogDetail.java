package com.example.ebc003.tripolarcon.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.LeadListAdapter;
import com.example.ebc003.tripolarcon.adapter.ShowLogAdapter;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LeadListData;
import com.example.ebc003.tripolarcon.model.LogData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityShowLogDetail extends AppCompatActivity {

    private String TAG=ActivityShowLogDetail.class.getSimpleName ();
    private String mUserID;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<LeadListData> mLeadListData;

    @BindView (R.id.recyclerShowLog) RecyclerView mRecyclerView;
    @BindView (R.id.toolbar_show_log) Toolbar mToolbar;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_show_log_detail);

        ButterKnife.bind (this);

        getIntentData();
        getData ();
        mLayoutManager=new LinearLayoutManager (this);
        mRecyclerView.setLayoutManager (mLayoutManager);
    }

    private void getIntentData () {
        Intent mUserDataIntent=getIntent ();
        mUserID=mUserDataIntent.getStringExtra (Constants.USER_ID);
        Log.i (TAG,"UserId:"+mUserID);
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest (Request.Method.POST,Constants.URL_SHOW_LOG_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            jsonArray = new JSONArray (response);
                            for (int i=0;i<=jsonArray.length ();i++){
                                try{
                                    LeadListData listData=new LeadListData ();

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

                                    mLeadListData.add (listData);

                                }catch (JSONException e){
                                    e.printStackTrace ();
                                }
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
                stringMap.put (Constants.USER_ID,mUserID);
                Log.i (TAG,"USER ID:-"+mUserID);
                return stringMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setRecycler () {
        LeadListAdapter leadListAdapter=new LeadListAdapter (this,mLeadListData);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration (this, DividerItemDecoration.HORIZONTAL);
        mRecyclerView.addItemDecoration (dividerItemDecoration);
        mRecyclerView.setAdapter (leadListAdapter);
    }

}
