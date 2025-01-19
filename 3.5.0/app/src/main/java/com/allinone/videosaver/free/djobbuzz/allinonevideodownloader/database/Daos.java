package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;

import java.util.List;


public class Daos {

    @Dao
    public static abstract class CommonDaoInterface {


        @Transaction
        void updateCommonModel(CommonModel channelList) {
            insert(channelList);
        }

        @Insert
        abstract void insert(CommonModel channelList);

        @Insert
        abstract long insertdata(CommonModel channelList);

        @Insert
        abstract void insertAll(CommonModel... commonModels);

        @Query("SELECT * From CommonModel ORDER BY uid DESC")
        abstract List<CommonModel> getAllDownloads();


        @Query("DELETE From CommonModel")
        abstract void deleteAll();

        @Query("DELETE From CommonModel where uid=:id")
        abstract void deletefile(long id);
    }


}
