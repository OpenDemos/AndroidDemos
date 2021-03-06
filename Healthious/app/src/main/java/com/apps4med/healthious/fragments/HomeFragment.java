package com.apps4med.healthious.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apps4med.healthious.ChartActivity;
import com.apps4med.healthious.PricesListActivity;
import com.apps4med.healthious.R;
import com.apps4med.healthious.TipsListActivity;
import com.apps4med.healthious.app.HealthiousApplication;
import com.apps4med.healthious.app.SessionHandler;
import com.apps4med.healthious.app.Tag;

/**
 * Created by iskitsas on 24/10/2014.
 */
public class HomeFragment extends Fragment {
    private static SessionHandler sSessionHandler;
    TextView bmiCurrentValue;
    TextView dateTextView;
    Button birthdateButton;
    Button weekButton;
    Button weightButton;
    Button saveWeekWeightButton;
    Button goToChartButton;
    Button healthPricesButton;
    Button healthTipsButton;
    TextView lastWeekWeightTextView;
    TextView allMeasurementsTextView;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sSessionHandler = HealthiousApplication.getInstance().getSessionHandler();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        bmiCurrentValue = (TextView) rootView.findViewById(R.id.bmiCurrentValue);
        dateTextView = (TextView) rootView.findViewById(R.id.dateTextView);
        birthdateButton = (Button) rootView.findViewById(R.id.birtdateButton);
        weekButton = (Button) rootView.findViewById(R.id.weekButton);
        weightButton = (Button) rootView.findViewById(R.id.weightButton);
        saveWeekWeightButton = (Button) rootView.findViewById(R.id.saveWeekWeightButton);
        goToChartButton = (Button) rootView.findViewById(R.id.goToChartButton);
        healthPricesButton = (Button) rootView.findViewById(R.id.healthPricesButton);
        healthTipsButton = (Button) rootView.findViewById(R.id.healthTipsButton);
        lastWeekWeightTextView = (TextView) rootView.findViewById(R.id.lastWeekWeightTextView);
        allMeasurementsTextView = (TextView) rootView.findViewById(R.id.allMeasurementsTextView);

        showAllMeasurements();

        String lastWeek = sSessionHandler.getStringPreference(Tag.lastWeek.name());
        String lastWeight = sSessionHandler.getStringPreference(Tag.lastWeight.name());

        if(!TextUtils.isEmpty(lastWeek) && !TextUtils.isEmpty(lastWeight)){
            lastWeekWeightTextView.setText("Week: "+lastWeek +", Weight: "+lastWeight);
        }

        String bmi = sSessionHandler.getStringPreference(Tag.bmi.name());
        if(!TextUtils.isEmpty(bmi)){
            bmiCurrentValue.setText(bmi);
        }
        String birthdate = sSessionHandler.getStringPreference(Tag.birthdate.name());
        if(!TextUtils.isEmpty(birthdate)){
            dateTextView.setText(birthdate);
        }

        initListeners();
        return rootView;
    }

    private void showAllMeasurements() {
        String ret="";
        String s;
        String w;
        for(int i=1;i<=12;i++){
            w=Tag.week.name()+"_"+i;
            s = sSessionHandler.getStringPreference(w);
            if(!TextUtils.isEmpty(s)){
                ret+="Week: "+i +", Weight: "+s+"\n";
            }
        }
        if(!TextUtils.isEmpty(ret)) {
            allMeasurementsTextView.setText(ret);
        }
    }

    void initListeners(){
        birthdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setDateTextView(dateTextView);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "dateTimePicker");
            }
        });

        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeekFragment weekFragment = new WeekFragment();
                weekFragment.setWeekButton(weekButton);
                weekFragment.show(getActivity().getSupportFragmentManager(), "weekPicker");

            }
        });

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KilogramsFragment kilogramsFragment = new KilogramsFragment();
                kilogramsFragment.setWeightButton(weightButton);
                kilogramsFragment.show(getActivity().getSupportFragmentManager(), "kilogramsFragment2");
            }
        });

        saveWeekWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sSessionHandler.lastWeek != null && sSessionHandler.lastWeight!=null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.save_question);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sSessionHandler.saveStringPreference(Tag.week.name()+"_"+sSessionHandler.lastWeek,sSessionHandler.lastWeight+"");
                            lastWeekWeightTextView.setText("Week: " + sSessionHandler.lastWeek + ", Weight: " + sSessionHandler.lastWeight);
                            showAllMeasurements();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.no_week_or_weight);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        goToChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChartActivity.class));
            }
        });

        healthPricesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PricesListActivity.class));
            }
        });

        healthTipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TipsListActivity.class));
            }
        });
    }

    void showDialog(String message){

    }
}
