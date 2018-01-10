package com.example.ebc003.tripolarcon.app.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GenerateLog extends AppCompatActivity {

    @BindView (R.id.spinAddLogSchedule)
    Spinner mSpinnerSchedule;
    @BindView (R.id.spinAddLogDate)
    Spinner mSpinnerDate;
    @BindView (R.id.spinAddLogTime)
    Spinner mSpinnerTime;
    @BindView (R.id.editAddLogRemark)
    EditText mEditRemark;
    @BindView (R.id.add_log_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_generate_log);

        ButterKnife.bind (this);

        //setUp Toolbar
        setToolbar();

        //Add Spinner item
        setSpinner();
    }

    private void setSpinner () {
        String [] mListSchedule={"Select the item","Meeting","Site Visit","Phone Call","Reminder","Follow up"};
        ArrayAdapter<String> adapterSchedule=new ArrayAdapter<String> (getApplicationContext (),android.R.layout.simple_spinner_dropdown_item,mListSchedule);
        mSpinnerSchedule.setAdapter (adapterSchedule);
    }

    private void setToolbar () {
        setSupportActionBar (toolbar);
        ActionBar actionBar=getSupportActionBar ();
        if (actionBar!=null){
            actionBar.setTitle (R.string.add_log_detail);
            actionBar.setDisplayHomeAsUpEnabled (true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.add_log_menu,menu);
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        int id=item.getItemId ();

        switch (id){

            case R.id.addLog:{

                addLogDetails();

                break;
            }
        }
        return super.onOptionsItemSelected (item);
    }

    private void addLogDetails () {

        Toast.makeText (getApplicationContext (),"Add Log",Toast.LENGTH_LONG).show ();

    }
}
