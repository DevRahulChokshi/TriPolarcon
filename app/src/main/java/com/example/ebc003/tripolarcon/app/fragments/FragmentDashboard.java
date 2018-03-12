package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ebc003.tripolarcon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EBC003 on 2/22/2018.
 */

public class FragmentDashboard extends Fragment {

    private String TAG=FragmentDashboard.class.getSimpleName ();

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View  view=inflater.inflate (R.layout.fragment_dashboard,container,false);
        setUpToolbar ();

        ButterKnife.bind (this,view);


        return view;
    }

    private void setUpToolbar () {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.title_dashboard);
    }
}
