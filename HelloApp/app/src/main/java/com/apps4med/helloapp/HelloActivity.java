package com.apps4med.helloapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apps4med.helloapp.fragments.DatePickerFragment;
import com.apps4med.helloapp.util.DateHelper;


public class HelloActivity extends Activity {
    TextView topTextView;
    EditText editTextView;
    TextView dateTextView;
    DateHelper dateHelper = new DateHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        //Set View object
        topTextView = (TextView) findViewById(R.id.topTextView);
        editTextView = (EditText) findViewById(R.id.editTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        dateTextView.setText(dateHelper.getNowDate());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitButton(View view) {
        // http://developer.android.com/guide/topics/ui/dialogs.html

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(HelloActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(editTextView.getText())
                .setTitle(R.string.app_name);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void changeDate(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDateTextView(dateTextView);
        datePickerFragment.show(getFragmentManager(), "dateTimePicker");
    }
}
