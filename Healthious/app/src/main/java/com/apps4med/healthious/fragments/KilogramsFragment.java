package com.apps4med.healthious.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.apps4med.healthious.R;
import com.apps4med.healthious.app.HealthiousApplication;
import com.apps4med.healthious.app.SessionHandler;

/**
 * Created by iskitsas on 25/10/2014.
 */
public class KilogramsFragment extends DialogFragment {
    private static SessionHandler sSessionHandler;
    TextView weightTextView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sSessionHandler = HealthiousApplication.getInstance().getSessionHandler();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Kilograms");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_kilograms, null);
        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(150);
        numberPicker.setMinValue(40);
        if (sSessionHandler.weight != null) {
            numberPicker.setValue(sSessionHandler.weight);
        } else {
            numberPicker.setValue(75);
        }
        numberPicker.setWrapSelectorWheel(true);
        builder.setView(view)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int kilograms = numberPicker.getValue();
                        sSessionHandler.weight = kilograms;
                        weightTextView.setText(""+kilograms);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        KilogramsFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public TextView getWeightTextView() {
        return weightTextView;
    }

    public void setWeightTextView(TextView weightTextView) {
        this.weightTextView = weightTextView;
    }
}
