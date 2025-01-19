package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database.DBOperations;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DashboardFragment;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.StoriesData;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.StoriesProgressView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.TextHttpResponseHandler1;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;

import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import videodownload.com.newmusically.R;

import static com.google.android.gms.ads.AdSize.SMART_BANNER;

public class FullStoryViewActivity extends BaseActivity implements StoriesProgressView.StoriesListener {
    private static final String TAG = "FullStoryViewActivity";
    private StoriesProgressView storiesProgressView;
    private ImageView image;
    /* ArrayList<ItemModel> list;*/
    private ProgressBar mProgressBar;
    private LinearLayout mVideoViewLayout;
    private FloatingActionButton fab_download;
    private ArrayList<StoriesData> mStoriesList = new ArrayList<>();
    private static ArrayList<String> getStories = new ArrayList<>();
    private ArrayList<View> mediaPlayerArrayList = new ArrayList<>();
    private static final int PROGRESS_COUNT = 6;
    private String user = "";
    private int counter = 0;
    long pressTime = 0L;
    long limit = 500L;


    ViewPropertyTransition.Animator animationObject;
    private RequestOptions requestOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handledarkmode();
        setContentView(R.layout.activity_full_story_view);
        getintentdata();

        bindme();
        callStoriesDetailApi(user);
        callads();
    }

    private void callads() {
        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);

    }

    private void bindme() {
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        mProgressBar = findViewById(R.id.progressBar);
        fab_download = findViewById(R.id.fab_download);
        mVideoViewLayout = findViewById(R.id.videoView);
        image = (ImageView) findViewById(R.id.image);
        //image.setImageResource(resources[counter]);

        // bind reverse view
        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
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
        fab_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadfile(mStoriesList.get(counter));
            }
        });
    }

    private void callStoriesDetailApi(String UserId) {
        //pr_loading_bar.setVisibility(View.VISIBLE);
        // String buildurl = "https://i.instagram.com/api/v1/users/" + UserId + "/full_detail_info?max_id=";
        String buildurl = "https://i.instagram.com/api/v1/feed/user/" + UserId + "/reel_media/";
        String Coockie = "ds_user_id=" + PrefManager.getInstance(this).getString(PrefManager.USERID)
                + "; sessionid=" + PrefManager.getInstance(this).getString(PrefManager.SESSIONID);
        String ua = "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"";
        Log.e(TAG, "callStoriesDetailApi: buildurl:" + buildurl);
        Log.e(TAG, "callStoriesDetailApi: Coockie:" + Coockie);
        Log.e(TAG, "callStoriesDetailApi: ua:" + ua);
        ServiceHandler.get(buildurl, null, new TextHttpResponseHandler1() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {


                ArrayList<String> imageList = new ArrayList();
                ArrayList<String> videoList = new ArrayList();
                try {
                    JSONArray array = new JSONObject(responseString).getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject itemObj = array.getJSONObject(i);
                        JSONArray video = itemObj.optJSONArray("video_versions");
                        if (video != null) {
                            videoList.add(video.getJSONObject(0).getString("url"));
                        } else {
                            String url = itemObj.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url");
                            if (!url.endsWith(".jpg")) {
                                url = url + ".jpg";
                            }
                            imageList.add(url);
                        }
                    }
                    getStories.clear();
                    getStories.addAll(imageList);
                    getStories.addAll(videoList);
                    for (String storyModel : getStories) {
                        mStoriesList.add(new StoriesData(storyModel, storyModel.endsWith(".jpg") ? "image/png" : "video"));
                    }
                    storiesProgressView.setStoriesCount(getStories.size());
                    storiesProgressView.setStoriesListener(FullStoryViewActivity.this);
                    for (int i = 0; i < mStoriesList.size(); i++) {
                        if (mStoriesList.get(i).mimeType.contains("video")) {
                            mediaPlayerArrayList.add(getVideoView(i));
                        } else if (mStoriesList.get(i).mimeType.contains("image")) {
                            mediaPlayerArrayList.add(getImageView(i));
                        }
                    }
                    setstory(counter);

                } catch (Exception e) {
                    System.out.println(e);
                }


            }
        }, true, Coockie, ua);
    }

    private void setstory(int counter) {

        final View view = (View) mediaPlayerArrayList.get(counter);
        mVideoViewLayout.addView(view);
        if (view instanceof VideoView) {
            final VideoView video = (VideoView) view;
            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {

                            switch (i) {
                                case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: {
                                    mProgressBar.setVisibility(View.GONE);
                                    storiesProgressView.resume();
                                    return true;
                                }
                                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                                case MediaPlayer.MEDIA_INFO_AUDIO_NOT_PLAYING: {
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    storiesProgressView.pause();
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                    video.start();
                    mProgressBar.setVisibility(View.GONE);
                    storiesProgressView.setStoryDuration(mediaPlayer.getDuration());
                    storiesProgressView.startStories(counter);
                }
            });
        } else if (view instanceof ImageView) {
            final ImageView image = (ImageView) view;
            mProgressBar.setVisibility(View.GONE);


            Glide.with(this).load(mStoriesList.get(counter).mediaUrl).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Toast.makeText(FullStoryViewActivity.this, "Failed to load media...", Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    mProgressBar.setVisibility(View.GONE);
                    storiesProgressView.setStoryDuration(5000);
                    storiesProgressView.startStories(counter);
                    return false;
                }
            }).into(image);
        }

    }

    private VideoView getVideoView(int position) {
        final VideoView video = new VideoView(this);
        video.setVideoPath(mStoriesList.get(position).mediaUrl);
        video.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return video;
    }

    private ImageView getImageView(int position) {
        final ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    private void getintentdata() {
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
    }

    @Override
    public void onNext() {
        mVideoViewLayout.removeAllViews();
        mProgressBar.setVisibility(View.VISIBLE);
        setstory(++counter);
    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;
        storiesProgressView.destroy();
        mVideoViewLayout.removeAllViews();
        mProgressBar.setVisibility(View.VISIBLE);
        setstory(--counter);
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    public void onComplete() {
        finish();
    }

    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }

    File checkfileexistsornnot = null;
    File dirname = null;

    @SuppressLint("StaticFieldLeak")
    private void downloadfile(StoriesData itemModel) {
        pressTime = System.currentTimeMillis();
        storiesProgressView.pause();
        final Dialog dialog = new Dialog(FullStoryViewActivity.this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.appdialog);
        dialog.setCancelable(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        dialog.getWindow().setAttributes(layoutParams);
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressDialog);
        final TextView textView = (TextView) dialog.findViewById(R.id.txtcountstart);
        ((TextView) dialog.findViewById(R.id.txttitle)).setText("Downloading...");
        progressBar.setProgress(0);
        textView.setText("0%");
       // dialog.show();
        String videoPath = "";
        String downloadfilename = "";
        if (itemModel.mimeType.equalsIgnoreCase("video")) {
            videoPath = itemModel.mediaUrl;
            downloadfilename = DashboardFragment.forinstagram+"story_" + System.currentTimeMillis() + ".mp4";
        } else {
            videoPath = itemModel.mediaUrl;
            downloadfilename = DashboardFragment.forinstagram+"story_" + System.currentTimeMillis() + ".png";
        }

        Log.e("videoUrl", videoPath);
        String path = Utility.STORAGEDIR ;//+ DashboardFragment.forinstagram;

        checkfileexistsornnot = new File(path);
        if (!checkfileexistsornnot.exists()) {
            checkfileexistsornnot.mkdir();
        }
       // path = checkfileexistsornnot.getAbsolutePath() + "/.temp/";
        path = checkfileexistsornnot.getAbsolutePath() + "/";
        dirname = new File(path);
        if (!dirname.exists()) {
            dirname.mkdir();
        }
        String stringBuilder2 = dirname + "/" + downloadfilename;
        File file = new File(stringBuilder2);

        String finalVideoPath = videoPath;
        String finalDownloadfilename1 = downloadfilename;

        CommonModel commonModel = new CommonModel();
        commonModel.setSaved_video_url(videoPath);
        commonModel.setSaved_video_date(String.valueOf(System.currentTimeMillis()));
        commonModel.setSaved_video_path(dirname.getAbsolutePath());
        commonModel.setSaved_video_name(downloadfilename);
        Toast.makeText(this, getResources().getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        DBOperations.InsertDownload(this, commonModel, new DownloadListener() {
            @Override
            public void onComplete(int totalBytes) {
                Toast.makeText(FullStoryViewActivity.this, getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();
                String stringBuilder = checkfileexistsornnot +
                        "/" +
                        finalDownloadfilename1;
                File file2 = new File(stringBuilder);
               // file.renameTo(file2);
                addVideo(file2);
              /*  if (dirname.exists()) {
                    dirname.delete();
                }*/

            }

            @Override
            public void onPause(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onPending(int percent, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onFail(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onCancel(int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onRunning(int percent, int totalBytes, int downloadedBytes, float downloadSpeed) {

            }
        });

        /*new AsyncTask<Void, String, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                int count;
                try {
                    URL url = new URL(finalVideoPath);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream to write file
                    OutputStream output = new FileOutputStream(stringBuilder2);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

                return null;
            }

            private void publishProgress(String s) {

                //textView.setText(String.format("%d%%", i));

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(FullStoryViewActivity.this, getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();

                String stringBuilder = String.valueOf(checkfileexistsornnot) +
                        "/" +
                        finalDownloadfilename1;
                File file2 = new File(stringBuilder);
                file.renameTo(file2);
                addVideo(file2);
                if (dirname.exists()) {
                    dirname.delete();
                }

            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                textView.setText(Integer.parseInt(values[0]));
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/

    }

    public void addVideo(File file) {
        ContentValues contentValues = new ContentValues(3);
        contentValues.put("title", "My video title");
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("_data", file.getAbsolutePath());
        getApplicationContext().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
        long now = System.currentTimeMillis();

        storiesProgressView.resume();
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
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE);
        String string = sharedPreferences.getString(getString(R.string.dark_mode), getString(R.string.dark_mode_def_value));
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