package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.broadcast;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class FileObserverService extends Service {
    private static final String TAG = "FileObserverService";
    private FileObserver mFileObserver;
    String ss = "";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String path = Environment.getExternalStorageDirectory() + "/.MojPost/";
            File file = new File(path);
            mFileObserver = new FileObserver(path) {
                @Override
                public void onEvent(int event, String path) {
                    Log.e(TAG, "onEvent: path" + path);
                    if (ss!=null && ss.equals("")) {
                        ss = path;

                           }
                }
            };
            mFileObserver.startWatching();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // The FileObserver starts watching
        return Service.START_NOT_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}