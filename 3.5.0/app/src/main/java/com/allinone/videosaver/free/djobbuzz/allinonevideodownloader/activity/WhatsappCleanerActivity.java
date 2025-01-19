package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.TiktokvideoFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.cleaner.WacleanerFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;
import com.google.android.material.tabs.TabLayout;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import videodownload.com.newmusically.R;

public class WhatsappCleanerActivity extends BaseActivity {
    private WhatsappCleanerActivity activity;
    private TabLayout tabWaStatus;
    private ViewPager viewpager;

    private PrefManager prefManager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.handledarkmode(this);
        setContentView(R.layout.activity_whatsapp_cleaner);
        activity = this;
        prefManager = new PrefManager(activity);
        bindviews();
    }

    private void bindviews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //What to do on back clicked
            }
        });
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabWaStatus = (TabLayout) findViewById(R.id.tabWaStatus);
        tabWaStatus.setTabMode(TabLayout.MODE_FIXED);
        RelativeLayout adView = findViewById(R.id.adView);
        //loadbannerads(adView);
        TiktokvideoFragment.ViewPagerAdapter viewPagerAdapter = new TiktokvideoFragment.ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new WacleanerFragment().newInstance("wa", ""), "");
        viewPagerAdapter.addFragment(new WacleanerFragment().newInstance("wb", ""), "");
        viewpager.setAdapter(viewPagerAdapter);
        tabWaStatus.setupWithViewPager(this.viewpager);
        tabWaStatus.setTabTextColors(getResources().getColor(R.color.tabSelectedIconColor), getResources().getColor(R.color.tabUnselectedIconColor));
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            if (i == 0) {
                int color = ContextCompat.getColor(activity, R.color.tabSelectedIconColor);
                TextView view = (TextView) ((LinearLayout) LayoutInflater.from(activity).inflate(R.layout.custom_tab_heading, null)).findViewById(R.id.tabContent);
                view.setText("Whatsapp");
                view.setTextColor(color);
                view.setTypeface(null, Typeface.BOLD);

                this.tabWaStatus.getTabAt(i).setCustomView(view);
            } else {
                TextView view = (TextView) ((LinearLayout) LayoutInflater.from(activity).inflate(R.layout.custom_tab_heading, null)).findViewById(R.id.tabContent);
                view.setText("Whatsapp Business");
                view.setTextColor(ContextCompat.getColor(activity, R.color.tabUnselectedIconColor));
                view.setTypeface(null, Typeface.BOLD);
                this.tabWaStatus.getTabAt(i).setCustomView(view);
            }

        }


        this.tabWaStatus.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewpager) {
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabContent);
                textView.setTextColor(ContextCompat.getColor(activity, R.color.tabSelectedIconColor));
                textView.setTypeface(null, Typeface.BOLD);
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabContent);
                textView.setTextColor(ContextCompat.getColor(activity, R.color.tabUnselectedIconColor));
                textView.setTypeface(null, Typeface.BOLD);
            }

            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
    }
}