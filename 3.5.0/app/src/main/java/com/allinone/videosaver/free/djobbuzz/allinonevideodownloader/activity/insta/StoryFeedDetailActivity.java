package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.handledarkmode;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.BaseActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta.TabAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.insta.FeedFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.insta.IGTVFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.insta.StoryFragment;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import videodownload.com.newmusically.R;

public class StoryFeedDetailActivity extends BaseActivity {
    String IntentFullName;
    String IntentProfileUrl;
    String IntentUserName;
    String UserId = "";
    StoryFeedDetailActivity activity;


    ImageView im_back;
    TextView tvHeadName;
    TextView tvHeadUserName;
    CircleImageView imHeadImage;
    ViewPager viewpager;
    View RLHeadProfile;
    com.google.android.material.tabs.TabLayout tabs;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handledarkmode(this);
        setContentView(R.layout.activity_story_feed_detail);
        this.activity = this;
        bindMe();
        initViews();

    }

    private void bindMe() {
        im_back = findViewById(R.id.im_back);
        tvHeadName = findViewById(R.id.tvHeadNames);
        tvHeadUserName = findViewById(R.id.tvHeadUserNames);
        imHeadImage = findViewById(R.id.imHeadImages);
        viewpager = findViewById(R.id.viewpager);
        tabs = findViewById(R.id.tabs);
        RLHeadProfile = findViewById(R.id.RLHeadProfile);
        RLHeadProfile.setVisibility(View.GONE);
    }

    public void initViews() {
        this.UserId = getIntent().getStringExtra("UserId");
        this.IntentFullName = getIntent().getStringExtra("Name");
        this.IntentUserName = getIntent().getStringExtra("UserName");
        this.IntentProfileUrl = getIntent().getStringExtra("ProfileImage");
        im_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StoryFeedDetailActivity.this.onBackPressed();
            }
        });
        try {
            tvHeadName.setText(this.IntentFullName);
            tvHeadUserName.setText(this.IntentUserName);
            Glide.with(this.activity).load(this.IntentProfileUrl).into(imHeadImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupViewPager(viewpager);

        tabs.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter tabAdapter = new TabAdapter(this.activity.getSupportFragmentManager(), 1);
        tabAdapter.addFragment(FeedFragment.newInstance(this.UserId), this.activity.getResources().getString(R.string.feed));
        tabAdapter.addFragment(StoryFragment.newInstance(this.UserId), this.activity.getResources().getString(R.string.story));
        tabAdapter.addFragment(IGTVFragment.newInstance(this.UserId), this.activity.getResources().getString(R.string.igtv));
        viewPager.setAdapter(tabAdapter);
    }
}
