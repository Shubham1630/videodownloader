package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.ItemModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FeedModel implements Serializable {
    @SerializedName("auto_load_more_enabled")
    private boolean auto_load_more_enabled;
    @SerializedName("items")
    private ArrayList<ItemModel> items;
    @SerializedName("more_available")
    private boolean more_available;
    @SerializedName("next_max_id")
    private String next_max_id;
    @SerializedName("num_results")
    private int num_results;

    public ArrayList<ItemModel> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<ItemModel> arrayList) {
        this.items = arrayList;
    }

    public int getNum_results() {
        return this.num_results;
    }

    public void setNum_results(int i) {
        this.num_results = i;
    }

    public boolean isMore_available() {
        return this.more_available;
    }

    public void setMore_available(boolean z) {
        this.more_available = z;
    }

    public String getNext_max_id() {
        return this.next_max_id;
    }

    public void setNext_max_id(String str) {
        this.next_max_id = str;
    }

    public boolean isAuto_load_more_enabled() {
        return this.auto_load_more_enabled;
    }

    public void setAuto_load_more_enabled(boolean z) {
        this.auto_load_more_enabled = z;
    }
}
