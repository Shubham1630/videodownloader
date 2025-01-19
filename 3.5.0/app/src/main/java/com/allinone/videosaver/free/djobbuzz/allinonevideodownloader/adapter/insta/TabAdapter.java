package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList();
    private final List<String> mFragmentTitleList = new ArrayList();

    public TabAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager, i);
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
