package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EBC003 on 1/4/2018.
 */

public class FragmentTradingService extends Fragment {

    private static final String TAG=FragmentTradingService.class.getSimpleName ();

    @BindView (R.id.txtTradingServiceDateData) TextView mTradingServiceDate;
    @BindView (R.id.txtOrderDescriptionData) TextView mOrderDescription;
    @BindView (R.id.txtProductAreaData) TextView mArea;
    @BindView (R.id.txtProductUnitData) TextView mUnit;
    @BindView (R.id.txtServiceSourceTypeData) TextView mSourceType;
    @BindView (R.id.txtServiceStatusData) TextView mStatus;
    @BindView (R.id.txtServiceActionData) TextView mAction;
    @BindView (R.id.txtServiceFollowUpData) TextView mFollowUp;
    @BindView (R.id.txtServiceRequirementRemarkData) TextView mRequirementRemark;
    @BindView (R.id.txtServiceAssignToData) TextView mAssignTo;
    @BindView (R.id.txtServiceGeneratedToData) TextView mGeneratedTo;
    @BindView (R.id.progressBarShowServiceDetails) ProgressBar progressBar;

    private String user_id;
    private String assign_to;
    private String order_description;
    private String area;
    private String unit;
    private String source_id;
    private String status_id;
    private String action_id;
    private String follow_id;
    private String requirement_remark;
    private String order_date;
    private String generated_by;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate (R.layout.fragment_trading_service,container,false);

        ButterKnife.bind (this,view);

        Intent intent=getActivity ().getIntent ();
        if (intent!=null){
            user_id=intent.getStringExtra (Constants.USER_ID);
            assign_to=intent.getStringExtra (Constants.ASSIGN_TO);

            Log.i (TAG,"USER_ID:-"+user_id);
            Log.i (TAG,"ASSIGN_TO:-"+assign_to);
        }

        getLeadData ();

        return view;
    }

    private void getLeadData () {
        //Creating a string request
        StringRequest stringRequest = new StringRequest (Request.Method.POST,Constants.URL_SHOW_TRADING_SERVICE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray (response);
                            int size=jsonArray.length ();
                            Log.i (TAG,"Size:-"+size);
                            for (int i=0;i<=jsonArray.length ();i++){
                                try{
                                    String CompanyName=jsonArray.getJSONObject (i).getString ("customer_id");
                                    if (CompanyName.equals (user_id)){

                                        order_description=jsonArray.getJSONObject (i).getString (Constants.ORDER_DESCRIPTION);
                                        area=jsonArray.getJSONObject (i).getString (Constants.AREA);
                                        unit=jsonArray.getJSONObject (i).getString (Constants.UNIT);
                                        source_id=jsonArray.getJSONObject (i).getString (Constants.SOURCE_ID);
                                        status_id=jsonArray.getJSONObject (i).getString (Constants.ENQUIRE_STATUS_ID);
                                        action_id=jsonArray.getJSONObject (i).getString (Constants.ENQUIRE_ACTION_ID);
                                        follow_id=jsonArray.getJSONObject (i).getString (Constants.FOLLOW_UP_ID);
                                        requirement_remark =jsonArray.getJSONObject (i).getString (Constants.REQUIREMENT_REMARK);
                                        order_date =jsonArray.getJSONObject (i).getString (Constants.ORDER_DATE);
                                        assign_to =jsonArray.getJSONObject (i).getString (Constants.ASSIGN_TO);
                                        generated_by =jsonArray.getJSONObject (i).getString (Constants.GENERATED_BY);

                                        mTradingServiceDate.setText (order_date);
                                        mOrderDescription.setText (order_description);
                                        mArea.setText (area);
                                        mUnit.setText (unit);
                                        mSourceType.setText (source_id);
                                        mStatus.setText (status_id);
                                        mAction.setText (action_id);
                                        mFollowUp.setText (follow_id);
                                        mRequirementRemark.setText (requirement_remark);
                                        mGeneratedTo.setText (generated_by);
                                        mAssignTo.setText (assign_to);
                                    }
                                    else {
                                        Log.i (TAG,"USER NAME NOT EQUAL");
                                    }

                                    progressBar.setVisibility (View.GONE);

                                }catch (JSONException e){
                                    e.printStackTrace ();
                                }
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
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String,String> stringMap=new HashMap<> ();
                stringMap.put (Constants.USER_ID,user_id);
                return stringMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext ());
        requestQueue.add(stringRequest);
    }
}
