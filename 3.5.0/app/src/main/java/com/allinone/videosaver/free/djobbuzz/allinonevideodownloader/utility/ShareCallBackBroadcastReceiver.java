package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresApi;

public class ShareCallBackBroadcastReceiver extends BroadcastReceiver {
    @RequiresApi(api = 22)
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            StringBuilder stringBuilder;
            String stringBuilder2;
            ComponentName componentName = (ComponentName) intent.getExtras().getParcelable("android.intent.extra.CHOSEN_COMPONENT");
            String str = "ShareCallBackBroadcastReceiver";
            if (componentName != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Class name : ");
                stringBuilder.append(componentName.getClassName());
                stringBuilder2 = stringBuilder.toString();
            } else {
                stringBuilder2 = "ComponentName is null";
            }
            str = "ShareCallBackBroadcastReceiver";
            if (componentName != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Package name : ");
                stringBuilder.append(componentName.getPackageName());
                stringBuilder2 = stringBuilder.toString();
            } else {
                stringBuilder2 = "ComponentName is null";
            }
            if (componentName != null) {
            }
        }
    }
}
