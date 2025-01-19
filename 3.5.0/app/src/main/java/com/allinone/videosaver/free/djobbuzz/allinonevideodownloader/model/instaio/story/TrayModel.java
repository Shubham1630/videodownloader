package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TrayModel implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("items")
    private ArrayList<ItemModel> items;
    @SerializedName("media_count")
    private int media_count;
    @SerializedName("user")
    private UserModel user;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public UserModel getUser() {
        return this.user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public int getMedia_count() {
        return this.media_count;
    }

    public void setMedia_count(int i) {
        this.media_count = i;
    }

    public ArrayList<ItemModel> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<ItemModel> arrayList) {
        this.items = arrayList;
    }
}
