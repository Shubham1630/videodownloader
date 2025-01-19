package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CaptionModel implements Serializable {
    @SerializedName("content_type")
    private String content_type;
    @SerializedName("created_at")
    private long created_at;
    @SerializedName("created_at_utc")
    private long created_at_utc;
    @SerializedName("media_id")
    private long media_id;
    @SerializedName("status")
    private String status;
    @SerializedName("text")
    private String text;
    @SerializedName("type")
    private int type;

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public long getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(long j) {
        this.created_at = j;
    }

    public long getCreated_at_utc() {
        return this.created_at_utc;
    }

    public void setCreated_at_utc(long j) {
        this.created_at_utc = j;
    }

    public String getContent_type() {
        return this.content_type;
    }

    public void setContent_type(String str) {
        this.content_type = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public long getMedia_id() {
        return this.media_id;
    }

    public void setMedia_id(long j) {
        this.media_id = j;
    }
}
