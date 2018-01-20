package com.example.ebc003.tripolarcon.app.fragments;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.ShowDailyPlanAdapter;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LogData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EBC003 on 1/16/2018.
 */

public class DailyPlanFragment extends Fragment{

    @BindView (R.id.recyclerShowDailyPlan) RecyclerView mRecyclerDailyPlan;

    private RecyclerView.LayoutManager layoutManager;
    private String TAG=DailyPlanFragment.class.getSimpleName ();
    private List<LogData> mLogDataList;
    String mUserId;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        Bundle bundle=getArguments ();
        if (bundle!=null){
            mUserId=bundle.getString (Constants.USER_ID);
            Log.i (TAG,"USER ID:-"+mUserId);
        }
        super.onCreate (savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_daily_plan,container,false);

        ButterKnife.bind (this,view);

        layoutManager=new LinearLayoutManager (view.getContext ());
        mRecyclerDailyPlan.setLayoutManager (layoutManager);

        mLogDataList =new ArrayList<> ();
        getData ();

        return view;
    }

    private void getData()  {
        //Creating a string request
        StringRequest stringRequest = new StringRequest (Request.Method.POST,Constants.URL_SHOW_LOG_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            jsonArray = new JSONArray (response);

                            JSONArray jsonSortedArray=new JSONArray ();
                            List<JSONObject> jsonObjects=new ArrayList<> ();

                            for (int i=0;i<jsonArray.length ();i++){
                                jsonObjects.add (jsonArray.getJSONObject (i));
                            }

                            Collections.sort (jsonObjects, new Comparator<JSONObject> () {
                                @Override
                                public int compare (JSONObject o1, JSONObject o2) {

                                    String valA=new String ();
                                    String valB=new String ();

                                    try {
                                        valA= (String) o1.get (Constants.SHOW_LOG_DATE);
                                        valB= (String) o1.get (Constants.SHOW_LOG_DATE);
                                    } catch (JSONException e) {
                                        e.printStackTrace ();
                                    }

                                    return valA.compareTo (valB);

                                }
                            });

                            for (int i=0;i<jsonObjects.size ();i++){
                                jsonSortedArray.put (jsonObjects.get (i));
                            }

                            String js=jsonSortedArray.toString ();

                            for (int i=0;i<=jsonSortedArray.length ();i++){
                                try{
                                    LogData logData=new LogData ();
                                    logData.setLogUserLatter (jsonSortedArray.getJSONObject (i).getString (Constants.USER_ID));
                                    logData.setLogCompanyName(jsonSortedArray.getJSONObject (i).getString (Constants.USER_ID));
                                    logData.setLogCompanyName(jsonSortedArray.getJSONObject (i).getString (Constants.USER_ID));
                                    logData.setLogCompanyDate(jsonSortedArray.getJSONObject (i).getString (Constants.SHOW_LOG_DATE));
                                    logData.setLogScheduleType (jsonSortedArray.getJSONObject (i).getString (Constants.SHOW_LOG_SCHEDULE));
                                    logData.setLogCompanyTime(jsonSortedArray.getJSONObject (i).getString (Constants.SHOW_LOG_TIME));

                                    mLogDataList.add (logData);
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
                stringMap.put (Constants.USER_ID,mUserId);
                return stringMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext ());
        requestQueue.add(stringRequest);
    }
    private void setRecycler(){
        ShowDailyPlanAdapter showDailyPlanAdapter=new ShowDailyPlanAdapter (getContext (),mLogDataList);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration (getContext (), DividerItemDecoration.HORIZONTAL);
        mRecyclerDailyPlan.addItemDecoration (dividerItemDecoration);
        mRecyclerDailyPlan.setAdapter (showDailyPlanAdapter);
    }
}
