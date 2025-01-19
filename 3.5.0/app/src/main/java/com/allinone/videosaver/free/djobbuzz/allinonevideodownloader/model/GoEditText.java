package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.GoEditTextListener;

import java.util.ArrayList;
import java.util.Iterator;

public class GoEditText extends AppCompatEditText {
    ArrayList<GoEditTextListener> a = new ArrayList();

    public GoEditText(Context context) {
        super(context);
    }

    public GoEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GoEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void addListener(GoEditTextListener goEditTextListener) {
        try {
            this.a.add(goEditTextListener);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean onTextContextMenuItem(int i) {
        boolean onTextContextMenuItem = super.onTextContextMenuItem(i);
        switch (i) {
            case 16908320:
                onTextCut();
                return onTextContextMenuItem;
            case 16908321:
                onTextCopy();
                return onTextContextMenuItem;
            case 16908322:
                onTextPaste();
                return onTextContextMenuItem;
            default:
                return onTextContextMenuItem;
        }
    }

    public void onTextCopy() {
    }

    public void onTextCut() {
    }

    public void onTextPaste() {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            ((GoEditTextListener) it.next()).onUpdate();
        }
    }
}
