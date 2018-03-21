package com.example.ebc003.tripolarcon.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebc003.tripolarcon.R;

/**
 * Created by EBC003 on 12/9/2017.
 */

public class FragmentNotification extends Fragment{

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate (R.layout.fragment_reminder,container,false);
        setUpToolbar ();
        return view;
    }

    private void setUpToolbar () {
        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.strNotification);
    }
}
