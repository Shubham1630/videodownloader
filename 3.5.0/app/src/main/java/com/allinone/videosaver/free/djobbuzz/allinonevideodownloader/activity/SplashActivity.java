package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.LocaleManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Methods;

import videodownload.com.newmusically.R;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    TextView txtHeaderSplash, txtSubSplash;
    ImageView imgNewLogo;
    ImageView imgBorderLogo;
    Methods methods;
    SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        LocaleManager.setLocale(mContext);
        String[] darkModeValues = getResources().getStringArray(R.array.dark_mode_values);
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(getString(R.string.dark_mode),  getString(R.string.dark_mode_def_value));
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

        setContentView(R.layout.activity_splashscren);
        methods = new Methods(mContext);
        txtSubSplash = findViewById(R.id.txtSubSplash);
        txtHeaderSplash = findViewById(R.id.txtHeaderSplash);
        this.imgBorderLogo = (ImageView) findViewById(R.id.imgBorderLogo);
        this.imgNewLogo = (ImageView) findViewById(R.id.imgNewLogo);
        hideStatusBar();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.imgBorderLogo, View.ALPHA, 0.0f, 1.0f);
        ofFloat.setDuration(500);
        ofFloat.start();
        this.imgBorderLogo.setVisibility(View.VISIBLE);
        setMainLogoFadeIn(10);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mContext = this;
        LocaleManager.setLocale(mContext);
    }

    private void setMainLogoFadeIn(int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.imgNewLogo, View.ALPHA, 0.0f, 1.0f);
        ofFloat.setDuration(500);
        ofFloat.setStartDelay((long) i);
        ofFloat.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handledelay();
            }
        }, (long) (i + 10));
    }

    public void handledelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handledelayfornewlogo();
            }
        }, 500);
    }

    public void handledelayfornewlogo() {
        startTextAnimation();
    }

    private void startTextAnimation() {
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("translationY", 300.0f, 0.0f);
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.ALPHA, 0.0f, 1.0f);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.txtHeaderSplash, ofFloat, ofFloat2);
        ofPropertyValuesHolder.setDuration(500);
        this.txtHeaderSplash.setVisibility(View.VISIBLE);
        ofPropertyValuesHolder.start();
        new Handler().postDelayed(() -> {
            handleanimation(ofFloat, ofFloat2);
        }, 200);
    }

    private void handleanimation(PropertyValuesHolder ofFloat, PropertyValuesHolder ofFloat2) {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(txtSubSplash, ofFloat, ofFloat2);
        ofPropertyValuesHolder.setDuration(500);
        txtSubSplash.setVisibility(View.VISIBLE);
        ofPropertyValuesHolder.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, 500);

    }


    public void init() {
        opennextactivity();
    }

//
    void opennextactivity() {
        Intent intent;
        intent = new Intent(SplashActivity.this, NewDashboardActivity.class);
        overridePendingTransition(R.anim.start_fullscreen, R.anim.tooltip_enter);
        startActivity(intent);
        finish();


    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0) {
            Log.e("result", "Permission: " + permissions[0] + "was " + grantResults[0]);
            if (requestCode == 1) {
                opennextactivity();
            }
        }
    }


    void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


}
