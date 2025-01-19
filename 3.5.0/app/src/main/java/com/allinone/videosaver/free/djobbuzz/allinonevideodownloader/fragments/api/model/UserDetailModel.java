package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.api.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDetailModel implements Serializable {
    @SerializedName("user")
    private User user;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user2) {
        this.user = user2;
    }
}