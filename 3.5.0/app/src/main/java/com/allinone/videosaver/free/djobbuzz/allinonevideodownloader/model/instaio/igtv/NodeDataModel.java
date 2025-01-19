package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.igtv;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NodeDataModel implements Serializable {
    @SerializedName("display_url")
    private String display_url;
    @SerializedName("id")
    private String id;
    @SerializedName("is_video")
    private boolean is_video;
    @SerializedName("shortcode")
    private String shortcode;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getShortcode() {
        return this.shortcode;
    }

    public void setShortcode(String str) {
        this.shortcode = str;
    }

    public String getDisplay_url() {
        return this.display_url;
    }

    public void setDisplay_url(String str) {
        this.display_url = str;
    }

    public boolean isIs_video() {
        return this.is_video;
    }

    public void setIs_video(boolean z) {
        this.is_video = z;
    }
}
