package com.example.ebc003.tripolarcon.app.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.PagerDailyPlan;
import com.example.ebc003.tripolarcon.app.fragments.FragmentDailyPlan;
import com.example.ebc003.tripolarcon.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDailyPlan extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tabLayoutDailyPlan)
    TabLayout tabLayout;
    @BindView (R.id.pagerDailyPlan)
    ViewPager viewPager;
    @BindView (R.id.toolbar_daily_plan)
    Toolbar mToolbar;

    private String TAG=ActivityDailyPlan.class.getSimpleName ();
    String mUserId;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_daily_plan);

        ButterKnife.bind (this);

        tabLayout.addTab(tabLayout.newTab().setText("Today Plan"));
        tabLayout.addTab(tabLayout.newTab().setText("Tomorrow Plan"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerDailyPlan adapter = new PagerDailyPlan (getSupportFragmentManager (), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(this);

        setUpToolbar();
        getBundleValue();

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
            actionBar.setTitle (R.string.daily_plan);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void getBundleValue () {
        Intent intent=getIntent ();
        Bundle  bundle=intent.getBundleExtra (Constants.USER_ID);
        if (bundle!=null){
            mUserId=bundle.getString (Constants.USER_ID);
            Log.i (TAG,"USER ID:-"+mUserId);
        }
    }

    @Override
    public void onTabSelected (TabLayout.Tab tab) {
        viewPager.setCurrentItem (tab.getPosition (),true);
    }

    @Override
    public void onTabUnselected (TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected (TabLayout.Tab tab) {

    }
}
