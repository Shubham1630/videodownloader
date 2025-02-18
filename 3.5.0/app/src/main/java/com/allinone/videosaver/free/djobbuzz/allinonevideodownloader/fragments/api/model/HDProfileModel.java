package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HDProfileModel implements Serializable {
    @SerializedName("height")
    private int height;
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
}