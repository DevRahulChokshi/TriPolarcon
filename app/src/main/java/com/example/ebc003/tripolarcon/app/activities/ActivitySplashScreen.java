package com.example.ebc003.tripolarcon.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.model.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitySplashScreen extends AppCompatActivity {

    @BindView (R.id.icon_image) ImageView mImageView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash_screen);

        ButterKnife.bind (this);

        new Handler ().postDelayed (new Runnable () {
            @Override
            public void run () {
                checkSharedPreference ();
            }
        },2000);

    }

    private void checkSharedPreference () {
        SharedPreferences sharedPreferences=getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        Boolean aBoolean=sharedPreferences.contains (Constants.EMP_ID);

        if (!aBoolean){
            Intent intent=new Intent (this,ActivityLogin.class);
            startActivity (intent);
            finish ();
        }else {
            Intent intent=new Intent (this,ActivityContainer.class);
            startActivity (intent);
            finish ();
        }
    }

}
