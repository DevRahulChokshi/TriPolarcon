package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.CustomGridViewAdapter;
import com.example.ebc003.tripolarcon.app.activities.ActivityLogin;
import com.example.ebc003.tripolarcon.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EBC003 on 2/22/2018.
 */

public class FragmentDashboard extends Fragment {

    private String TAG=FragmentDashboard.class.getSimpleName ();
    @BindView (R.id.grid_view_image_text) GridView androidGridView;

    String[] gridViewStringCointer = {
            "230", "400", "65", "911",
    } ;
    String[] gridViewString = {
            "Generated Lead", "Converted Lead", "Pending Lead", "Lost Lead",

    } ;
    int[] gridViewImageId = {
            R.drawable.ic_account_box_white, R.drawable.ic_assignment_turned_in_white, R.drawable.ic_assignment_late_white, R.drawable.ic_restore_page_white,
    };

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setHasOptionsMenu (true);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View  view=inflater.inflate (R.layout.fragment_dashboard,container,false);
        setUpToolbar ();

        ButterKnife.bind (this,view);

        CustomGridViewAdapter adapterViewAndroid = new CustomGridViewAdapter(getContext (), gridViewStringCointer,gridViewString, gridViewImageId);

        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext (), "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void setUpToolbar () {
        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.title_dashboard);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

        MenuInflater menuInflater=new MenuInflater (getContext ());

        menuInflater.inflate (R.menu.main_menu,menu);

        MenuItem item = menu.findItem(R.id.action_search);

        item.setVisible (false);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id=item.getItemId ();
        switch (id){
            case R.id.menu_logout:{
                clearSharedPreference();
                Intent intent=new Intent (getContext (),ActivityLogin.class);
                startActivity (intent);
                break;
            }
        }
        return super.onOptionsItemSelected (item);
    }

    private void clearSharedPreference(){
        SharedPreferences sharedPreferences=getActivity ().getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit ();
        editor.clear ();
        editor.apply ();
    }
}
