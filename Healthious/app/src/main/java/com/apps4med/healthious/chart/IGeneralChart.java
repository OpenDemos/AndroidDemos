package com.apps4med.healthious.chart;

import android.content.Context;

import org.achartengine.GraphicalView;

/**
 * Created by iskitsas on 7/27/14.
 */
public interface IGeneralChart {
    /** A constant for the name field in a list activity. */
    String NAME = "name";
    /** A constant for the description field in a list activity. */
    String DESC = "desc";

    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    String getName();

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    String getDesc();

    /**
     * Executes the chart demo.
     *
     * @param context the context
     * @return the built intent
     */
    GraphicalView create(Context context);
}
