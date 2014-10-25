package com.apps4med.healthious.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
public class HeightFragment extends DialogFragment {
    private static SessionHandler sSessionHandler;
    TextView heightTextView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sSessionHandler = HealthiousApplication.getInstance().getSessionHandler();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Centimeters");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_height, null);
        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(220);
        numberPicker.setMinValue(140);
        if (sSessionHandler.height != null) {
            numberPicker.setValue(sSessionHandler.height);
        } else {
            numberPicker.setValue(170);
        }
        numberPicker.setWrapSelectorWheel(true);
        builder.setView(view)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int centimeters = numberPicker.getValue();
                        sSessionHandler.height = centimeters;
                        heightTextView.setText(""+centimeters);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HeightFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public TextView getHeightTextView() {
        return heightTextView;
    }

    public void setHeightTextView(TextView heightTextView) {
        this.heightTextView = heightTextView;
    }
}
