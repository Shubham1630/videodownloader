package cn.jzvid;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import videodownload.com.newmusically.R;

public class JZVideoPlayerStandard extends JZVideoPlayer {
    public static int LAST_GET_BATTERYLEVEL_PERCENT = 70;
    public static long LAST_GET_BATTERYLEVEL_TIME;
    protected static Timer u;
    protected ImageView A;
    protected Dialog B;
    protected ProgressBar C;
    protected TextView D;
    protected ImageView E;
    protected Dialog F;
    protected ProgressBar G;
    protected TextView H;
    public ImageView backButton;
    private BroadcastReceiver battertReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                int percent = level * 100 / scale;
                LAST_GET_BATTERYLEVEL_PERCENT = percent;
                setBatteryLevel();
                getContext().unregisterReceiver(battertReceiver);
            }
        }
    };
    public ImageView batteryLevel;
    public LinearLayout batteryTimeLayout;
    public ProgressBar bottomProgressBar;
    public TextView clarity;
    public PopupWindow clarityPopWindow;
    public ImageView imgShareFacebookPlayer;
    public ImageView imgShareInstaPlayer;
    public ImageView imgShareWhatsAppPlayer;
//    public View layoutSocialIcon;
    public ProgressBar loadingProgressBar;
    public TextView mRetryBtn;
    public LinearLayout mRetryLayout;
    public ImageView thumbImageView;
    public ImageView tinyBackImageView;
    public TextView titleTextView;
    protected DismissControlViewTimerTask v;
    public TextView videoCurrentTime;
    public View viewTransparentForSocialIcon;
    protected Dialog w;
    protected ProgressBar x;
    protected TextView y;
    protected TextView z;

    public class DismissControlViewTimerTask extends TimerTask {
        final   JZVideoPlayerStandard a;

        public DismissControlViewTimerTask(JZVideoPlayerStandard jZVideoPlayerStandard) {
            this.a = jZVideoPlayerStandard;
        }

        public void run() {
            this.a.dissmissControlView();
        }
    }

    public JZVideoPlayerStandard(Context context) {
        super(context);
    }

    public JZVideoPlayerStandard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void init(Context context) {
        super.init(context);
        this.batteryTimeLayout = (LinearLayout) findViewById(R.id.battery_time_layout);
        this.bottomProgressBar = (ProgressBar) findViewById(R.id.bottom_progress);
        this.titleTextView = (TextView) findViewById(R.id.title);
        this.backButton = (ImageView) findViewById(R.id.back);
        this.thumbImageView = (ImageView) findViewById(R.id.thumb);
        this.loadingProgressBar = (ProgressBar) findViewById(R.id.loading);
        this.tinyBackImageView = (ImageView) findViewById(R.id.back_tiny);
        this.batteryLevel = (ImageView) findViewById(R.id.battery_level);
        this.videoCurrentTime = (TextView) findViewById(R.id.video_current_time);
        this.clarity = (TextView) findViewById(R.id.clarity);
        this.mRetryBtn = (TextView) findViewById(R.id.retry_btn);
        this.mRetryLayout = (LinearLayout) findViewById(R.id.retry_layout);
//        this.layoutSocialIcon = findViewById(R.id.layoutSocialIcon);
        this.viewTransparentForSocialIcon = findViewById(R.id.viewTransparentForSocialIcon);
//        this.imgShareWhatsAppPlayer = (ImageView) findViewById(R.id.imgShareWhatsAppPlayer);
//        this.imgShareFacebookPlayer = (ImageView) findViewById(R.id.imgShareFacebookPlayer);
//        this.imgShareInstaPlayer = (ImageView) findViewById(R.id.imgShareInstaPlayer);
        this.thumbImageView.setOnClickListener(this);
        this.backButton.setOnClickListener(this);
        this.tinyBackImageView.setOnClickListener(this);
        this.clarity.setOnClickListener(this);
        this.mRetryBtn.setOnClickListener(this);
    }

    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen, objects);
        if (objects.length != 0) titleTextView.setText(objects[0].toString());
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            fullscreenButton.setImageResource(R.drawable.jz_shrink);
            backButton.setVisibility(View.VISIBLE);
            tinyBackImageView.setVisibility(View.INVISIBLE);
            batteryTimeLayout.setVisibility(View.VISIBLE);
            if (((LinkedHashMap) dataSourceObjects[0]).size() == 1) {
                clarity.setVisibility(GONE);
            } else {
                clarity.setText(JZUtils.getKeyFromDataSource(dataSourceObjects, currentUrlMapIndex));
                clarity.setVisibility(View.VISIBLE);
            }
            changeStartButtonSize((int) getResources().getDimension(R.dimen.jz_start_button_w_h_fullscreen));
        } else if (currentScreen == SCREEN_WINDOW_NORMAL
                || currentScreen == SCREEN_WINDOW_LIST) {
            fullscreenButton.setImageResource(R.drawable.jz_enlarge);
            backButton.setVisibility(View.GONE);
            tinyBackImageView.setVisibility(View.INVISIBLE);
            changeStartButtonSize((int) getResources().getDimension(R.dimen.jz_start_button_w_h_normal));
            batteryTimeLayout.setVisibility(View.GONE);
            clarity.setVisibility(View.GONE);
        } else if (currentScreen == SCREEN_WINDOW_TINY) {
            tinyBackImageView.setVisibility(View.VISIBLE);
            setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
            batteryTimeLayout.setVisibility(View.GONE);
            clarity.setVisibility(View.GONE);
        }
        setSystemTimeAndBattery();


        if (q) {
            q = false;
            JZVideoPlayerManager.setFirstFloor(this);
            backPress();
        }
    }
    public void changeStartButtonSize(int i) {
        ViewGroup.LayoutParams layoutParams = this.startButton.getLayoutParams();
        layoutParams.height = i;
        layoutParams.width = i;
        layoutParams = this.loadingProgressBar.getLayoutParams();
        layoutParams.height = i;
        layoutParams.width = i;
    }

    public int getLayoutId() {
        return R.layout.jz_layout_standard;
    }

    public void onStateNormal() {
        super.onStateNormal();
        changeUiToNormal();
    }

    public void onStatePreparing() {
        super.onStatePreparing();
        changeUiToPreparing();
    }

    public void onStatePreparingChangingUrl(int i, long j) {
        super.onStatePreparingChangingUrl(i, j);
        this.loadingProgressBar.setVisibility(VISIBLE);
        this.startButton.setVisibility(INVISIBLE);
    }

    public void onStatePlaying() {
        super.onStatePlaying();
        changeUiToPlayingClear();
    }

    public void onStatePause() {
        super.onStatePause();
        changeUiToPauseShow();
        cancelDismissControlViewTimer();
    }

    public void onStateError() {
        super.onStateError();
        changeUiToError();
    }

    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        changeUiToComplete();
        cancelDismissControlViewTimer();
        this.bottomProgressBar.setProgress(100);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();
        if (id != R.id.surface_container) {
            if (id == R.id.bottom_seek_progress) {
                switch (motionEvent.getAction()) {
                    case 0:
                        cancelDismissControlViewTimer();
                        break;
                    case 1:
                        startDismissControlViewTimer();
                        break;
                    default:
                        break;
                }
            }
        }
        switch (motionEvent.getAction()) {
            case 0:
            case 2:
                break;
            case 1:
                startDismissControlViewTimer();
                if (this.k) {
                    long duration = getDuration();
                    long j = this.p * 100;
                    if (duration == 0) {
                        duration = 1;
                    }
                    this.bottomProgressBar.setProgress((int) (j / duration));
                }
                if (!(this.k || this.j)) {
                    onEvent(102);
                    onClickUiToggle();
                    break;
                }
            default:
                break;
        }
        return super.onTouch(view, motionEvent);
    }

    public void onClick(View view) {
        super.onClick(view);
        Log.e(TAG, "onClick: called"+view.animate() );
        Log.e(TAG, "onClick: called"+view.getId() );
        int id = view.getId();
//        if (id == R.id.thumb) {
//            if (i == R.id.thumb) {
//                if (dataSourceObjects == null || JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex) == null) {
//                    Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (currentState == CURRENT_STATE_NORMAL) {
//                    if (!JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex).toString().startsWith("file") &&
//                            !JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex).toString().startsWith("/") &&
//                            !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
//                        showWifiDialog();
//                        return;
//                    }
//                    startVideo();
//                    onEvent(JZUserActionStandard.ON_CLICK_START_THUMB);//开始的事件应该在播放之后，此处特殊
//                } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
//                    onClickUiToggle();
//                }
//            }
//        }

        if (id == R.id.thumb) {
            if (this.dataSourceObjects != null) {
                if (JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex) != null) {
                    if (this.currentState == 0) {
                        if (JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex).toString().startsWith("file") || JZUtils.getCurrentFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex).toString().startsWith("/") || JZUtils.isWifiConnected(getContext()) || !JZUtils.isMobileDataConnected(getContext()) || WIFI_TIP_DIALOG_SHOWED) {
                            onEvent(101);
                            startVideo();
                            if (this.s != null) {
                                this.s.onClick(view);
                            }
                        } else {
                            showWifiDialog();
                            return;
                        }
                    } else if (this.currentState == 6) {
                        onClickUiToggle();
                    }
                }
                else
                {
//                    Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (id == R.id.surface_container) {
            startDismissControlViewTimer();
        } else if (id == R.id.back) {
            JZVideoPlayer.backPress();
        } else if (id == R.id.back_tiny) {
            if (JZVideoPlayerManager.getFirstFloor().currentScreen == 1) {
                JZVideoPlayer.quitFullscreenOrTinyWindow();
            } else {
                JZVideoPlayer.backPress();
            }
        } else if (id == R.id.clarity) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.jz_layout_clarity, null);

            OnClickListener mQualityListener = new OnClickListener() {
                public void onClick(View v) {
                    int index = (int) v.getTag();
                    onStatePreparingChangingUrl(index, getCurrentPositionWhenPlaying());
                    clarity.setText(JZUtils.getKeyFromDataSource(dataSourceObjects, currentUrlMapIndex));
                    for (int j = 0; j < layout.getChildCount(); j++) {//设置点击之后的颜色
                        if (j == currentUrlMapIndex) {
                            ((TextView) layout.getChildAt(j)).setTextColor(Color.parseColor("#fff85959"));
                        } else {
                            ((TextView) layout.getChildAt(j)).setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                    if (clarityPopWindow != null) {
                        clarityPopWindow.dismiss();
                    }
                }
            };
            for (int j = 0; j < ((LinkedHashMap) dataSourceObjects[0]).size(); j++) {
                String key = JZUtils.getKeyFromDataSource(dataSourceObjects, j);
                TextView clarityItem = (TextView) View.inflate(getContext(), R.layout.jz_layout_clarity_item, null);
                clarityItem.setText(key);
                clarityItem.setTag(j);
                layout.addView(clarityItem, j);
                clarityItem.setOnClickListener(mQualityListener);
                if (j == currentUrlMapIndex) {
                    clarityItem.setTextColor(Color.parseColor("#fff85959"));
                }
            }
            clarityPopWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
            clarityPopWindow.setContentView(layout);
            clarityPopWindow.showAsDropDown(clarity);
            layout.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int offsetX = clarity.getMeasuredWidth() / 3;
            int offsetY = clarity.getMeasuredHeight() / 3;
            clarityPopWindow.update(clarity, - offsetX, - offsetY, Math.round(layout.getMeasuredWidth() * 2), layout.getMeasuredHeight());
        } else if (id == R.id.retry_btn) {
            if (dataSourceObjects == null || JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex) == null) {
//                Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex).toString().startsWith("file") && !
                    JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex).toString().startsWith("/") &&
                    !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                showWifiDialog();
                return;
            }
            initTextureView();//和开始播放的代码重复
            addTextureView();
            JZMediaManager.setDataSource(dataSourceObjects);
            JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex));
            onStatePreparing();
            onEvent(JZUserAction.ON_CLICK_START_ERROR);

        }
    }

    public void showWifiDialog() {
        super.showWifiDialog();
        Builder builder = new Builder(getContext());
        builder.setMessage(getResources().getString(R.string.tips_not_wifi));
        builder.setPositiveButton(getResources().getString(R.string.tips_not_wifi_confirm), new DialogInterface.OnClickListener( ) {

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
               onEvent(103);
                 startVideo();
                JZVideoPlayer.WIFI_TIP_DIALOG_SHOWED = true;
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.tips_not_wifi_cancel), new DialogInterface.OnClickListener( ) {

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                clearFloatScreen();
            }
        });
        builder.setOnCancelListener(new OnCancelListener( ) {

            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
        cancelDismissControlViewTimer();
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        super.onStopTrackingTouch(seekBar);
        if (this.currentState == 3) {
            dissmissControlView();
        } else {
            startDismissControlViewTimer();
        }
    }

    public void onClickUiToggle() {
        if (this.bottomContainer.getVisibility() != VISIBLE) {
            setSystemTimeAndBattery();
            this.clarity.setText(JZUtils.getKeyFromDataSource(this.dataSourceObjects, this.currentUrlMapIndex));
        }
        if (this.currentState == 1) {
            changeUiToPreparing();
            if (this.bottomContainer.getVisibility() != VISIBLE) {
                setSystemTimeAndBattery();
            }
        } else if (this.currentState == 3) {
            if (this.bottomContainer.getVisibility() == VISIBLE) {
                changeUiToPlayingClear();
            } else {
                changeUiToPlayingShow();
            }
        } else if (this.currentState != 5) {
        } else {
            if (this.bottomContainer.getVisibility() == VISIBLE) {
                changeUiToPauseClear();
            } else {
                changeUiToPauseShow();
            }
        }
    }

    public void setSystemTimeAndBattery() {
        this.videoCurrentTime.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        if (System.currentTimeMillis() - LAST_GET_BATTERYLEVEL_TIME > 30000) {
            LAST_GET_BATTERYLEVEL_TIME = System.currentTimeMillis();
            getContext().registerReceiver(this.battertReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            return;
        }
        setBatteryLevel();
    }

    public void setBatteryLevel() {
        int i = LAST_GET_BATTERYLEVEL_PERCENT;
        if (i < 15) {
            this.batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_10);
        } else if (i >= 15 && i < 40) {
            this.batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_30);
        } else if (i >= 40 && i < 60) {
            this.batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_50);
        } else if (i >= 60 && i < 80) {
            this.batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_70);
        } else if (i >= 80 && i < 95) {
            this.batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_90);
        } else if (i >= 95 && i <= 100) {
            this.batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_100);
        }
    }

    public void onCLickUiToggleToClear() {
        if (this.currentState == 1) {
            if (this.bottomContainer.getVisibility() == VISIBLE) {
                changeUiToPreparing();
            }
        } else if (this.currentState == 3) {
            if (this.bottomContainer.getVisibility() == VISIBLE) {
                changeUiToPlayingClear();
            }
        } else if (this.currentState == 5) {
            if (this.bottomContainer.getVisibility() == VISIBLE) {
                changeUiToPauseClear();
            }
        } else if (this.currentState == 6 && this.bottomContainer.getVisibility() == VISIBLE) {
            changeUiToComplete();
        }
    }

    public void setProgressAndText(int i, long j, long j2) {
        super.setProgressAndText(i, j, j2);
        if (i != 0) {
            this.bottomProgressBar.setProgress(i);
        }
    }

    public void setBufferProgress(int i) {
        super.setBufferProgress(i);
        if (i != 0) {
            this.bottomProgressBar.setSecondaryProgress(i);
        }
    }

    public void resetProgressAndTime() {
        super.resetProgressAndTime();
        this.bottomProgressBar.setProgress(0);
        this.bottomProgressBar.setSecondaryProgress(0);
    }

    public void changeUiToNormal() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisiblity(0, 4, 0, 4, 0, 4, 4);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisiblity(0, 4, 0, 4, 0, 4, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToPreparing() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisiblity(4, 4, 4, 0, 0, 4, 4);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisiblity(4, 4, 4, 0, 0, 4, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToPlayingShow() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisiblity(0, 0, 0, 4, 4, 4, 4);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisiblity(0, 0, 0, 4, 4, 4, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToPlayingClear() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisiblity(4, 4, 4, 4, 4, 0, 4);
                return;
            case 2:
                setAllControlsVisiblity(4, 4, 4, 4, 4, 0, 4);
                return;
            default:
                return;
        }
    }

    public void changeUiToPauseShow() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisiblity(0, 0, 0, 4, 4, 4, 4);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisiblity(0, 0, 0, 4, 4, 4, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToPauseClear() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisiblity(4, 4, 4, 4, 4, 0, 4);
                return;
            case 2:
                setAllControlsVisiblity(4, 4, 4, 4, 4, 0, 4);
                return;
            default:
                return;
        }
    }

    public void changeUiToComplete() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisiblity(0, 4, 0, 4, 0, 4, 4);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisiblity(0, 4, 0, 4, 0, 4, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToError() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisiblity(4, 4, 0, 4, 4, 4, 0);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisiblity(0, 4, 0, 4, 4, 4, 0);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void setAllControlsVisiblity(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (this.r) {
         //   this.topContainer.setVisibility(i);
            this.topContainer.setVisibility(INVISIBLE);
            this.bottomContainer.setVisibility(INVISIBLE);
            this.startButton.setVisibility(i3);
            this.loadingProgressBar.setVisibility(i4);
            this.thumbImageView.setVisibility(i5);
            this.bottomProgressBar.setVisibility(INVISIBLE);
            this.mRetryLayout.setVisibility(i7);
            return;
        }
       // this.topContainer.setVisibility(i);
        this.topContainer.setVisibility(INVISIBLE);
        //this.bottomContainer.setVisibility(i2);
        this.bottomContainer.setVisibility(INVISIBLE);
        this.startButton.setVisibility(i3);
        this.loadingProgressBar.setVisibility(i4);
        this.thumbImageView.setVisibility(i5);
        this.bottomProgressBar.setVisibility(i6);
        this.mRetryLayout.setVisibility(i7);
    }

    public void updateStartImage() {
        if (this.r) {
//            this.layoutSocialIcon.setVisibility(GONE);
            this.viewTransparentForSocialIcon.setVisibility(GONE);
            if (this.currentState == 3) {
                this.startButton.setVisibility(INVISIBLE);
            } else if (this.currentState == 7) {
                this.startButton.setImageResource(R.drawable.jz_click_replay_selector);
                this.startButton.setVisibility(VISIBLE);
            } else if (this.currentState == 6) {
                this.startButton.setVisibility(INVISIBLE);
            } else {
                this.startButton.setImageResource(R.drawable.ic_gif);
            }
        } else {
//            this.layoutSocialIcon.setVisibility(GONE);
            this.viewTransparentForSocialIcon.setVisibility(GONE);
            if (this.currentState == 3) {
                this.startButton.setVisibility(VISIBLE);
                this.startButton.setImageResource(R.drawable.jz_click_pause_selector);
            } else if (this.currentState == 7) {
                this.startButton.setVisibility(INVISIBLE);
            } else if (this.currentState == 6) {
                this.startButton.setVisibility(VISIBLE);
                this.startButton.setImageResource(R.drawable.jz_click_replay_selector);
//                this.layoutSocialIcon.setVisibility(VISIBLE);
                this.viewTransparentForSocialIcon.setVisibility(VISIBLE);
            } else {
                this.startButton.setImageResource(R.drawable.jz_click_play_selector);
            }
        }
        if (!this.isShowSocialIcon) {
//            this.layoutSocialIcon.setVisibility(GONE);
            this.viewTransparentForSocialIcon.setVisibility(GONE);
        }
    }

    public void showProgressDialog(float f, String str, long j, String str2, long j2) {
        super.showProgressDialog(f, str, j, str2, j2);
        if (this.w == null) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.jz_dialog_progress, null);
            this.x = (ProgressBar) inflate.findViewById(R.id.duration_progressbar);
            this.y = (TextView) inflate.findViewById(R.id.tv_current);
            this.z = (TextView) inflate.findViewById(R.id.tv_duration);
            this.A = (ImageView) inflate.findViewById(R.id.duration_image_tip);
            this.w = createDialogWithView(inflate);
        }
        if (!this.w.isShowing()) {
            this.w.show();
        }
        this.y.setText(str);
        z.setText(" / " + str2);

        this.x.setProgress(j2 <= 0 ? null : (int) ((j * 100) / j2));
        if (f > 0) {
            this.A.setBackgroundResource(R.drawable.jz_forward_icon);
        } else {
            this.A.setBackgroundResource(R.drawable.jz_backward_icon);
        }
        onCLickUiToggleToClear();
    }

    public void dismissProgressDialog() {
        super.dismissProgressDialog();
        if (this.w != null) {
            this.w.dismiss();
        }
    }

    public void showVolumeDialog(float f, int i) {
        super.showVolumeDialog(f, i);
        if (this.B == null) {
            View vv = LayoutInflater.from(getContext()).inflate(R.layout.jz_dialog_volume, null);
            this.E = (ImageView) vv.findViewById(R.id.volume_image_tip);
            this.D = (TextView) vv.findViewById(R.id.tv_volume);
            this.C = (ProgressBar) vv.findViewById(R.id.volume_progressbar);
            this.B = createDialogWithView(vv);
        }
        if (!this.B.isShowing() ) {
            this.B.show();
        }
        if (i <= 0) {
            this.E.setBackgroundResource(R.drawable.jz_close_volume);
        } else {
            this.E.setBackgroundResource(R.drawable.jz_add_volume);
        }
        if (i > 100) {
            i = 100;
        } else if (i < 0) {
            i = 0;
        }

        D.setText(i + "%");
        this.C.setProgress(i);
        onCLickUiToggleToClear();
    }









    public void dismissVolumeDialog() {
        super.dismissVolumeDialog();
        if (this.B != null) {
            this.B.dismiss();
        }
    }

    public void showBrightnessDialog(int i) {
        super.showBrightnessDialog(i);
        if (this.F == null) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.jz_dialog_brightness, null);
            this.H = (TextView) inflate.findViewById(R.id.tv_brightness);
            this.G = (ProgressBar) inflate.findViewById(R.id.brightness_progressbar);
            this.F = createDialogWithView(inflate);
        }
        if (!this.F.isShowing()) {
            this.F.show();
        }
        if (i > 100) {
            i = 100;
        } else if (i < 0) {
            i = 0;
        }
        TextView textView = this.H;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append("%");
        textView.setText(stringBuilder.toString());
        this.G.setProgress(i);
        onCLickUiToggleToClear();
    }

    public void dismissBrightnessDialog() {
        super.dismissBrightnessDialog();
        if (this.F != null) {
            this.F.dismiss();
        }
    }

    public Dialog createDialogWithView(View localView) {
        Dialog dialog = new Dialog(getContext(), R.style.jz_style_dialog_progress);
        dialog.setContentView(localView);
        Window window = dialog.getWindow();
        window.addFlags(Window.FEATURE_ACTION_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        window.setLayout(-2, -2);
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(localLayoutParams);
        return dialog;
    }


    public void startDismissControlViewTimer() {
        cancelDismissControlViewTimer();
        u = new Timer();
        this.v = new DismissControlViewTimerTask(this);
        u.schedule(this.v, 2500);
    }

    public void cancelDismissControlViewTimer() {
        if (u != null) {
            u.cancel();
        }
        if (this.v != null) {
            this.v.cancel();
        }
    }

    public void onAutoCompletion() {
        super.onAutoCompletion();
        cancelDismissControlViewTimer();
    }

    public void onCompletion() {
        super.onCompletion();
        cancelDismissControlViewTimer();
        if (this.clarityPopWindow != null) {
            this.clarityPopWindow.dismiss();
        }
    }

    public void dissmissControlView() {
        if (this.currentState != 0 && this.currentState != 7 && this.currentState != 6) {
            post(new Runnable( ) {


                public void run() {
                     bottomContainer.setVisibility(INVISIBLE);
                    topContainer.setVisibility(INVISIBLE);
                   startButton.setVisibility(INVISIBLE);
                    if ( clarityPopWindow != null) {
                         clarityPopWindow.dismiss();
                    }
                    if ( currentScreen != 3 && ! r) {
                         bottomProgressBar.setVisibility(VISIBLE);
                    }
                }
            });
        }
    }
}
