package com.example.ebc003.tripolarcon.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.ebc003.tripolarcon.app.MyDatePicker;
import com.example.ebc003.tripolarcon.app.MyTimePicker;

import java.util.Calendar;

/**
 * Created by EBC003 on 1/11/2018.
 */

public class TimePickerDialogExample extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    String format;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {

        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this , hour, minute, false);
    }

    @Override
    public void onTimeSet (TimePicker view, int hourOfDay, int minute) {
        if (hourOfDay == 0) {

            hourOfDay += 12;

            format = "AM";
        } else if (hourOfDay == 12) {

            format = "PM";

        } else if (hourOfDay > 12) {

            hourOfDay -= 12;

            format = "PM";

        } else {

            format = "AM";
        }

        MyTimePicker myTimePicker = (MyTimePicker) getActivity ();
        myTimePicker.getItem (hourOfDay, minute,format);
    }
}
