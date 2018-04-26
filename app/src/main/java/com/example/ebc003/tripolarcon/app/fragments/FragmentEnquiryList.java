package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EBC003 on 12/9/2017.
 */


public class FragmentEnquiryList extends Fragment implements SearchView.OnQueryTextListener{

    private String TAG=FragmentEnquiryList.class.getSimpleName ();
    ArrayList<LeadListData> listData;

    @BindView(R.id.progressBarShowLead) ProgressBar progressBar;
    @BindView (R.id.recyclerConvertedLead) RecyclerView convertedLeadList;
//    @BindView (R.id.mSearch) SearchView searchView;
    SearchView searchView;
    LeadListAdapter leadListAdapter;
    Button mBtnFilter;


    private RecyclerView.LayoutManager layoutManager;
    private String user_id;

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
        setHasOptionsMenu(true);
        Bundle bundle=getArguments ();
        if (bundle!=null){
            user_id=bundle.getString (Constants.USER_ID,"N/A");
            Log.i (TAG,"USER_ID:-"+user_id);
        }
        getData ();
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState (outState);
    }

    @Override
    public void onViewStateRestored (@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored (savedInstanceState);
    }

    private void setUpToolbar () {
        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.strEnquiryList);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i (TAG,"onCreateView");
        View view=inflater.inflate (R.layout.fragment_enquiry_list,container,false);
        ButterKnife.bind (this,view);

        setUpToolbar();

        layoutManager=new LinearLayoutManager (view.getContext());
        convertedLeadList.setLayoutManager (layoutManager);


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

        mBtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(),"Hello World!",Toast.LENGTH_SHORT).show();

            }
        });

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

                            if (!response.equals (Constants.RESPONSE_STATUS_FAIL)){
                                for (int i=0;i<=jsonArray.length ();i++){
                                    try{
                                        LeadListData leadListData=new LeadListData ();

                                        String CompanyID=jsonArray.getJSONObject (i).getString ("id");
                                        Log.i (TAG,"COMPANY ID:"+CompanyID);
                                        String CompanyName=jsonArray.getJSONObject (i).getString ("name");
                                        String CompanyEmail=jsonArray.getJSONObject (i).getString ("email");
                                        String source=jsonArray.getJSONObject (i).getString ("source");

                                        char first= CompanyName.charAt (0);
                                        String firstData= String.valueOf (first);

                                        leadListData.setTxtUserEmail (CompanyEmail);
                                        leadListData.setTxtCompanyId (CompanyID);
                                        leadListData.setTxtLeadTitle (CompanyName);
                                        leadListData.setTxtUser (firstData);
                                        leadListData.setTxtSource (source);

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
                stringMap.put (Constants.USER_ID,user_id);
                Log.i (TAG,"USER ID:-"+user_id);
                return stringMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext ());
        requestQueue.add(stringRequest);
    }

    private void setRecycler () {
        leadListAdapter=new LeadListAdapter (getContext (),listData);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration (getContext (),DividerItemDecoration.VERTICAL);
        convertedLeadList.addItemDecoration (dividerItemDecoration);
        convertedLeadList.setAdapter (leadListAdapter);
        progressBar.setVisibility (View.GONE);

    }

    @Override
    public boolean onQueryTextSubmit (String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange (String newText) {
        leadListAdapter.getFilter ().filter (newText);
        return false;
    }
}
