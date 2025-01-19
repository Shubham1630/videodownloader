package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.app.Activity;
import android.content.Context;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.VideoHistoryModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.IOnFetchCompleted;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FetchVideoUtils {
    private static final String TAG = "FetchVideoUtils";
    public static FetchVideoUtils _this;
    public final Object lock = new Object();
    public Context mCtx;

    public interface IDownloadFinisheCallback {
        void onDownloadFailed(int i);

        void onDownloadFinished(List<VideoHistoryModel> list);
    }

    public interface IQueryVideoSizeCallback {
        void onQueryFinished();
    }

    private void fetchFacebook(final String str, final IDownloadFinisheCallback iDownloadFinisheCallback) {
        FbFetch.getData(str, new IOnFetchCompleted() {
            public void onError(int i) {
                if (i == 407) {
                    iDownloadFinisheCallback.onDownloadFailed(i);
                    return;
                }
                // TODO: 3/12/2021
                iDownloadFinisheCallback.onDownloadFailed(-1);
            }

            public void onFetchCompleted(List<VideoHistoryModel> list) {
                FetchVideoUtils.this.prepareData(list, iDownloadFinisheCallback);
            }
        });
    }

    private void fetchFb(String str, IOnFetchCompleted iOnFetchCompleted) {
    }


    private void prepareData(List<VideoHistoryModel> list, IDownloadFinisheCallback iDownloadFinisheCallback) {
        if (list.size() > 0) {
            iDownloadFinisheCallback.onDownloadFinished(list);
        } else {
            iDownloadFinisheCallback.onDownloadFailed(0);
        }
    }

    private void setContext(Context context) {
        this.mCtx = context;
    }

    public static FetchVideoUtils sharedInstance(Context context) {
        if (_this == null) {
            _this = new FetchVideoUtils();
        }
        _this.setContext(context);
        return _this;
    }


    public void fetchVideo(String str, IDownloadFinisheCallback iDownloadFinisheCallback) {
        if (str.contains("facebook.com") || str.contains("//fb.watch")) {
            if (str.contains("facebook.com/groups0000")) {
                //// TODO: 3/12/2021
                iDownloadFinisheCallback.onDownloadFailed(-1);
                return;
            }
            fetchFacebook(str, iDownloadFinisheCallback);
        }
    }

    public void getVideoSize(final VideoHistoryModel videoHistoryModel, final IQueryVideoSizeCallback iQueryVideoSizeCallback) {
        if (videoHistoryModel.size <= 0) {
            new Thread(new Runnable() {
                public void run() {
                    synchronized (FetchVideoUtils.this.lock) {
                        try {
                            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(videoHistoryModel.video_url).openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                            httpURLConnection.setRequestProperty("Referer", videoHistoryModel.video_url);
                            httpURLConnection.setRequestProperty("Charset", "UTF-8");
                            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                            httpURLConnection.setConnectTimeout(20000);
                            httpURLConnection.setReadTimeout(10000);
                            httpURLConnection.connect();
                            if (httpURLConnection.getResponseCode() == 200) {
                                final long contentLength = (long) httpURLConnection.getContentLength();
                                System.out.println(contentLength);
                                ((Activity) FetchVideoUtils.this.mCtx).runOnUiThread(new Runnable() {
                                    public void run() {
                                        videoHistoryModel.size = contentLength;
                                        iQueryVideoSizeCallback.onQueryFinished();
                                    }
                                });
                            } else {
                                ((Activity) FetchVideoUtils.this.mCtx).runOnUiThread(new Runnable() {
                                    public void run() {
                                        videoHistoryModel.size = -1;
                                        iQueryVideoSizeCallback.onQueryFinished();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
