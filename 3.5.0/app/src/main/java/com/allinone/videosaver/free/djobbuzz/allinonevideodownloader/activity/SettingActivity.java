package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import videodownload.com.newmusically.R;

public class SettingActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.SummaryProvider<ListPreference> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String darkModeString = getString(R.string.dark_mode);


        if (key != null && key.equalsIgnoreCase(darkModeString) && sharedPreferences != null) {
            String[] darkModeValues = getResources().getStringArray(R.array.dark_mode_values);
            String string = sharedPreferences.getString(darkModeString, darkModeValues[0]);
            if (string != null) {
                if (string.equalsIgnoreCase(darkModeValues[0])) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else if (string.equalsIgnoreCase(darkModeValues[1])) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else if (string.equalsIgnoreCase(darkModeValues[2])) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else if (string.equalsIgnoreCase(darkModeValues[3])) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
            }

        }

    }

    @Override
    public CharSequence provideSummary(ListPreference preference) {
        if (preference != null && preference.getKey().equals(getString(R.string.dark_mode))) {
            return preference.getEntry();
        } else {
            return "Unknown Preference";
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}