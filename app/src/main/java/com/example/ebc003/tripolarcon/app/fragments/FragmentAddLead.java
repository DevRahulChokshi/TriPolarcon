package com.example.ebc003.tripolarcon.app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ebc003.tripolarcon.R;
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

/**
 * Created by EBC003 on 12/14/2017.
 */

public class FragmentAddLead extends Fragment implements View.OnClickListener {

    private static final String TAG=FragmentAddLead.class.getSimpleName ();

    @BindView (R.id.edtCompanyName) EditText mEdtCompanyName;
    @BindView (R.id.edtAddress) EditText mEdtAddress;
    @BindView (R.id.edtEmail) EditText mEdtEmail;
    @BindView (R.id.edtPhoneNo) EditText mEdtPhoneNo;
    @BindView (R.id.edtMobileNo) EditText mEdtMobileNo;
    @BindView (R.id.edtPincode) EditText mEdtPinCode;
    @BindView (R.id.edtState) EditText mEdtState;
    @BindView (R.id.edtWebSite) EditText mEdtWebSite;
    @BindView (R.id.edtGSTIN) EditText mEdtGSTIN;

    @BindView (R.id.insertFragData) Button insertFragData;


    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View  view=inflater.inflate (R.layout.fragment_add_lead,container,false);

        ButterKnife.bind (this,view);
        insertFragData.setOnClickListener (this);

        return view;
    }


    private void getStringData () {

        String company_name= mEdtCompanyName.getText ().toString ();
        String address= mEdtAddress.getText ().toString ();
        String email= mEdtEmail.getText ().toString ();
        String gst_no= mEdtGSTIN.getText ().toString ();
        String pin_code= mEdtPinCode.getText ().toString ();
        String mobile_no= mEdtMobileNo.getText ().toString ();
        String phone_no= mEdtPhoneNo.getText ().toString ();
        String state= mEdtState.getText ().toString ();
        String website= mEdtWebSite.getText ().toString ();

        MyAsyncTask  myAsyncTask=new MyAsyncTask ();
        myAsyncTask.execute (company_name,address,email,gst_no,pin_code,mobile_no,phone_no,state,website);
    }

    @Override
    public void onClick (View v) {
        getStringData ();
        insertFragData.setEnabled (false);
    }

    class MyAsyncTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected void onPreExecute () {
            super.onPreExecute ();
        }

        @Override
        protected Boolean doInBackground (String... strings) {

            String company_name= strings[0];
            String address=strings[1];
            String email=strings[2];
            String gst_no=strings[3];
            String pin_code=strings[4];
            String mobile_no=strings[5];
            String phone_no=strings[6];
            String state=strings[7];
            String website=strings[8];

            List<NameValuePair> listData=new ArrayList<> ();

            listData.add (new BasicNameValuePair (Constants.COMPANY_NAME,company_name));
            listData.add (new BasicNameValuePair (Constants.ADDRESS,address));
            listData.add (new BasicNameValuePair (Constants.PIN_CODE,pin_code));
            listData.add (new BasicNameValuePair (Constants.PHONE_NO,phone_no));
            listData.add (new BasicNameValuePair (Constants.MOBILE_NO,mobile_no));
            listData.add (new BasicNameValuePair (Constants.WEB_SITE,website));
            listData.add (new BasicNameValuePair (Constants.GST_NO,gst_no));
            listData.add (new BasicNameValuePair (Constants.STATE,state));
            listData.add (new BasicNameValuePair (Constants.USER_EMAIL,email));

            JSONParser parser=new JSONParser ();
            JSONObject response=parser.makeHttpRequest (Constants.URL_INSERT_LEAD,Constants.METHOD_POST,listData);

            Log.i (TAG," "+response.toString ());

            if (!response.isNull (Constants.REQUEST_STATUS)){
                try {
                    String status=response.getString (Constants.REQUEST_STATUS);

                    return status.equals (Constants.REQUEST_STATUS);
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }
            else {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute (Boolean aBoolean) {
            insertFragData.setEnabled (true);
    //            Leads leads=new Leads ();
    //            FragmentManager fragmentManager=getFragmentManager ();
    //            FragmentTransaction transaction=fragmentManager.beginTransaction ();
    //            transaction.remove (leads);
            super.onPostExecute (aBoolean);

        }
    }
}
