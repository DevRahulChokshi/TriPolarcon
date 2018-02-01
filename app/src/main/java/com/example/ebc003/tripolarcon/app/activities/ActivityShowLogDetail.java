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
    private List<LogData> mLeadListData;

    @BindView (R.id.recyclerShowLog) RecyclerView mRecyclerView;
    @BindView (R.id.toolbar_show_log) Toolbar mToolbar;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_show_log_detail);

        ButterKnife.bind (this);

        setUpToolbar();
        getIntentData();
        getData ();

        mLayoutManager=new LinearLayoutManager (this);
        mRecyclerView.setLayoutManager (mLayoutManager);
        mLeadListData=new ArrayList<> ();

        mToolbar.setNavigationOnClickListener (new View.OnClickListener () {
             @Override
             public void onClick (View v) {
                 finish ();
             }
         });
    }

    private void setUpToolbar () {
        setSupportActionBar (mToolbar);
        ActionBar actionBar=getSupportActionBar ();
            if (actionBar!=null){
                    actionBar.setDisplayShowTitleEnabled (true);
                    actionBar.setTitle (R.string.show_log_detail);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            else {
                    Log.i (TAG,"ACTION BAR");
            }
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

                                    LogData logData=new LogData ();
                                    logData.setLogUserLatter (jsonArray.getJSONObject (i).getString (Constants.USER_ID));
                                    logData.setLogCompanyName(jsonArray.getJSONObject (i).getString (Constants.USER_ID));
                                    logData.setLogCompanyDate(jsonArray.getJSONObject (i).getString (Constants.SHOW_LOG_DATE));
                                    logData.setLogCompanyTime(jsonArray.getJSONObject (i).getString (Constants.SHOW_LOG_TIME));
                                    logData.setLogCompanyRemark (jsonArray.getJSONObject (i).getString (Constants.SHOW_LOG_REMARK));

                                    mLeadListData.add (logData);

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
        ShowLogAdapter showLogAdapter=new ShowLogAdapter (getApplicationContext (),mLeadListData);
//        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration (this, DividerItemDecoration.HORIZONTAL);
//        mRecyclerView.addItemDecoration (dividerItemDecoration);
        mRecyclerView.setAdapter (showLogAdapter);
    }

}
