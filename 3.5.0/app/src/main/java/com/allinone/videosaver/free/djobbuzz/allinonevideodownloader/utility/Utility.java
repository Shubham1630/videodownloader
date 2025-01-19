package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.DownloadListAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.app.App;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.FileItem;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.siaray.downloadmanagerplus.classes.Downloader;
import ir.siaray.downloadmanagerplus.enums.DownloadStatus;
import ir.siaray.downloadmanagerplus.interfaces.ActionListener;
import ir.siaray.downloadmanagerplus.model.DownloadItem;
import ir.siaray.downloadmanagerplus.utils.Utils;
import videodownload.com.newmusically.R;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler.gettxlink;

public class Utility {
    private static final String TAG = "Utility";
    /* In app purchase product ID */
    public static final String IAP_ID = "adsremovallifetime";

    /* In app purchase licence key */
    public static final String IAP_LICENCE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtLFQyY8rTO+H3wGJi0jMYn+IbHvxnn80ZBdFEyczut5wNrDH/u734+mLj9bRH7cxvsL8Aha7z0nBPdEd20c6pXzTwkivKOOnuTQWZmSq1bj/rQbwCCq+KVLA2P01PrdXico2KpiKkl1gbQgETk1f4b8JBO6a2z6UEDEaNMTRjO6aDETGFjwXDHOAhYh1QFQtUvSnABHSDSV0FGvA5nw962o+awWS/uuiHC9VYbtS89tdVP8ygiEJI9KSrctvbtGXj1v0bO73B9KXlTarVtM8AnjJ7DQNxjupwJ763oggSdcT/DVKOBjodVltqrs+BWad4YpmTEzhsMnUJebiqJr6qwIDAQAB";

    /* To Enable/Disable IN App purchase option , Default is true*/
    public static final boolean IS_ALLOW_IN_APP_PURCHASE = true;

    /* To enable /Disable Youtube feature, Default is false (set to true if needed) */
    public static final boolean is_yns = false;


    public static final String ABSOLUTEPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String STORAGEDIR = getstoragedir();
    public static int NOTIFICATION_VISIBILITY = DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED;
    public static final String nodatafound = "No data found";
    public static final String nointernet = "Please check your Internet Connection";
    public static final String tryagain = "Try again later";
    public static final String tryagain_login = "Please login with instagram to download private images/videos.";
    public static final String alreadydownloaded = "Video already downloaded";
    public static final String dirforwhatsapp = Environment.getExternalStorageDirectory() + "/WhatsApp/Media/.Statuses";
    public static final String dirforwbussines = Environment.getExternalStorageDirectory() + "/WhatsApp Business/Media/.Statuses";
    public static final String dirformoj = Environment.getExternalStorageDirectory() + "/.Moj/Camera";

    /* whatsapp dir location*/
    public static final String wabasedirname = ABSOLUTEPATH + "/WhatsApp/";
    public static final String databasePath = wabasedirname + "Databases";
    public static final String wallpaperPath = wabasedirname + "Media/WallPaper";
    public static final String imagepathSent = wabasedirname + "Media/WhatsApp Images/Sent";
    public static final String imagepath = wabasedirname + "Media/WhatsApp Images";
    public static final String voicePath = wabasedirname + "Media/WhatsApp Voice Notes";
    public static final String voicePathSent = wabasedirname + "Media/WhatsApp Voice Notes/Sent";
    public static final String audioPath = wabasedirname + "Media/WhatsApp Audio";
    public static final String audioPathSent = wabasedirname + "Media/WhatsApp Audio/Sent";
    public static final String videoPath = wabasedirname + "Media/WhatsApp Video";
    public static final String videoPathSent = wabasedirname + "Media/WhatsApp Video/Sent";
    public static final String profilePath = wabasedirname + "Media/WhatsApp Profile Photos";
    public static final String documentspath = wabasedirname + "Media/WhatsApp Documents";
    public static final String documentspathSent = wabasedirname + "Media/WhatsApp Documents/Sent";
    public static final String stickerpath = wabasedirname + "Media/WhatsApp Stickers";
    public static final String stickerpathSent = wabasedirname + "Media/WhatsApp Stickers/Sent";


    /* whatsapp bussiness dir location*/
    public static final String wabasedirname_wb = ABSOLUTEPATH + "/WhatsApp Business/";
    public static final String databasePath_wb = wabasedirname_wb + "Databases";
    public static final String wallpaperPath_wb = wabasedirname_wb + "Media/WallPaper";
    public static final String imagepathSent_wb = wabasedirname_wb + "Media/WhatsApp Business Images/Sent";
    public static final String imagepath_wb = wabasedirname_wb + "Media/WhatsApp Business Images";
    public static final String voicePath_wb = wabasedirname_wb + "Media/WhatsApp Business Voice Notes";
    public static final String voicePathSent_wb = wabasedirname_wb + "Media/WhatsApp Business Voice Notes/Sent";
    public static final String audioPath_wb = wabasedirname_wb + "Media/WhatsApp Business Audio";
    public static final String audioPathSent_wb = wabasedirname_wb + "Media/WhatsApp Business Audio/Sent";
    public static final String videoPath_wb = wabasedirname_wb + "Media/WhatsApp Business Video";
    public static final String videoPathSent_wb = wabasedirname_wb + "Media/WhatsApp Business Video/Sent";
    public static final String profilePath_wb = wabasedirname_wb + "Media/WhatsApp Business Profile Photos";
    public static final String documentspath_wb = wabasedirname_wb + "Media/WhatsApp Business Documents";
    public static final String documentspathSent_wb = wabasedirname_wb + "Media/WhatsApp Business Documents/Sent";
    public static final String stickerpath_wb = wabasedirname_wb + "Media/WhatsApp Business Stickers";
    public static final String stickerpathSent_wb = wabasedirname_wb + "Media/WhatsApp Business Stickers/Sent";
    // change privacy url here
    public static final String privacyurl = "https://www.djobbuzz.com/dvideodownloader_privacypolicy.html";
    public static final String sharetext = "Best " + App.getContext().getResources().getString(R.string.app_name) + " App in Playstore \n Click to Download Now http://play.google.com/store/apps/details?id=" + App.getContext().getPackageName();

    /**
     * user agents
     */

    public static final String USER_AGENT_1 = "Mozilla/5.0 (Linux; Android 9; ONEPLUS A5000) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.92 Mobile Safari/537.36";
    public static final String USER_AGENT_2 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";

    /**
     * language code
     */
    public static final String LANG_EN = "en";
    public static final String LANG_IN = "in";
    public static final String LANG_IT = "it";
    public static final String LANG_KO = "ko";
    public static final String LANG_HI = "hi";
    public static final String LANG_AR = "ar";
    public static final String LANG_RU = "ru";
    public static final String LANG_MA = "ma";
    public static String COOKIE_FACEBOOK = "";
    public static String txurl = gettxlink();
    private static Toast toast;
    private static final Pattern urlPattern = Pattern.compile("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)", 42);


    public static int convertDpToPixel(int i, Context context) {
        return (int) (((float) i) * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static void shareVideoToOtherApps(Activity activity, String str, Uri uri) {
        shareVideoToOtherApps(activity, str, uri, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static void shareVideoToOtherApps(Activity activity, String str, Uri uri, boolean z) {
        Intent intent = new Intent("android.intent.action.SEND");
        if (z) {
            intent.setType("video/*");
        } else {
            intent.setType("image/*");
        }
        intent.putExtra("android.intent.extra.SUBJECT", sharetext);
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.putExtra("android.intent.extra.TEXT", str);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT < 22) {
            activity.startActivity(Intent.createChooser(intent, "Choose one..."));
            return;
        }
        activity.startActivity(Intent.createChooser(intent, "Choose one...", PendingIntent.getBroadcast(activity, 0, new Intent(activity, ShareCallBackBroadcastReceiver.class).addFlags(FLAG_ACTIVITY_NEW_TASK), PendingIntent.FLAG_UPDATE_CURRENT).getIntentSender()));
    }

    public static void shareVideoWhatsApp(Context activity, String str, Uri uri) {
        shareVideoWhatsApp(activity, str, uri, true);
    }

    public static void shareVideoWhatsApp(Context activity, String str, Uri uri, boolean z) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setPackage("com.whatsapp");
        if (z) {
            intent.setType("video/*");
        } else {
            intent.setType("image/*");
        }
        intent.putExtra("android.intent.extra.SUBJECT", sharetext);
        intent.putExtra("android.intent.extra.TEXT", str);
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(activity, "Install WhatsApp first", Toast.LENGTH_SHORT).show();

        }
    }

    public static void setToast(Context _mContext, String str) {
        Toast toast = Toast.makeText(_mContext, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String videoUrl(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals("")) {
            Matcher matcher = urlPattern.matcher(str);
            if (matcher.find()) {
                return str.substring(matcher.start(1), matcher.end());
            }
        }
        return null;
    }


    public static ArrayList<String> patternforsharechat(String str) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Matcher matcher = Pattern.compile("\\(?\\b(http(s?)://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]").matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            if (group.startsWith("(") && group.endsWith(")")) {
                group = group.substring(1, group.length() - 1);
            }
            arrayList.add(group);
        }
        return arrayList;
    }

    public static void RateApp(Context context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    public static void ShareApp(Context context) {

        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        sendInt.putExtra(Intent.EXTRA_TEXT, sharetext);
        sendInt.setType("text/plain");
        context.startActivity(Intent.createChooser(sendInt, "Share"));
    }

    private static String getstoragedir() {
        try {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void handledarkmode(Activity activity) {
        try {
            String[] darkModeValues = activity.getResources().getStringArray(R.array.dark_mode_values);
            SharedPreferences sharedPreferences = activity.getSharedPreferences("Theme", Context.MODE_PRIVATE);
            String string = sharedPreferences.getString(activity.getString(R.string.dark_mode), activity.getString(R.string.dark_mode_def_value));
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setDownloadBackgroundColor(View view, DownloadStatus status) {
        if (status == DownloadStatus.SUCCESSFUL) {
            view.setBackgroundResource(0);
        } else if (status == DownloadStatus.FAILED) {
            view.setBackgroundResource(R.drawable.download_button_background_shape_red);
        } else if (status == DownloadStatus.PENDING || status == DownloadStatus.PAUSED) {
            view.setBackgroundResource(R.drawable.download_button_background_shape_yellow);
        } else if (status == DownloadStatus.RUNNING) {
            view.setBackgroundResource(R.drawable.download_button_background_shape_blue);
        } else {
            view.setBackgroundResource(R.drawable.download_button_background_shape_gray);
        }
    }


    public static String getFileShortName(String name) {
        if (name.length() > 10) {
            name = name.substring(0, 5) + ".." + name.substring(name.length() - 4, name.length());
        }
        return name;
    }

    public static void showPopUpMenu(final Activity activity, View view, final FileItem item, final ActionListener deleteListener, DownloadListAdapter.DeleteListner deleteListner1, int pos) {
        PopupMenu overflowPopupMenu = new PopupMenu(activity, view);
        overflowPopupMenu.getMenuInflater().inflate(R.menu.popup_overflow_options, overflowPopupMenu.getMenu());

        overflowPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.popUpCancel:
                        cancelDownload(activity, item);
                        break;
                    case R.id.popUpDelete:
                        new AlertDialog.Builder(activity)
                                .setTitle("Delete File")

                                .setMessage("Are you sure you want to delete this File?")
                                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteFile(activity, item, deleteListener, deleteListner1, pos))
                                .setNegativeButton(android.R.string.no, null)
                                .show();

                        break;
                    case R.id.popUpDetails:

                        String url = item.getCommonModel().getSaved_video_path();
                        if (url != null) {
                            int i = url.lastIndexOf('.');
                            if (i < 0) {
                                url = url + "/" + item.getCommonModel().getSaved_video_name();
                            }
                        } else {
                            url = "";
                        }
                        WhatsAppStatusHandler.copyStatusInSavedDirectory(url, "", "s", activity);
                        //showInfoDialog(activity, Downloader.getDownloadItem(activity, item.getToken()));
                        break;
                }
                return true;
            }
        });
        // ir.siaray.downloadmanagerplussample.Log.print("status: " + item.getToken() + " : " + item.getDownloadStatus());
        MenuItem cancelButton = overflowPopupMenu.getMenu().getItem(0);
        if (item.getDownloadStatus() == DownloadStatus.SUCCESSFUL
                || item.getDownloadStatus() == DownloadStatus.FAILED
                || item.getDownloadStatus() == DownloadStatus.CANCELED
                || item.getDownloadStatus() == DownloadStatus.NONE)
            cancelButton.setVisible(false);
        overflowPopupMenu.show();
    }

    public static void deleteFile(Context context, FileItem item, ActionListener deleteListener, DownloadListAdapter.DeleteListner deleteListner1, int pos) {
        Downloader downloader = Downloader.getInstance(context)
                .setUrl(item.getUri())
                .setListener(item.getListener());

        boolean deleted = downloader.deleteFile(item.getToken(), deleteListener);
        Log.e(TAG, "deleteFile: " + deleted);
        if (deleteListner1 != null)
            deleteListner1.ondeleted(item.getCommonModel().getUid(), pos);

        //    ir.siaray.downloadmanagerplussample.Log.print("File deleted: " + deleted);
    }


    public static void cancelDownload(Context context, FileItem item) {
        Downloader downloader = Downloader.getInstance(context)
                .setUrl(item.getUri())
                .setListener(item.getListener());

        downloader.cancel(item.getToken());
    }

    public static void showInfoDialog(final Activity activity, final DownloadItem downloadItem) {
        if (downloadItem == null) {
            Toast.makeText(activity, "Download details not available", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog dialog;
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        dialog = builder.setTitle("Details")
                .setMessage(ir.siaray.downloadmanagerplus.utils.Log.printItems(downloadItem))
                .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openFile(activity, downloadItem.getLocalUri());

                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }

    public static boolean isStoragePermissionGranted(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity
                , WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        return true;
    }

    public static String getSafeString(Object obj) {
        return obj != null ? obj.toString() : "";
    }

    public static boolean isNullOrEmpty(String str) {
        str = getSafeString(str);
        return TextUtils.isEmpty(str) || str.toLowerCase().equals("null");
    }

    public static int getSafeInt32(Object obj) {
        try {
            return Integer.parseInt(getSafeString(obj));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getSafeLong(Object obj) {
        try {
            return (long) Integer.parseInt(getSafeString(obj));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String formatSeconds(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";
        if (hours < 10)
            formattedTime += "0";
        formattedTime += hours + ":";

        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }

    public static String FOLDER = "/In Insta/";
    public static File DIRECTORY_FOLDER = new File(Environment.getExternalStorageDirectory() + "/Download/In Insta/");

    public static void createFile() {
        if (!DIRECTORY_FOLDER.exists()) {
            DIRECTORY_FOLDER.mkdirs();
        }
    }



}
