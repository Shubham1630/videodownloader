package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.VideoPlayFragment;

import java.util.ArrayList;


public class VideoPageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> arrVideo = new ArrayList();

    public VideoPageAdapter(FragmentManager fragmentManager, ArrayList<String> arrayList) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.arrVideo = arrayList;
    }

    public int getCount() {
        return this.arrVideo.size();
    }

    public Fragment getItem(int i) {
        return  VideoPlayFragment.createInstance((String) this.arrVideo.get(i));
    }
}
