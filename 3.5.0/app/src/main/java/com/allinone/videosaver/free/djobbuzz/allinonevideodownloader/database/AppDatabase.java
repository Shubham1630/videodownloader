package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;



@Database(entities = {CommonModel.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract Daos.CommonDaoInterface commonDaoInterface();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.class.getName())
                            .fallbackToDestructiveMigration()
//                            // allow queries on the main thread.
//                            // Don't do this on a real app! See PersistenceBasicSample for an example.
//                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
