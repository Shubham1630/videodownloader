package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StoryModel implements Serializable {
    @SerializedName("status")
    private String status;
    @SerializedName("tray")
    private ArrayList<TrayModel> tray;

    public ArrayList<TrayModel> getTray() {
        return this.tray;
    }

    public void setTray(ArrayList<TrayModel> arrayList) {
        this.tray = arrayList;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
