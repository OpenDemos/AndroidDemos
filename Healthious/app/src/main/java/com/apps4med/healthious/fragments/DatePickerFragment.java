package com.apps4med.healthious.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.apps4med.healthious.app.HealthiousApplication;
import com.apps4med.healthious.app.SessionHandler;
import com.apps4med.healthious.app.Tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by iskitsas on 23/10/2014.
 * http://developer.android.com/guide/topics/ui/controls/pickers.html
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static SessionHandler sSessionHandler;
    private Integer mYear;
    private Integer mMonth;
    private Integer mDay;
    TextView dateTextView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sSessionHandler = HealthiousApplication.getInstance().getSessionHandler();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date =  simpleDateFormat.format(calendar.getTime());
        dateTextView.setText(date);
        sSessionHandler.saveStringPreference(Tag.birthdate.name(),date);

    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public void setDateTextView(TextView dateTextView) {
        this.dateTextView = dateTextView;
    }
}
