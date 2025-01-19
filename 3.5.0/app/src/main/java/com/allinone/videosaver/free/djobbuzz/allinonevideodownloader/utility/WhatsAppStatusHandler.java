package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.app.App;

import org.apache.commons.io.FileUtils;

import java.io.File;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.shareVideoToOtherApps;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.shareVideoWhatsApp;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.sharetext;


public class WhatsAppStatusHandler {
    private static final String TAG = "WhatsAppStatusHandler";

    private static class CopyStatusTask extends AsyncTask<Void, Void, Boolean> {

        String filename;
        String from;
        String is;
        Activity activity;


        private CopyStatusTask(String str, String from, String is, Activity activity) {
            Log.e(TAG, "CopyStatusTask:from: " + from);
            Log.e(TAG, "CopyStatusTask: str:" + str);
            Log.e(TAG, "CopyStatusTask: is:" + is);
            this.filename = str;
            this.from = from;
            this.is = is;
            this.activity = activity;
        }


        protected Boolean doInBackground(Void... voidArr) {
            return WhatsAppStatusHandler.copyFileInSavedDir(this.filename, from);
        }


        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (bool && is.equals("d")) {
                Toast.makeText(App.getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
            } else if (bool && is.equals("w")) {
                Log.e(TAG, "onPostExecute:getSavedDir: " + getSavedDir(from));
                Log.e(TAG, "onPostExecute:getName: " + new File(filename).getName());
                Log.e(TAG, "onPostExecute:filename: " + filename);
                Log.e(TAG, "onPostExecute:from: " + from);
                File to = new File(getSavedDir(from), new File(filename).getName().replace("_transcode", ""));
                Log.e(TAG, "onPostExecute: " + to.getName());
                Log.e(TAG, "onPostExecute:11 " + to.getAbsolutePath());
                if (!to.getAbsolutePath().contains(from)) {
                    Log.e(TAG, "onPostExecute: not contains");
                    to = new File(getSavedDir(from), from+new File(  filename).getName().replace("_transcode", ""));
                } else {
                    Log.e(TAG, "onPostExecute: contains");
                }
                Log.e(TAG, "onPostExecute: 222" + to.getAbsolutePath());
                shareVideoWhatsApp(App.getContext(), sharetext, Uri.fromFile(to));
            } else if (bool && is.equals("s")) {
                File to = new File(getSavedDir(from), new File(filename).getName().replace("_transcode", ""));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    shareVideoToOtherApps(activity, sharetext, Uri.fromFile(to));
                }

            }
        }
    }


    @Nullable
    public static File getSavedDir(String path) {
        String stringBuilder = Utility.STORAGEDIR;//+ path;
        File file = new File(stringBuilder);
        return (file.exists() || file.isDirectory() || file.mkdirs()) ? file : null;
    }

    public static boolean isFileExistInSavedDire(String fileName, String str) {
        try {
            Log.e(TAG, "isFileExistInSavedDire:fileName " + fileName);
            Log.e(TAG, "isFileExistInSavedDire:str: " + str);
            Log.e(TAG, "isFileExistInSavedDire:getSavedDir: " + getSavedDir(str));
            String stringBuilder = getSavedDir(str) + File.separator + fileName;
            if (!stringBuilder.contains(str)) {
                stringBuilder = getSavedDir(str) + File.separator + str + fileName;
            }

            Log.e(TAG, "isFileExistInSavedDire: stringBuilder:" + stringBuilder);
            File file = new File(stringBuilder);
            Log.d("myFile", file.getAbsolutePath());
            return file.exists();
        } catch (Exception unused) {
            unused.printStackTrace();
            return false;
        }
    }

    private static boolean copyFileInSavedDir(String str, String path) {
        try {
            Log.e(TAG, "copyFileInSavedDir:str: " + str);
            Log.e(TAG, "copyFileInSavedDir:path: " + path);
            Log.e(TAG, "copyFileInSavedDir:name: " + new File(path + str).getName().replace("_transcode", ""));

            if (isFileExistInSavedDire(new File(path + str).getName().replace("_transcode", ""), path)) {
                return true;
            }
            Log.e(TAG, "copyFileInSavedDir: " + new File(str).getAbsolutePath());
            Log.e(TAG, "copyFileInSavedDir: " + getSavedDir(path).getAbsolutePath());
            if (!new File(str).getAbsolutePath().equalsIgnoreCase(getSavedDir(path).getAbsolutePath())) {
                FileUtils.copyFileToDirectory(new File(str), getSavedDir(path));
            }
            if (str.contains("_transcode")) {
                File from = new File(getSavedDir(path), new File(str).getName());
                File to = new File(getSavedDir(path), new File(path + str).getName().replace("_transcode", ""));
                from.renameTo(to);
            } else {
                Log.e(TAG, "copyFileInSavedDir: not contain _transcode so rename here for whatsapp");
                if (path.equalsIgnoreCase("whatsapp") || path.equalsIgnoreCase("whatsappb")) {
                    Log.e(TAG, "copyFileInSavedDir: path contain ->" + path);
                    File from = new File(getSavedDir(path), new File(str).getName());
                    Log.e(TAG, "copyFileInSavedDir:from: " + from.getAbsolutePath());
                    Log.e(TAG, "copyFileInSavedDir: path + str:" + (path + str));
                    File to = new File(getSavedDir(path), path + from.getName());
                    Log.e(TAG, "copyFileInSavedDir: to:" + to.getAbsolutePath());
                    from.renameTo(to);
                } else {
                    Log.e(TAG, "copyFileInSavedDir: equalsIgnoreCase not match-->" + path);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void copyStatusInSavedDirectory(String str, String from, String is, Activity activity) {
        Log.e(TAG, "copyStatusInSavedDirectory: str:" + str);
        Log.e(TAG, "copyStatusInSavedDirectory: from:" + from);
        Log.e(TAG, "copyStatusInSavedDirectory: is:" + is);
        new CopyStatusTask(str, from, is, activity).execute();
    }
}
