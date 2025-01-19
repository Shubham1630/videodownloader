package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio;

import java.io.Serializable;

public class FullViewModel implements Serializable {
    private String ImageUrl;
    private String Thumbnail;
    private String VideoUrl;
    private String id;
    private boolean isVideo;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getThumbnail() {
        return this.Thumbnail;
    }

    public void setThumbnail(String str) {
        this.Thumbnail = str;
    }

    public String getImageUrl() {
        return this.ImageUrl;
    }

    public void setImageUrl(String str) {
        this.ImageUrl = str;
    }

    public String getVideoUrl() {
        return this.VideoUrl;
    }

    public void setVideoUrl(String str) {
        this.VideoUrl = str;
    }

    public boolean isVideo() {
        return this.isVideo;
    }

    public void setVideo(boolean z) {
        this.isVideo = z;
    }
}
