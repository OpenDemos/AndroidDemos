package com.apps4med.healthious;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.apps4med.healthious.app.HealthiousApplication;
import com.apps4med.healthious.app.SessionHandler;
import com.apps4med.healthious.app.Tag;
import com.apps4med.healthious.chart.ProgressChart;
import com.apps4med.healthious.model.Weight;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class ChartActivity extends ActionBarActivity {
    GraphicalView mChartView;
    private static SessionHandler sSessionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        sSessionHandler = HealthiousApplication.getInstance().getSessionHandler();
        createChart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chart, menu);
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

    void createChart() {
        // Create Progress Chart
        if (mChartView == null) {
            Double weightTarget = 99.0;
            String savedWeightTarget = sSessionHandler.getStringPreference(Tag.weightTarget.name());
            if (!TextUtils.isEmpty(savedWeightTarget)) {
                weightTarget = Double.valueOf(savedWeightTarget);
            }

            Double wStart = 110.0;
            String savedWeightStart = sSessionHandler.getStringPreference(Tag.week.name()+"_1");
            if (!TextUtils.isEmpty(savedWeightStart)) {
                wStart = Double.valueOf(savedWeightStart);
            }
            List<Weight> weightList =getWeightList();
            ProgressChart progressChart = new ProgressChart(2, wStart, weightTarget, weightList);
            LinearLayout layout = (LinearLayout) findViewById(R.id.progressChart);
            mChartView = progressChart.create(ChartActivity.this);
            layout.addView(mChartView);

        } else {
            mChartView.repaint();
        }
    }

    List<Weight> getWeightList(){
        List<Weight> weightList = new ArrayList<Weight>();
        String s;
        String w;
        for(int i=1;i<=12;i++){
            w=Tag.week.name()+"_"+i;
            s = sSessionHandler.getStringPreference(w);
            if(!TextUtils.isEmpty(s)){
                weightList.add(new Weight(i, Double.parseDouble(s)));
            }
        }
        return weightList;
    }
}
