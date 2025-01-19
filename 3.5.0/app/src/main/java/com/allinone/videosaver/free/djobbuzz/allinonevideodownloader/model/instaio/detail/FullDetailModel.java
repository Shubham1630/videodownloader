package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FullDetailModel implements Serializable {
    @SerializedName("feed")
    private FeedModel feed;
    @SerializedName("reel_feed")
    private ReelFeedModel reel_feed;
    @SerializedName("user_detail")
    private UserDetailModel user_detail;

    public UserDetailModel getUser_detail() {
        return this.user_detail;
    }

    public void setUser_detail(UserDetailModel userDetailModel) {
        this.user_detail = userDetailModel;
    }

    public FeedModel getFeed() {
        return this.feed;
    }

    public void setFeed(FeedModel feedModel) {
        this.feed = feedModel;
    }

    public ReelFeedModel getReel_feed() {
        return this.reel_feed;
    }

    public void setReel_feed(ReelFeedModel reelFeedModel) {
        this.reel_feed = reelFeedModel;
    }
}
