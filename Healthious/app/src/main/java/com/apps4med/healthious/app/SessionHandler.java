package com.apps4med.healthious.app;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by iskitsas on 25/10/2014.
 */
public class SessionHandler {
    public static final String TOKEN_KEY = "token";


    private final SharedPreferences preferences;

    public String gender; // male, female
    public String dateOfBirth; // dd/MM/yyyy
    public Double weightTarget; // kilograms
    public Integer height; // cm
    public Integer weight; // kilograms
    public Double bmi;
    public Integer week;
    public Integer lastWeight;
    public Integer lastWeek;


    public SessionHandler(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void saveStringPreference(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveBooleanPreference(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void saveStringSetPreference(String key, Set<String> set) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, set);
        editor.commit();
    }

    public boolean getBooleanPreference(String key) {
        return preferences.getBoolean(key, false);
    }

    public String getStringPreference(String key) {
        return preferences.getString(key, null);
    }

    public Set<String> getStringSetPreference(String key) {
        return preferences.getStringSet(key, null);
    }

    public Set<String> getClonedStringSetPreference(String key) {
        Set<String> s = new HashSet<String>(preferences.getStringSet(key, new HashSet<String>()));
        return s;
    }


}
