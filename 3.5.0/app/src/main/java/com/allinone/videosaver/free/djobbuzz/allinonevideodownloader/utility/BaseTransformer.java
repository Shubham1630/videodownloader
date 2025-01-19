package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.view.View;

import androidx.viewpager.widget.ViewPager.PageTransformer;

public abstract class BaseTransformer implements PageTransformer {
    protected abstract void isone(View view, float f);

    protected boolean isone() {
        return true;
    }

    protected boolean istwo() {
        return false;
    }

    protected void isthree(View view, float f) {
    }

    public void transformPage(View view, float f) {
        istwo(view, f);
        isone(view, f);
        isthree(view, f);
    }

    protected void istwo(View view, float f) {
        float width = (float) view.getWidth();
        float f2 = 0.0f;
        view.setRotationX(0.0f);
        view.setRotationY(0.0f);
        view.setRotation(0.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        view.setTranslationY(0.0f);
        view.setTranslationX(istwo() ? 0.0f : (-width) * f);
        if (isone()) {
            if (f > -1.0f && f < 1.0f) {
                f2 = 1.0f;
            }
            view.setAlpha(f2);
            return;
        }
        view.setAlpha(1.0f);
    }
}
