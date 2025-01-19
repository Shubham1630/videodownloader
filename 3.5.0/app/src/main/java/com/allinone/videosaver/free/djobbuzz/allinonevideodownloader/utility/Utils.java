package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;


import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DashboardFragment.forinstagram;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.WhatsAppStatusHandler.copyStatusInSavedDirectory;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database.DBOperations;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import videodownload.com.newmusically.R;

public class Utils {
    private static final String TAG = "Utils";
    public static String FilePath = null;
    public static File RootDirectoryInstaShow;
    public static String StaticShareDownloadRepost;
    private static Context context;

    public static ProgressDialog progress = null;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append("/Download/InstaPro");
        RootDirectoryInstaShow = new File(stringBuilder.toString());
        String str = "";
        StaticShareDownloadRepost = str;
        FilePath = str;
    }

    public Utils(Context context) {
        this.context = context;
    }

    public static void setToast(Context context, String str) {
        Toast makeText = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    public static void createFileFolder() {
        if (!RootDirectoryInstaShow.exists()) {
            RootDirectoryInstaShow.mkdirs();
        }
    }


    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static File dirname = null;
    private static File checkfileexistsornnot = null;
    private static String downloadfilename = "";
    private static PrefManager prefManager;


    public static void startDownload(String str,   Context context, String str3, String str4) {

        prefManager = new PrefManager(context);
        Log.e(TAG, "startDownload: str url:" + str);
        Log.e(TAG, "startDownload: str3 filename:" + str3);
        Log.e(TAG, "startDownload: str4: from " + str4);
        String path = Utility.STORAGEDIR;

        checkfileexistsornnot = new File(path);
        if (!checkfileexistsornnot.exists()) {
            checkfileexistsornnot.mkdir();
        }
        path = checkfileexistsornnot.getAbsolutePath() + "/";
        dirname = new File(path);
        if (!dirname.exists()) {
            dirname.mkdir();
        }
        downloadfilename = forinstagram + "_" + str3;
        CommonModel commonModel = new CommonModel();
        commonModel.setSaved_video_url(str);
        commonModel.setSaved_video_date(String.valueOf(System.currentTimeMillis()));
        commonModel.setSaved_video_path(dirname.getAbsolutePath());
        commonModel.setSaved_video_name(downloadfilename);
        Toast.makeText(context, context.getResources().getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        DBOperations.InsertDownload(context, commonModel, new DownloadListener() {
            @Override
            public void onComplete(int totalBytes) {
                Toast.makeText(context, context.getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();

                String stringBuilder = checkfileexistsornnot + "/" + downloadfilename;
                File file2 = new File(stringBuilder);
                //file.renameTo(file2);
                addVideo(file2, context, str4.equalsIgnoreCase("Share"));
                /*if (dirname.exists()) {
                    dirname.delete();
                }*/
                if (prefManager.getSaveFirstLaunch()) {
                    prefManager.setSaveFirstLaunch(false);

                }
            }

            @Override
            public void onPause(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onPending(int percent, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onFail(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {
                Log.e(TAG, "onFail: called" + reason.getValue());
            }

            @Override
            public void onCancel(int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onRunning(int percent, int totalBytes, int downloadedBytes, float downloadSpeed) {

            }
        });

    }

    public static void startDownloadNew(String downloadPath, String destinationPath, Context context, String FileName) {
        Log.d(TAG,"Downloading....");
        Uri uri = Uri.parse(downloadPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(FileName+"");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS,destinationPath+FileName);
        ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);

        try {
            if (Build.VERSION.SDK_INT >= 21) {
                MediaScannerConnection.scanFile(context, new String[]{new File(DIRECTORY_DOWNLOADS + "/" + destinationPath + FileName).getAbsolutePath()},
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
            } else {
                context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(DIRECTORY_DOWNLOADS + "/" + destinationPath + FileName))));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void addVideo(File file, Context context, boolean isshareselected) {
        if (isshareselected) {
            isshareselected = false;
            copyStatusInSavedDirectory(file.getAbsolutePath(), "", "s", ((Activity) context));
            //shareVideoToOtherApps(activity,);
        }
       /* ContentValues contentValues = new ContentValues(3);
        contentValues.put("title", file.getName());
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("_data", file.getAbsolutePath());
        context.getApplicationContext().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);*/

    }

    /*public static void DownloadFile(String str, String str2, Context context, String str3) {
        Request request = new Request(Uri.parse(str));
        request.setAllowedNetworkTypes(3);
        request.setNotificationVisibility(1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str3);
        stringBuilder.append("");
        request.setTitle(stringBuilder.toString());
        request.setVisibleInDownloadsUi(true);
        String str4 = Environment.DIRECTORY_DOWNLOADS;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str2);
        stringBuilder2.append(str3);
        request.setDestinationInExternalPublicDir(str4, stringBuilder2.toString());
        ((DownloadManager) context.getSystemService("download")).enqueue(request);
        try {
            String str5 = "/";
            StringBuilder stringBuilder3;
            if (VERSION.SDK_INT >= 19) {
                String[] strArr = new String[1];
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(Environment.DIRECTORY_DOWNLOADS);
                stringBuilder3.append(str5);
                stringBuilder3.append(str2);
                stringBuilder3.append(str3);
                strArr[0] = new File(stringBuilder3.toString()).getAbsolutePath();
                MediaScannerConnection.scanFile(context, strArr, null, new OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
                return;
            }
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(Environment.DIRECTORY_DOWNLOADS);
            stringBuilder3.append(str5);
            stringBuilder3.append(str2);
            stringBuilder3.append(str3);
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(stringBuilder3.toString()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public static void shareImage(Context context, String str) {
       /* try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.TEXT", context.getResources().getString(R.string.share_txt));
            intent.putExtra("android.intent.extra.STREAM", Uri.parse(Media.insertImage(context.getContentResolver(), str, "", null)));
            intent.setType("image/*");
            context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.share_image_via)));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0044 */
    /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|3|(2:5|6)(1:7)|8|9|10|11|12|17) */
    /* JADX WARNING: Missing block: B:16:?, code skipped:
            return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void shareImageVideoOnInstagram(Context context, String str, boolean z) {
      /*  String str2 = "";
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setPackage("com.instagram.android");
            intent.putExtra("android.intent.extra.TEXT", str2);
            String str3 = "android.intent.extra.STREAM";
            if (z) {
                intent.putExtra(str3, Uri.parse(str));
                intent.setType("video/*");
            } else {
                intent.putExtra(str3, Uri.parse(Media.insertImage(context.getContentResolver(), str, str2, null)));
                intent.setType("image/*");
            }
            intent.addFlags(1);
            context.startActivity(intent);
            setToast(context, context.getResources().getString(R.string.instagram_not_installed));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static void shareVideo(Context context, String str) {
      /*  Uri parse = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("video/mp4");
        intent.putExtra("android.intent.extra.STREAM", parse);
        intent.addFlags(1);
        try {
            context.startActivity(Intent.createChooser(intent, "Share Video using"));
        } catch (ActivityNotFoundException unused) {
            setToast(context, context.getResources().getString(R.string.no_app_found));
        }*/
    }


    public static String getDate(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return simpleDateFormat.format(instance.getTime());
    }

    public static String getTimeAgoString(long j, Context context) {
        try {
            Date parse = new SimpleDateFormat("dd/MM/yyyy").parse(getDate(j));
            Date date = new Date();
            long toSeconds = TimeUnit.MILLISECONDS.toSeconds(date.getTime() - parse.getTime());
            long toMinutes = TimeUnit.MILLISECONDS.toMinutes(date.getTime() - parse.getTime());
            long toHours = TimeUnit.MILLISECONDS.toHours(date.getTime() - parse.getTime());
            j = TimeUnit.MILLISECONDS.toDays(date.getTime() - parse.getTime());
            StringBuilder stringBuilder;
            if (toSeconds < 60) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(toSeconds);
                stringBuilder.append(context.getResources().getString(R.string.second_ago));
                return stringBuilder.toString();
            } else if (toMinutes < 60) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(toMinutes);
                stringBuilder.append(context.getResources().getString(R.string.minutes_ago));
                return stringBuilder.toString();
            } else if (toHours < 24) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(toHours);
                stringBuilder.append(context.getResources().getString(R.string.hours_ago));
                return stringBuilder.toString();
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(j);
                stringBuilder2.append(context.getResources().getString(R.string.day_ago));
                return stringBuilder2.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void showProgress(Context context) {
       /* if (context != null) {
            try {
                if (progress == null || !progress.isShowing()) {
                    progress = ProgressDialog.show(context, null, null);
                    progress.getWindow().setBackgroundDrawableResource(17170445);
                    progress.setContentView(R.layout.progress_loading);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    public static void hideProgress() {
        ProgressDialog progressDialog = progress;
        if (progressDialog != null && progressDialog.isShowing()) {
            progress.dismiss();
        }
    }

    public static void sessionTimeout(Context context) {

    }


}
