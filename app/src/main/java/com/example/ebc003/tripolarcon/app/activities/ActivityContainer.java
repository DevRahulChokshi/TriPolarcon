package com.example.ebc003.tripolarcon.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.app.MyAdapterItemActivity;
import com.example.ebc003.tripolarcon.app.fragments.DailyPlanFragment;
import com.example.ebc003.tripolarcon.app.fragments.HomeFragment;
import com.example.ebc003.tripolarcon.app.fragments.LeadFragment;
import com.example.ebc003.tripolarcon.app.fragments.NavigationDrawerFragment;
import com.example.ebc003.tripolarcon.app.fragments.ReminderFragment;
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
            actionBar.setTitle (R.string.title_home);
        }
    }

    private void setUpDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drwr_fragment);
        drawerFragment.setUpDrawer(R.id.nav_drwr_fragment, drawerLayout, mMainToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.main_menu,menu);
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
            case "Home":{
                HomeFragment homeFragment=new HomeFragment ();
                FragmentTransaction transaction=manager.beginTransaction ();
                transaction.replace (R.id.fragment_container,homeFragment,Constants.FRAG_HOME);
                transaction.addToBackStack (Constants.FRAG_HOME);
                Bundle bundle=new Bundle ();
                bundle.putString (Constants.USER_ID,userID);
                homeFragment.setArguments (bundle);
                transaction.commit ();
                drawerLayout.closeDrawer (GravityCompat.START);
                break;
            }
            case "Leads":{
                LeadFragment leadFragment=new LeadFragment ();
                FragmentTransaction transaction=manager.beginTransaction ();
                transaction.replace (R.id.fragment_container,leadFragment,Constants.FRAG_LEADS);
                transaction.addToBackStack (Constants.FRAG_LEADS);
                transaction.commit ();
                drawerLayout.closeDrawer (GravityCompat.START);
                break;
            }
            case "Reminder": {
                ReminderFragment reminderFragment = new ReminderFragment ();
                FragmentTransaction transaction = manager.beginTransaction ();
                transaction.replace (R.id.fragment_container, reminderFragment, Constants.FRAG_REMINDER);
                transaction.addToBackStack (Constants.FRAG_REMINDER);
                transaction.commit ();
                drawerLayout.closeDrawer (GravityCompat.START);
                break;
            }
            case "Daily plan":{
                DailyPlanFragment dailyPlanFragment=new DailyPlanFragment ();
                FragmentTransaction transaction=manager.beginTransaction ();
                transaction.replace (R.id.fragment_container,dailyPlanFragment,Constants.FRAG_DAILY_PLAN);
                transaction.addToBackStack (Constants.FRAG_DAILY_PLAN);
                Bundle bundle=new Bundle ();
                bundle.putString (Constants.USER_ID,userID);
                dailyPlanFragment.setArguments (bundle);
                transaction.commit ();
                drawerLayout.closeDrawer (GravityCompat.START);
                break;
            }
        }
    }
}
