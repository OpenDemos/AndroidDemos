package com.apps4med.healthious.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apps4med.healthious.R;
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

    void initListeners(){
        birthdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setDateTextView(dateTextView);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "dateTimePicker");
            }
        });
    }
}
