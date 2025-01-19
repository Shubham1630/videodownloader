package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelMyStories implements Serializable {

    @SerializedName("tray")
    private ArrayList<ModelTrail> tray;

    @SerializedName("status")
    private String status;

    public ArrayList<ModelTrail> getTray() {
        return tray;
    }

    public void setTray(ArrayList<ModelTrail> tray) {
        this.tray = tray;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StoryModel{" +
                "tray=" + tray +
                ", status='" + status + '\'' +
                '}';
    }
}
