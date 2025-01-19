package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database;

import android.content.Context;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;

import java.util.List;


public class DatabaseRoom {
    private static final String TAG = DatabaseRoom.class.getName();
    private static DatabaseRoom instance;
    public static String[] extra_category = {"All", "Favourites"};
    AppDatabase db;
    public static Context mcontext;

    private DatabaseRoom(Context mContext) {
        db = AppDatabase.getAppDatabase(mContext);
    }

    public static DatabaseRoom with(Context context) {
        mcontext = context;
        instance = new DatabaseRoom(context);
        return instance;
    }

    public long insertDownload(CommonModel commonModel) {
        return db.commonDaoInterface().insertdata(commonModel);
    }

    public List<CommonModel> getAllDownloads() {
        return db.commonDaoInterface().getAllDownloads();
    }

    public void delefile(long fileid) {
        db.commonDaoInterface().deletefile(fileid);
    }
    /*public  CommonModel  getLastInserted() {
      return   db.commonDaoInterface().insertdata();
    }*/


}

