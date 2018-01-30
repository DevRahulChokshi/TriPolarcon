package com.example.ebc003.tripolarcon.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.LogData;

import java.util.List;

import butterknife.BindView;

/**
 * Created by EBC003 on 1/25/2018.
 */

public class FragmentTomorrowPlan extends Fragment {

    @BindView(R.id.recyclerTodayPlan)
    RecyclerView mRecyclerToDayPlan;
    @BindView (R.id.progressBarToDayPlan)
    ProgressBar mProgressBar;
    private RecyclerView.LayoutManager layoutManager;
    private String user_id;
    private String TAG=FragmentTodayPlan.class.getSimpleName ();
    List<LogData> mLogList;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_tomorrow_plan,container,false);
        return view;
    }
}
