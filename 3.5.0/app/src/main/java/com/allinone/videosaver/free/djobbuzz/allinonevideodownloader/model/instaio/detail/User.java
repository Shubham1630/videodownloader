package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("biography")
    private String biography;
    @SerializedName("follower_count")
    private int follower_count;
    @SerializedName("following_count")
    private int following_count;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("hd_profile_pic_url_info")
    private HDProfileModel hdProfileModel;
    @SerializedName("is_private")
    private boolean is_private;
    @SerializedName("is_verified")
    private boolean is_verified;
    @SerializedName("media_count")
    private int media_count;
    @SerializedName("mutual_followers_count")
    private int mutual_followers_count;
    @SerializedName("pk")
    private long pk;
    @SerializedName("profile_context")
    private String profile_context;
    @SerializedName("profile_pic_id")
    private String profile_pic_id;
    @SerializedName("profile_pic_url")
    private String profile_pic_url;
    @SerializedName("total_igtv_videos")
    private String total_igtv_videos;
    @SerializedName("username")
    private String username;

    public long getPk() {
        return this.pk;
    }

    public void setPk(long j) {
        this.pk = j;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String getFull_name() {
        return this.full_name;
    }

    public void setFull_name(String str) {
        this.full_name = str;
    }

    public boolean isIs_private() {
        return this.is_private;
    }

    public void setIs_private(boolean z) {
        this.is_private = z;
    }

    public String getProfile_pic_url() {
        return this.profile_pic_url;
    }

    public void setProfile_pic_url(String str) {
        this.profile_pic_url = str;
    }

    public String getProfile_pic_id() {
        return this.profile_pic_id;
    }

    public void setProfile_pic_id(String str) {
        this.profile_pic_id = str;
    }

    public boolean isIs_verified() {
        return this.is_verified;
    }

    public void setIs_verified(boolean z) {
        this.is_verified = z;
    }

    public int getMedia_count() {
        return this.media_count;
    }

    public void setMedia_count(int i) {
        this.media_count = i;
    }

    public int getFollower_count() {
        return this.follower_count;
    }

    public void setFollower_count(int i) {
        this.follower_count = i;
    }

    public int getFollowing_count() {
        return this.following_count;
    }

    public void setFollowing_count(int i) {
        this.following_count = i;
    }

    public String getBiography() {
        return this.biography;
    }

    public void setBiography(String str) {
        this.biography = str;
    }

    public String getTotal_igtv_videos() {
        return this.total_igtv_videos;
    }

    public void setTotal_igtv_videos(String str) {
        this.total_igtv_videos = str;
    }

    public HDProfileModel getHdProfileModel() {
        return this.hdProfileModel;
    }

    public void setHdProfileModel(HDProfileModel hDProfileModel) {
        this.hdProfileModel = hDProfileModel;
    }

    public int getMutual_followers_count() {
        return this.mutual_followers_count;
    }

    public void setMutual_followers_count(int i) {
        this.mutual_followers_count = i;
    }

    public String getProfile_context() {
        return this.profile_context;
    }

    public void setProfile_context(String str) {
        this.profile_context = str;
    }
}
