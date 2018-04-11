package com.example.ebc003.tripolarcon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ebc003.tripolarcon.app.fragments.FragmentPendingEnquiry;
import com.example.ebc003.tripolarcon.app.fragments.FragmentTodayPlan;
import com.example.ebc003.tripolarcon.app.fragments.FragmentTomorrowPlan;


/**
 * Created by EBC003 on 1/25/2018.
 */

public class PagerDailyPlan extends FragmentStatePagerAdapter {
    private int tabCount;

    public PagerDailyPlan(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTodayPlan ();
            case 1:
                return new FragmentTomorrowPlan ();
            case 2:
                return new FragmentPendingEnquiry ();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
