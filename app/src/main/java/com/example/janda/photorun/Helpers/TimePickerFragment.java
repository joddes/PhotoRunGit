package com.example.janda.photorun.Helpers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.janda.photorun.R;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener  {

    private Calendar calendar;
    private TextView DisplayTime, timeTV;
    private Button Confirm;
    private TimePicker startTimePicker;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //DisplayTime = (TextView) getActivity().findViewById(R.id.starting_time);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));


    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
       // TimePicker startTimePicker = (TimePicker) getActivity().findViewById(R.id.timePicker);

        view.setIs24HourView(true);

        int hour = view.getCurrentHour();
        String hourfix;
        if (hour<10){
            hourfix = "0"+hour;
        }else{
            hourfix = ""+hour;
        }

        int min = view.getCurrentMinute();
        String minfix;
        if (min<10){
            minfix = "0"+min;
        }else{
            minfix = ""+ min;
        }

       // String startTime = "" + hour +":"+min+":00";
        TextView timeTV = getActivity().findViewById(R.id.starting_time);
        timeTV.setText(hourfix +":"+minfix+":00");
















    }




    }
