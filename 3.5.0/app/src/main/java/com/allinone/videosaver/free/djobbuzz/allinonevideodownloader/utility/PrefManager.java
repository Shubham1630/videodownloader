package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefManager {
    public static String MULTIUSERLIST = "MultiUserList";
    private static final String AFTER_CLICK_SAVE_OPEN = "AfterSaveOpen";
    private static final String AFTER_FIVE_TIME_TAP = "AfterFiveTimeTap";
    private static final String AFTER_FOUR_TIME_DOWNLOAD = "AfterFourTimeDownload";
    private static final String AFTER_FOUR_TIME_SAVE = "AfterFourTimeSave";
    private static final String APP_OPEN_COUNT = "AppCount";
    private static final String APP_VERSION = "appVersion";
    private static final String CHECK_RATE_DATE = "checkRateDate";
    private static final String INSTALL_API = "InstallApi";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_FIRST_TIME_LAUNCH_MUSICALLY = "MusiFirstTimeLaunch";
    private static final String IS_FIRST_TIME_LAUNCH_SAVED = "SaveFirstTimeLaunch";
    private static final String PREF_NAME = "videodownload-welcome";
    private static final String SERVICE_STATUS = "serviceStatus";
    private static final String SET_RATE_LATER_COUNT = "setRateLater";
    private static final String SET_RATE_NEVER_COUNT = "setRateNever";
    private static final String SET_SHARE_LATER_COUNT = "setLater";
    private static final String SET_SHARE_NEVER_COUNT = "setNever";
    private static final String UPDATE_SETTING = "updateSetting";
    private static final String WELCOME_SCREEN_SHOW = "IsFirstTimeWelScreen";
    private static final String pref_tooltip_whatsapp = "pref_tooltip_whatsapp";
    private static final String SHAREIT = "SHareee";
    public static final String DEFAULTLANG = "def_lang";
    public static String SESSIONID = "session_id";
    public static String USERID = "user_id";
    public static String COOKIES = "Cookies";
    public static String CSRF = "csrf";
    public static String ISINSTALOGIN = "IsInstaLogin";
    public static String IS_PURCHASED = "ispurchased";
    public static String ISSHOWHOWTOLIKEE = "IsShoHowToLikee";
    public static String FULLNAME = "Fullname";
    public static String USERNAME = "Username";
    public static String PROFILEIMAGEURL = "ProfileImageUrl";
    SharedPreferences sharedPreferences;
    Editor editor;
    Context context;
    private static PrefManager instance;
    int d = 0;

    public PrefManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, this.d);
        this.editor = this.sharedPreferences.edit();
    }

    public static PrefManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new PrefManager(ctx);
        }
        return instance;
    }

    public int getAfterFiveTimeTap() {
        return this.sharedPreferences.getInt(AFTER_FIVE_TIME_TAP, 0);
    }

    public int getAfterFourTimeDownload() {
        return this.sharedPreferences.getInt(AFTER_FOUR_TIME_DOWNLOAD, 0);
    }

    public int getshare() {
        return this.sharedPreferences.getInt(SHAREIT, 1);
    }

    public int getAfterFourTimeSAve() {
        return this.sharedPreferences.getInt(AFTER_FOUR_TIME_SAVE, 0);
    }

    public boolean getAfterSaveOpen() {
        return this.sharedPreferences.getBoolean(AFTER_CLICK_SAVE_OPEN, true);
    }

    public boolean getApiInstall() {
        return this.sharedPreferences.getBoolean(INSTALL_API, true);
    }

    public int getAppCount() {
        return this.sharedPreferences.getInt(APP_OPEN_COUNT, 0);
    }

    public int getAppVersion() {
        return this.sharedPreferences.getInt(APP_VERSION, 0);
    }

    public boolean getFirstTimeLaunch() {
        return this.sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public int getLater() {
        return this.sharedPreferences.getInt(SET_SHARE_LATER_COUNT, 0);
    }

    public boolean getMusiFirstLaunch() {
        return this.sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH_MUSICALLY, true);
    }

    public boolean getNever() {
        return this.sharedPreferences.getBoolean(SET_SHARE_NEVER_COUNT, false);
    }

    public String getRateDate() {
        return this.sharedPreferences.getString(CHECK_RATE_DATE, null);
    }

    public int getRateLater() {
        return this.sharedPreferences.getInt(SET_RATE_LATER_COUNT, 0);
    }

    public boolean getRateNever() {
        return this.sharedPreferences.getBoolean(SET_RATE_NEVER_COUNT, false);
    }

    public boolean getSaveFirstLaunch() {
        return this.sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH_SAVED, true);
    }

    public boolean getServiceStatus() {
        return this.sharedPreferences.getBoolean(SERVICE_STATUS, true);
    }

    public int getUpdateSetting() {
        return this.sharedPreferences.getInt(UPDATE_SETTING, 0);
    }

    public boolean getWelcomeScreenShow() {
        return this.sharedPreferences.getBoolean(WELCOME_SCREEN_SHOW, false);
    }
     public boolean getIsPurchased() {
        return this.sharedPreferences.getBoolean(IS_PURCHASED, false);
    }

    public void setIsPurchased(boolean z) {
        this.editor.putBoolean(IS_PURCHASED, z);
        this.editor.commit();
    }

    public void setAfterFiveTimeTap(int i) {
        this.editor.putInt(AFTER_FIVE_TIME_TAP, i);
        this.editor.commit();
    }

    public void setDefaultlang(String i) {
        this.editor.putString(DEFAULTLANG, i);
        this.editor.commit();
    }

    public String getDefaultlang() {
        return sharedPreferences.getString(DEFAULTLANG, Utility.LANG_EN);

    }

    public void setAfterFourTimeDownload(int i) {
        this.editor.putInt(AFTER_FOUR_TIME_DOWNLOAD, i);
        this.editor.commit();
    }


    public void setSHARE(int i) {
        this.editor.putInt(SHAREIT, i);
        this.editor.commit();
    }

    public void setAfterFourTimeSave(int i) {
        this.editor.putInt(AFTER_FOUR_TIME_SAVE, i);
        this.editor.commit();
    }

    public void setAfterSavedOpen(boolean z) {
        this.editor.putBoolean(AFTER_CLICK_SAVE_OPEN, z);
        this.editor.commit();
    }

    public void setApiInstall(boolean z) {
        this.editor.putBoolean(INSTALL_API, z);
        this.editor.commit();
    }

    public void setAppCount(int i) {
        this.editor.putInt(APP_OPEN_COUNT, i);
        this.editor.commit();
    }

    public void setAppVersion(int i) {
        this.editor.putInt(APP_VERSION, i);
        this.editor.commit();
    }

    public void setFirstTimeLaunch(boolean z) {
        this.editor.putBoolean(IS_FIRST_TIME_LAUNCH, z);
        this.editor.commit();
    }

    public void setLater(int i) {
        this.editor.putInt(SET_SHARE_LATER_COUNT, i);
        this.editor.commit();
    }

    public void setMusiFirstLaunch(boolean z) {
        this.editor.putBoolean(IS_FIRST_TIME_LAUNCH_MUSICALLY, z);
        this.editor.commit();
    }

    public void setNever(boolean z) {
        this.editor.putBoolean(SET_SHARE_NEVER_COUNT, z);
        this.editor.commit();
    }

    public void setRateDate(String str) {
        this.editor.putString(CHECK_RATE_DATE, str);
        this.editor.commit();
    }

    public void setRateLater(int i) {
        this.editor.putInt(SET_RATE_LATER_COUNT, i);
        this.editor.commit();
    }

    public void setRateNever(boolean z) {
        this.editor.putBoolean(SET_RATE_NEVER_COUNT, z);
        this.editor.commit();
    }

    public void setSaveFirstLaunch(boolean z) {
        this.editor.putBoolean(IS_FIRST_TIME_LAUNCH_SAVED, z);
        this.editor.commit();
    }

    public void setServiceStatus(boolean z) {
        this.editor.putBoolean(SERVICE_STATUS, z);
        this.editor.commit();
    }

    public void setUpdateSetting(int i) {
        this.editor.putInt(UPDATE_SETTING, i);
        this.editor.commit();
    }

    public void setWelcomeScreenShow(boolean z) {
        this.editor.putBoolean(WELCOME_SCREEN_SHOW, z);
        this.editor.commit();
    }



    public void setTooltipWhatsapp(boolean z) {

        this.editor.putBoolean(pref_tooltip_whatsapp, z);
        this.editor.commit();
    }

    public boolean getTooltipWhatsapp() {
        return sharedPreferences.getBoolean(pref_tooltip_whatsapp, false);
    }


    public void putString(String key, String val) {
        sharedPreferences.edit().putString(key, val).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putInt(String key, Integer val) {
        sharedPreferences.edit().putInt(key, val).apply();
    }

    public void putBoolean(String key, Boolean val) {
        sharedPreferences.edit().putBoolean(key, val).apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void clearSharePrefs() {
        sharedPreferences.edit().clear().apply();
    }
}
