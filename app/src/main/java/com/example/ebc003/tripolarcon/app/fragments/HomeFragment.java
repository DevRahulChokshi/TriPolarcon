package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LeadListData;

import com.example.ebc003.tripolarcon.adapter.LeadListAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EBC003 on 12/9/2017.
 */

public class HomeFragment extends Fragment{

    private String TAG=HomeFragment.class.getSimpleName ();
    List<LeadListData> listData;

    @BindView(R.id.progressBarShowLead)
    ProgressBar progressBar;
    @BindView (R.id.recyclerConvertedLead)
    RecyclerView convertedLeadList;
    @BindView (R.id.txtTotalLead)
    TextView mTxtTotalLead;

    private RecyclerView.LayoutManager layoutManager;
    private String user_id;
    private int size;


    @Override
    public void onAttach (Context context) {
        super.onAttach (context);
        Log.i (TAG,"onAttach");
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Log.i (TAG,"onCreate");

        listData=new ArrayList<> ();

        Bundle bundle=getArguments ();
        if (bundle!=null){
            user_id=bundle.getString (Constants.USER_ID,"N/A");
            Log.i (TAG,"USER_ID:-"+user_id);
        }
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i (TAG,"onCreateView");
        View view=inflater.inflate (R.layout.fragment_home,container,false);

        ButterKnife.bind (this,view);

        getData ();
        layoutManager=new LinearLayoutManager (view.getContext ());
        convertedLeadList.setLayoutManager (layoutManager);

        return view;
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);
        Log.i (TAG,"onActivityCreated");
    }

    @Override
    public void onStart () {
        super.onStart ();
        Log.i (TAG,"onStart");
    }

    @Override
    public void onResume () {
        super.onResume ();
        Log.i (TAG,"onResume");
    }

    @Override
    public void onPause () {
        super.onPause ();
        Log.i (TAG,"onPause");
    }

    @Override
    public void onStop () {
        super.onStop ();
        Log.i (TAG,"onStop");
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView ();
        Log.i (TAG,"onDestroyView");
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
        Log.i (TAG,"onDestroy");
    }

    @Override
    public void onDetach () {
        super.onDetach ();
        Log.i (TAG,"onDetach");
    }

    private void getData(){

        //Creating a string request
        StringRequest stringRequest = new StringRequest (Request.Method.POST,Constants.URL_SHOW_LEAD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            jsonArray = new JSONArray (response);
                            size=jsonArray.length ();
                            Log.i (TAG,"Size:-"+size);
                            for (int i=0;i<=jsonArray.length ();i++){
                                try{

                                    LeadListData leadListData=new LeadListData ();

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
                stringMap.put (Constants.USER_ID,user_id);
                return stringMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext ());
        requestQueue.add(stringRequest);
    }

    private void setRecycler () {
        LeadListAdapter leadListAdapter=new LeadListAdapter (getContext (),listData);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration (getContext (), DividerItemDecoration.HORIZONTAL);
        convertedLeadList.addItemDecoration (dividerItemDecoration);
        convertedLeadList.setAdapter (leadListAdapter);
        progressBar.setVisibility (View.GONE);

        String mTxtTotalSize=size+" "+"Leads";
        mTxtTotalLead.setText (mTxtTotalSize);
    }
}
