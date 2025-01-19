package com.yxcorp.gifshow.util;

import android.content.Context;

public class CPU {
    static {
        System.loadLibrary("core");
    }

    public static native int getCheckRes(Context context, int i);

    public static native String getClock(Context arg0, byte[] arg1, int arg2);

    public static native String getMagic(Context arg0, int arg1);

    public static synchronized String m39025a(Context context, byte[] bArr, int i) {
        String clock;
        synchronized (CPU.class) {
            clock = getClock(context, bArr, i);
        }
        return clock;
    }

}
