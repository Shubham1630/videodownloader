package cn.jzvid;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;

public class JZMediaManager implements SurfaceTextureListener {
    public static final int HANDLER_PREPARE = 0;
    public static final int HANDLER_RELEASE = 2;
    public static final String TAG = "JiaoZiVideoPlayer";
    public static JZMediaManager jzMediaManager;
    public static SurfaceTexture savedSurfaceTexture;
    public static Surface surface;
    public static JZResizeTextureView textureView;
    public int currentVideoHeight = 0;
    public int currentVideoWidth = 0;
    public JZMediaInterface jzMediaInterface;
    public MediaHandler mMediaHandler;
    public HandlerThread mMediaHandlerThread = new HandlerThread("JiaoZiVideoPlayer");
    public Handler mainThreadHandler;
    public int positionInList = -1;

    public class MediaHandler extends Handler {
        public MediaHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_PREPARE:
                    currentVideoWidth = 0;
                    currentVideoHeight = 0;
                    jzMediaInterface.prepare();

                    if (savedSurfaceTexture != null) {
                        if (surface != null) {
                            surface.release();
                        }
                        surface = new Surface(savedSurfaceTexture);
                        jzMediaInterface.setSurface(surface);
                    }
                    break;
                case HANDLER_RELEASE:
                    jzMediaInterface.release();
                    break;
            }
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public JZMediaManager() {
        mMediaHandlerThread = new HandlerThread(TAG);
        mMediaHandlerThread.start();
        mMediaHandler = new MediaHandler(mMediaHandlerThread.getLooper());
        mainThreadHandler = new Handler();
        if (jzMediaInterface == null)
            jzMediaInterface = new JZMediaSystem();
    }

    public static JZMediaManager instance() {
        if (jzMediaManager == null) {
            jzMediaManager = new JZMediaManager();
        }
        return jzMediaManager;
    }

    public static Object[] getDataSource() {
        return instance().jzMediaInterface.dataSourceObjects;
    }

    public static void setDataSource(Object[] objArr) {
        instance().jzMediaInterface.dataSourceObjects = objArr;
    }

    public static Object getCurrentDataSource() {
        return instance().jzMediaInterface.currentDataSource;
    }

    public static void setCurrentDataSource(Object obj) {
        instance().jzMediaInterface.currentDataSource = obj;
    }

    public static long getCurrentPosition() {
        return instance().jzMediaInterface.getCurrentPosition();
    }

    public static long getDuration() {
        return instance().jzMediaInterface.getDuration();
    }

    public static void seekTo(long j) {
        instance().jzMediaInterface.seekTo(j);
    }

    public static void pause() {
        instance().jzMediaInterface.pause();
    }

    public static void start() {
        instance().jzMediaInterface.start();
    }

    public static boolean isPlaying() {
        return instance().jzMediaInterface.isPlaying();
    }

    public void releaseMediaPlayer() {
        this.mMediaHandler.removeCallbacksAndMessages(null);
        Message message = new Message();
        message.what = 2;
        this.mMediaHandler.sendMessage(message);
    }

    public void prepare() {
        releaseMediaPlayer();
        Message message = new Message();
        message.what = 0;
        this.mMediaHandler.sendMessage(message);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        if(JZVideoPlayerManager.getCurrentJzvd() == null) return;
        Log.i(TAG, "onSurfaceTextureAvailable [" + JZVideoPlayerManager.getCurrentJzvd().hashCode() + "] ");
        if (savedSurfaceTexture == null) {
            savedSurfaceTexture = surfaceTexture;
            prepare();
        } else {
            textureView.setSurfaceTexture(savedSurfaceTexture);
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return savedSurfaceTexture == null;
    }
}
