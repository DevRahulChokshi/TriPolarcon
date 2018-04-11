package com.example.ebc003.tripolarcon.app.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.ShowPendingEnquiryAdapter;
import com.example.ebc003.tripolarcon.adapter.ShowTomorrowPlanAdapter;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LogData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EBC003 on 1/25/2018.
 */

public class FragmentPendingEnquiry extends Fragment {

    @BindView(R.id.recyclerTomorrow) RecyclerView mRecyclerToDayPlan;
    @BindView (R.id.progressBarTomorrowPlan) ProgressBar mProgressBar;
    private RecyclerView.LayoutManager layoutManager;
    private String user_id;
    private String TAG=FragmentPendingEnquiry.class.getSimpleName ();
    List<LogData> mLogList;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_pending_enquiry,container,false);

        ButterKnife.bind (this,view);

        mLogList=new ArrayList<> ();
        getData ();
        layoutManager=new LinearLayoutManager (view.getContext ());
        mRecyclerToDayPlan.setLayoutManager (layoutManager);
        mProgressBar.setVisibility (View.VISIBLE);

        getSharedPreferenceData();

        return view;
    }

    private void getSharedPreferenceData () {
        SharedPreferences sharedPreferences=getContext ().getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        user_id=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        Log.i (TAG,"USER_ID:-"+user_id);
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest (Request.Method.POST,Constants.URL_SHOW_PENDING_ENQUIRY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            if (!response.isEmpty ()){
                                //Parsing the fetched Json String to JSON Object
                                jsonArray = new JSONArray (response);
                                for (int i=0;i<=jsonArray.length ();i++){
                                    try{
                                        LogData logData=new LogData ();
                                        String fistLatter=jsonArray.getJSONObject (i).getString (Constants.USER_ID_NAME);
                                        char first=fistLatter.charAt (0);
                                        String firstData= String.valueOf (first);
                                        logData.setLogUserLatter (firstData);

                                        logData.setLogCompanyId (jsonArray.getJSONObject (i).getString (Constants.CUSTOMER_ID));
                                        logData.setLogCompanyName(jsonArray.getJSONObject (i).getString (Constants.USER_ID_NAME));
                                        logData.setLogCompanyDate(jsonArray.getJSONObject (i).getString (Constants.SHOW_LOG_DATE));
                                        logData.setLogCompanyTime(jsonArray.getJSONObject (i).getString (Constants.SHOW_LOG_TIME));
                                        logData.setLogScheduleType (jsonArray.getJSONObject (i).getString (Constants.SHOW_LOG_SCHEDULE));
                                        mLogList.add (logData);
                                    }catch (JSONException e){
                                        e.printStackTrace ();
                                    }
                                }
                                setRecycler();
                            }else {
                               mProgressBar.setVisibility (View.GONE);
                            }
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
            protected Map<String, String> getParams () {
                Map<String,String> stringMap=new HashMap<> ();
                stringMap.put (Constants.USER_ID,user_id);
                return stringMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext ());
        requestQueue.add(stringRequest);
    }

    private void setRecycler () {
        ShowPendingEnquiryAdapter pendingEnquiryAdapter=new ShowPendingEnquiryAdapter (getContext (),mLogList);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration (getContext (),DividerItemDecoration.VERTICAL);
        mRecyclerToDayPlan.addItemDecoration (dividerItemDecoration);
        mRecyclerToDayPlan.setAdapter (pendingEnquiryAdapter);
        mProgressBar.setVisibility (View.GONE);
    }
}
