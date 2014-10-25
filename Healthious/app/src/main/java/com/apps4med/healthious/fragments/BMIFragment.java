package com.apps4med.healthious.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.apps4med.healthious.R;
import com.apps4med.healthious.app.AppHelper;
import com.apps4med.healthious.app.HealthiousApplication;
import com.apps4med.healthious.app.SessionHandler;
import com.apps4med.healthious.app.Tag;

import java.text.DecimalFormat;

/**
 * Created by iskitsas on 25/10/2014.
 */
public class BMIFragment extends Fragment {
    private static SessionHandler sSessionHandler;
    private static final String ARG_BMI = "bmi";
    Button calculateBmiButton;
    TextView bmiWeightTextView;
    TextView bmiHeightTextView;
    TextView classificationTextView;
    TextView bmiTextView;
    TextView targetWeightTextView;
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;
    Button bmiButtonWeight;
    Button bmiButtonHeight;

    public static BMIFragment newInstance(double value) {
        BMIFragment fragment = new BMIFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_BMI, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bmi, container, false);
        sSessionHandler = HealthiousApplication.getInstance().getSessionHandler();
        calculateBmiButton = (Button) rootView.findViewById(R.id.calculateBmiButton);
        bmiWeightTextView = (TextView) rootView.findViewById(R.id.bmiWeightTextView);
        bmiHeightTextView = (TextView) rootView.findViewById(R.id.bmiHeightTextView);
        classificationTextView = (TextView) rootView.findViewById(R.id.classificationTextView);
        targetWeightTextView = (TextView) rootView.findViewById(R.id.targetWeightTextView);
        bmiTextView = (TextView) rootView.findViewById(R.id.bmiTextView);
        maleRadioButton = (RadioButton) rootView.findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton) rootView.findViewById(R.id.femaleRadioButton);
        femaleRadioButton.setChecked(true);
        bmiButtonWeight = (Button) rootView.findViewById(R.id.bmiButtonWeight);
        bmiButtonHeight = (Button) rootView.findViewById(R.id.bmiButtonHeight);
        initListeners();
        return rootView;
    }

    void initListeners(){
        calculateBmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = validateBMIInputs();
                if(!TextUtils.isEmpty(message)) {
                    showDialog(message);
                }else{
                    //calculate bmi
                    double bmi = AppHelper.getBmi(sSessionHandler.weight, sSessionHandler.height);
                    sSessionHandler.bmi = Double.valueOf(new DecimalFormat("##.##").format(bmi));
                    sSessionHandler.weightTarget = AppHelper.getTargetWeightInKilos(12, sSessionHandler.weight, sSessionHandler.height);

                    targetWeightTextView.setText(""+sSessionHandler.weightTarget);
                    bmiTextView.setText(""+sSessionHandler.bmi);
                    classificationTextView.setText(AppHelper.getWeightClassification(bmi)+"");

                    sSessionHandler.saveStringPreference(Tag.bmi.name(),sSessionHandler.bmi+"");

                }
            }
        });

        bmiButtonWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KilogramsFragment kilogramsFragment = new KilogramsFragment();
                kilogramsFragment.setWeightTextView(bmiWeightTextView);
                kilogramsFragment.show(getActivity().getSupportFragmentManager(), "kilogramsFragment");
            }
        });

        bmiButtonHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HeightFragment heightFragment = new HeightFragment();
                heightFragment.setHeightTextView(bmiHeightTextView);
                heightFragment.show(getActivity().getSupportFragmentManager(), "heightFragment");
            }
        });


    }

    void showDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.errors)
               .setMessage(message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    String validateBMIInputs(){
        String ret = "";

        try {
            Integer.valueOf(bmiWeightTextView.getText().toString());
        }catch(Exception e){
            ret = getString(R.string.weight_not_properly_set);
        }

        try {
            Integer.valueOf(bmiHeightTextView.getText().toString());
        }catch(Exception e){
            ret += "\n"+getString(R.string.height_not_properly_set);
        }


        return ret;
    }
}
