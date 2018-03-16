package com.example.ebc003.tripolarcon.app.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.app.MyDatePicker;
import com.example.ebc003.tripolarcon.app.MyTimePicker;
import com.example.ebc003.tripolarcon.model.AddLogAsyncTask;
import com.example.ebc003.tripolarcon.model.Constants;
import com.example.ebc003.tripolarcon.model.DatePickerDialogExample;
import com.example.ebc003.tripolarcon.model.TimePickerDialogExample;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityGenerateLog extends AppCompatActivity implements View.OnClickListener,MyDatePicker,MyTimePicker,AdapterView.OnItemSelectedListener,PermissionResultCallback, ActivityCompat.OnRequestPermissionsResultCallback{

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
    String userName;
    String companyId;
    String mSpinnserSchedule;
    String mSpinnserCallType;
    String mSpinnserStaus;

    ArrayList<String> permissions = new ArrayList<> ();
    PermissionUtils permissionUtils;
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO_CAMERA = 1;
    static final int REQUEST_TAKE_PHOTO_GALLERY = 2;
    static String fileFinalPath;
    String dateforrow;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_generate_log);

        ButterKnife.bind (this);
        //setUp Toolbar
        setToolbar();
        //Add Spinner mSpinnerSchedule
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

        permissionUtils = new PermissionUtils (this);

        permissions.add (Manifest.permission.CAMERA);
        permissions.add (Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add (Manifest.permission.READ_EXTERNAL_STORAGE);

        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd hh:mm:ss a");

        dateforrow = dateFormat.format(cal1.getTime());

        System.out.println("Date Format with dd-M-yyyy hh:mm:ss : "+dateforrow);
    }

    private void checkShredPreference () {
        SharedPreferences sharedPreferences=getSharedPreferences (Constants.PREFERENCE_KEY,MODE_PRIVATE);
        userID=sharedPreferences.getString (Constants.EMP_ID,"N/A");
        userName=sharedPreferences.getString (Constants.USER_NAME,"N/A");
        Log.i (TAG,"User id:-"+userID);
    }

    private void setSpinner () {
        String [] mListSchedule={"Meeting","Site Visit","Phone Call","Reminder","Follow up"};
        ArrayAdapter<String> adapterSchedule=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListSchedule);
        mSpinnerSchedule.setAdapter (adapterSchedule);

        String [] mListCallType={"Phone call","Enquiry call","Reminder Call","Follow up","Payment"};
        ArrayAdapter<String> adapterCallType=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListCallType);
        mSpinnerCallType.setAdapter (adapterCallType);

        String [] mListStatus={"Call back","No response","No related","Order closed","Directly forwarded","Number not reachable","Others","Quotation","Order finalise","Sampling","Site visit","Send to contractor","FCD"};
        ArrayAdapter<String> adapterStatus=new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,mListStatus);
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
                intent.putExtra (Constants.COMPANY_NAME,companyId);
                Log.i (TAG,"USER ID:-"+userID);
                Log.i (TAG,"COMPANY ID:-"+companyId);
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
        myCalender.set (Calendar.AM_PM,Calendar.AM);
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);
        int seconds = myCalender.get(Calendar.SECOND);

        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int day = myCalender.get(Calendar.DAY_OF_MONTH);

        String CurrentDate=year+"-"+((month)+1)+"-"+day;
        String CurrentTime=hour+":"+minute+":"+seconds;

        checkValidation(mDate,mTime,CurrentDate,CurrentTime,mRemark);
    }

    @Override
    public void onClick (View v) {
       int id= v.getId ();

       switch (id){
           case R.id.btnDtn:{
               mEdtDate.setError(null);
               mEdtDate.clearFocus();
               DialogFragment dialogFragment=new DatePickerDialogExample ();
               dialogFragment.show (getFragmentManager (),"date");
               break;
           }
           case R.id.btnTime:{
               mEdtTime.setError(null);
               mEdtTime.clearFocus();
               DialogFragment dialogFragment=new TimePickerDialogExample ();
               dialogFragment.show (getFragmentManager (),"time");
               break;
           }
           case R.id.btnPhoto:{
               permissionUtils.check_permission (permissions, "Explain here why the app needs permissions", 1);
               break;
           }
       }
    }

    @Override
    public void getItem (int day, int month, int years) {
        String myDate=years+"/"+(month+1)+"/"+day;
        mEdtDate.setText (myDate);
        Typeface regularFont=Typeface.createFromAsset (getAssets (),"fonts/OpenSansCondensed-Light.ttf");
        mEdtDate.setTypeface (regularFont);
    }

    @Override
    public void getItem (int hours, int minuets,String format) {
        String myTime=hours+":"+minuets+" "+format;
        mEdtTime.setText (myTime);
        Typeface regularFont=Typeface.createFromAsset (getAssets (),"fonts/OpenSansCondensed-Light.ttf");
        mEdtTime.setTypeface (regularFont);
    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
        {
            ((TextView) view).setTextColor(getResources ().getColor (R.color.textColorEarthquakeLocation));
        }
        int viewId=parent.getId ();

        switch (viewId){
            case R.id.spinAddLogSchedule:{
                mSpinnserSchedule = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mSpinnserSchedule);
                break;
            }
            case R.id.spinAddLogCallType:{
                mSpinnserCallType = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mSpinnserCallType);
                break;
            }
            case R.id.spinAddLogStatus:{
                mSpinnserStaus = (String) parent.getItemAtPosition (position);
                Log.i (TAG,"SpinnerSchedule:-"+mSpinnserStaus);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {}

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult (requestCode, permissions, grantResults);
    }

    // Callback functions

    @Override
    public void PermissionGranted (int request_code) {
//        dispatchTakePictureIntent();
        captureImage ();
        Log.i ("PERMISSION", "GRANTED");
    }

    private void captureImage () {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder (ActivityGenerateLog.this);
        builder.setTitle ("Add Photo!");
        builder.setItems (items, new DialogInterface.OnClickListener () {
            @Override
            public void onClick (DialogInterface dialog, int item) {
                if (items[item].equals ("Take Photo")) {
                    dispatchTakePictureIntent ();
                } else if (items[item].equals ("Choose from Library")) {

                    galleryIntent ();
                } else if (items[item].equals ("Cancel")) {
                    dialog.dismiss ();
                }
            }
        });
        builder.show ();
    }

    void galleryIntent () {
        Intent intent = new Intent (
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType ("image/*");
        startActivityForResult (
                Intent.createChooser (intent, "Select File"),
                REQUEST_TAKE_PHOTO_GALLERY);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO_CAMERA && resultCode == RESULT_OK) {
            setPic ();
        } else if (requestCode == REQUEST_TAKE_PHOTO_GALLERY && resultCode == RESULT_OK) {
            getGallery (data);
        }
    }

    private void getGallery (Intent intent) {
        Uri selectedImageUri = intent.getData();
        mCurrentPhotoPath = getPath(selectedImageUri, ActivityGenerateLog.this);

        Bitmap bm;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bm = BitmapFactory.decodeFile(mCurrentPhotoPath, bitmapOptions);
        Log.i (TAG,"Current File Path:-"+mCurrentPhotoPath);
        mImageUpload.setImageBitmap(bm);
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat ("yyyyMMdd_HHmmss").format(new Date ());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.ebc003.tripolarcon.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_CAMERA);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageUpload.getWidth();
        int targetH = mImageUpload.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Log.i (TAG,"Current File Path:-"+mCurrentPhotoPath);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageUpload.setImageBitmap(bitmap);
        galleryAddPic();
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
    }

    private void checkValidation (String mDate,String mTime,String mCurrentDate,String mCurrentTime,String mRemark) {
        if (mDate.isEmpty () && mDate.length ()==0){
            mEdtDate.setError ("Choose date");
            mEdtDate.setFocusable (true);
        }
        else if(mTime.isEmpty ()&& mTime.length ()==0){
            mEdtTime.setError ("Choose time");
            mEdtTime.setFocusable (true);
        }
        else {
            mEdtTime.setFocusable (false);
            mEdtDate.setFocusable (false);

            Typeface regularFont=Typeface.createFromAsset (getAssets (),"fonts/OpenSansCondensed-Light.ttf");
            mEdtRemark.setTypeface (regularFont);

            AddLogAsyncTask addLogAsyncTask=new AddLogAsyncTask (getApplicationContext (),progressBar);
            Log.i (TAG,"AsyncTask:-"+mCurrentPhotoPath);
            addLogAsyncTask.execute (mSpinnserSchedule,mCurrentDate,mCurrentTime,mSpinnserCallType,mSpinnserStaus,mRemark,mDate,mTime,companyId,userID,mCurrentPhotoPath,dateforrow,company_name,userName);
            Log.i (TAG,"ADD IN ASYNC TASK:-"+company_name);
            Log.i (TAG,"AsyncTask:-"+userName);
        }
    }
}
