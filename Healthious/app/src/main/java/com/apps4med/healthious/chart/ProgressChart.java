package com.apps4med.healthious.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.apps4med.healthious.R;
import com.apps4med.healthious.app.AppHelper;
import com.apps4med.healthious.model.Weight;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iskitsas on 7/27/14.
 */
public class ProgressChart extends GeneralChart {
    int currentWeek=1;
    Double weightStart;
    Double weightTarget;
    List<Weight> weightList;
    boolean demo=false;
    /** The main dataset that includes all the series that go into a chart. */
    private XYMultipleSeriesDataset dataset;
    /** The main renderer that includes all the renderers customizing a chart. */
    private XYMultipleSeriesRenderer renderer;

    public ProgressChart(int currentWeek,Double weightStart,Double weightTarget, List<Weight> weightList) {
        this.currentWeek = currentWeek;
        this.weightStart = weightStart;
        this.weightTarget = weightTarget;
        this.weightList = weightList;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }

    /**
     * Executes the chart demo.
     *
     * @param context the context
     * @return the built intent
     */
    public GraphicalView create(Context context) {
        String[] titles = new String[] { context.getResources().getString(R.string.label_target_weight), context.getResources().getString(R.string.label_current_weight)};
        List<double[]> values = new ArrayList<double[]>();

        //set target weight per week

        if(isDemo()){
            values.add(new double[] { 108, 107.1, 106.2, 105.3, 104.4, 103.5, 102.6, 101.7, 100.8, 99.9, 99,98.1});
        }else{
            double lossPerWeek= AppHelper.lossTargetPerWeekKilos(12, weightStart, weightTarget);
            double[] lpw=new double[12];
            double target=weightStart;
            for(int i=0;i<12;i++){
                lpw[i]=target;
                target-=lossPerWeek;
            }
            values.add(lpw);
        }


        List<double[]> x = new ArrayList<double[]>();
        for (int i = 0; i < titles.length; i++) {
            if(i==0) {
                x.add(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
            }else if(i==1){
                if(isDemo()){
                    x.add(new double[] { 1, 2, 3, 4, 5 });
                    values.add(new double[] { 109, 107.3, 105.1, 104.3,106 });
                }else{
                    if(weightList == null ||(weightList!=null && weightList.isEmpty())){
                        x.add(new double[] {1});
                        values.add(new double[] {weightStart});
                    }else{
                        double[] xWeeks = new double[weightList.size()];
                        double[] yWeights = new double[weightList.size()];
                        int cnt=0;
                        for(Weight w:weightList){
                            xWeeks[cnt]=w.week;
                            yWeights[cnt]=w.weight;
                            cnt++;
                        }
                        x.add(xWeeks);
                        values.add(yWeights);
                    }
                }
            }
        }

        int[] colors = new int[] { Color.parseColor("#EECBAD"), Color.parseColor("#FC7D10") };
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.CIRCLE};
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, "", "", "", 0.8, 12.2, weightTarget.intValue()-5, weightStart.intValue()+5,
                Color.LTGRAY, Color.LTGRAY);
        renderer.setXLabels(14);
        renderer.setYLabels(5);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setZoomButtonsVisible(false);
        renderer.setPanEnabled(false);
        renderer.setZoomEnabled(false);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setMarginsColor(Color.WHITE);
//        renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
//        renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

        XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
        setDataset(dataset);
        setRenderer(renderer);
        renderer.setShowGridX(true);
        renderer.setYAxisColor(Color.WHITE);
        //renderer.setYLabelsColor(0,Color.BLACK);
//        XYSeries series = dataset.getSeriesAt(0);
//        series.addAnnotation("Vacation", 6, 30);
        return ChartFactory.getLineChartView(context, dataset, renderer);
    }

    public XYMultipleSeriesDataset getDataset() {
        return dataset;
    }

    public void setDataset(XYMultipleSeriesDataset dataset) {
        this.dataset = dataset;
    }

    public XYMultipleSeriesRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(XYMultipleSeriesRenderer renderer) {
        this.renderer = renderer;
    }

    public boolean isDemo() {
        return demo;
    }

    public void setDemo(boolean demo) {
        this.demo = demo;
    }
}
