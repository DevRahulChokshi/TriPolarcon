package com.example.ebc003.tripolarcon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ebc003.tripolarcon.app.fragments.FragmentEditTrading;
import com.example.ebc003.tripolarcon.app.fragments.FragmentEditTradingService;
import com.example.ebc003.tripolarcon.app.fragments.FragmentTrading;
import com.example.ebc003.tripolarcon.app.fragments.FragmentTradingService;

/**
 * Created by EBC003 on 3/27/2018.
 */

public class PagerEditTrading extends FragmentStatePagerAdapter {

    int tabCount;

    public PagerEditTrading(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentEditTrading ();
            case 1:
                return new FragmentEditTradingService ();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}