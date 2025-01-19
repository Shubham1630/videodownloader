package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.view.View;

public class RotateDownTransformer extends BaseTransformer {
    private static final float ROT_MOD = -15.0f;

    protected boolean istwo() {
        return true;
    }

    protected void isone(View view, float f) {
        float height = (float) view.getHeight();
        f = (f * -15.0f) * -1.25f;
        view.setPivotX(((float) view.getWidth()) * 0.5f);
        view.setPivotY(height);
        view.setRotation(f);
    }
}
