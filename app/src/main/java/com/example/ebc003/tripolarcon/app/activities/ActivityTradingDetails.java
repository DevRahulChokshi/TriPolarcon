package com.example.ebc003.tripolarcon.app.activities;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.Pager;
import com.example.ebc003.tripolarcon.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTradingDetails extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    @BindView (R.id.tabLayout) TabLayout tabLayout;
    @BindView (R.id.pager) ViewPager viewPager;
    @BindView (R.id.toolbar) Toolbar toolbar;

    private String mStrCompanyId;
    private String mStrAssignPersonId;
    private String mStrCompanyName;

    private String TAG=ActivityTradingDetails.class.getSimpleName ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_trading_details);

        ButterKnife.bind (this);

        setUpToolbar();

        tabLayout.addTab(tabLayout.newTab().setText("Trading"));
        tabLayout.addTab(tabLayout.newTab().setText("TradingService"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Pager adapter = new Pager (getSupportFragmentManager(), tabLayout.getTabCount());

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
            mStrAssignPersonId=intent.getStringExtra (Constants.ASSIGN_TO);
            mStrCompanyId=intent.getStringExtra (Constants.USER_ID);

            Log.i (TAG,mStrCompanyId);
            Log.i (TAG,mStrAssignPersonId);
            Log.i (TAG,mStrCompanyName);
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

//    @Override
//    public boolean onCreateOptionsMenu (Menu menu) {
//        getMenuInflater ().inflate (R.menu.lead_info_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected (MenuItem item) {
//        int id=item.getItemId ();
//        switch (id){
//            case R.id.editLog:{
//                Intent intent=new Intent (this,ActivityEditTradingDetailsTabView.class);
//                intent.putExtra (Constants.COMPANY_NAME,mStrCompanyName);
//                intent.putExtra (Constants.ASSIGN_TO,mStrAssignPersonId);
//                intent.putExtra (Constants.USER_ID,mStrCompanyId);
//                startActivity (intent);
//                break;
//            }
//        }
//        return super.onOptionsItemSelected (item);
//    }

    @Override
    public void onTabSelected (TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected (TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected (TabLayout.Tab tab) {

    }
}
