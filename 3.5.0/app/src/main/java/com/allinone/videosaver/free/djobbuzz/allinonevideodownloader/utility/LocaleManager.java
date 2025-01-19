package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.*;

public class LocaleManager {

    private static final String TAG = "LocaleManager";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({LANG_EN, LANG_HI, LANG_AR, LANG_IN, LANG_IT, LANG_KO, LANG_RU,LANG_MA})
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = {LANG_EN, LANG_HI, LANG_AR, LANG_IN, LANG_IT, LANG_KO, LANG_RU,LANG_MA};
    }


    /**
     * SharedPreferences Key
     */
    private static final String LANGUAGE_KEY = "language_key";

    /**
     * set current pref locale
     */
    public static Context setLocale(Context mContext) {
        return updateResources(mContext, getLanguagePref(mContext));
    }

    /**
     * Set new Locale with context
     */
    public static Context setNewLocale(Context mContext, @LocaleDef String language) {
        setLanguagePref(mContext, language);
        return updateResources(mContext, language);
    }

    /**
     * Get saved Locale from SharedPreferences
     *
     * @param mContext current context
     * @return current locale key by default return english locale
     */
    public static String getLanguagePref(Context mContext) {
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Log.e(TAG, "getLanguagePref: " + mPreferences.getString(LANGUAGE_KEY, LANG_EN));
        if (Locale.getDefault().getLanguage().contains("ar"))
            return mPreferences.getString(LANGUAGE_KEY, LANG_AR);
        else if (Locale.getDefault().getLanguage().contains("ru"))
            return mPreferences.getString(LANGUAGE_KEY, LANG_RU);
        else
            return mPreferences.getString(LANGUAGE_KEY, LANG_EN);
    }

    /**
     * set pref key
     */
    private static void setLanguagePref(Context mContext, String localeKey) {
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mPreferences.edit().putString(LANGUAGE_KEY, localeKey).apply();
    }

    /**
     * update resource
     */
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        return context;
    }

    /**
     * get current locale
     */
    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }
}
