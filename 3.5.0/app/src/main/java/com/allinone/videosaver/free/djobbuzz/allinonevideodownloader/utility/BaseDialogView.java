package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import androidx.core.content.ContextCompat;

import videodownload.com.newmusically.R;

public abstract class BaseDialogView {
    public Context mCtx;
    public Dialog mDlg;
    public View mView;

    public BaseDialogView(Context context) {
        this.mCtx = context;
    }

    public void dismiss() {
        Dialog dialog = this.mDlg;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public Dialog getDlg() {
        return this.mDlg;
    }

    public abstract View getView();

    public void showDialogView() {
        Dialog dialog = this.mDlg;
        if (dialog != null) {
            dialog.dismiss();
        }
        Activity activity = (Activity) this.mCtx;
        if (activity != null && !activity.isFinishing()) {
            this.mView = getView();
            dialog = new Dialog(this.mCtx);
            this.mDlg = dialog;
            dialog.setCancelable(true);
            this.mDlg.setCanceledOnTouchOutside(true);
            Window window = this.mDlg.getWindow();
            window.setContentView(this.mView);
            window.setBackgroundDrawable(ContextCompat.getDrawable(this.mCtx, R.drawable.dialog_background));
            this.mDlg.show();
        }
    }
}
