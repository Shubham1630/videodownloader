package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import android.Manifest;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import videodownload.com.newmusically.R;

public class BaseActivity extends AppCompatActivity {
    private int MY_PERMISSIONS_REQUEST = 101;
    private int REQUEST_PERMISSION_SETTING = 102;
    private PermissonDelegate permissonDelegate;

    public interface PermissonDelegate {
        void onAllow();
    }

    public void requestapppermission(PermissonDelegate permissonDelegate) {
        this.permissonDelegate = permissonDelegate;
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ArrayList<String> arrayList = new ArrayList();
        if (checkSelfPermission != 0) {
            arrayList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (arrayList.isEmpty() || VERSION.SDK_INT < 23) {
            permissonDelegate.onAllow();
        } else {
            requestPermissions(arrayList.toArray(new String[0]), this.MY_PERMISSIONS_REQUEST);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.REQUEST_PERMISSION_SETTING) {
            requestapppermission(this.permissonDelegate);
        }
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        String[] darkModeValues = getResources().getStringArray(R.array.dark_mode_values);
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(getString(R.string.dark_mode),  getString(R.string.dark_mode_def_value));
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

    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == this.MY_PERMISSIONS_REQUEST) {
            String str = strArr[0];
            if (iArr.length <= 0 || iArr[0] != 0) {
                Builder builder;
                CharSequence charSequence;
                OnClickListener anonymousClass4;
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
                    builder = new Builder(this);
                    builder.setTitle(getResources().getString(R.string.storageheader));
                    builder.setMessage(getResources().getString(R.string.storagedescription));
                    builder.setPositiveButton(getResources().getString(R.string.ok), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                             requestapppermission(BaseActivity.this.permissonDelegate);
                        }
                    });
                    charSequence =getResources().getString(R.string.cancelpermission);
                    anonymousClass4 = new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    };
                } else {
                    builder = new Builder(this);
                    builder.setTitle(getResources().getString(R.string.storageheader));
                    builder.setMessage(getResources().getString(R.string.storagedescription));
                    builder.setPositiveButton(getResources().getString(R.string.ok), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.fromParts("package", BaseActivity.this.getPackageName(), null));
                            startActivityForResult(intent, BaseActivity.this.REQUEST_PERMISSION_SETTING);
                        }
                    });
                    charSequence = getResources().getString(R.string.cancelpermission);
                    anonymousClass4 = new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    };
                }
                builder.setNegativeButton(charSequence, anonymousClass4);
                builder.show();
            } else if (VERSION.SDK_INT >= 23) {
                this.permissonDelegate.onAllow();
            }
        }
    }



}
