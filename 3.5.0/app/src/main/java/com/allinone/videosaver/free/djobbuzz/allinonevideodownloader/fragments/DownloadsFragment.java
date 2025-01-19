package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.NewDashboardActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import videodownload.com.newmusically.R;


public class DownloadsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "DownloadsFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    SmartTabLayout viewpagertab;
    private Context context;
    private NewDashboardActivity activity;
    private boolean isinitiated = false;

    public DownloadsFragment() {
        // Required empty public constructor
    }


    public static DownloadsFragment newInstance(String param1, String param2) {
        DownloadsFragment fragment = new DownloadsFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_downloads, container, false);
        context = (NewDashboardActivity) getContext();
        activity = (NewDashboardActivity) getActivity();
        bindView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: called");
        if (!isinitiated) {
            isinitiated = true;
            handlefragmnets();
        }
    }

    private void handlefragmnets() {
        FragmentPagerItems fragmentPagerItems = new FragmentPagerItems(getContext());
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            Bundle bundlefor_wa = new Bundle();
            bundlefor_wa.putString("what", "whatsapp");
            fragmentPagerItems.add(FragmentPagerItem.of("Whatsapp", 1.0f, SavedvideoFragment.class, bundlefor_wa));
            if (DashboardFragment.isPackageInstalled(DashboardFragment.PKG_WAB, activity)) {
                Bundle bundlefor_wab = new Bundle();
                bundlefor_wab.putString("what", "whatsappb");
                fragmentPagerItems.add(FragmentPagerItem.of("Whatsapp Bussiness", 1.0f, SavedvideoFragment.class, bundlefor_wab));
            }
        }



        Bundle bundlefor_moj = new Bundle();
        bundlefor_moj.putString("what", DashboardFragment.formoj);
        fragmentPagerItems.add(FragmentPagerItem.of("Moj Video", 1.0f, SavedvideoFragment.class, bundlefor_moj));
        Bundle bundlefor_tiktok = new Bundle();
        bundlefor_tiktok.putString("what", DashboardFragment.fortiktok);
        fragmentPagerItems.add(FragmentPagerItem.of("All", 1.0f, TiktokvideoFragment.class, bundlefor_tiktok));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), fragmentPagerItems);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_selected},
                        new int[]{}
                },
                new int[]{
                        getResources().getColor(R.color.colorPrimary),
                        getResources().getColor(R.color.colorPrimary),
                        activity.getResources().getColor(R.color.titletext)
                }
        );

        viewpagertab.setDefaultTabTextColor(myColorStateList);
        viewpagertab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewpagertab.setViewPager(viewPager);
        viewpagertab.setForegroundGravity(0);
        viewpagertab.setSelectedIndicatorColors(activity.getResources().getColor(R.color.titletext));
        viewpagertab.setVisibility(View.VISIBLE);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    default:
                        viewpagertab.setDefaultTabTextColor(getResources().getColor(R.color.colorPrimary));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });
    }

    private void bindView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewpagertab = view.findViewById(R.id.viewpagertab);//BooksDownloadsFragment
       // handlefragmnets();

    }
}