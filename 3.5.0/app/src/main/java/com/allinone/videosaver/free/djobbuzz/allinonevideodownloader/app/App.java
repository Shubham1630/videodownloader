package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.app;

import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.util.Log;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adservices.AppOpenManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.LocaleManager;
import com.android.volley.RequestQueue;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;
import com.gw.swipeback.tools.WxSwipeBackActivityManager;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;

import videodownload.com.newmusically.BuildConfig;

public class App extends Application {
    public static final String TAG = "Api : ";
    private static App _instance;
    private RequestQueue mRequestQueue;
    private PrefManager prefManager;
    private  AppOpenManager appOpenManager;

    public App() {
        _instance = this;
    }

    public static Context getContext() {
        return _instance;
    }

    public static synchronized App getInstance() {
        App videoDownloadApplication;
        synchronized (App.class) {
            videoDownloadApplication = _instance;
        }
        return videoDownloadApplication;
    }



    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
//        MobileAds.initialize(
//                this,
//                new OnInitializationCompleteListener() {
//                    @Override
//                    public void onInitializationComplete(InitializationStatus initializationStatus) {}
//                });
        appOpenManager = new AppOpenManager(this);
        this.prefManager = new PrefManager(this);
        FirebaseApp.initializeApp(this);
        setlanguage();
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler( ) {


            public void uncaughtException(Thread thread, Throwable th) {
                if (thread.getName().startsWith("AdWorker")) {
                    Log.w("ADMOB", "AdWorker thread thrown an exception.", th);
                } else if (defaultUncaughtExceptionHandler != null) {
                    defaultUncaughtExceptionHandler.uncaughtException(thread, th);
                } else {
                    throw new RuntimeException("No default uncaught exception handler.", th);
                }
            }
        });
        WxSwipeBackActivityManager.getInstance().init(this);


        this.prefManager.setAppCount(this.prefManager.getAppCount() + 1);
        if (VERSION.SDK_INT >= 18) {
            Builder builder = new Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    private void setlanguage() {
        LocaleManager.setLocale(getApplicationContext());
    }

}
