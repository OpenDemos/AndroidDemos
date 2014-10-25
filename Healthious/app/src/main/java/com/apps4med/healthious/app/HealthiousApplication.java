package com.apps4med.healthious.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by iskitsas on 25/10/2014.
 */
public class HealthiousApplication extends Application {
    private static HealthiousApplication sInstance;
    private SessionHandler mSessionHandler;

    public static HealthiousApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sInstance.initializeInstance();
    }

    protected void initializeInstance() {
        mSessionHandler = new SessionHandler(this.getSharedPreferences("PREFS_PRIVATE", Context.MODE_PRIVATE));
    }

    public SessionHandler getSessionHandler() {
        return mSessionHandler;
    }
}
