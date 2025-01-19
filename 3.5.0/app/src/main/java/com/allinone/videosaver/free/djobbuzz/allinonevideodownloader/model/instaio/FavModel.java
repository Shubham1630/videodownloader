package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio;

public class FavModel {
    private String full_name;
    private long pk;
    private String profile_pic_id;
    private String profile_pic_url;
    private String username;

    public FavModel(long j, String str, String str2, String str3, String str4) {
        this.pk = j;
        this.username = str;
        this.full_name = str2;
        this.profile_pic_url = str3;
        this.profile_pic_id = str4;
    }

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
}
