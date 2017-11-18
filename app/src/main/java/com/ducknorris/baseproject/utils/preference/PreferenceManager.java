package com.ducknorris.baseproject.utils.preference;

import android.content.SharedPreferences;

import com.ducknorris.baseproject.BaseApplication;

/**
 * Created by ndelanou on 17/05/2017.
 */

public class PreferenceManager {


    public static final PreferenceManager INSTANCE = new PreferenceManager();
    private SharedPreferences mSharedPreferences;

    private SharedPreferences getPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = android.preference.PreferenceManager
                    .getDefaultSharedPreferences(BaseApplication.Companion.getINSTANCE().getApplicationContext());
        }
        return mSharedPreferences;
    }

    public boolean isKeySet(String key) {
        return getPreferences().contains(key);
    }

    public String getStringPreference(String pref, String defValue) {
        return getPreferences().getString(pref, defValue);
    }

    public void setStringPreference(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    public Boolean getBooleanPreference(String pref, Boolean defValue) {
        return getPreferences().getBoolean(pref, defValue);
    }

    public void setBooleanPreference(String key, Boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    public Integer getIntegerPreference(String pref, Integer defValue) {
        return getPreferences().getInt(pref, defValue);
    }

    public void setIntegerPreference(String key, Integer value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    public Long getLongPreference(String pref, Long defValue) {
        return getPreferences().getLong(pref, defValue);
    }

    public void setLongPreference(String key, Long value) {
        getPreferences().edit().putLong(key, value).apply();
    }

    public void removePref(String key) {
        getPreferences().edit().remove(key).apply();
    }
}
