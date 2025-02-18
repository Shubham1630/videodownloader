package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.api.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GraphModel implements Serializable {

    @SerializedName("shortcode_media")
    private ShortMediaModel shortcodeMedia;

    public ShortMediaModel getShortcodeMedia() {
        return this.shortcodeMedia;
    }

    public void setShortcodeMedia(ShortMediaModel shortcodeMedia2) {
        this.shortcodeMedia = shortcodeMedia2;
    }

}
