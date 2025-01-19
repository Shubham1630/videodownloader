package cn.jzvid;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.view.Surface;

import java.lang.reflect.Method;
import java.util.Map;

public class JZMediaSystem extends JZMediaInterface implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener {
    public MediaPlayer mediaPlayer;

    public void start() {
        this.mediaPlayer.start();
    }

    public void prepare() {
        try {
            this.mediaPlayer = new MediaPlayer();
            this.mediaPlayer.setAudioStreamType(3);
            if (this.dataSourceObjects.length > 1) {
                this.mediaPlayer.setLooping(((Boolean) this.dataSourceObjects[1]).booleanValue());
            }
            this.mediaPlayer.setOnPreparedListener(this);
            this.mediaPlayer.setOnCompletionListener(this);
            this.mediaPlayer.setOnBufferingUpdateListener(this);
            this.mediaPlayer.setScreenOnWhilePlaying(true);
            this.mediaPlayer.setOnSeekCompleteListener(this);
            this.mediaPlayer.setOnErrorListener(this);
            this.mediaPlayer.setOnInfoListener(this);
            this.mediaPlayer.setOnVideoSizeChangedListener(this);
            Method declaredMethod = MediaPlayer.class.getDeclaredMethod("setDataSource", new Class[]{String.class, Map.class});
            if (this.dataSourceObjects.length > 2) {
                declaredMethod.invoke(this.mediaPlayer, new Object[]{this.currentDataSource.toString(), this.dataSourceObjects[2]});
            } else {
                declaredMethod.invoke(this.mediaPlayer, new Object[]{this.currentDataSource.toString(), null});
            }
            this.mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        this.mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }




    @Override
    public void seekTo(long time) {
        try {
            mediaPlayer.seekTo((int) time);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
    public void release() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
        }
    }

    public long getCurrentPosition() {
        return this.mediaPlayer != null ? (long) this.mediaPlayer.getCurrentPosition() : 0;
    }

    public long getDuration() {
        return this.mediaPlayer != null ? (long) this.mediaPlayer.getDuration() : 0;
    }

    public void setSurface(Surface surface) {
        this.mediaPlayer.setSurface(surface);
    }

    public void setVolume(float f, float f2) {
        this.mediaPlayer.setVolume(f, f2);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        if (currentDataSource.toString().toLowerCase().contains("mp3") ||
                currentDataSource.toString().toLowerCase().contains("wav")) {
            JZMediaManager.instance().mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                        JZVideoPlayerManager.getCurrentJzvd().onPrepared();
                    }
                }
            });
        }
    }


    public void onCompletion(MediaPlayer mediaPlayer) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable( ) {


            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().onAutoCompletion();
                }
            }
        });
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, final int i) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable( ) {


            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().setBufferProgress(i);
                }
            }
        });
    }

    public void onSeekComplete(MediaPlayer mediaPlayer) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable( ) {


            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().onSeekComplete();
                }
            }
        });
    }

    public boolean onError(MediaPlayer mediaPlayer, final int i, final int i2) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable( ) {

            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().onError(i, i2);
                }
            }
        });
        return true;
    }

    public boolean onInfo(MediaPlayer mediaPlayer, final int i, final int i2) {
        JZMediaManager.instance().mainThreadHandler.post(new Runnable( ) {

            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() == null) {
                    return;
                }
                if (i != 3) {
                    JZVideoPlayerManager.getCurrentJzvd().onInfo(i, i2);
                } else if (JZVideoPlayerManager.getCurrentJzvd().currentState == 1 || JZVideoPlayerManager.getCurrentJzvd().currentState == 2) {
                    JZVideoPlayerManager.getCurrentJzvd().onPrepared();
                }
            }
        });
        return false;
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        JZMediaManager.instance().currentVideoWidth = i;
        JZMediaManager.instance().currentVideoHeight = i2;
        JZMediaManager.instance().mainThreadHandler.post(new Runnable( ) {

            public void run() {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().onVideoSizeChanged();
                }
            }
        });
    }
}
