package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.NewDashboardActivity;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.List;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import videodownload.com.newmusically.R;

import static com.google.android.gms.ads.AdSize.SMART_BANNER;


public class TiktokvideoFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SavedvideoFragment";
    private ConstraintLayout clWhatsappTip;
    private ImageView imgCloseTip;
    private MenuItem menuDelete;
    private TabLayout tabWaStatus;
    private View viewWhatsappTip;
    private ViewPager viewpager;
    private OtherStatusFragment whatsappNewStatusFragment;

    private String mParam1;
    private String mParam2;
    private String what = "";
    private Context context;
    private NewDashboardActivity activity;
    private PrefManager prefManager;


    public TiktokvideoFragment() {
        // Required empty public constructor
    }


    public static TiktokvideoFragment newInstance(String param1, String param2) {
        TiktokvideoFragment fragment = new TiktokvideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            what = getArguments().getString("what");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {        // Inflate the layout for this fragment

        activity = (NewDashboardActivity) getActivity();
        context = (NewDashboardActivity) getContext();
        View view = inflater.inflate(R.layout.fragment_savedvideo, container, false);
        this.whatsappNewStatusFragment = new OtherStatusFragment().newInstance(1, what);
        bindview(view);
        if (prefManager.getTooltipWhatsapp()) {
            this.viewWhatsappTip.setVisibility(View.GONE);
            this.clWhatsappTip.setVisibility(View.GONE);
        } else {
            ViewTreeObserver viewTreeObserver = this.clWhatsappTip.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                    }
                });
            }
        }
        this.imgCloseTip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clWhatsappTip.setVisibility(View.GONE);
                viewWhatsappTip.setVisibility(View.GONE);
                prefManager.setTooltipWhatsapp(true);
            }
        });
        return view;
    }

    private void bindview(View v) {
        prefManager = new PrefManager(context);
        this.viewpager = (ViewPager) v.findViewById(R.id.viewpager);
        this.imgCloseTip = (ImageView) v.findViewById(R.id.imgCloseTip);
        this.clWhatsappTip = (ConstraintLayout) v.findViewById(R.id.clWhatsappTip);
        this.tabWaStatus = (TabLayout) v.findViewById(R.id.tabWaStatus);
        tabWaStatus.setTabMode(TabLayout.MODE_SCROLLABLE);
        this.viewWhatsappTip = v.findViewById(R.id.viewWhatsappTip);
        RelativeLayout adView = v.findViewById(R.id.adView);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        for (String s : DashboardFragment.FORLIST) {
            viewPagerAdapter.addFragment(new OtherStatusFragment().newInstance(1, s), "");
        }

        this.viewpager.setAdapter(viewPagerAdapter);
        this.tabWaStatus.setupWithViewPager(this.viewpager);
        tabWaStatus.setTabTextColors(getResources().getColor(R.color.tabSelectedIconColor), getResources().getColor(R.color.tabUnselectedIconColor));
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            if (i == 0) {
                int color = ContextCompat.getColor(context, R.color.tabSelectedIconColor);
                TextView view = (TextView) ((LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_tab_heading, null)).findViewById(R.id.tabContent);
                view.setText(DashboardFragment.APPLIST[i]);
                view.setTextColor(color);
                Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_saved_status_tab_selected).mutate();
                icon.setColorFilter(getResources().getColor(R.color.tabSelectedIconColor), PorterDuff.Mode.SRC_ATOP);
                view.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                this.tabWaStatus.getTabAt(i).setCustomView(view);
            } else {
                TextView view = (TextView) ((LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_tab_heading, null)).findViewById(R.id.tabContent);
                view.setText(DashboardFragment.APPLIST[i]);
                view.setTextColor(ContextCompat.getColor(context, R.color.tabUnselectedIconColor));
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saved_status_tab, 0, 0, 0);
                this.tabWaStatus.getTabAt(i).setCustomView(view);
            }

        }


        this.tabWaStatus.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewpager) {
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabContent);
                textView.setTextColor(ContextCompat.getColor(context, R.color.tabSelectedIconColor));
                Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_saved_status_tab_selected).mutate();
                icon.setColorFilter(getResources().getColor(R.color.tabSelectedIconColor), PorterDuff.Mode.SRC_ATOP);

                textView.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabContent);
                textView.setTextColor(ContextCompat.getColor(context, R.color.tabUnselectedIconColor));
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saved_status_tab, 0, 0, 0);
            }

            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return (Fragment) this.mFragmentList.get(i);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String str) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(str);
        }

        public CharSequence getPageTitle(int i) {
            return (CharSequence) this.mFragmentTitleList.get(i);
        }
    }




    private void loadAdbmobbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        final AdView mAdView = new AdView(getContext());
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
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(getContext(), getContext().getString(R.string.fbbanner), AdSize.BANNER_HEIGHT_50);
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
}