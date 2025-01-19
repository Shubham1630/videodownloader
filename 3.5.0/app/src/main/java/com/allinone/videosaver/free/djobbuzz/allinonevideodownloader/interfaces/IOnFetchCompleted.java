package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.VideoHistoryModel;

import java.util.List;

public interface IOnFetchCompleted {
    void onError(int i);

    void onFetchCompleted(List<VideoHistoryModel> list);
}
