package com.apps4med.healthious.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.apps4med.healthious.R;
import com.apps4med.healthious.app.HealthiousApplication;
import com.apps4med.healthious.app.SessionHandler;
import com.apps4med.healthious.app.Tag;

/**
 * Created by iskitsas on 25/10/2014.
 */
public class WeekFragment extends DialogFragment {
    private static SessionHandler sSessionHandler;
    Button weekButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sSessionHandler = HealthiousApplication.getInstance().getSessionHandler();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.week);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_week, null);
        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(12);

        if (sSessionHandler.week != null) {
            numberPicker.setValue(sSessionHandler.week);
        } else {
            numberPicker.setValue(1);
        }
        numberPicker.setWrapSelectorWheel(true);
        builder.setView(view)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int week = numberPicker.getValue();
                        sSessionHandler.week = week;
                        sSessionHandler.saveStringPreference(Tag.lastWeek.name(), week + "");
                        if (weekButton != null) {
                            weekButton.setText(week + "");
                            sSessionHandler.lastWeek = week;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WeekFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public Button getWeekButton() {
        return weekButton;
    }

    public void setWeekButton(Button weekButton) {
        this.weekButton = weekButton;
    }
}
