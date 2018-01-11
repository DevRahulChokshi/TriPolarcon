package com.example.ebc003.tripolarcon.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.ebc003.tripolarcon.app.MyDatePicker;

import java.util.Calendar;

/**
 * Created by EBC003 on 1/11/2018.
 */

public class DatePickerDialogExample extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog (getActivity (), this,
                year,month,day);
    }

    @Override
    public void onDateSet (DatePicker view, final int year, final int month, final int dayOfMonth) {
        MyDatePicker  myDatePicker= (MyDatePicker) getActivity ();
        myDatePicker.getItem (dayOfMonth,month,year);
    }
}
