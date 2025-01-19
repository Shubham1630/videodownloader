package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta;


import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.handledarkmode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.BaseActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.LocaleManager;

import videodownload.com.newmusically.R;

public class LoginActivity extends BaseActivity {
    LoginActivity activity;
    private TextView opentiktok;
    private TextView txtLabel;
    private Toolbar mToolbar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handledarkmode(this);
        activity = this;
        LocaleManager.setLocale(activity);
        setContentView(R.layout.fragment_story);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24));

        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //What to do on back clicked
            }
        });

        opentiktok = findViewById(R.id.opentiktok);
        txtLabel = findViewById(R.id.txtLabel);
        txtLabel.setText(getResources().getString(R.string.instagram_private_videos));
        opentiktok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this.activity, LoginWebviewActivity.class).putExtra("isSearch", getIntent().getBooleanExtra("isSearch", false)));
                finish();
            }
        });
        /*
        this.activity = this;
        findViewById(R.id.tvLogin).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this.activity, LoginWebviewActivity.class));
            }
        });*/

    }


    public void onBackPressed() {
        finish();

    }
}
