package com.example.ebc003.tripolarcon.app.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityLeadInformation extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG=ActivityLeadInformation.class.getSimpleName ();

    @BindView (R.id.lead_info_toolbar) Toolbar mToolbar;
    @BindView (R.id.txtCompanyNameData) TextView mTxtCompanyName;
    @BindView (R.id.txtUserEmailData) TextView mTxtCompanyEmail;
    @BindView (R.id.txtCompanyWebSiteData) TextView mTxtWebSite;
    @BindView (R.id.txtOfficePhoneNumberData) TextView mTxtOfficePhone;
    @BindView (R.id.txtAddressData) TextView mTxtAddress;
    @BindView (R.id.txtFaxData) TextView mTxtFax;
    @BindView (R.id.txtPersonNameData) TextView mTxtPersonName;
//    @BindView (R.id.txtPersonNumberData) TextView mTxtPersonNumber;
    @BindView (R.id.txtPersonNoteData) TextView mTxtPersonNote;
    @BindView (R.id.txtLeadInfo) TextView mTxtLeadInfo;
    @BindView (R.id.txtEnquiryStatusData) TextView mTxtEnquiryStatus;
    @BindView (R.id.progressBarLeadDetails) ProgressBar progressBar;

    String company_name;
    String CompanyID;
    String assign_to;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_lead_information);
        ButterKnife.bind (this);

        mTxtLeadInfo.setOnClickListener (this);

        setUPToolbar();

        checkIntentData();

        getLeadData ();

        mToolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.lead_info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id=item.getItemId ();
        switch (id){
            case R.id.editLog:{
                Intent intent=new Intent (this,ActivityGenerateLog.class);
                intent.putExtra (Constants.COMPANY_NAME,company_name);
                intent.putExtra (Constants.USER_ID,CompanyID);
                startActivity (intent);
                break;
            }
        }
        return super.onOptionsItemSelected (item);
    }

    private void checkIntentData () {
        Intent intent=getIntent ();
        if (intent!=null){
            Bundle bundle=intent.getExtras ();
            if (bundle!=null){
                company_name=bundle.getString (Constants.COMPANY_NAME,"N/A");
                CompanyID=bundle.getString (Constants.USER_ID,"N/A");
                Log.i (TAG,company_name);
            }else {
                Log.i (TAG,"Bundle is null");
            }
        }else{
            Log.i (TAG,"Intent is null");
        }
    }

    private void getLeadData () {
        //Creating a string request
        StringRequest stringRequest = new StringRequest (Request.Method.POST,Constants.URL_SHOW_LEAD_DETAIL_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            jsonArray = new JSONArray (response);
                            int size=jsonArray.length ();
                            Log.i (TAG,"Size:-"+size);
                            for (int i=0;i<=jsonArray.length ();i++){
                                try{
                                    CompanyID=jsonArray.getJSONObject (i).getString (Constants.USER_ID);
                                    String CompanyName=jsonArray.getJSONObject (i).getString ("name");
                                    String CompanyEmail=jsonArray.getJSONObject (i).getString ("email");
                                    String OfficePhoneNumber=jsonArray.getJSONObject (i).getString ("cust_comp_phn");
                                    String company_website=jsonArray.getJSONObject (i).getString ("website");
                                    String Address=jsonArray.getJSONObject (i).getString ("invoicing_address");
                                    String FaxNumber=jsonArray.getJSONObject (i).getString ("cust_comp_fax");
                                    String ContactPersonName=jsonArray.getJSONObject (i).getString ("contact_person");
//                                    String ContactPersonNumber=jsonArray.getJSONObject (i).getString ("con_per_no");
                                    String Note=jsonArray.getJSONObject (i).getString ("cust_note");
                                    String Source=jsonArray.getJSONObject (i).getString ("source");
                                    assign_to=jsonArray.getJSONObject (i).getString ("assign_to");

                                    mTxtCompanyEmail.setText (CompanyEmail);
                                    mTxtCompanyName.setText (CompanyName);
                                    mTxtOfficePhone.setText (OfficePhoneNumber);
                                    mTxtWebSite.setText (company_website);
                                    mTxtAddress.setText (Address);
                                    mTxtFax.setText (FaxNumber);
                                    mTxtPersonName.setText (ContactPersonName);
//                                    mTxtPersonNumber.setText (ContactPersonNumber);
                                    mTxtPersonNote.setText (Note);
                                    mTxtEnquiryStatus.setText (Source);

                                }catch (JSONException e){
                                    e.printStackTrace ();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility (View.GONE);
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
                Log.i (TAG,"HasMap"+company_name);
                Map<String,String> stringMap=new HashMap<> ();
                stringMap.put (Constants.USER_ID,CompanyID);
                return stringMap;
            }
        };

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext ());

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    private void setUPToolbar () {
        setSupportActionBar (mToolbar);
        ActionBar actionBar=getSupportActionBar ();
        if (actionBar!=null){
            actionBar.setTitle (R.string.lead_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onClick (View v) {
        int id=v.getId ();

        switch (id){
            case R.id.txtLeadInfo:
                    Intent intent=new Intent (this,ActivityTradingDetails.class);
                    intent.putExtra (Constants.USER_ID,CompanyID);
                    intent.putExtra (Constants.ASSIGN_TO,assign_to);
                    startActivity (intent);
                break;
        }
    }
}
