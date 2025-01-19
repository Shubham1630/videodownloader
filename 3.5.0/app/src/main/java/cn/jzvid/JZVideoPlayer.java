package cn.jzvid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.provider.Settings.SettingNotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import videodownload.com.newmusically.R;

import static android.provider.Settings.System.getInt;

public abstract class JZVideoPlayer extends FrameLayout implements OnClickListener, OnTouchListener, OnSeekBarChangeListener {
    public static boolean ACTION_BAR_EXIST = true;
    public static long CLICK_QUIT_FULLSCREEN_TIME = 0;
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    public static final int CURRENT_STATE_ERROR = 7;
    public static final int CURRENT_STATE_NORMAL = 0;
    public static final int CURRENT_STATE_PAUSE = 5;
    public static final int CURRENT_STATE_PLAYING = 3;
    public static final int CURRENT_STATE_PREPARING = 1;
    public static final int CURRENT_STATE_PREPARING_CHANGING_URL = 2;
    public static int FULLSCREEN_ORIENTATION = 0;
    public static final int FULL_SCREEN_NORMAL_DELAY = 300;
    public static int NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static boolean SAVE_PROGRESS = true;
    public static final int SCREEN_WINDOW_FULLSCREEN = 2;
    public static final int SCREEN_WINDOW_LIST = 1;
    public static final int SCREEN_WINDOW_NORMAL = 0;
    public static final int SCREEN_WINDOW_TINY = 3;
    public static final String TAG = "JiaoZiVideoPlayer";
    public static final int THRESHOLD = 80;
    public static boolean TOOL_BAR_EXIST = true;
    public static final String URL_KEY_DEFAULT = "URL_KEY_DEFAULT";
    public static int VIDEO_IMAGE_DISPLAY_TYPE = 0;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER = 0;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT = 1;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP = 2;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL = 3;
    public static boolean WIFI_TIP_DIALOG_SHOWED = false;
    protected static JZUserAction a;
    protected static Timer b;
    public static long lastAutoFullscreenTime;
    public static OnAudioFocusChangeListener onAudioFocusChangeListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int i) {
            if (i != 1) {
                StringBuilder stringBuilder;
                switch (i) {
                    case -2:
                        try {
                            if (JZVideoPlayerManager.getCurrentJzvd().currentState == 3) {
                                JZVideoPlayerManager.getCurrentJzvd().startButton.performClick();
                            }
                        } catch (Exception i2) {
                            i2.printStackTrace();
                        }
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("AUDIOFOCUS_LOSS_TRANSIENT [");
                        stringBuilder.append(hashCode());
                        stringBuilder.append("]");
                        Log.d("JiaoZiVideoPlayer", stringBuilder.toString());
                        return;
                    case -1:
                        JZVideoPlayer.releaseAllVideos();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("AUDIOFOCUS_LOSS [");
                        stringBuilder.append(hashCode());
                        stringBuilder.append("]");
                        Log.d("JiaoZiVideoPlayer", stringBuilder.toString());
                        return;
                    default:
                        return;
                }
            }
        }
    };
    public ViewGroup bottomContainer;
    protected int c;
    public int currentScreen = -1;
    public int currentState = -1;
    public TextView currentTimeTextView;
    public int currentUrlMapIndex = 0;
    protected int d;
    public Object[] dataSourceObjects;
    protected AudioManager e;
    protected ProgressTimerTask f;
    public ImageView fullscreenButton;
    protected boolean g;
    protected float h;
    public int heightRatio = 0;
    protected float i;
    public boolean isShowSocialIcon = false;
    protected boolean j;
    protected boolean k;
    protected boolean l;
    protected long m;
    protected int n;
    protected float o;
    public Object[] objects = null;
    protected long p;
    public int positionInList = -1;
    public SeekBar progressBar;
    boolean q = false;
    protected boolean r = false;
    protected OnClickListener s;
    public long seekToInAdvance = 0;
    public ImageView startButton;
    OnClickListener t;
    public ViewGroup textureViewContainer;
    public ViewGroup topContainer;
    public TextView totalTimeTextView;
    public int videoRotation = 0;
    public int widthRatio = 0;

    public static class JZAutoFullscreenListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {//可以得到传感器实时测量出来的变化值
            final float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
            float z = event.values[SensorManager.DATA_Z];
            //过滤掉用力过猛会有一个反向的大数值
            if (x < -12 || x > 12) {
                if ((System.currentTimeMillis() - lastAutoFullscreenTime) > 2000) {
                    if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                        JZVideoPlayerManager.getCurrentJzvd().autoFullscreen(x);
                    }
                    lastAutoFullscreenTime = System.currentTimeMillis();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    public class ProgressTimerTask extends TimerTask {
        final  JZVideoPlayer a;

        public ProgressTimerTask(JZVideoPlayer jZVideoPlayer) {
            this.a = jZVideoPlayer;
        }

        public void run() {
            if (currentState == 3 || this.a.currentState == 5) {
                post(new Runnable() {


                    public void run() {
                        long currentPositionWhenPlaying = a.getCurrentPositionWhenPlaying();
                        long duration = a.getDuration();
                        a.setProgressAndText((int) ((100 * currentPositionWhenPlaying) / (duration == 0 ? 1 : duration)), currentPositionWhenPlaying, duration);
                    }
                });
            }
        }
    }

    public void dismissBrightnessDialog() {
    }

    public void dismissProgressDialog() {
    }

    public void dismissVolumeDialog() {
    }

    public abstract int getLayoutId();

    public void onSeekComplete() {
    }

    public void showBrightnessDialog(int i) {
    }

    public void showProgressDialog(float f, String str, long j, String str2, long j2) {
    }

    public void showVolumeDialog(float f, int i) {
    }

    public void showWifiDialog() {
    }

    public JZVideoPlayer(Context context) {
        super(context);
        init(context);
    }

    public JZVideoPlayer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public static void releaseAllVideos() {
        if (System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME > 300) {
            Log.d("JiaoZiVideoPlayer", "releaseAllVideos");
            JZVideoPlayerManager.completeAll();
            JZMediaManager.instance().positionInList = -1;
            JZMediaManager.instance().releaseMediaPlayer();
        }
    }

    public static void startFullscreen(Context context, Class _class, String url, Object... objects) {
        LinkedHashMap map = new LinkedHashMap();
        map.put(URL_KEY_DEFAULT, url);
        Object[] dataSourceObjects = new Object[1];
        dataSourceObjects[0] = map;
        startFullscreen(context, _class, dataSourceObjects, 0, objects);
    }

    public static void startFullscreen(Context context, Class _class, Object[] dataSourceObjects, int defaultUrlMapIndex, Object... objects) {
        hideSupportActionBar(context);
        JZUtils.setRequestedOrientation(context, FULLSCREEN_ORIENTATION);
        ViewGroup vp = (JZUtils.scanForActivity(context))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View old = vp.findViewById(R.id.jz_fullscreen_id);
        if (old != null) {
            vp.removeView(old);
        }
        try {
            Constructor<JZVideoPlayer> constructor = _class.getConstructor(Context.class);
            final JZVideoPlayer jzVideoPlayer = constructor.newInstance(context);
            jzVideoPlayer.setId(R.id.jz_fullscreen_id);
            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            vp.addView(jzVideoPlayer, lp);
//            final Animation ra = AnimationUtils.loadAnimation(context, R.anim.start_fullscreen);
//            jzVideoPlayer.setAnimation(ra);
            jzVideoPlayer.setUp(dataSourceObjects, defaultUrlMapIndex, SCREEN_WINDOW_FULLSCREEN, objects);
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            jzVideoPlayer.startButton.performClick();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean backPress() {
        Log.i("JiaoZiVideoPlayer", "backPress");
        if (System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME < 300) {
            return false;
        }
        if (JZVideoPlayerManager.getSecondFloor() != null) {
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            if (JZUtils.dataSourceObjectsContainsUri(JZVideoPlayerManager.getFirstFloor().dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                JZVideoPlayer secondFloor = JZVideoPlayerManager.getSecondFloor();
                secondFloor.onEvent(secondFloor.currentScreen == 2 ? 8 : 10);
                JZVideoPlayerManager.getFirstFloor().playOnThisJzvd();
            } else {
                quitFullscreenOrTinyWindow();
            }
            return true;
        } else if (JZVideoPlayerManager.getFirstFloor() == null || (JZVideoPlayerManager.getFirstFloor().currentScreen != 2 && JZVideoPlayerManager.getFirstFloor().currentScreen != 3)) {
            return false;
        } else {
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            quitFullscreenOrTinyWindow();
            return true;
        }
    }

    public static void quitFullscreenOrTinyWindow() {
        JZVideoPlayerManager.getFirstFloor().clearFloatScreen();
        JZMediaManager.instance().releaseMediaPlayer();
        JZVideoPlayerManager.completeAll();
    }

    @SuppressLint({"RestrictedApi"})
    public static void showSupportActionBar(Context context) {
        if (ACTION_BAR_EXIST && JZUtils.getAppCompActivity(context) != null) {
            ActionBar supportActionBar = JZUtils.getAppCompActivity(context).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setShowHideAnimationEnabled(false);
                supportActionBar.show();
            }
        }
        if (TOOL_BAR_EXIST) {
            JZUtils.getWindow(context).clearFlags(1024);
        }
    }

    @SuppressLint({"RestrictedApi"})
    public static void hideSupportActionBar(Context context) {
        if (ACTION_BAR_EXIST && JZUtils.getAppCompActivity(context) != null) {
            ActionBar supportActionBar = JZUtils.getAppCompActivity(context).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setShowHideAnimationEnabled(false);
                supportActionBar.hide();
            }
        }
        if (TOOL_BAR_EXIST) {
            JZUtils.getWindow(context).setFlags(1024, 1024);
        }
    }

    public static void clearSavedProgress(Context context, String str) {
        JZUtils.clearSavedProgress(context, str);
    }

    public static void setJzUserAction(JZUserAction jZUserAction) {
        a = jZUserAction;
    }

    public static void goOnPlayOnResume() {
        if (JZVideoPlayerManager.getCurrentJzvd() != null) {
            JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
            if (currentJzvd.currentState == 5) {
                currentJzvd.onStatePlaying();
                JZMediaManager.start();
            }
        }
    }

    public static void goOnPlayOnPause() {
        if (JZVideoPlayerManager.getCurrentJzvd() != null) {
            JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
            if (currentJzvd.currentState != 6 && currentJzvd.currentState != 0) {
                if (currentJzvd.currentState != 7) {
                    currentJzvd.onStatePause();
                    JZMediaManager.pause();
                }
            }
        }
    }

    public static void onScrollAutoTiny(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        int currentPlayPosition = JZMediaManager.instance().positionInList;
        if (currentPlayPosition >= 0) {
            if ((currentPlayPosition < firstVisibleItem || currentPlayPosition > (lastVisibleItem - 1))) {
                if (JZVideoPlayerManager.getCurrentJzvd() != null &&
                        JZVideoPlayerManager.getCurrentJzvd().currentScreen != JZVideoPlayer.SCREEN_WINDOW_TINY &&
                        JZVideoPlayerManager.getCurrentJzvd().currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                    if (JZVideoPlayerManager.getCurrentJzvd().currentState == JZVideoPlayer.CURRENT_STATE_PAUSE) {
                        JZVideoPlayer.releaseAllVideos();
                    } else {
                        Log.e(TAG, "onScroll: out screen");
                        JZVideoPlayerManager.getCurrentJzvd().startWindowTiny();
                    }
                }
            } else {
                if (JZVideoPlayerManager.getCurrentJzvd() != null &&
                        JZVideoPlayerManager.getCurrentJzvd().currentScreen == JZVideoPlayer.SCREEN_WINDOW_TINY) {
                    Log.e(TAG, "onScroll: into screen");
                    JZVideoPlayer.backPress();
                }
            }
        }
    }


    public static void onScrollReleaseAllVideos(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        int currentPlayPosition = JZMediaManager.instance().positionInList;
        Log.e(TAG, "onScrollReleaseAllVideos: " +
                currentPlayPosition + " " + firstVisibleItem + " " + currentPlayPosition + " " + lastVisibleItem);
        if (currentPlayPosition >= 0) {
            if ((currentPlayPosition < firstVisibleItem || currentPlayPosition > (lastVisibleItem - 1))) {
                if (JZVideoPlayerManager.getCurrentJzvd().currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                    JZVideoPlayer.releaseAllVideos();//为什么最后一个视频横屏会调用这个，其他地方不会
                }
            }
        }
    }

    public static void onChildViewAttachedToWindow(View view, int jzvdId) {
        if (JZVideoPlayerManager.getCurrentJzvd() != null && JZVideoPlayerManager.getCurrentJzvd().currentScreen == JZVideoPlayer.SCREEN_WINDOW_TINY) {
            JZVideoPlayer videoPlayer = view.findViewById(jzvdId);
            if (videoPlayer != null && JZUtils.getCurrentFromDataSource(videoPlayer.dataSourceObjects, videoPlayer.currentUrlMapIndex).equals(JZMediaManager.getCurrentDataSource())) {
                JZVideoPlayer.backPress();
            }
        }
    }

    public static void onChildViewDetachedFromWindow(View view) {
        if (JZVideoPlayerManager.getCurrentJzvd() != null && JZVideoPlayerManager.getCurrentJzvd().currentScreen != JZVideoPlayer.SCREEN_WINDOW_TINY) {
            JZVideoPlayer videoPlayer = JZVideoPlayerManager.getCurrentJzvd();
            if (((ViewGroup) view).indexOfChild(videoPlayer) != -1) {
                if (videoPlayer.currentState == JZVideoPlayer.CURRENT_STATE_PAUSE) {
                    JZVideoPlayer.releaseAllVideos();
                } else {
                    videoPlayer.startWindowTiny();
                }
            }
        }
    }

    public static void setTextureViewRotation(int i) {
        if (JZMediaManager.textureView != null) {
            JZMediaManager.textureView.setRotation((float) i);
        }
    }

    public static void setVideoImageDisplayType(int type) {
        JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE = type;
        if (JZMediaManager.textureView != null) {
            JZMediaManager.textureView.requestLayout();
        }
    }

    public Object getCurrentUrl() {
        return JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex);
    }

    public void init(Context context) {
        View.inflate(context, getLayoutId(), this);
        this.startButton = (ImageView) findViewById(R.id.start);
        this.fullscreenButton = (ImageView) findViewById(R.id.fullscreen);
        this.progressBar = (SeekBar) findViewById(R.id.bottom_seek_progress);
        this.currentTimeTextView = (TextView) findViewById(R.id.current);
        this.totalTimeTextView = (TextView) findViewById(R.id.total);
        this.bottomContainer = (ViewGroup) findViewById(R.id.layout_bottom);
        this.textureViewContainer = (ViewGroup) findViewById(R.id.surface_container);
        this.topContainer = (ViewGroup) findViewById(R.id.layout_top);
        this.startButton.setOnClickListener(this);
        this.fullscreenButton.setOnClickListener(this);
        this.progressBar.setOnSeekBarChangeListener(this);
        this.bottomContainer.setOnClickListener(this);
        this.textureViewContainer.setOnClickListener(this);
        this.textureViewContainer.setOnTouchListener(this);
        this.c = getContext().getResources().getDisplayMetrics().widthPixels;
        this.d = getContext().getResources().getDisplayMetrics().heightPixels;
        this.e = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        try {
            if (isCurrentPlay()) {
                NORMAL_ORIENTATION = ((AppCompatActivity) context).getRequestedOrientation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp(String url, int screen, Object... objects) {
        LinkedHashMap map = new LinkedHashMap();
        map.put(URL_KEY_DEFAULT, url);
        Object[] dataSourceObjects = new Object[1];
        dataSourceObjects[0] = map;
        setUp(dataSourceObjects, 0, screen, objects);
    }

    public void setUpGif(String str, int i, Object... objArr) {
        LinkedHashMap map = new LinkedHashMap();
        map.put(URL_KEY_DEFAULT, str);

        Object[] dataSourceObjects = new Object[]{1};
        dataSourceObjects[0] = map;
        setUp(str, 0, i, objArr);
    }

    public void setUp(Object[] objArr, int i, int i2, Object... objArr2) {
        if (this.dataSourceObjects == null || JZUtils.getCurrentFromDataSource(objArr, this.currentUrlMapIndex) == null || !JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex).equals(JZUtils.getCurrentFromDataSource(objArr, this.currentUrlMapIndex))) {
            if (isCurrentJZVD() && JZUtils.dataSourceObjectsContainsUri(objArr, JZMediaManager.getCurrentDataSource())) {
                long currentPosition;
                try {
                    currentPosition = JZMediaManager.getCurrentPosition();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    currentPosition = 0;
                }
                if (currentPosition != 0) {
                    JZUtils.saveProgress(getContext(), JZMediaManager.getCurrentDataSource(), currentPosition);
                }
                JZMediaManager.instance().releaseMediaPlayer();
            } else if (isCurrentJZVD() && !JZUtils.dataSourceObjectsContainsUri(objArr, JZMediaManager.getCurrentDataSource())) {
                startWindowTiny();
            } else if (isCurrentJZVD() || !JZUtils.dataSourceObjectsContainsUri(objArr, JZMediaManager.getCurrentDataSource())) {
                if (!isCurrentJZVD()) {
                    JZUtils.dataSourceObjectsContainsUri(objArr, JZMediaManager.getCurrentDataSource());
                }
            } else if (JZVideoPlayerManager.getCurrentJzvd() != null && JZVideoPlayerManager.getCurrentJzvd().currentScreen == 3) {
                this.q = true;
            }
            this.dataSourceObjects = objArr;
            this.currentUrlMapIndex = i;
            this.currentScreen = i2;
            this.objects = objArr2;
            onStateNormal();
        }
    }

    public void setOnThumbOrPlayButtonClickListener(OnClickListener onClickListener) {
        this.s = onClickListener;
    }

    public void onClick(View view) {
        int id = view.getId();
        Log.e(TAG, "onClick: id :"+id );
        StringBuilder stringBuilder;
        if (id == R.id.start) {
            String stringBuilder2 = "onClick start [" + hashCode() + "] ";
            Log.e("JiaoZiVideoPlayer", stringBuilder2);
            if (this.dataSourceObjects != null) {
                if (JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex) != null) {
                    if (this.currentState == 0) {
                        if (JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex).toString().startsWith("file") || JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex).toString().startsWith("/") || JZUtils.isWifiConnected(getContext()) || !JZUtils.isMobileDataConnected(getContext()) || WIFI_TIP_DIALOG_SHOWED) {
                            startVideo();
                            onEvent(0);
                            if (this.s != null) {
                                this.s.onClick(view);
                            }
                        } else {
                            showWifiDialog();
                        }
                    } else if (this.currentState == 3) {
                        onEvent(3);
                        String stringBuilder1 = "pauseVideo [" + hashCode() + "] ";
                        Log.e("JiaoZiVideoPlayer", stringBuilder1);
                        JZMediaManager.pause();
                        onStatePause();
                    } else if (this.currentState == 5) {
                        onEvent(4);
                        JZMediaManager.start();
                        onStatePlaying();
                    } else if (this.currentState == 6) {
                        onEvent(2);
                        startVideo();
                    }
                }
            }
//            Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT ).show();
        } else if (id == R.id.fullscreen) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("onClick fullscreen [");
            stringBuilder.append(hashCode());
            stringBuilder.append("] ");
            Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
            if (this.currentState != 6) {
                if (this.currentScreen == 2) {
                    backPress();
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("toFullscreenActivity [");
                    stringBuilder.append(hashCode());
                    stringBuilder.append("] ");
                    Log.d("JiaoZiVideoPlayer", stringBuilder.toString());
                    onEvent(7);
                    startWindowFullscreen();
                }
            }
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        JZVideoPlayer jZVideoPlayer = this;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (view.getId() == R.id.surface_container) {
            StringBuilder stringBuilder;
            StringBuilder stringBuilder2;
            switch (motionEvent.getAction()) {
                case 0:
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onTouch surfaceContainer actionDown [");
                    stringBuilder.append(hashCode());
                    stringBuilder.append("] ");
                    Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
                    jZVideoPlayer.g = true;
                    jZVideoPlayer.h = x;
                    jZVideoPlayer.i = y;
                    jZVideoPlayer.j = false;
                    jZVideoPlayer.k = false;
                    jZVideoPlayer.l = false;
                    break;
                case 1:
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("onTouch surfaceContainer actionUp [");
                    stringBuilder2.append(hashCode());
                    stringBuilder2.append("] ");
                    Log.i("JiaoZiVideoPlayer", stringBuilder2.toString());
                    jZVideoPlayer.g = false;
                    dismissProgressDialog();
                    dismissVolumeDialog();
                    dismissBrightnessDialog();
                    if (jZVideoPlayer.k) {
                        onEvent(12);
                        JZMediaManager.seekTo(jZVideoPlayer.p);
                        long duration = getDuration();
                        long j = jZVideoPlayer.p * 100;
                        if (duration == 0) {
                            duration = 1;
                        }
                        jZVideoPlayer.progressBar.setProgress((int) (j / duration));
                    }
                    if (jZVideoPlayer.j) {
                        onEvent(11);
                    }
                    startProgressTimer();
                    if (!(jZVideoPlayer.k || jZVideoPlayer.j)) {
                        Log.i("JiaoZiVideoPlayer", "random clicked");
                        if (jZVideoPlayer.t != null) {
                            jZVideoPlayer.t.onClick(view);
                            break;
                        }
                    }
                    break;
                case 2:
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onTouch surfaceContainer actionMove [");
                    stringBuilder.append(hashCode());
                    stringBuilder.append("] ");
                    Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
                    float f = x - jZVideoPlayer.h;
                    float f2 = y - jZVideoPlayer.i;
                    x = Math.abs(f);
                    y = Math.abs(f2);
                    if (!(jZVideoPlayer.currentScreen != 2 || jZVideoPlayer.k || jZVideoPlayer.j || jZVideoPlayer.l || (x <= 80.0f && y <= 80.0f))) {
                        cancelProgressTimer();
                        if (x >= 80.0f) {
                            if (jZVideoPlayer.currentState != 7) {
                                jZVideoPlayer.k = true;
                                jZVideoPlayer.m = getCurrentPositionWhenPlaying();
                            }
                        } else if (jZVideoPlayer.h < ((float) jZVideoPlayer.c) * 0.5f) {
                            jZVideoPlayer.l = true;
                            WindowManager.LayoutParams attributes = JZUtils.getWindow(getContext()).getAttributes();
                            if (attributes.screenBrightness < 0.0f) {
                                try {
                                    jZVideoPlayer.o = (float) getInt(getContext().getContentResolver(), "screen_brightness");
                                    stringBuilder2 = new StringBuilder();
                                    stringBuilder2.append("current system brightness: ");
                                    stringBuilder2.append(jZVideoPlayer.o);
                                    Log.i("JiaoZiVideoPlayer", stringBuilder2.toString());
                                } catch (SettingNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                jZVideoPlayer.o = attributes.screenBrightness * 255.0f;
                                stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("current activity brightness: ");
                                stringBuilder2.append(jZVideoPlayer.o);
                                Log.i("JiaoZiVideoPlayer", stringBuilder2.toString());
                            }
                        } else {
                            jZVideoPlayer.j = true;
                            jZVideoPlayer.n = jZVideoPlayer.e.getStreamVolume(3);
                        }
                    }
                    if (jZVideoPlayer.k) {
                        long duration2 = getDuration();
                        jZVideoPlayer.p = (long) ((int) (((float) jZVideoPlayer.m) + ((((float) duration2) * f) / ((float) jZVideoPlayer.c))));
                        if (jZVideoPlayer.p > duration2) {
                            jZVideoPlayer.p = duration2;
                        }
                        String stringForTime = JZUtils.stringForTime(jZVideoPlayer.p);
                        String stringForTime2 = JZUtils.stringForTime(duration2);
                        showProgressDialog(f, stringForTime, jZVideoPlayer.p, stringForTime2, duration2);
                    }
                    if (jZVideoPlayer.j) {
                        f2 = -f2;
                        int streamMaxVolume = jZVideoPlayer.e.getStreamMaxVolume(3);
                        jZVideoPlayer.e.setStreamVolume(3, jZVideoPlayer.n + ((int) (((((float) streamMaxVolume) * f2) * 3.0f) / ((float) jZVideoPlayer.d))), 0);
                        showVolumeDialog(-f2, (int) (((float) ((jZVideoPlayer.n * 100) / streamMaxVolume)) + (((f2 * 3.0f) * 100.0f) / ((float) jZVideoPlayer.d))));
                    }
                    if (jZVideoPlayer.l) {
                        x = -f2;
                        int i = (int) (((255.0f * x) * 3.0f) / ((float) jZVideoPlayer.d));
                        WindowManager.LayoutParams attributes2 = JZUtils.getWindow(getContext()).getAttributes();
                        float f3 = (float) i;
                        if ((jZVideoPlayer.o + f3) / 255.0f >= 1) {
                            attributes2.screenBrightness = 1;
                        } else if ((jZVideoPlayer.o + f3) / 255.0f <= 0.0f) {
                            attributes2.screenBrightness = 0.01f;
                        } else {
                            attributes2.screenBrightness = (jZVideoPlayer.o + f3) / 255.0f;
                        }
                        JZUtils.getWindow(getContext()).setAttributes(attributes2);
                        showBrightnessDialog((int) (((jZVideoPlayer.o * 100.0f) / 255.0f) + (((x * 3.0f) * 100.0f) / ((float) jZVideoPlayer.d))));
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    public void setOnClickWhileVideoIsPlaying(OnClickListener onClickListener) {
        this.t = onClickListener;
    }

    public void startVideo() {
        JZVideoPlayerManager.completeAll();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("startVideo [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.d("JiaoZiVideoPlayer", stringBuilder.toString());
        initTextureView();
        addTextureView();
        ((AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE)).requestAudioFocus(onAudioFocusChangeListener, 3, 2);
        JZUtils.scanForActivity(getContext()).getWindow().addFlags(128);
        JZMediaManager.setDataSource(this.dataSourceObjects);
        JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex));
        JZMediaManager.instance().positionInList = this.positionInList;
        onStatePreparing();
        JZVideoPlayerManager.setFirstFloor(this);
    }

    public void onPrepared() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onPrepared  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        onStatePrepared();
        onStatePlaying();
    }

    public void setState(int i) {
        setState(i, 0, 0);
    }

    public void setState(int i, int i2, int i3) {
        switch (i) {
            case 0:
                onStateNormal();
                return;
            case 1:
                onStatePreparing();
                return;
            case 2:
                onStatePreparingChangingUrl(i2, (long) i3);
                return;
            case 3:
                onStatePlaying();
                return;
            case 5:
                onStatePause();
                return;
            case 6:
                onStateAutoComplete();
                return;
            case 7:
                onStateError();
                return;
            default:
                return;
        }
    }

    public void onStateNormal() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onStateNormal  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        this.currentState = 0;
        cancelProgressTimer();
    }

    public void onStatePreparing() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onStatePreparing  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        this.currentState = 1;
        resetProgressAndTime();
    }

    public void onStatePreparingChangingUrl(int i, long j) {
        this.currentState = 2;
        this.currentUrlMapIndex = i;
        this.seekToInAdvance = j;
        JZMediaManager.setDataSource(this.dataSourceObjects);
        JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex));
        JZMediaManager.instance().prepare();
    }

    public void onStatePrepared() {
        if (this.seekToInAdvance != 0) {
            JZMediaManager.seekTo(this.seekToInAdvance);
            this.seekToInAdvance = 0;
            return;
        }
        long savedProgress = JZUtils.getSavedProgress(getContext(), JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex));
        if (savedProgress != 0) {
            JZUtils.clearSavedProgress(getContext(), JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex));
            JZMediaManager.seekTo(savedProgress);
        }
    }

    public void onStatePlaying() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onStatePlaying  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        this.currentState = 3;
        startProgressTimer();
    }

    public void onStatePause() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onStatePause  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        this.currentState = 5;
        startProgressTimer();
    }

    public void onStateError() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onStateError  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        this.currentState = 7;
        cancelProgressTimer();
    }

    public void onStateAutoComplete() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onStateAutoComplete  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        this.currentState = 6;
        cancelProgressTimer();
        this.progressBar.setProgress(100);
        this.currentTimeTextView.setText(this.totalTimeTextView.getText());
    }

    public void onInfo(int i, int i2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onInfo what - ");
        stringBuilder.append(i);
        stringBuilder.append(" extra - ");
        stringBuilder.append(i2);
        Log.d("JiaoZiVideoPlayer", stringBuilder.toString());
    }

    public void onError(int i, int i2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onError ");
        stringBuilder.append(i);
        stringBuilder.append(" - ");
        stringBuilder.append(i2);
        stringBuilder.append(" [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.e("JiaoZiVideoPlayer", stringBuilder.toString());
        if (i != 38 && i2 != -38 && i != -38 && i2 != 38 && i2 != -19) {
            onStateError();
            if (isCurrentPlay()) {
                JZMediaManager.instance().releaseMediaPlayer();
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.currentScreen != 2) {
            if (this.currentScreen != 3) {
                if (this.widthRatio == 0 || this.heightRatio == 0) {
                    super.onMeasure(i, i2);
                } else {
                    i = MeasureSpec.getSize(i);
                    i2 = (int) ((((float) i) * ((float) this.heightRatio)) / ((float) this.widthRatio));
                    setMeasuredDimension(i, i2);
                    getChildAt(0).measure(MeasureSpec.makeMeasureSpec(i, 1073741824), MeasureSpec.makeMeasureSpec(i2, 1073741824));
                }
                return;
            }
        }
        super.onMeasure(i, i2);
    }

    public void onAutoCompletion() {
        Runtime.getRuntime().gc();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onAutoCompletion  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        onEvent(6);
        dismissVolumeDialog();
        dismissProgressDialog();
        dismissBrightnessDialog();
        onStateAutoComplete();
        if (this.currentScreen == 2 || this.currentScreen == 3) {
            backPress();
        }
        JZMediaManager.instance().releaseMediaPlayer();
        JZUtils.saveProgress(getContext(), JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex), 0);
    }

    public void onCompletion() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onCompletion  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        if (this.currentState == 3 || this.currentState == 5) {
            JZUtils.saveProgress(getContext(), JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex), getCurrentPositionWhenPlaying());
        }
        cancelProgressTimer();
        dismissBrightnessDialog();
        dismissProgressDialog();
        dismissVolumeDialog();
        onStateNormal();
        this.textureViewContainer.removeView(JZMediaManager.textureView);
        JZMediaManager.instance().currentVideoWidth = 0;
        JZMediaManager.instance().currentVideoHeight = 0;
        ((AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE)).abandonAudioFocus(onAudioFocusChangeListener);
        JZUtils.scanForActivity(getContext()).getWindow().clearFlags(128);
        clearFullscreenLayout();
        JZUtils.setRequestedOrientation(getContext(), NORMAL_ORIENTATION);
        if (JZMediaManager.surface != null) {
            JZMediaManager.surface.release();
        }
        if (JZMediaManager.savedSurfaceTexture != null) {
            JZMediaManager.savedSurfaceTexture.release();
        }
        JZMediaManager.textureView = null;
        JZMediaManager.savedSurfaceTexture = null;
    }

    public void release() {
        if (JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex).equals(JZMediaManager.getCurrentDataSource()) && System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME > 300) {
            if (JZVideoPlayerManager.getSecondFloor() != null && JZVideoPlayerManager.getSecondFloor().currentScreen == 2) {
                return;
            }
            if (JZVideoPlayerManager.getSecondFloor() != null || JZVideoPlayerManager.getFirstFloor() == null || JZVideoPlayerManager.getFirstFloor().currentScreen != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("releaseMediaPlayer [");
                stringBuilder.append(hashCode());
                stringBuilder.append("]");
                Log.d("JiaoZiVideoPlayer", stringBuilder.toString());
                releaseAllVideos();
            }
        }
    }

    public void initTextureView() {
        removeTextureView();
        JZMediaManager.textureView = new JZResizeTextureView(getContext());
        JZMediaManager.textureView.setSurfaceTextureListener(JZMediaManager.instance());
    }

    public void addTextureView() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("addTextureView [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.d("JiaoZiVideoPlayer", stringBuilder.toString());
        this.textureViewContainer.addView(JZMediaManager.textureView, new LayoutParams(-1, -1, 17));
    }

    public void removeTextureView() {
        JZMediaManager.savedSurfaceTexture = null;
        if (JZMediaManager.textureView != null && JZMediaManager.textureView.getParent() != null) {
            ((ViewGroup) JZMediaManager.textureView.getParent()).removeView(JZMediaManager.textureView);
        }
    }

    public void clearFullscreenLayout() {
        ViewGroup viewGroup = (ViewGroup) JZUtils.scanForActivity(getContext()).findViewById(16908290);
        View findViewById = viewGroup.findViewById(R.id.jz_fullscreen_id);
        View findViewById2 = viewGroup.findViewById(R.id.jz_tiny_id);
        if (findViewById != null) {
            viewGroup.removeView(findViewById);
        }
        if (findViewById2 != null) {
            viewGroup.removeView(findViewById2);
        }
        showSupportActionBar(getContext());
    }

    public void clearFloatScreen() {
        JZUtils.setRequestedOrientation(getContext(), NORMAL_ORIENTATION);
        showSupportActionBar(getContext());
        ViewGroup viewGroup = (ViewGroup) JZUtils.scanForActivity(getContext()).findViewById(16908290);
        JZVideoPlayer jZVideoPlayer = (JZVideoPlayer) viewGroup.findViewById(R.id.jz_fullscreen_id);
        JZVideoPlayer jZVideoPlayer2 = (JZVideoPlayer) viewGroup.findViewById(R.id.jz_tiny_id);
        if (jZVideoPlayer != null) {
            viewGroup.removeView(jZVideoPlayer);
            if (jZVideoPlayer.textureViewContainer != null) {
                jZVideoPlayer.textureViewContainer.removeView(JZMediaManager.textureView);
            }
        }
        if (jZVideoPlayer2 != null) {
            viewGroup.removeView(jZVideoPlayer2);
            if (jZVideoPlayer2.textureViewContainer != null) {
                jZVideoPlayer2.textureViewContainer.removeView(JZMediaManager.textureView);
            }
        }
        JZVideoPlayerManager.setSecondFloor(null);
    }

    public void onVideoSizeChanged() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onVideoSizeChanged  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        if (JZMediaManager.textureView != null) {
            if (this.videoRotation != 0) {
                JZMediaManager.textureView.setRotation((float) this.videoRotation);
            }
            JZMediaManager.textureView.setVideoSize(JZMediaManager.instance().currentVideoWidth, JZMediaManager.instance().currentVideoHeight);
        }
    }

    public void startProgressTimer() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("startProgressTimer:  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        cancelProgressTimer();
        b = new Timer();
        this.f = new ProgressTimerTask(this);
        b.schedule(this.f, 0, 300);
    }

    public void cancelProgressTimer() {
        if (b != null) {
            b.cancel();
        }
        if (this.f != null) {
            this.f.cancel();
        }
    }

    public void setProgressAndText(int i, long j, long j2) {
        if (!(this.g || i == 0)) {
            this.progressBar.setProgress(i);
        }
        if (j != 0) {
            this.currentTimeTextView.setText(JZUtils.stringForTime(j));
        }
        this.totalTimeTextView.setText(JZUtils.stringForTime(j2));
    }

    public void setBufferProgress(int i) {
        if (i != 0) {
            this.progressBar.setSecondaryProgress(i);
        }
    }

    public void resetProgressAndTime() {
        this.progressBar.setProgress(0);
        this.progressBar.setSecondaryProgress(0);
        this.currentTimeTextView.setText(JZUtils.stringForTime(0));
        this.totalTimeTextView.setText(JZUtils.stringForTime(0));
    }

    public long getCurrentPositionWhenPlaying() {
        long j = 0;
        if (this.currentState == 3 || this.currentState == 5) {
            try {
                j = JZMediaManager.getCurrentPosition();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return j;
    }

    public long getDuration() {
        try {
            return JZMediaManager.getDuration();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStartTrackingTouch [" + this.hashCode() + "] ");
        cancelProgressTimer();
        ViewParent vpdown = getParent();
        while (vpdown != null) {
            vpdown.requestDisallowInterceptTouchEvent(true);
            vpdown = vpdown.getParent();
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bottomProgress onStopTrackingTouch [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        onEvent(5);
        startProgressTimer();
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        if (this.currentState == 3 || this.currentState == 5) {
            long progress = (((long) seekBar.getProgress()) * getDuration()) / 100;
            JZMediaManager.seekTo(progress);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("seekTo ");
            stringBuilder2.append(progress);
            stringBuilder2.append(" [");
            stringBuilder2.append(hashCode());
            stringBuilder2.append("] ");
            Log.i("JiaoZiVideoPlayer", stringBuilder2.toString());
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            this.currentTimeTextView.setText(JZUtils.stringForTime((((long) i) * getDuration()) / 100));
        }
    }

    public void startWindowFullscreen() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("startWindowFullscreen  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        hideSupportActionBar(getContext());
        ViewGroup viewGroup = (ViewGroup) JZUtils.scanForActivity(getContext()).findViewById(16908290);
        View findViewById = viewGroup.findViewById(R.id.jz_fullscreen_id);
        if (findViewById != null) {
            viewGroup.removeView(findViewById);
        }
        this.textureViewContainer.removeView(JZMediaManager.textureView);
        try {
            JZVideoPlayer jZVideoPlayer = (JZVideoPlayer) getClass().getConstructor(new Class[]{Context.class}).newInstance(new Object[]{getContext()});
            jZVideoPlayer.setId(R.id.jz_fullscreen_id);
            viewGroup.addView(jZVideoPlayer, new LayoutParams(-1, -1));
            jZVideoPlayer.setSystemUiVisibility(4102);
            jZVideoPlayer.setUp(this.dataSourceObjects, this.currentUrlMapIndex, 2, this.objects);
            jZVideoPlayer.setState(this.currentState);
            jZVideoPlayer.addTextureView();
            JZVideoPlayerManager.setSecondFloor(jZVideoPlayer);
            JZUtils.setRequestedOrientation(getContext(), FULLSCREEN_ORIENTATION);
            onStateNormal();
            jZVideoPlayer.progressBar.setSecondaryProgress(this.progressBar.getSecondaryProgress());
            jZVideoPlayer.startProgressTimer();
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startWindowTiny() {
        Log.i(TAG, "startWindowTiny " + " [" + this.hashCode() + "] ");
        onEvent(JZUserAction.ON_ENTER_TINYSCREEN);
        if (currentState == CURRENT_STATE_NORMAL || currentState == CURRENT_STATE_ERROR || currentState == CURRENT_STATE_AUTO_COMPLETE)
            return;
        ViewGroup vp = (JZUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View old = vp.findViewById(R.id.jz_tiny_id);
        if (old != null) {
            vp.removeView(old);
        }
        textureViewContainer.removeView(JZMediaManager.textureView);

        try {
            Constructor<JZVideoPlayer> constructor = (Constructor<JZVideoPlayer>) JZVideoPlayer.this.getClass().getConstructor(Context.class);
            JZVideoPlayer jzVideoPlayer = constructor.newInstance(getContext());
            jzVideoPlayer.setId(R.id.jz_tiny_id);
            LayoutParams lp = new LayoutParams(400, 400);
            lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            vp.addView(jzVideoPlayer, lp);
            jzVideoPlayer.setUp(dataSourceObjects, currentUrlMapIndex, SCREEN_WINDOW_TINY, objects);
            jzVideoPlayer.setState(currentState);
            jzVideoPlayer.addTextureView();
            JZVideoPlayerManager.setSecondFloor(jzVideoPlayer);
            onStateNormal();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCurrentPlay() {
        return isCurrentJZVD() && JZUtils.dataSourceObjectsContainsUri(this.dataSourceObjects, JZMediaManager.getCurrentDataSource());
    }

    public boolean isCurrentJZVD() {
        return JZVideoPlayerManager.getCurrentJzvd() != null && JZVideoPlayerManager.getCurrentJzvd() == this;
    }

    public void playOnThisJzvd() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("playOnThisJzvd  [");
        stringBuilder.append(hashCode());
        stringBuilder.append("] ");
        Log.i("JiaoZiVideoPlayer", stringBuilder.toString());
        this.currentState = JZVideoPlayerManager.getSecondFloor().currentState;
        this.currentUrlMapIndex = JZVideoPlayerManager.getSecondFloor().currentUrlMapIndex;
        clearFloatScreen();
        setState(this.currentState);
        addTextureView();
    }

    public void autoFullscreen(float x) {
        if (isCurrentPlay()
                && (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PAUSE)
                && currentScreen != SCREEN_WINDOW_FULLSCREEN
                && currentScreen != SCREEN_WINDOW_TINY) {
            if (x > 0) {
                JZUtils.setRequestedOrientation(getContext(), ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                JZUtils.setRequestedOrientation(getContext(), ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
            onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
            startWindowFullscreen();
        }
    }

    public void autoQuitFullscreen() {
        if (System.currentTimeMillis() - lastAutoFullscreenTime > 2000 && isCurrentPlay() && this.currentState == 3 && this.currentScreen == 2) {
            lastAutoFullscreenTime = System.currentTimeMillis();
            backPress();
        }
    }

    public void onEvent(int i) {
        if (a != null && isCurrentPlay() && this.dataSourceObjects != null) {
            a.onEvent(i, JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex), this.currentScreen, this.objects);
        }
    }

    public static void setMediaInterface(JZMediaInterface jZMediaInterface) {
        JZMediaManager.instance().jzMediaInterface = jZMediaInterface;
    }
}
