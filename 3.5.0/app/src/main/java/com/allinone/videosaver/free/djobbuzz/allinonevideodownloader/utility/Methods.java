package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.content.Context;
import android.content.SharedPreferences;


public class Methods {

    private Context _context;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;


    public Methods(Context context){
        this._context = context;
        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }




}
