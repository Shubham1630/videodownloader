package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.FullViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import videodownload.com.newmusically.R;

public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<FullViewModel> mListenerList;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable saveState() {
        return null;
    }

    public ViewPageAdapter(ArrayList<FullViewModel> arrayList, Context context) {
        this.context = context;
        this.mListenerList = arrayList;
    }

    public int getCount() {
        return this.mListenerList.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = this.layoutInflater.inflate(R.layout.slidingimages_layout, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.im_fullViewImage);
        ImageView imageView2 = (ImageView) inflate.findViewById(R.id.im_vpPlay);
        final VideoView videoView = (VideoView) inflate.findViewById(R.id.videoView);
        if (((FullViewModel) this.mListenerList.get(i)).isVideo()) {
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
        ((RequestBuilder) Glide.with(this.context).load(((FullViewModel) this.mListenerList.get(i)).getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView);
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                videoView.setVisibility(0);
                try {
                    MediaController mediaController = new MediaController(ViewPageAdapter.this.context);
                    mediaController.setAnchorView(videoView);
                    Uri parse = Uri.parse(((FullViewModel) ViewPageAdapter.this.mListenerList.get(i)).getVideoUrl());
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(parse);
                    videoView.start();
                    videoView.setOnPreparedListener(new OnPreparedListener() {
                        public void onPrepared(MediaPlayer mediaPlayer) {
                        }
                    });
                    videoView.setOnCompletionListener(new OnCompletionListener() {
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            videoView.setVisibility(8);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((ViewPager) viewGroup).addView(inflate, 0);
        return inflate;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ((ViewPager) viewGroup).removeView((View) obj);
    }
}
