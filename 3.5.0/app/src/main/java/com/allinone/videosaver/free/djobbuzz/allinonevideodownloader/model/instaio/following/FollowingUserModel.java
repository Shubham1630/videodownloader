package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.following;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.UserModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FollowingUserModel implements Serializable {
    @SerializedName("big_list")
    private boolean big_list;
    @SerializedName("next_max_id")
    private int next_max_id;
    @SerializedName("page_size")
    private int page_size;
    @SerializedName("status")
    private String status;
    @SerializedName("users")
    private ArrayList<UserModel> userList;

    public ArrayList<UserModel> getUserList() {
        return this.userList;
    }

    public void setUserList(ArrayList<UserModel> arrayList) {
        this.userList = arrayList;
    }

    public boolean isBig_list() {
        return this.big_list;
    }

    public void setBig_list(boolean z) {
        this.big_list = z;
    }

    public int getNext_max_id() {
        return this.next_max_id;
    }

    public void setNext_max_id(int i) {
        this.next_max_id = i;
    }

    public int getPage_size() {
        return this.page_size;
    }

    public void setPage_size(int i) {
        this.page_size = i;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
