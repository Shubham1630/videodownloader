package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelUsersDetails implements Serializable {

    @SerializedName("user")
    private ModelUsers modelUsers;

    public ModelUsers getModelUsers() {
        return modelUsers;
    }

    public void setModelUsers(ModelUsers modelUsers) {
        this.modelUsers = modelUsers;
    }
}

