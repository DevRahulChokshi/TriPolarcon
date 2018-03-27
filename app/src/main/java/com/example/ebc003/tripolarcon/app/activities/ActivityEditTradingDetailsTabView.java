package com.example.ebc003.tripolarcon.app.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.PagerEditTradingDetails;
import com.example.ebc003.tripolarcon.model.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityEditTradingDetailsTabView extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    @BindView(R.id.tabLayout_trading_tab_view) TabLayout tabLayout;
    @BindView (R.id.pager_trading_tab_view) ViewPager viewPager;
    @BindView (R.id.toolbar_trading_tab_view) Toolbar toolbar;

    private String mStrUserID;
    private String mStrGeneratedByName;
    private String mStrAssignPersonName;
    private String mStrAssignPersonId;
    private String mStrCompanyName;

    private String TAG=ActivityEditTradingDetailsTabView.class.getSimpleName ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trading_details_tab_view);

        ButterKnife.bind (this);

        setUpToolbar();

        tabLayout.addTab(tabLayout.newTab().setText("Trading"));
        tabLayout.addTab(tabLayout.newTab().setText("TradingService"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerEditTradingDetails adapter = new PagerEditTradingDetails (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(this);

        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });

        Intent intent=getIntent ();
        if (intent!=null){
            mStrCompanyName=intent.getStringExtra (Constants.COMPANY_NAME);
            Log.i (TAG,"Company Name:"+mStrCompanyName);
        }
    }

    private void setUpToolbar () {
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar ();
        if (actionBar!=null){
            actionBar.setTitle (R.string.order_details);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onTabSelected (TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        String tabStr= (String) tab.getText ();
        Log.i (TAG,tabStr);
    }

    @Override
    public void onTabUnselected (TabLayout.Tab tab) {
        String tabStr= (String) tab.getText ();
        Log.i (TAG,tabStr);
    }

    @Override
    public void onTabReselected (TabLayout.Tab tab) {

    }
}
