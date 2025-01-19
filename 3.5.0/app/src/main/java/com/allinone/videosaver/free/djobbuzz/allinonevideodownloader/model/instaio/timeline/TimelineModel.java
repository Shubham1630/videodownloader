package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TimelineModel implements Serializable {
    @SerializedName("auto_load_more_enabled")
    private boolean auto_load_more_enabled;
    @SerializedName("client_feed_changelist_applied")
    private boolean client_feed_changelist_applied;
    @SerializedName("is_direct_v2_enabled")
    private boolean is_direct_v2_enabled;
    @SerializedName("items")
    private ArrayList<TimelineItemModel> items;
    @SerializedName("more_available")
    private boolean more_available;
    @SerializedName("next_max_id")
    private String next_max_id;
    @SerializedName("num_results")
    private int num_results;
    @SerializedName("request_id")
    private String request_id;
    @SerializedName("status")
    private String status;
    @SerializedName("view_state_version")
    private String view_state_version;

    public ArrayList<TimelineItemModel> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<TimelineItemModel> arrayList) {
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

    public boolean isAuto_load_more_enabled() {
        return this.auto_load_more_enabled;
    }

    public void setAuto_load_more_enabled(boolean z) {
        this.auto_load_more_enabled = z;
    }

    public boolean isIs_direct_v2_enabled() {
        return this.is_direct_v2_enabled;
    }

    public void setIs_direct_v2_enabled(boolean z) {
        this.is_direct_v2_enabled = z;
    }

    public String getNext_max_id() {
        return this.next_max_id;
    }

    public void setNext_max_id(String str) {
        this.next_max_id = str;
    }

    public String getView_state_version() {
        return this.view_state_version;
    }

    public void setView_state_version(String str) {
        this.view_state_version = str;
    }

    public boolean isClient_feed_changelist_applied() {
        return this.client_feed_changelist_applied;
    }

    public void setClient_feed_changelist_applied(boolean z) {
        this.client_feed_changelist_applied = z;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String str) {
        this.request_id = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
