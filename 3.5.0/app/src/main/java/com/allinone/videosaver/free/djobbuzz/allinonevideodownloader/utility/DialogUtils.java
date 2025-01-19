package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build.VERSION;

import androidx.core.view.ViewCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;




import videodownload.com.newmusically.R;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.convertDpToPixel;


public class DialogUtils {
    private static final int DAYS_UNTIL_PROMPT_RATE_DIALOG = 3;
    private static Dialog dialog = null;
    private static boolean isForceClose = false;
    private static int mColorBtnBackground = -1;
    private static int mColorBtnBorder = -16777216;
    private static int mColorBtnText = -16777216;
    private static int mColorDescTextDialog = -16777216;
    private static int mColorTitleDialog = -16777216;
    private static int mCornerRadiBtn = 0;
    private static int mImgRateDialogRes = 0;
    private static int mImgShareDialogRes = 0;
    private static int mImgUpdateForceDialogRes = 0;
    private static String mSubjectToShare = "";
    private static String mTextToShare = "";
    private static int mWidthBtnBorder;

    private static Drawable getBtnDrawable() {
        if (mCornerRadiBtn == 0) {
            throw new IllegalStateException("You must init DialogUtils class using 'DialogUtils.wtih(Context)' method");
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mColorBtnBackground);
        gradientDrawable.setStroke(mWidthBtnBorder, mColorBtnBorder);
        gradientDrawable.setCornerRadius((float) mCornerRadiBtn);
        return gradientDrawable;
    }

    private static int getColorPrimary(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new TypedValue().data, new int[]{R.attr.colorPrimary});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    private static int getColorPrimaryDark(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new TypedValue().data, new int[]{R.attr.colorPrimaryDark});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    private static void rateApp(Context context) {
        String stringBuilder = "market://details?id=" + context.getPackageName();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder));
        intent.addFlags(VERSION.SDK_INT >= 21 ? 1208483840 : 1476427776);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            String stringBuilder2 = "http://play.google.com/store/apps/details?id=" +
                    context.getPackageName();
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder2)));
        }
    }


    public static void showAppRaterIfNeeded(Activity activity) {
        DialogPrefManager dialogPrefManager = new DialogPrefManager(activity);
        if (!dialogPrefManager.isAppRaterClicked()) {
            long valueOf = dialogPrefManager.getDateFirstLoadedApp();
            if (valueOf == 0) {
                valueOf = System.currentTimeMillis();
                dialogPrefManager.setDateFirstLoadedApp(valueOf);
            }
            if (System.currentTimeMillis() >= valueOf + 259200000 && dialogPrefManager.getAppRateThreshold() < 3) {
                dialogPrefManager.setAppRateCount(dialogPrefManager.getAppRateCount() + 1);
                if (dialogPrefManager.getAppRateCount() == 5 || dialogPrefManager.getAppRateCount() == 15 || dialogPrefManager.getAppRateCount() == 30) {
                    dialogPrefManager.setAppRateThreshold(dialogPrefManager.getAppRateThreshold() + 1);
                    showRateAppDialog(activity);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Date long : ");
            stringBuilder.append(dialogPrefManager.getDateFirstLoadedApp());
            Log.d("myRaterCount", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Threshold : ");
            stringBuilder.append(dialogPrefManager.getAppRateThreshold());
            stringBuilder.append("  Count : ");
            stringBuilder.append(dialogPrefManager.getAppRateCount());
            Log.d("myRaterCount", stringBuilder.toString());
        }
    }

    private static void showRateAppDialog(final Activity activity) {
        final DialogPrefManager dialogPrefManager = new DialogPrefManager(activity);
        if (dialog == null || !dialog.isShowing()) {
            dialog = new Dialog(activity, R.style.Theme_D1NoTitleDim);
            dialog.setContentView(R.layout.dialog_rate_app);
            ((ImageView) dialog.findViewById(R.id.imgAppRateDialog)).setImageResource(mImgRateDialogRes);
            TextView textView = (TextView) dialog.findViewById(R.id.txtTitleRate);
            TextView textView2 = (TextView) dialog.findViewById(R.id.txtDescRate);
            ViewCompat.setBackground(dialog.findViewById(R.id.layoutBtn), getBtnDrawable());
            textView.setTextColor(mColorTitleDialog);
            textView2.setTextColor(mColorDescTextDialog);
            textView = (TextView) dialog.findViewById(R.id.txtBtnRate);
            textView.setTextColor(mColorBtnText);
            textView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    dialogPrefManager.setAppRaterClicked();
                    DialogUtils.dialog.dismiss();
                    DialogUtils.rateApp(activity);
                }
            });
            dialog.show();
        }
    }


    public static DialogUtils with(Context context) {
        mColorBtnBackground = getColorPrimary(context);
        mColorBtnBorder = getColorPrimaryDark(context);
        mWidthBtnBorder = convertDpToPixel(1, context);
        mCornerRadiBtn = convertDpToPixel(19, context);
        return new DialogUtils();
    }

    public DialogUtils setRateDialogImageRes(int i) {
        mImgRateDialogRes = i;
        return this;
    }
    public DialogUtils setTitleAndDescTextColors(int i, int i2) {
        mColorTitleDialog = i;
        mColorDescTextDialog = i2;
        return this;
    }
    public DialogUtils setButtonColors(int i, int i2) {
        mColorBtnBackground = i;
        mColorBtnBorder = i2;
        return this;
    }
    public DialogUtils setButtonTextColor(int i) {
        mColorBtnText = i;
        return this;
    }

}
