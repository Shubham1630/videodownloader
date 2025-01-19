package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.ItemModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ReelFeedModel implements Serializable {
    @SerializedName("expiring_atexpiring_at")
    private long expiring_at;
    @SerializedName("id")
    private long id;
    @SerializedName("items")
    private ArrayList<ItemModel> items;
    @SerializedName("latest_reel_media")
    private long latest_reel_media;
    @SerializedName("media_count")
    private int media_count;
    @SerializedName("reel_type")
    private String reel_type;
    @SerializedName("seen")
    private long seen;

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public long getLatest_reel_media() {
        return this.latest_reel_media;
    }

    public void setLatest_reel_media(long j) {
        this.latest_reel_media = j;
    }

    public long getExpiring_at() {
        return this.expiring_at;
    }

    public void setExpiring_at(long j) {
        this.expiring_at = j;
    }

    public long getSeen() {
        return this.seen;
    }

    public void setSeen(long j) {
        this.seen = j;
    }

    public String getReel_type() {
        return this.reel_type;
    }

    public void setReel_type(String str) {
        this.reel_type = str;
    }

    public ArrayList<ItemModel> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<ItemModel> arrayList) {
        this.items = arrayList;
    }

    public int getMedia_count() {
        return this.media_count;
    }

    public void setMedia_count(int i) {
        this.media_count = i;
    }
}
