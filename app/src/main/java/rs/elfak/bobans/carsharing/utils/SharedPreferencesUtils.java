package rs.elfak.bobans.carsharing.utils;

import android.content.SharedPreferences;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class SharedPreferencesUtils {

    // Shared pref mode
    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "CarSharing";

    private static SharedPreferencesUtils instance;

    public static SharedPreferencesUtils getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesUtils();
        }
        return instance;
    }

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private SharedPreferencesUtils() {
        prefs = CarSharingApplication.getInstance().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public void putPrefString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getPrefString(String key) {
        return prefs.getString(key, null);
    }

    public void putPrefInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getPrefInt(String key) {
        return prefs.getInt(key, -1);
    }

    public int getPrefInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public void putPrefLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getPrefLong(String key) {
        return prefs.getLong(key, -1);
    }

    public long getPrefLong(String key, long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    public void putPrefBoolean(String key, boolean b) {
        editor.putBoolean(key, b);
        editor.commit();
    }

    public boolean getPrefBoolen(String key) {
        return prefs.getBoolean(key, false);
    }

    public boolean getPrefBoolen(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public void deletePref(String key) {
        editor.remove(key);
        editor.apply();
    }

}
