package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.VideoPageAdapter;

import videodownload.com.newmusically.R;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.sharetext;

public class PlayvideosActivity extends AppCompatActivity {
    private static final String TAG = "PlayvideosActivity";
    private ArrayList<String> arrVideo = new ArrayList();
    private boolean isFullScreen = false;
    ImageView btnshare;
    ViewPager pager;
    int items = 0;
    String filename = "";
    String videoPath = "videoPath";

    private boolean isPackageInstalled(String str) {
        try {
            getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    public void fullScreenvideo() {
        LayoutParams layoutParams;
        if (this.isFullScreen) {
            layoutParams = (LayoutParams) this.pager.getLayoutParams();
            getWindow().getDecorView().setSystemUiVisibility(0);
            layoutParams.width = -2;
            layoutParams.height = 0;
            layoutParams.setMargins(0, 0, 0, 60);
            this.pager.setLayoutParams(layoutParams);
            this.isFullScreen = false;
            return;
        }
        layoutParams = (LayoutParams) this.pager.getLayoutParams();
        getWindow().getDecorView().setSystemUiVisibility(4);
        layoutParams.width = -2;
        layoutParams.height = -1;
        layoutParams.leftMargin = 0;
        layoutParams.setMargins(0, 0, 0, 0);
        this.pager.setLayoutParams(layoutParams);
        this.isFullScreen = true;
    }

    public void getFile(int i) {
        if (this.arrVideo.get(i).contains("http")) {
            Log.e(TAG, "getFile: " + arrVideo.get(i));
            btnshare.setVisibility(View.GONE);

        } else {
            File file = new File((String) this.arrVideo.get(i));
            if (file != null) {
                this.filename = file.getName();
                if (!TextUtils.isEmpty(this.filename) && this.filename.contains("_")) {
                    CharSequence substring = this.filename.substring(0, this.filename.indexOf("_") - 1);
                    getSupportActionBar().setTitle(substring);
                }
            }
        }

    }

    public void onBackPressed() {
        if (this.isFullScreen) {
            LayoutParams layoutParams = (LayoutParams) this.pager.getLayoutParams();
            getWindow().getDecorView().setSystemUiVisibility(0);
            layoutParams.width = -2;
            layoutParams.height = 0;
            layoutParams.setMargins(0, 0, 0, 60);
            this.pager.setLayoutParams(layoutParams);
            this.isFullScreen = false;
            return;
        }
        super.onBackPressed();
    }




    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handledarkmode();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.items = bundle.getInt("item");
            this.arrVideo = new ArrayList((Collection) bundle.get(this.videoPath));
//            Log.e("arrpath : ", String.valueOf(this.arrVideo.get(this.items)));
            setContentView((int) R.layout.play_download_video);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);
            toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.setTitleTextColor));
            RelativeLayout mAdView = findViewById(R.id.adView);



            this.btnshare = (ImageView) findViewById(R.id.btnshare);
            this.pager = (ViewPager) findViewById(R.id.pager);
            this.pager.setAdapter(new VideoPageAdapter(getSupportFragmentManager(), this.arrVideo));
            getFile(this.items);
            this.btnshare.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    File file = new File((String) arrVideo.get(items));
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("video/mp4");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));

                    intent.putExtra("android.intent.extra.TEXT", sharetext);
                    startActivity(Intent.createChooser(intent, "send"));
                }
            });
            this.pager.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageScrollStateChanged(int i) {
                }

                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    getFile(i);
                }
            });
            this.pager.setCurrentItem(this.items);
            if (arrVideo.get(pager.getCurrentItem()).contains("http")) {
                btnshare.setVisibility(View.GONE);
            }
            return;
        }
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icon_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        if (menuItem.getItemId() == R.id.musicicon) {
            if (isPackageInstalled("in.mohalla.sharechat")) {
                startActivity(getPackageManager().getLaunchIntentForPackage("in.mohalla.sharechat"));
            } else {
                Toast makeText = makeText(this, "App not installed", LENGTH_SHORT);
                makeText.setGravity(17, 0, 0);
                makeText.show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }



    private void loadAdbmobbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        final AdView mAdView = new AdView(this);
        mAdView.setAdSize(SMART_BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_ad_units_id1));
        mAdView.loadAd(new AdRequest.Builder().build());

        mAdView.setAdListener(new AdListener() {
            public void onAdClosed() {

                Log.e("Adbmobbanner closed", "onAdClosed");
            }

            public void onAdFailedToLoad(LoadAdError i) {
                topBannerView.setVisibility(View.GONE);

                Log.e("Adbmobbanner failed", String.valueOf(i));
            }

            public void onAdLeftApplication() {
                Log.e("left", "onAdLeftApplication");
            }

            public void onAdLoaded() {
                topBannerView.removeAllViews();
                topBannerView.setVisibility(View.VISIBLE);
                topBannerView.addView(mAdView);
                Log.e("onAdLoaded", "onAdLoaded");
            }

            public void onAdOpened() {
                Log.e("opened", "onAdOpened");
            }
        });

    }


    private void loadfbbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(this, getString(R.string.fbbanner), AdSize.BANNER_HEIGHT_50);
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                topBannerView.setVisibility(View.GONE);
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {

                topBannerView.removeAllViews();
                topBannerView.setVisibility(View.VISIBLE);
                topBannerView.addView(adView);
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());

    }

    private void handledarkmode() {
        String[] darkModeValues = getResources().getStringArray(R.array.dark_mode_values);
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(getString(R.string.dark_mode), getString(R.string.dark_mode_def_value));
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
