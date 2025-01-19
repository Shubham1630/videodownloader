package cn.jzvid;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Locale;

public class JZUtils {
    public static final String TAG = "JiaoZiVideoPlayer";

    public static String stringForTime(long timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = timeMs / 1000;
        int seconds = (int) (totalSeconds % 60);
        int minutes = (int) ((totalSeconds / 60) % 60);
        int hours = (int) (totalSeconds / 3600);
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }



    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }


    public static boolean isMobileDataConnected(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        boolean z = false;
        if (telephonyManager == null) {
            return false;
        }
        if (telephonyManager.getSimState() == 5) {
            if (VERSION.SDK_INT < 17) {
                if (Secure.getInt(context.getContentResolver(), "mobile_data", 1) == 1) {
                }
            }
            z = true;
        }
        return z;
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static AppCompatActivity getAppCompActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        }
        if (context instanceof ContextThemeWrapper) {
            return getAppCompActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    public static void setRequestedOrientation(Context context, int i) {
        if (getAppCompActivity(context) != null) {
            getAppCompActivity(context).setRequestedOrientation(i);
        } else {
            scanForActivity(context).setRequestedOrientation(i);
        }
    }

    public static Window getWindow(Context context) {
        if (getAppCompActivity(context) != null) {
            return getAppCompActivity(context).getWindow();
        }
        return scanForActivity(context).getWindow();
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void saveProgress(Context context, Object url, long progress) {
        if (!JZVideoPlayer.SAVE_PROGRESS) return;
        Log.i(TAG, "saveProgress: " + progress);
        if (progress < 5000) {
            progress = 0;
        }
        SharedPreferences spn = context.getSharedPreferences("JZVD_PROGRESS",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spn.edit();
        editor.putLong("newVersion:" + url.toString(), progress).apply();
    }


    public static long getSavedProgress(Context context, Object url) {
        if (!JZVideoPlayer.SAVE_PROGRESS) return 0;
        SharedPreferences spn = context.getSharedPreferences("JZVD_PROGRESS",
                Context.MODE_PRIVATE);
        return spn.getLong("newVersion:" + url.toString(), 0);
    }


    public static void clearSavedProgress(Context context, Object url) {
        if (url == null) {
            SharedPreferences spn = context.getSharedPreferences("JZVD_PROGRESS",
                    Context.MODE_PRIVATE);
            spn.edit().clear().apply();
        } else {
            SharedPreferences spn = context.getSharedPreferences("JZVD_PROGRESS",
                    Context.MODE_PRIVATE);
            spn.edit().putLong("newVersion:" + url.toString(), 0).apply();
        }
    }

    public static Object getCurrentFromDataSource(Object[] objArr, int i) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) objArr[0];
        return (linkedHashMap == null || linkedHashMap.size() <= 0) ? null : getValueFromLinkedMap(linkedHashMap, i);
    }

    public static Object getValueFromLinkedMap(LinkedHashMap<String, Object> linkedHashMap, int i) {
        int i2 = 0;
        for (String str : linkedHashMap.keySet()) {
            if (i2 == i) {
                return linkedHashMap.get(str);
            }
            i2++;
        }
        return null;
    }

    public static boolean dataSourceObjectsContainsUri(Object[] objArr, Object obj) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) objArr[0];
        if (linkedHashMap == null || obj == null) {
            return false;
        }
        return linkedHashMap.containsValue(obj);
    }

    public static String getKeyFromDataSource(Object[] dataSourceObjects, int index) {
        LinkedHashMap<String, Object> map = (LinkedHashMap) dataSourceObjects[0];
        int currentIndex = 0;
        for (String key : map.keySet()) {
            if (currentIndex == index) {
                return key;
            }
            currentIndex++;
        }
        return null;
    }

}
