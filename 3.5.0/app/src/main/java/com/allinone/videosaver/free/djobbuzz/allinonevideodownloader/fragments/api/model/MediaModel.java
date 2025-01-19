package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MediaModel implements Serializable {
    @SerializedName("media")
    private MediaDataModel mediaDataModel;

    public MediaDataModel getMediaDataModel() {
        return this.mediaDataModel;
    }

    public void setMediaDataModel(MediaDataModel mediaDataModel2) {
        this.mediaDataModel = mediaDataModel2;
    }
}