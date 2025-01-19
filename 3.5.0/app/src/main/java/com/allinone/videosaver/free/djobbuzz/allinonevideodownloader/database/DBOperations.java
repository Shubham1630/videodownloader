package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database;

import android.app.DownloadManager;
import android.content.Context;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.FileItem;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.AsyncTaskCoroutine;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.DownloaderNew;

import ir.siaray.downloadmanagerplus.enums.DownloadStatus;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import ir.siaray.downloadmanagerplus.model.DownloadItem;
import ir.siaray.downloadmanagerplus.utils.Utils;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.NOTIFICATION_VISIBILITY;

public class DBOperations {

    private static final String TAG ="DBOperations" ;

    public static void InsertDownload(Context context, CommonModel commonModel, DownloadListener downloadListener) {
        new AsyncTaskCoroutine<Object, Object>() {
            long uid;
            @Override
            public Object doInBackground(Object... params) {
                uid = DatabaseRoom.with(context).insertDownload(commonModel);
                return null;
            }

            @Override
            public void onPostExecute(Object result) {
                super.onPostExecute(result);
                DownloadItem item = new DownloadItem();
                item.setToken(String.valueOf(uid));
                item.setUri(commonModel.getSaved_video_url());
                item.setLocalFilePath(commonModel.getSaved_video_path());
                FileItem fileItem = new FileItem(item);

                fileItem.setUuid(uid);
                fileItem.setCommonModel(commonModel);

                final DownloaderNew downloader = getDownloader(fileItem, context,downloadListener);

                if (downloader.getStatus(item.getToken()) == DownloadStatus.RUNNING
                        || downloader.getStatus(item.getToken()) == DownloadStatus.PAUSED
                        || downloader.getStatus(item.getToken()) == DownloadStatus.PENDING)
                    downloader.cancel(item.getToken());
                else if (downloader.getStatus(item.getToken()) == DownloadStatus.SUCCESSFUL) {
                    Utils.openFile(context, downloader.getDownloadedFilePath(item.getToken()));
                } else
                    downloader.start();
            }
        }.execute();

    }

    public static DownloaderNew getDownloader(FileItem item, Context context, DownloadListener downloadListener) {
        DownloaderNew request = DownloaderNew.getInstance(context)
                   .setListener(downloadListener)
                .setUrl(item.getUri())
                .setToken(item.getToken())
                .setKeptAllDownload(false)//if true: canceled download token keep in db
                .setAllowedOverRoaming(true)
                .setVisibleInDownloadsUi(true)
                .setDescription(Utils.readableFileSize(item.getFileSize()))
                .setScanningByMediaScanner(true)
                .setNotificationVisibility(NOTIFICATION_VISIBILITY)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                //.setCustomDestinationDir(DOWNLOAD_DIRECTORY, Utils.getFileName(item.getUri()))//TargetApi 28 and lower
                .setCustomDestinationDir(item.getCommonModel().getSaved_video_path(), item.getCommonModel().getSaved_video_name())
                .setNotificationTitle(item.getCommonModel().getSaved_video_name());
        request.setAllowedOverMetered(true); //Api 16 and higher
        return request;
    }
}
