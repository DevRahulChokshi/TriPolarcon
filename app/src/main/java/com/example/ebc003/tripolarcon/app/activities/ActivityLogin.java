package com.example.ebc003.tripolarcon.app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.JSONParser;
import com.example.ebc003.tripolarcon.model.Constants;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityLogin extends AppCompatActivity {

    @BindView (R.id.txt_login_text) TextView mTxtLogin;
    @BindView (R.id.editEmail) EditText mEdtEmail;
    @BindView (R.id.editPassword) EditText mEdtPassword;
    @BindView (R.id.loginProgressbar) ProgressBar mProgressBar;

    private static final String TAG=ActivityLogin.class.getSimpleName ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        ButterKnife.bind (this);

        setTypeFace();
    }

    private void setTypeFace () {
        Typeface customFont=Typeface.createFromAsset (getAssets (),"fonts/Nunito-Bold.ttf");
        mTxtLogin.setTypeface (customFont);
    }

    public void onHomePage (View view) {

        String strEmail=mEdtEmail.getText ().toString ();
        String strPassword=mEdtPassword.getText ().toString ();

        if (strEmail.isEmpty ()&&strEmail.length ()==0){
            mEdtEmail.setError ("Enter the email");
            mEdtEmail.setFocusable (true);
        }
        else if(strPassword.isEmpty ()&&strPassword.length ()==0){
            mEdtPassword.setError ("Enter the password");
            mEdtPassword.setFocusable (true);
        }
        else {
            new MyAsyncTask ().execute (strEmail,strPassword);
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

            if (!response.isNull (Constants.EMP_ID)){
                try {
                    String reg_id=response.getString (Constants.EMP_ID);
                    String user_name=response.getString (Constants.USER_NAME);

                    //SET SharedPreference
                    setSharedPreference(reg_id,strEmail,user_name);

                    Intent intent=new Intent (getApplicationContext (),ActivityContainer.class);
                    startActivity (intent);
                    finish ();
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
            mProgressBar.setVisibility (View.INVISIBLE);
            super.onPostExecute (aBoolean);

            if (!aBoolean){
                mEdtEmail.setError ("Enter the correct email");
                mEdtEmail.setFocusable (true);
                mEdtPassword.setError ("Enter the correct password");
                mEdtPassword.setFocusable (true);
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
}
