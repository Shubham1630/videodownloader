package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelFullDetails implements Serializable {

    @SerializedName("user_detail")
    private ModelUsersDetails user_detail;

    @SerializedName("reel_feed")
    private ModelReel reel_feed;

    public ModelUsersDetails getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(ModelUsersDetails user_detail) {
        this.user_detail = user_detail;
    }

    public ModelReel getReel_feed() {
        return reel_feed;
    }

    public void setReel_feed(ModelReel reel_feed) {
        this.reel_feed = reel_feed;
    }
}
