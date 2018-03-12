package com.example.ebc003.tripolarcon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ebc003.tripolarcon.app.fragments.FragmentTrading;
import com.example.ebc003.tripolarcon.app.fragments.FragmentTradingService;

/**
 * Created by Rahul Chokshi on 2/3/2016.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTrading();
            case 1:
                return new FragmentTradingService();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}