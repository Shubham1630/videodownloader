package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.ReelFeedModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FullDetailModelNew implements Serializable {

    @SerializedName("reels_media")
    private ArrayList<ReelFeedModel> reels_media;

    public ArrayList<ReelFeedModel> getReels_media() {
        return reels_media;
    }

    public void setReels_media(ArrayList<ReelFeedModel> reels_media) {
        this.reels_media = reels_media;
    }
}


