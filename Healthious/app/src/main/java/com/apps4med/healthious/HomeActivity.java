package com.apps4med.healthious;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.apps4med.healthious.fragments.BMIFragment;
import com.apps4med.healthious.fragments.HomeFragment;

/**
 * http://developer.android.com/tools/support-library/index.html
 * */

public class HomeActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);



        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActionBar().getThemedContext(),
                R.array.action_list, android.R.layout.simple_spinner_dropdown_item);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);

//        // Set up the dropdown list navigation in the action bar.
//        actionBar.setListNavigationCallbacks(
//                // Specify a SpinnerAdapter to populate the dropdown list.
//                new ArrayAdapter<String>(
//                        actionBar.getThemedContext(),
//                        android.R.layout.simple_list_item_1,
//                        android.R.id.text1,
//                        new String[] {
//                                getString(R.string.title_section1),
//                                getString(R.string.title_section2),
//                                getString(R.string.title_section3),
//                        }),
//                this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                goToBMIFragment(null);
                break;
            case 2:
                startActivity(new Intent(HomeActivity.this, ChartActivity.class));
                break;
            case 3:
                startActivity(new Intent(HomeActivity.this, TipsListActivity.class));
                break;
            case 4:
                startActivity(new Intent(HomeActivity.this, PricesListActivity.class));
                break;
        }
        return true;
    }

    public void goToBMIFragment(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, BMIFragment.newInstance(2))
                .commit();
    }
}
