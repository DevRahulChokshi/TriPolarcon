package com.example.ebc003.tripolarcon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ebc003.tripolarcon.app.fragments.FragmentEditTradingDetails;
import com.example.ebc003.tripolarcon.app.fragments.FragmentEditTradingServiceDetails;
import com.example.ebc003.tripolarcon.app.fragments.FragmentTrading;
import com.example.ebc003.tripolarcon.app.fragments.FragmentTradingService;

/**
 * Created by developer on 11-03-2018.
 */

public class PagerEditTradingDetails extends FragmentStatePagerAdapter {

    int tabCount;

    public PagerEditTradingDetails(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentEditTradingDetails();
            case 1:

                return new FragmentEditTradingServiceDetails();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}