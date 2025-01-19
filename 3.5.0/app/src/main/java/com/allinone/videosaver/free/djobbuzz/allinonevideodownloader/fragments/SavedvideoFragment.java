package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.NewDashboardActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.app.App;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;


public class SavedvideoFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SavedvideoFragment";
    private ConstraintLayout clWhatsappTip;
    private ConstraintLayout clWhatsappTipnew;
    private TextView opentiktok;
    private ImageView imgCloseTip;
    private MenuItem menuDelete;
    private TabLayout tabWaStatus;
    private View viewWhatsappTip;
    private ViewPager viewpager;
    private WhatsappNewStatusFragment whatsappNewStatusFragment;

    private String mParam1;
    private String mParam2;
    private String what = "";
    private Context context;
    NewDashboardActivity activity;
    private PrefManager prefManager;
    int hintIdx = 0;


    public final String[] strHint = {App.getInstance().getResources().getString(R.string.mojins1), App.getInstance().getResources().getString(R.string.mojins2),
            App.getInstance().getResources().getString(R.string.mojins3),App.getInstance().getResources().getString(R.string.mojins4)};
    public static final int[] bitRes = {R.drawable.hint1, R.drawable.hint2, R.drawable.hint3, R.drawable.hint4};

    public SavedvideoFragment() {
        // Required empty public constructor
    }


    public static SavedvideoFragment newInstance(String param1, String param2) {
        SavedvideoFragment fragment = new SavedvideoFragment();
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
            Log.e(TAG, "onCreateView: bundlefor_wa:" + getArguments().getString("what"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (NewDashboardActivity) getActivity();
        context = (NewDashboardActivity) getContext();
        View view = inflater.inflate(R.layout.fragment_savedvideo, container, false);

        this.whatsappNewStatusFragment = new WhatsappNewStatusFragment().newInstance(1, what);
        bindview(view);
        prefManager.setTooltipWhatsapp(true);
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

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: called");
    }

    private void bindview(View v) {
        prefManager = new PrefManager(context);
        this.viewpager = (ViewPager) v.findViewById(R.id.viewpager);
        this.imgCloseTip = (ImageView) v.findViewById(R.id.imgCloseTip);
        this.clWhatsappTip = (ConstraintLayout) v.findViewById(R.id.clWhatsappTip);
        this.clWhatsappTipnew = (ConstraintLayout) v.findViewById(R.id.clWhatsappTipnew);
        this.tabWaStatus = (TabLayout) v.findViewById(R.id.tabWaStatus);
        this.viewWhatsappTip = v.findViewById(R.id.viewWhatsappTip);
        this.opentiktok = v.findViewById(R.id.opentiktok);
        RelativeLayout adView = v.findViewById(R.id.adView);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        if (what.equals("whatsapp") || what.equals("whatsappb") || what.equals(DashboardFragment.formoj)) {
            viewPagerAdapter.addFragment(new WhatsappNewStatusFragment().newInstance(0, what), "Status");
        }
        if (what.equals(DashboardFragment.formoj)) {
            clWhatsappTipnew.setVisibility(View.VISIBLE);
        } else {
            clWhatsappTipnew.setVisibility(View.GONE);
        }
        viewPagerAdapter.addFragment(this.whatsappNewStatusFragment, "Saved");
        this.viewpager.setAdapter(viewPagerAdapter);
        this.tabWaStatus.setupWithViewPager(this.viewpager);
        tabWaStatus.setTabTextColors(getResources().getColor(R.color.tabSelectedIconColor), getResources().getColor(R.color.tabUnselectedIconColor));
        int color = ContextCompat.getColor(context, R.color.tabSelectedIconColor);
        TextView view = (TextView) ((LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_tab_heading, null)).findViewById(R.id.tabContent);
        view.setText("Status");
        view.setTextColor(color);

        Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_status_tab).mutate();
        icon.setColorFilter( getResources().getColor(R.color.tabSelectedIconColor), PorterDuff.Mode.SRC_ATOP);


        view.setCompoundDrawablesWithIntrinsicBounds(icon,null,null,null);

        this.tabWaStatus.getTabAt(0).setCustomView(view);
        TextView view2 = (TextView) ((LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_tab_heading, null)).findViewById(R.id.tabContent);
        view2.setText("Saved");
        view2.setTextColor(ContextCompat.getColor(context, R.color.tabUnselectedIconColor));
        view2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saved_status_tab, 0, 0, 0);
        this.tabWaStatus.getTabAt(1).setCustomView(view2);
        this.tabWaStatus.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewpager) {
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabContent);
                textView.setTextColor(ContextCompat.getColor(context, R.color.tabSelectedIconColor));
                if (tab.getPosition() == 0) {


                    Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_status_tab).mutate();
                    icon.setColorFilter( getResources().getColor(R.color.tabSelectedIconColor), PorterDuff.Mode.SRC_ATOP);

                    textView.setCompoundDrawablesWithIntrinsicBounds(icon,null,null,null);


                   // textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_status_tab, 0, 0, 0);
                } else {

                    Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_saved_status_tab_selected).mutate();
                    icon.setColorFilter( getResources().getColor(R.color.tabSelectedIconColor), PorterDuff.Mode.SRC_ATOP);


                    textView.setCompoundDrawablesWithIntrinsicBounds(icon,null,null,null);
                  //  textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saved_status_tab_selected, 0, 0, 0);
                }
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabContent);
                textView.setTextColor(ContextCompat.getColor(context, R.color.tabUnselectedIconColor));
                if (tab.getPosition() == 0) {
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_status_tab_unselect, 0, 0, 0);
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saved_status_tab, 0, 0, 0);
                }
            }

            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
        opentiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHowToUsedDialog();
            }
        });
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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

    Dialog howtouse;

    private void showHowToUsedDialog() {

        howtouse = new Dialog(activity);
        final View decorView = howtouse
                .getWindow()
                .getDecorView();

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
        scaleDown.setDuration(500);
        scaleDown.start();

        howtouse.setContentView(R.layout.dialog_howtoused);
        final ImageView id_iv_introimg = howtouse.findViewById(R.id.id_iv_introimg);
        final ImageView id_iv_introprev = howtouse.findViewById(R.id.id_iv_introprev);
        final ImageView id_iv_intronext = howtouse.findViewById(R.id.id_iv_intronext);
        final ImageView id_iv_introfinish = howtouse.findViewById(R.id.id_iv_introfinish);
        final TextView id_txt_introtext = howtouse.findViewById(R.id.id_txt_introtext);

        hintIdx = 0;
        Glide.with(context).load(bitRes[hintIdx]).apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_placeholder)).into(id_iv_introimg);

       // id_iv_introimg.setImageResource(bitRes[hintIdx]);
        id_txt_introtext.setText(strHint[hintIdx]);
        id_iv_introprev.setVisibility(View.INVISIBLE);
        howtouse.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
        howtouse.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(howtouse.getWindow().getAttributes());
        layoutParams.width = MATCH_PARENT;
        layoutParams.height = WRAP_CONTENT;
        howtouse.getWindow().setAttributes(layoutParams);
        id_iv_intronext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintIdx++;
                if (hintIdx >= bitRes.length) {
                    hintIdx--;
                    return;
                }
                Glide.with(context).load(bitRes[hintIdx]).apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_placeholder)).into(id_iv_introimg);

               // id_iv_introimg.setImageResource(bitRes[hintIdx]);
                id_txt_introtext.setText(strHint[hintIdx]);
                if (hintIdx >= bitRes.length - 1) {
                    id_iv_intronext.setVisibility(View.INVISIBLE);
                    id_iv_introfinish.setVisibility(View.VISIBLE);
                } else {
                    id_iv_intronext.setVisibility(View.VISIBLE);
                    id_iv_introfinish.setVisibility(View.INVISIBLE);
                }
                if (hintIdx > 0)
                    id_iv_introprev.setVisibility(View.VISIBLE);
                else
                    id_iv_introprev.setVisibility(View.INVISIBLE);

            }
        });

        id_iv_introfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                howtouse.dismiss();

            }
        });

        id_iv_introprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_iv_introfinish.setVisibility(View.GONE);
                hintIdx--;
                if (hintIdx < 0) {
                    hintIdx = 0;
                    return;
                }
                Glide.with(context).load(bitRes[hintIdx]).apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_placeholder)).into(id_iv_introimg);

                //id_iv_introimg.setImageResource(bitRes[hintIdx]);
                id_txt_introtext.setText(strHint[hintIdx]);
                if (hintIdx >= bitRes.length - 1)
                    id_iv_intronext.setVisibility(View.GONE);
                else
                    id_iv_intronext.setVisibility(View.VISIBLE);
                if (hintIdx > 0)
                    id_iv_introprev.setVisibility(View.VISIBLE);
                else
                    id_iv_introprev.setVisibility(View.GONE);
            }
        });

        howtouse.show();

    }




    private void loadAdbmobbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        final AdView mAdView = new AdView(getContext());
        mAdView.setAdSize(SMART_BANNER);
        mAdView.setAdUnitId( getString(R.string.banner_ad_units_id1));
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