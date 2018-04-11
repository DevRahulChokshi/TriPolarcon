package com.example.ebc003.tripolarcon.app.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.adapter.CustomGridViewAdapter;
import com.example.ebc003.tripolarcon.app.activities.ActivityContainer;
import com.example.ebc003.tripolarcon.app.activities.ActivityLogin;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EBC003 on 2/22/2018.
 */

public class FragmentDashboard extends Fragment {

    private String TAG=FragmentDashboard.class.getSimpleName ();
    private String user_id;
    String generated_lead;
    String converted_lead;
    String loss_lead;
    String pending_lead;
    @BindView (R.id.grid_view_image_text) GridView androidGridView;

    ArrayList<String> gridViewStringCointer ;
    String[] gridViewString = {
            "Generated Lead", "Converted Lead", "Pending Lead", "Lost Lead",
    } ;
    int[] gridViewImageId = {
            R.drawable.ic_account_box, R.drawable.ic_assignment_turned_in, R.drawable.ic_assignment_late, R.drawable.ic_restore_page,
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
        checkShredPreference();
        new MyAsyncTask ().execute (user_id);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
              //  Toast.makeText(getContext (), "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
                String viewString=gridViewString[+i];
                switch (viewString){
                    case "Generated Lead":{
                        FragmentLead fragmentLead =new FragmentLead ();
                        FragmentManager manager=getFragmentManager ();
                        FragmentTransaction transaction=manager.beginTransaction ();
                        transaction.setCustomAnimations(R.animator.fade_in_slow, R.animator.fade_out_quick);
                        transaction.replace (R.id.fragment_container, fragmentLead,Constants.FRAG_LEADS);
                        transaction.commit ();
                        break;
                    }
                    case "Converted Lead":{
                        break;
                    }
                    case "Pending Lead":{
                        break;
                    }
                    case "Lost Lead":{
                        break;
                    }
                }
            }
        });

        return view;
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getActivity ().getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        user_id=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        Log.i (TAG,"User id:-"+user_id);
    }

    private void setUpToolbar () {
        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.title_dashboard);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

        MenuInflater menuInflater=new MenuInflater (getContext ());

        menuInflater.inflate (R.menu.main_menu,menu);
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

    class MyAsyncTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute () {
//            mProgressBar.setVisibility (View.VISIBLE);
            super.onPreExecute ();
        }

        @Override
        protected Boolean doInBackground (String... strings) {

            String id=strings[0];

            List<NameValuePair> listData=new ArrayList<> ();
            listData.add (new BasicNameValuePair (Constants.USER_ID,id));
            Log.i (TAG,id);


            JSONParser parser=new JSONParser ();
            JSONObject response=parser.makeHttpRequest (Constants.URL_DASHBOARD,Constants.METHOD_POST,listData);

            try {
                    generated_lead=response.getString (Constants.GENERATED_LEAD);
                    converted_lead=response.getString (Constants.CONVERTED_LEAD);
                    loss_lead=response.getString (Constants.LOSS_LEAD);
                    pending_lead=response.getString (Constants.PENDING_LEAD);

                    gridViewStringCointer=new ArrayList<> ();
                    gridViewStringCointer.add (generated_lead);
                    gridViewStringCointer.add (converted_lead);
                    gridViewStringCointer.add (loss_lead);
                    gridViewStringCointer.add (pending_lead);

                    Log.i (TAG,generated_lead);
                    Log.i (TAG,converted_lead);
                    Log.i (TAG,loss_lead);
                    Log.i (TAG,pending_lead);

                Handler refresh = new Handler (Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run()
                    {
                        setAdapter ();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace ();
            }
            return true;
        }

        @Override
        protected void onPostExecute (Boolean aBoolean) {
            super.onPostExecute (aBoolean);

            if (!aBoolean){

            }
        }
    }

    void setAdapter(){
        CustomGridViewAdapter adapterViewAndroid = new CustomGridViewAdapter(getContext (), gridViewStringCointer,gridViewString, gridViewImageId);
        androidGridView.setAdapter(adapterViewAndroid);
    }
}
