package com.example.ebc003.tripolarcon.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.PagerDailyPlan;
import com.example.ebc003.tripolarcon.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EBC003 on 1/16/2018.
 */

public class DailyPlanFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    @BindView (R.id.tabLayoutDailyPlan) TabLayout tabLayout;
    @BindView (R.id.pagerDailyPlan) ViewPager viewPager;


    private String TAG=DailyPlanFragment.class.getSimpleName ();
    String mUserId;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        Bundle bundle=getArguments ();
        if (bundle!=null){
            mUserId=bundle.getString (Constants.USER_ID);
            Log.i (TAG,"USER ID:-"+mUserId);
        }
        super.onCreate (savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_daily_plan,container,false);
        ButterKnife.bind (this,view);

        tabLayout.addTab(tabLayout.newTab().setText("Today Plan"));
        tabLayout.addTab(tabLayout.newTab().setText("Tomorrow Plan"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerDailyPlan adapter = new PagerDailyPlan (getChildFragmentManager (), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(this);

        return view;
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
