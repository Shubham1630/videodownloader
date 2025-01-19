package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio;

import java.io.Serializable;

public class MultiUserModel implements Serializable {
    private String Cookies;
    private String Fullname;
    private String ProfileImageUrl;
    private String Username;
    private String csrf;
    private String session_id;
    private String user_id;

    public String getSession_id() {
        return this.session_id;
    }

    public void setSession_id(String str) {
        this.session_id = str;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String str) {
        this.user_id = str;
    }

    public String getCookies() {
        return this.Cookies;
    }

    public void setCookies(String str) {
        this.Cookies = str;
    }

    public String getCsrf() {
        return this.csrf;
    }

    public void setCsrf(String str) {
        this.csrf = str;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String str) {
        this.Username = str;
    }

    public String getFullname() {
        return this.Fullname;
    }

    public void setFullname(String str) {
        this.Fullname = str;
    }

    public String getProfileImageUrl() {
        return this.ProfileImageUrl;
    }

    public void setProfileImageUrl(String str) {
        this.ProfileImageUrl = str;
    }
}
