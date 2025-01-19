package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;

import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.FragmentInterface;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.net.URLConnection;

import cn.jzvid.JZVideoPlayer;
import cn.jzvid.JZVideoPlayerStandard;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.WhatsappFullScreenStatusActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.ModelWhatsAppStatus;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.WhatsAppStatusHandler;
import videodownload.com.newmusically.R;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.shareVideoToOtherApps;

public class FragmentStatusVideoFull extends Fragment implements FragmentInterface {
    private static final String EXTRA_MODEL = "model";
    private static final String TAG = FragmentStatusVideoFull.class.getSimpleName();

    private ConstraintLayout clDownload;
    private ConstraintLayout clShareOption;
    private ConstraintLayout clWhatsappShare;
    private int from;
    private ImageView imgImageThumb;
    private boolean isVideo;
    private JZVideoPlayerStandard jcVideoPlayer;
    private ModelWhatsAppStatus model;
    private WhatsappFullScreenStatusActivity activity;
    private View view;
    private View viewTouch;
    ViewPropertyTransition.Animator animationObject;
    private RequestOptions requestOptions;
    public void onFabGoToTopClicked() {
    }

    public static FragmentStatusVideoFull newInstance(int i, ModelWhatsAppStatus modelWhatsAppStatus) {
        FragmentStatusVideoFull fragmentStatusVideoFull = new FragmentStatusVideoFull();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", modelWhatsAppStatus);
        bundle.putInt("from", i);
        fragmentStatusVideoFull.setArguments(bundle);
        return fragmentStatusVideoFull;
    }

    public static FragmentStatusVideoFull newInstance() {
        return new FragmentStatusVideoFull();
    }

    public void fragmentBecameVisible() {
        if (this.isVideo && this.jcVideoPlayer != null) {
            this.jcVideoPlayer.startVideo();
        }
    }

    public void onPause() {
        super.onPause();
        if (this.isVideo && this.jcVideoPlayer != null) {
            this.jcVideoPlayer.release();
        }
    }


    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        activity = (WhatsappFullScreenStatusActivity) getActivity();
        this.view = layoutInflater.inflate(R.layout.fragment_status_video_full, viewGroup, false);
        animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
                // if it's a custom view class, cast it here
                // then find subviews and do the animations
                // here, we just use the entire view for the fade animation
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(500);
                fadeAnim.start();
            }
        };
        requestOptions = new RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder);
        this.model = (ModelWhatsAppStatus) getArguments().getSerializable("model");
        this.from = getArguments().getInt("from");
        this.jcVideoPlayer = (JZVideoPlayerStandard) this.view.findViewById(R.id.videoplayerFull);
        this.imgImageThumb = (ImageView) this.view.findViewById(R.id.imgImageThumb);
        this.clShareOption = (ConstraintLayout) this.view.findViewById(R.id.clShareOption);
        this.clWhatsappShare = (ConstraintLayout) this.view.findViewById(R.id.clWhatsappShare);
        this.clDownload = (ConstraintLayout) this.view.findViewById(R.id.clDownload);
        this.viewTouch = this.view.findViewById(R.id.viewTouch);
        this.viewTouch.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (FragmentStatusVideoFull.isVideoFile(FragmentStatusVideoFull.this.model.filePath)) {
                    switch (motionEvent.getAction()) {
                        case 0:
                            JZVideoPlayer.goOnPlayOnPause();
                            break;
                        case 1:
                            JZVideoPlayer.goOnPlayOnResume();
                            break;
                    }
                }
                return false;
            }
        });
        if (isVideoFile(this.model.filePath)) {
            this.isVideo = true;
            this.jcVideoPlayer.setUpGif(Uri.fromFile(new File(this.model.filePath)).toString(), 0, "");
            this.imgImageThumb.setVisibility(View.GONE);
            this.jcVideoPlayer.setVisibility(View.VISIBLE);
        } else {
            this.isVideo = false;
            Glide.with(getContext()).load(this.model.filePath).transition(GenericTransitionOptions.with(animationObject)).apply(requestOptions).into(this.imgImageThumb);
            this.jcVideoPlayer.setVisibility(View.GONE);
            this.imgImageThumb.setVisibility(View.VISIBLE);
        }
        if (this.from == 0) {
            this.clDownload.setVisibility(View.VISIBLE);
        } else {
            this.clDownload.setVisibility(View.GONE);
        }
        this.clShareOption.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            public void onClick(View view) {

                WhatsAppStatusHandler.copyStatusInSavedDirectory(FragmentStatusVideoFull.this.model.filePath, activity.what, "s", activity);
            }
        });
        this.clWhatsappShare.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                WhatsAppStatusHandler.copyStatusInSavedDirectory(FragmentStatusVideoFull.this.model.filePath, activity.what, "w", activity);
            }
        });
        this.clDownload.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                if (WhatsAppStatusHandler.isFileExistInSavedDire(FragmentStatusVideoFull.this.model.fileName, activity.what)) {
                    Snackbar.make(view, (CharSequence) activity.getResources().getString(R.string.alreadysaved), Snackbar.LENGTH_SHORT).show();
                } else {
                    WhatsAppStatusHandler.copyStatusInSavedDirectory(FragmentStatusVideoFull.this.model.filePath, activity.what, "d", activity);
                }
            }
        });


        return this.view;
    }


    public static boolean isVideoFile(String str) {
        if (str.contains(".mp4")) {
            return true;
        }
        str = URLConnection.guessContentTypeFromName(str);
        return str != null && str.startsWith("video");
    }

    public FragmentStatusVideoFull() {

    }
}
