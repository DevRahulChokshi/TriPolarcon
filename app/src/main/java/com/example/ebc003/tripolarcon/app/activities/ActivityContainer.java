package com.example.ebc003.tripolarcon.app.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.app.MyAdapterItemActivity;
import com.example.ebc003.tripolarcon.app.fragments.FragmentDashboard;
import com.example.ebc003.tripolarcon.app.fragments.FragmentEnquiryList;
import com.example.ebc003.tripolarcon.app.fragments.FragmentLead;
import com.example.ebc003.tripolarcon.app.fragments.FragmentNavigationDrawer;
import com.example.ebc003.tripolarcon.app.fragments.FragmentNotification;
import com.example.ebc003.tripolarcon.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityContainer extends AppCompatActivity  implements MyAdapterItemActivity {

    private static final String TAG=ActivityContainer.class.getSimpleName ();
    @BindView (R.id.main_toolbar) Toolbar mMainToolbar;
    @BindView (R.id.main_drawer_layout) DrawerLayout drawerLayout;
    FragmentManager manager;

    String userEmail;
    String userName;
    String userID;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_container);

        ButterKnife.bind (this);
        setUpToolbar();
        setUpDrawer();
        checkShredPreference();
        manager=getSupportFragmentManager ();
        getDefaultFrag();
    }

    private void getDefaultFrag () {
        FragmentDashboard fragmentDashboard =new FragmentDashboard ();
        FragmentTransaction transaction=manager.beginTransaction ();
        transaction.replace (R.id.fragment_container, fragmentDashboard,Constants.FRAG_DASHBOARD);
        transaction.commit ();
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userEmail=sharedPreferences.getString (Constants.USER_EMAIL,"N/A");
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");

        Log.i (TAG,"User email:-"+userEmail);
        Log.i (TAG,"User id:-"+userID);
        Log.i (TAG,"User name:-"+userName);
    }

    private void setUpToolbar() {
        setSupportActionBar (mMainToolbar);
        ActionBar actionBar=getSupportActionBar ();
        if (actionBar!=null){
            actionBar.setDisplayShowTitleEnabled (true);
            actionBar.setTitle (R.string.title_dashboard);
        }
    }

    private void setUpDrawer() {
        FragmentNavigationDrawer drawerFragment = (FragmentNavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.nav_drwr_fragment);
        drawerFragment.setUpDrawer(R.id.nav_drwr_fragment, drawerLayout, mMainToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.main_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) ActivityContainer.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(ActivityContainer.this.getComponentName()));
        }
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId ()){
            case R.id.menu_logout: {
                clearSharedPreference();

                Intent intent=new Intent (this,ActivityLogin.class);
                startActivity (intent);
                finish ();
            }

        }
        return super.onOptionsItemSelected (item);
    }

    private void clearSharedPreference () {
        SharedPreferences sharedPreferences=getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit ();
        editor.clear ();
        editor.apply ();
    }

    @Override
    protected void onResume () {
        super.onResume ();

        LinearLayout navigationView=findViewById (R.id.nav_drwr_fragment);
        View hView =  navigationView.getChildAt (0);
        TextView nav_user =hView.findViewById(R.id.txvName);
        TextView nav_user_email =hView.findViewById(R.id.txvEmail);
        nav_user.setText(userName);
        nav_user_email.setText (userEmail);
    }

    @Override
    public void getItemActivity (String item) {
        switch (item){
            case "Dashboard":{
                FragmentDashboard fragmentDashboard =new FragmentDashboard ();
                FragmentTransaction transaction=manager.beginTransaction ();
                transaction.replace (R.id.fragment_container, fragmentDashboard,Constants.FRAG_DASHBOARD);
//                Bundle bundle=new Bundle ();
//                bundle.putString (Constants.USER_ID,userID);
//                fragmentHome.setArguments (bundle);
                transaction.commit ();
                drawerLayout.closeDrawer (GravityCompat.START);
                break;
            }
            case "Enquiry List":{
                FragmentEnquiryList fragmentEnquiryList =new FragmentEnquiryList ();
                FragmentTransaction transaction=manager.beginTransaction ();
                transaction.replace (R.id.fragment_container, fragmentEnquiryList,Constants.FRAG_HOME);
                Bundle bundle=new Bundle ();
                bundle.putString (Constants.USER_ID,userID);
                fragmentEnquiryList.setArguments (bundle);
                transaction.commit ();
                drawerLayout.closeDrawer (GravityCompat.START);
                break;
            }
            case "New Enquiry":{
                FragmentLead fragmentLead =new FragmentLead ();
                FragmentTransaction transaction=manager.beginTransaction ();
                transaction.replace (R.id.fragment_container, fragmentLead,Constants.FRAG_LEADS);
                transaction.commit ();
                drawerLayout.closeDrawer (GravityCompat.START);
                break;
                }
            case "Notification": {
                FragmentNotification fragmentNotification = new FragmentNotification ();
                FragmentTransaction transaction = manager.beginTransaction ();
                transaction.replace (R.id.fragment_container, fragmentNotification, Constants.FRAG_REMINDER);
                transaction.commit ();
                drawerLayout.closeDrawer (GravityCompat.START);
                break;
            }
            case "Daily plan":{
                Intent intent=new Intent (this,ActivityDailyPlan.class);
                Bundle bundle=new Bundle ();
                bundle.putString (Constants.USER_ID,userID);
                startActivity (intent,bundle);
                drawerLayout.closeDrawer (GravityCompat.START);

//                ActivityDailyPlan dailyPlan=new ActivityDailyPlan ();
////                FragmentDailyPlan dailyPlanFragment=new FragmentDailyPlan ();
//                FragmentTransaction transaction=manager.beginTransaction ();
////                transaction.replace (R.id.fragment_container,dailyPlanFragment,Constants.FRAG_DAILY_PLAN);
//                Bundle bundle=new Bundle ();
//                bundle.putString (Constants.USER_ID,userID);
////                dailyPlanFragment.setArguments (bundle);
//                transaction.commit ();
//                drawerLayout.closeDrawer (GravityCompat.START);

                break;
            }
        }
    }
}
