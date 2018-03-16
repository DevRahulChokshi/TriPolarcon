package com.example.ebc003.tripolarcon.app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.BroadcastMaster;
import com.example.ebc003.tripolarcon.model.JSONParser;
import com.example.ebc003.tripolarcon.model.Constants;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityLogin extends AppCompatActivity {

    @BindView (R.id.txt_login_text) TextView mTxtLogin;
    @BindView (R.id.editEmail) EditText mEdtEmail;
    @BindView (R.id.editPassword) EditText mEdtPassword;
    @BindView (R.id.loginProgressbar) ProgressBar mProgressBar;
    @BindView (R.id.constraintLogin) ConstraintLayout constraintLayout;

    private static final String TAG=ActivityLogin.class.getSimpleName ();
    BroadcastMaster broadcastMaster;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        ButterKnife.bind (this);
        setTypeFace();

        broadcastMaster=new BroadcastMaster (constraintLayout,getApplicationContext ());
    }

    @Override
    protected void onResume () {
        super.onResume ();
        registerInternetCheckReceiver();

    }

    private void registerInternetCheckReceiver() {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    public BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status=broadcastMaster.getConnectivityStatusString(context);
            broadcastMaster.setSnackBarMessage (status,false);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause:");
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
        unregisterReceiver(broadcastReceiver);
    }

    private void setTypeFace () {
        Typeface customFont=Typeface.createFromAsset (getAssets (),"fonts/OpenSansCondensed-Bold.ttf");
        mTxtLogin.setTypeface (customFont);
    }

    public void onHomePage (View view) {
        String strEmail=mEdtEmail.getText ().toString ();
        String strPassword=mEdtPassword.getText ().toString ();
        // Check patter for email id
        Pattern p = Pattern.compile (Constants.regEx);
        Matcher m = p.matcher (strEmail);

        if (strEmail.isEmpty ()&&strEmail.length ()==0){
            mEdtEmail.setError ("Enter the email");
            mEdtEmail.setFocusable (true);
        }
        else if(strPassword.isEmpty ()&&strPassword.length ()==0){
            mEdtPassword.setError ("Enter the password");
            mEdtPassword.setFocusable (true);
        }
        else if(!strEmail.matches (Constants.emailPattern)){
            mEdtEmail.setError ("Invalid email");
            mEdtEmail.setFocusable (true);
        }
        else if(strPassword.length ()<=5){
            mEdtPassword.setError ("Pass must be 5 latter or digit");
            mEdtPassword.setFocusable (true);
        }
        else {
            String status=broadcastMaster.getConnectivityStatusString(this);
            if (status.equalsIgnoreCase ("internet not connected")){
                broadcastMaster.setSnackBarMessage (status,false);
                Toast.makeText (this,"Check Internet Connection..",Toast.LENGTH_SHORT).show ();
            }else {
                new MyAsyncTask ().execute (strEmail,strPassword);
            }
        }
    }

    class MyAsyncTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected void onPreExecute () {
            mProgressBar.setVisibility (View.VISIBLE);
            super.onPreExecute ();
        }

        @Override
        protected Boolean doInBackground (String... strings) {

            String strEmail=strings[0];
            String strPassword=strings[1];

            List<NameValuePair> listData=new ArrayList<> ();
            listData.add (new BasicNameValuePair (Constants.USER_EMAIL,strEmail));
            listData.add (new BasicNameValuePair (Constants.USER_PASSWORD,strPassword));

            JSONParser parser=new JSONParser ();
            JSONObject response=parser.makeHttpRequest (Constants.URL_LOGIN,Constants.METHOD_POST,listData);

                try {
                    String status=response.getString (Constants.RESPONSE_STATUS);
                    if(status.equals (Constants.REQUEST_STATUS)){
                        String reg_id=response.getString (Constants.EMP_ID);
                        String user_name=response.getString (Constants.USER_NAME);

                        //SET SharedPreference
                        setSharedPreference(reg_id,strEmail,user_name);

                        Intent intent=new Intent (getApplicationContext (),ActivityContainer.class);
                        startActivity (intent);
                        finish ();
                    }
                    else {
                        return false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
                return true;
            }

        @Override
        protected void onPostExecute (Boolean aBoolean) {
            super.onPostExecute (aBoolean);

            if (!aBoolean){
                mEdtEmail.setError ("Enter the correct email");
                mEdtEmail.setFocusable (true);
                mEdtPassword.setError ("Enter the correct password");
                mEdtPassword.setFocusable (true);
                mProgressBar.setVisibility (View.INVISIBLE);
            }
        }
    }


        private void setSharedPreference (String reg_id,String userEmail,String userName){
            Context context=getApplicationContext ();
            SharedPreferences sharedPreferences=context.getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit ();
            editor.putString (Constants.EMP_ID,reg_id);
            editor.putString (Constants.USER_EMAIL,userEmail);
            editor.putString (Constants.USER_NAME,userName);
            editor.apply ();
        }
}

