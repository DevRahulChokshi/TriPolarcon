package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.NavigationDrawerAdapter;
import com.example.ebc003.tripolarcon.app.MyAdapterItem;
import com.example.ebc003.tripolarcon.app.MyAdapterItemActivity;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.NavigationDrawerItems;

import butterknife.BindView;

/**
 * Created by EBC003 on 12/8/2017.
 */

public class FragmentNavigationDrawer extends Fragment {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    public String drawerItem;

    @BindView (R.id.txvName) TextView mUserName;
    @BindView (R.id.txvEmail) TextView mUserEmail;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_navigation_drawer,container,false);
        setRecyclerView(view);

        return view;
    }

    private void setRecyclerView(View view){
        RecyclerView recyclerView=view.findViewById (R.id.drawerList);
        NavigationDrawerAdapter adapter=new NavigationDrawerAdapter (getActivity (), NavigationDrawerItems.getData (),myAdapterItem);
        recyclerView.setAdapter (adapter);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
        adapter.notifyDataSetChanged ();

        SharedPreferences sharedPreferences=getContext ().getSharedPreferences (Constants.PREFERENCE_KEY, Context.MODE_PRIVATE);
        String userEmail=sharedPreferences.getString (Constants.USER_EMAIL,"N/A");
        String userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");

        Log.i (FragmentNavigationDrawer.class.getSimpleName (),userName);
        Log.i (FragmentNavigationDrawer.class.getSimpleName (),userEmail);
    }

    public  void setUpDrawer(int fragmentId, final DrawerLayout drawerLayout, Toolbar toolbar){

        this.drawerLayout=drawerLayout;
        actionBarDrawerToggle=new ActionBarDrawerToggle (getActivity (),drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerOpened (View drawerView) {
                super.onDrawerOpened (drawerView);
            }

            @Override
            public void onDrawerClosed (View drawerView) {
                super.onDrawerClosed (drawerView);
            }

            @Override
            public void onDrawerSlide (View drawerView, float slideOffset) {
                super.onDrawerSlide (drawerView, slideOffset);
            }
        };

        drawerLayout.setDrawerListener (actionBarDrawerToggle);
        drawerLayout.post (new Runnable () {
            @Override
            public void run () {
                actionBarDrawerToggle.syncState ();
            }
        });
    }

    MyAdapterItem myAdapterItem=new MyAdapterItem () {
        @Override
        public void getItem (String item) {
            MyAdapterItemActivity myAdapterItemActivity= (MyAdapterItemActivity) getActivity ();
            myAdapterItemActivity.getItemActivity (item);
        }
    };
}
