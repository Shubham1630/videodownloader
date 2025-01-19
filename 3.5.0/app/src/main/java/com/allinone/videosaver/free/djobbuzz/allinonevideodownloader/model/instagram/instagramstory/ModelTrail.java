package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelTrail implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("user")
    private ModelUsersnew user;

    @SerializedName("media_count")
    private int media_count;

    @SerializedName("items")
    private ArrayList<ModelItems> items;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModelUsersnew getUser() {
        return user;
    }

    public void setUser(ModelUsersnew user) {
        this.user = user;
    }

    public int getMedia_count() {
        return media_count;
    }

    public void setMedia_count(int media_count) {
        this.media_count = media_count;
    }

    public ArrayList<ModelItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<ModelItems> items) {
        this.items = items;
    }
}
