package com.example.ebc003.tripolarcon.app.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.app.MyDatePicker;
import com.example.ebc003.tripolarcon.app.MyTimePicker;
import com.example.ebc003.tripolarcon.model.AddLogAsyncTask;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.DatePickerDialogExample;
import com.example.ebc003.tripolarcon.model.TimePickerDialogExample;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class ActivityGenerateLog extends AppCompatActivity implements View.OnClickListener,MyDatePicker,MyTimePicker,AdapterView.OnItemSelectedListener{

    private static final String TAG=ActivityGenerateLog.class.getSimpleName ();

    @BindView (R.id.spinAddLogSchedule) Spinner mSpinnerSchedule;
    @BindView (R.id.spinAddLogCallType) Spinner mSpinnerCallType;
    @BindView (R.id.spinAddLogStatus) Spinner mSpinnerStatus;
    @BindView (R.id.add_log_toolbar) Toolbar toolbar;
    @BindView (R.id.btnDtn) Button mBtnDate;
    @BindView (R.id.btnTime) Button mBtnTime;
    @BindView (R.id.editAddLogDtn) EditText mEdtDate;
    @BindView (R.id.editAddLogTime) EditText mEdtTime;
    @BindView (R.id.editAddLogRemark) EditText mEdtRemark;
    @BindView (R.id.progressBarAddLog) ProgressBar progressBar;
    @BindView (R.id.btnPhoto) Button mButtonUpload;
    @BindView (R.id.imgLog) ImageView mImageUpload;

    String company_name;
    String userID;
    String companyId;
    String mSpinnserSchedule;
    String mSpinnserCallType;
    String mSpinnserStaus;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALLERY_CHOOSER_IMAGE_REQUEST_CODE = 101;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final String IMAGE_DIRECTORY_NAME = "TriPolarcon Camera";
    private Uri fileUri;
    static String mediaPath;
    static String filePath;
    static String fileFinalPath;
    private Boolean upflag = false;
    String mCharAt="/";
    String mGetStringName;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_generate_log);

        ButterKnife.bind (this);
        //setUp Toolbar
        setToolbar();
        //Add Spinner mSpinnserSchedule
        setSpinner();

        mBtnDate.setOnClickListener (this);
        mBtnTime.setOnClickListener (this);
        mButtonUpload.setOnClickListener (this);

        mSpinnerSchedule.setOnItemSelectedListener (this);
        mSpinnerCallType.setOnItemSelectedListener (this);
        mSpinnerStatus.setOnItemSelectedListener (this);

        checkShredPreference ();

        Intent intent=getIntent ();
        if (intent!=null){
            company_name=intent.getStringExtra (Constants.COMPANY_NAME);
            companyId=intent.getStringExtra (Constants.USER_ID);

            Log.i (TAG,company_name);
            Log.i (TAG,companyId);
        }
        progressBar.setVisibility (View.GONE);

        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });

    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        Log.i (TAG,"User id:-"+userID);
    }

    private void setSpinner () {
        String [] mListSchedule={"Meeting","Site Visit","Phone Call","Reminder","Follow up"};
        ArrayAdapter<String> adapterSchedule=new ArrayAdapter<String> (getApplicationContext (),android.R.layout.simple_spinner_dropdown_item,mListSchedule);
        mSpinnerSchedule.setAdapter (adapterSchedule);

        String [] mListCallType={"Phone call","Enquiry call","Reminder Call","Follow up","Payment"};
        ArrayAdapter<String> adapterCallType=new ArrayAdapter<String> (getApplicationContext (),android.R.layout.simple_spinner_dropdown_item,mListCallType);
        mSpinnerCallType.setAdapter (adapterCallType);

        String [] mListStatus={"Call back","No response","No related","Order closed","Directly forwarded","Number not reachable","Others","Quotation","Order finalise","Sampling","Site visit","Send to contractor","FCD"};
        ArrayAdapter<String> adapterStatus=new ArrayAdapter<String> (getApplicationContext (),android.R.layout.simple_spinner_dropdown_item,mListStatus);
        mSpinnerStatus.setAdapter (adapterStatus);

    }

    private void setToolbar () {
        setSupportActionBar (toolbar);
        ActionBar actionBar=getSupportActionBar ();
        if (actionBar!=null){
            actionBar.setTitle (R.string.add_log_detail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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
            case R.id.showLog:{
                    Intent intent=new Intent (this,ActivityShowLogDetail.class);
                    intent.putExtra (Constants.USER_ID,userID);
                    startActivity (intent);
                break;
            }
        }
        return super.onOptionsItemSelected (item);
    }

    private void addLogDetails () {

        String mRemark=mEdtRemark.getText ().toString ();
        String mDate=mEdtDate.getText ().toString ();
        String mTime=mEdtTime.getText ().toString ();

        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int day = myCalender.get(Calendar.DAY_OF_MONTH);

        String CurrentDate=year+"/"+(month)+1+"/"+day;
        String CurrentTime=hour+"/"+minute;

        AddLogAsyncTask addLogAsyncTask=new AddLogAsyncTask (getApplicationContext (),progressBar);
        addLogAsyncTask.execute (mSpinnserSchedule,CurrentDate,CurrentTime,mSpinnserCallType,mSpinnserStaus,mRemark,mDate,mTime,companyId,userID);
    }

    @Override
    public void onClick (View v) {
       int id= v.getId ();

       switch (id){
           case R.id.btnDtn:{
               if (ContextCompat.checkSelfPermission(ActivityGenerateLog.this,
                       Manifest.permission.READ_EXTERNAL_STORAGE)
                       != PackageManager.PERMISSION_GRANTED) {
                   if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityGenerateLog.this,
                           Manifest.permission.READ_EXTERNAL_STORAGE)) {
                   } else {
                       ActivityCompat.requestPermissions(ActivityGenerateLog.this,
                               new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                               REQUEST_READ_EXTERNAL_STORAGE);
                   }
               } else {
                   ActivityCompat.requestPermissions(ActivityGenerateLog.this,
                           new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                           REQUEST_READ_EXTERNAL_STORAGE);
               }

               DialogFragment dialogFragment=new DatePickerDialogExample ();
               dialogFragment.show (getFragmentManager (),"date");
               break;
           }
           case R.id.btnTime:{
               DialogFragment dialogFragment=new TimePickerDialogExample ();
               dialogFragment.show (getFragmentManager (),"time");
               break;
           }
           case R.id.btnPhoto:{
               checkRunTimePermission();
               break;
           }
       }
    }

    private void checkRunTimePermission() {
        if (ContextCompat.checkSelfPermission(ActivityGenerateLog.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityGenerateLog.this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(ActivityGenerateLog.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_IMAGE_CAPTURE);
            }
        } else {
            ActivityCompat.requestPermissions(ActivityGenerateLog.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureImage();

                } else {
                    Toast.makeText(getApplicationContext(), "Sorry you don't use camera", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void captureImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityGenerateLog.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent ();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent ();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    void cameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }
    void galleryIntent(){
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                GALLERY_CHOOSER_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == GALLERY_CHOOSER_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view

                Uri selectedImageUri = data.getData();

                fileFinalPath = getPath(selectedImageUri, ActivityGenerateLog.this);
                filePath = getPath(selectedImageUri, ActivityGenerateLog.this);

                File f = new File(filePath);
                mGetStringName=f.getName ();
                filePath=mCharAt+mGetStringName;

                Log.i (TAG,"TEMP PATH FILE PATH:-"+filePath);
                Log.i (TAG,"TEMP PATH MEDIA PATH:-"+mediaPath);
                Log.i (TAG,"TEMP PATH FINAL PATH:-"+fileFinalPath);

                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(fileFinalPath, btmapOptions);
                mImageUpload.setImageBitmap(bm);


            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        upflag=true;
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void previewCapturedImage() {
        try {
            mImageUpload.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            mImageUpload.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaPath=mediaStorageDir.getPath();
        filePath=File.separator+ "IMG_" + timeStamp + ".jpg";
        fileFinalPath=mediaPath+filePath;

        Log.i(TAG,"MEDIA_PATH"+mediaPath);
        Log.i(TAG,"FILEPATH"+filePath);
        Log.i(TAG,"FULLPATH"+fileFinalPath);

        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(fileFinalPath);
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void getItem (int day, int month, int years) {
        String myDate=years+"/"+(month+1)+"/"+day;
        mEdtDate.setText (myDate);
    }

    @Override
    public void getItem (int hours, int minuets) {
        String myTime=hours+":"+minuets;
        mEdtTime.setText (myTime);
    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
        mSpinnserSchedule = (String) parent.getItemAtPosition (position);
        mSpinnserCallType = (String) parent.getItemAtPosition (position);
        mSpinnserStaus = (String) parent.getItemAtPosition (position);
    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {}

}
