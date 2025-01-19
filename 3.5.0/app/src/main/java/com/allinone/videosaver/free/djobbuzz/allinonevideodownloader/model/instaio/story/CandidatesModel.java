package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CandidatesModel implements Serializable {
    @SerializedName("height")
    private int height;
    @SerializedName("scans_profile")
    private String scans_profile;
    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private int width;

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getScans_profile() {
        return this.scans_profile;
    }

    public void setScans_profile(String str) {
        this.scans_profile = str;
    }
}
