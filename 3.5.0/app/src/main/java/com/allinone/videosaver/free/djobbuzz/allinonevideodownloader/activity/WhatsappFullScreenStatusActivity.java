package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager.widget.ViewPager.PageTransformer;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DashboardFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.FragmentStatusVideoFull;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.FragmentInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.AsyncTaskCoroutine;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import cn.jzvid.JZVideoPlayer;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.AdapterWhatsAppStatusHorizontal;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.ModelWhatsAppStatus;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.RotateDownTransformer;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;
import videodownload.com.newmusically.R;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DashboardFragment.formoj;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.dirformoj;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.dirforwhatsapp;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.dirforwbussines;

public class WhatsappFullScreenStatusActivity extends AppCompatActivity {
    private static final String TAG = WhatsappFullScreenStatusActivity.class.getSimpleName();
    private ArrayList<ModelWhatsAppStatus> arrWhatsappStatus = new ArrayList();
    private ViewPager pagerFullScreenStatus;
    private int pos = 0;
    public String what = "";
    private int selectedValue = 0;


    private final class TransformerItem {
        final String name;
        final Class<? extends PageTransformer> aClass;

        public TransformerItem(Class<? extends PageTransformer> cls) {
            this.aClass = cls;
            this.name = cls.getSimpleName();
        }

        public String toString() {
            return this.name;
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {

            return FragmentStatusVideoFull.newInstance(selectedValue, (ModelWhatsAppStatus) arrWhatsappStatus.get(i));
        }

        public int getCount() {
            return arrWhatsappStatus.size();
        }
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();

    }

    public void onResume() {
        super.onResume();

    }

    public void onDestroy() {

        super.onDestroy();
    }


    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
    }


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handledarkmode();
        setContentView((int) R.layout.activity_whatsapp_full_screen_status);

        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.selectedValue = bundle.getInt("selectedValue", 0);
            this.pos = bundle.getInt("pos", 0);
            this.what = getIntent().getStringExtra("what");

        }
        new AsyncTaskCoroutine<Object, Object>() {
            @Override
            public Object doInBackground(Object... params) {
                getStatusFromWhastapp();
                return null;
            }

            @Override
            public void onPostExecute(Object result) {
                super.onPostExecute(result);
                init();
            }
        }.execute();

    }


    private void init() {

        this.pagerFullScreenStatus = (ViewPager) findViewById(R.id.pagerFullScreenStatus);

        final PagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        this.pagerFullScreenStatus.setOffscreenPageLimit(7);
        this.pagerFullScreenStatus.setAdapter(myPagerAdapter);
        this.pagerFullScreenStatus.setCurrentItem(this.pos);
        try {
            this.pagerFullScreenStatus.setPageTransformer(true, (PageTransformer) new TransformerItem(RotateDownTransformer.class).aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.pagerFullScreenStatus.post(new Runnable() {
            public void run() {
                Fragment fragment = (Fragment) myPagerAdapter.instantiateItem(pagerFullScreenStatus, pagerFullScreenStatus.getCurrentItem());
                if (fragment != null && (fragment instanceof FragmentInterface)) {
                    ((FragmentInterface) fragment).fragmentBecameVisible();
                }
            }
        });
        this.pagerFullScreenStatus.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                Fragment fragment = (Fragment) myPagerAdapter.instantiateItem(pagerFullScreenStatus, i);
                if (fragment != null && (fragment instanceof FragmentInterface)) {
                    ((FragmentInterface) fragment).fragmentBecameVisible();
                }
            }
        });
    }

    private void getStatusFromWhastapp() {
        File file;
        if (this.selectedValue == 0) {

            String path = "";
            if (what.equals("whatsapp")) {
                path = dirforwhatsapp;
            } else if (what.equals("whatsappb")) {
                path = dirforwbussines;
            } else if (what.equals(DashboardFragment.formoj)) {
                path = dirformoj;
            }
            file = new File(path);


        } else {
            String path = "";
            path = Utility.STORAGEDIR;// + what;
            file = new File(path);
        }
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                Arrays.sort(listFiles, new Comparator() {
                    public int compare(Object obj, Object obj2) {
                        File file = (File) obj;
                        File file2 = (File) obj2;
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });

                this.arrWhatsappStatus.clear();

                for (File file3 : listFiles) {
                    if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {

                        if ((what.equals("whatsapp") || what.equals("whatsappb") || what.equals(formoj)) && selectedValue == 0) {
                            ModelWhatsAppStatus modelWhatsAppStatus2 = new ModelWhatsAppStatus(AdapterWhatsAppStatusHorizontal.itemStatus);
                            modelWhatsAppStatus2.setFilePath(file3.getAbsolutePath());
                            modelWhatsAppStatus2.setItemPos(this.arrWhatsappStatus.size());
                            this.arrWhatsappStatus.add(modelWhatsAppStatus2);
                        } else {
                            if (file3.getName().contains(what)) {
                                ModelWhatsAppStatus modelWhatsAppStatus2 = new ModelWhatsAppStatus(AdapterWhatsAppStatusHorizontal.itemStatus);
                                modelWhatsAppStatus2.setFilePath(file3.getAbsolutePath());
                                modelWhatsAppStatus2.setItemPos(this.arrWhatsappStatus.size());
                                this.arrWhatsappStatus.add(modelWhatsAppStatus2);
                            }

                        }

                    }

                }
            }
        }
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

    }
}
