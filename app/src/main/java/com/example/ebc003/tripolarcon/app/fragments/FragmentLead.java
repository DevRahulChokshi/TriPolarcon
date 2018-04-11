package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.ebc003.tripolarcon.adapter.LeadEditTradingAdapter;
import com.example.ebc003.tripolarcon.app.activities.ActivityGenerateLead;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.LeadListData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EBC003 on 12/9/2017.
 */

public class FragmentLead extends Fragment implements SearchView.OnQueryTextListener{

    private static final String TAG=FragmentLead.class.getSimpleName ();

    @BindView (R.id.fab) FloatingActionButton fab;
    @BindView (R.id.recyclerLeadDataList) RecyclerView mRecyclerDataList;
    @BindView(R.id.progressBarFragmentLead) ProgressBar progressBar;

    private RecyclerView.LayoutManager  layoutManager;
    private LeadEditTradingAdapter editTradingAdapter;
    ArrayList<LeadListData> listData;

    private String userID;
    private String userName;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        checkShredPreference ();
        listData=new ArrayList<> ();
        getData ();
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate (R.layout.fragment_lead,container,false);
        setUpToolbar ();
        ButterKnife.bind (this,view);
        setHasOptionsMenu(true);

        fab.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getActivity (), ActivityGenerateLead.class);

                getActivity ().startActivity (intent);
            }
        });

        setUpToolbar();

        layoutManager=new LinearLayoutManager (view.getContext());
        mRecyclerDataList.setLayoutManager (layoutManager);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_view, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setUpToolbar () {
        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.strEnquiry);
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getActivity ().getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");

        Log.i (TAG,"User id:-"+userID);
        Log.i (TAG,"User name:-"+userName);
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest (Request.Method.POST,Constants.URL_SHOW_FRAGMENT_LEAD_LIST,
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
                                        String Source=jsonArray.getJSONObject (i).getString ("source");

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
                                        leadListData.setTxtSource (Source);
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
            protected Map<String, String> getParams () {
                Map<String,String> stringMap=new HashMap<> ();
                stringMap.put (Constants.USER_ID,userID);
                Log.i (TAG,"USER ID:-"+userID);
                return stringMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext ());
        requestQueue.add(stringRequest);
    }

    private void setRecycler () {
        editTradingAdapter=new LeadEditTradingAdapter (getContext (),listData);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration (getContext (),DividerItemDecoration.VERTICAL);
        mRecyclerDataList.addItemDecoration (dividerItemDecoration);
        mRecyclerDataList.setAdapter (editTradingAdapter);
        progressBar.setVisibility (View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit (String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange (String newText) {
        editTradingAdapter.getFilter ().filter (newText);
        return false;
    }
}


