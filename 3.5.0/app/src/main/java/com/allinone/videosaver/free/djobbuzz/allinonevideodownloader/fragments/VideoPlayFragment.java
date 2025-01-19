package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.PlayvideosActivity;

import videodownload.com.newmusically.R;

public class VideoPlayFragment extends Fragment {
    private static final String TAG = "VideoPlayFragment";
    public static String extraVideourl = "videoUrl";
    VideoView videoView;
    ImageView imgpreview;
    ImageView imageplay;
    ImageView fullscreen;
    private String databaseContents;
    TextView txtstart;
    TextView txtend;
    ProgressBar progress;
    private Runnable onEverySecond = new Runnable() {
        public void run() {
            if (seekbar != null) {
                TextView textView;
                StringBuilder stringBuilder;
                String str;
                seekbar.setProgress(videoView.getCurrentPosition());
                int currentPosition = videoView.getCurrentPosition() / 1000;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(currentPosition);
                stringBuilder2.append("");
                if (stringBuilder2.toString().length() > 1) {
                    textView = txtstart;
                    stringBuilder = new StringBuilder();
                    str = "00:";
                } else {
                    textView = txtstart;
                    stringBuilder = new StringBuilder();
                    str = "00:0";
                }
                stringBuilder.append(str);
                stringBuilder.append(currentPosition);
                stringBuilder.append("");
                textView.setText(stringBuilder.toString());
                //textView.setText(milliSecondsToTimer(videoView.getCurrentPosition()));
            }
            if (videoView.isPlaying()) {
                seekbar.postDelayed(onEverySecond, 300);
            }
        }
    };
    private SeekBar seekbar;

    public static VideoPlayFragment createInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(extraVideourl, str);
        VideoPlayFragment videoPlayFragment = new VideoPlayFragment();
        videoPlayFragment.setArguments(bundle);
        return videoPlayFragment;
    }

    public VideoPlayFragment() {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.databaseContents = getArguments().getString(extraVideourl);
        View inflate = layoutInflater.inflate(R.layout.fragment_video_play, viewGroup, false);

        this.videoView = (VideoView) inflate.findViewById(R.id.vdo_ContentVideo);
        this.imgpreview = (ImageView) inflate.findViewById(R.id.imgpreview);
        this.imageplay = (ImageView) inflate.findViewById(R.id.imageplay);
        this.fullscreen = (ImageView) inflate.findViewById(R.id.fullscreen);
        this.seekbar = (SeekBar) inflate.findViewById(R.id.seekBar);
        this.txtstart = (TextView) inflate.findViewById(R.id.txtstart);
        this.txtend = (TextView) inflate.findViewById(R.id.txtend);
        this.progress = inflate.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        this.fullscreen.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((PlayvideosActivity) getActivity()).fullScreenvideo();
            }
        });
        this.imageplay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (videoView.isPlaying()) {
                    imageplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    videoView.pause();
                    return;
                }
                imageplay.setImageResource(R.drawable.ic_pause_black_24dp);
                imgpreview.setVisibility(View.GONE);
                videoView.start();
                onEverySecond.run();
            }
        });

        if (this.videoView != null) {
            Uri parse = Uri.parse(this.databaseContents);
            MediaController mediaController = new MediaController(getActivity());
            mediaController.setAnchorView(this.videoView);
            mediaController.setMediaPlayer(this.videoView);
            this.videoView.setVideoURI(parse);
            this.videoView.requestFocus();
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e(TAG, "onError: called");
                    progress.setVisibility(View.GONE);
                    return false;
                }
            });

            this.videoView.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Log.e(TAG, "onPrepared: called");
                    progress.setVisibility(View.GONE);
                    videoView.seekTo(0);
                    seekbar.setMax(videoView.getDuration());
                    int duration = (videoView.getDuration() - videoView.getCurrentPosition()) / 1000;
                    TextView textView = txtend;
                    String durations = "00:" + duration + "";
                    textView.setText(durations);
                    // textView.setText(milliSecondsToTimer(videoView.getDuration()));
                    Glide.with(getContext()).load(databaseContents).into(imgpreview);
                }
            });
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.e(TAG, "onCompletion: called");
                    progress.setVisibility(View.GONE);
                    videoView.seekTo(0);
                    txtstart.setText("00");
                    imageplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
            });
        }
        this.seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    videoView.seekTo(i);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return inflate;
    }

    public void onResume() {
        super.onResume();
    }

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (!z && this.videoView != null && this.videoView.isPlaying()) {
            this.imageplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            this.videoView.pause();
        }
    }

    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
}
