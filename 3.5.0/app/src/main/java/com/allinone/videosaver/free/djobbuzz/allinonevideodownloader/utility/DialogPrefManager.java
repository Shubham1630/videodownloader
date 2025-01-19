package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

class DialogPrefManager {
    private static final String KEY_APP_RATE_COUNT = "app_rate_count";
    private static final String KEY_APP_RATE_THRESHOLD = "app_rate_threshold";
    private static final String KEY_APP_VERSION_LAST_SHOWN = "force_update_dialog_key";
    private static final String KEY_DATE_COUNT_SHARE = "date_count_share";
    private static final String KEY_DATE_RATER_COUNT = "date_count_rater";
    private static final String KEY_IS_APP_RATER_CLICKED = "is_app_rater_shown";
    private static final String KEY_SHARE_DIALOG_DISPLAY_COUNT = "share_dialog_display_count";
    private static final String KEY_UPDATE_RESPONSE = "update_res";
    private static final String PREF_NAME = "hashone_dialog_pref";
    private static SharedPreferences pref;
    private Editor editor;

    DialogPrefManager(Context context) {
        if (pref == null) {
            pref = context.getSharedPreferences(PREF_NAME, 0);
        }
    }

    public int getAppRateCount() {
        return pref.getInt(KEY_APP_RATE_COUNT, 0);
    }

    public int getAppRateThreshold() {
        return pref.getInt(KEY_APP_RATE_THRESHOLD, 0);
    }

    public long getDateFirstLoadedApp() {
        return pref.getLong(KEY_DATE_RATER_COUNT, 0);
    }

    public long getDateFirstLoadedAppShare() {
        return pref.getLong(KEY_DATE_COUNT_SHARE, 0);
    }

    public int getNormalUpdateDialogShownVersionCode() {
        return pref.getInt(KEY_APP_VERSION_LAST_SHOWN, 0);
    }

    public int getShareDialogDisplayCount() {
        return pref.getInt(KEY_SHARE_DIALOG_DISPLAY_COUNT, 0);
    }

    public String getUpdateResponse() {
        return pref.getString(KEY_UPDATE_RESPONSE, "");
    }

    public boolean isAppRaterClicked() {
        return pref.getBoolean(KEY_IS_APP_RATER_CLICKED, false);
    }

    public void setAppRateCount(int i) {
        this.editor = pref.edit();
        this.editor.putInt(KEY_APP_RATE_COUNT, i);
        this.editor.commit();
    }

    public void setAppRateThreshold(int i) {
        this.editor = pref.edit();
        this.editor.putInt(KEY_APP_RATE_THRESHOLD, i);
        this.editor.commit();
    }

    public void setAppRaterClicked() {
        this.editor = pref.edit();
        this.editor.putBoolean(KEY_IS_APP_RATER_CLICKED, true);
        this.editor.commit();
    }

    public void setDateFirstLoadedApp(long j) {
        this.editor = pref.edit();
        this.editor.putLong(KEY_DATE_RATER_COUNT, j);
        this.editor.commit();
    }

    public void setDateFirstLoadedAppShare(long j) {
        this.editor = pref.edit();
        this.editor.putLong(KEY_DATE_COUNT_SHARE, j);
        this.editor.commit();
    }

    public void setNormalUpdateDialogShownVersionCode(int i) {
        this.editor = pref.edit();
        this.editor.putInt(KEY_APP_VERSION_LAST_SHOWN, i);
        this.editor.commit();
    }

    public void setShareDialogDisplayCount(int i) {
        this.editor = pref.edit();
        this.editor.putInt(KEY_SHARE_DIALOG_DISPLAY_COUNT, i);
        this.editor.commit();
    }

    public void setUpdateResponse(String str) {
        this.editor = pref.edit();
        this.editor.putString(KEY_UPDATE_RESPONSE, str);
        this.editor.commit();
    }
}
