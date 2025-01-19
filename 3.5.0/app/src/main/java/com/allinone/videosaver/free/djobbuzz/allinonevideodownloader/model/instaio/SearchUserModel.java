package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.UserModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchUserModel implements Serializable {
    @SerializedName("has_more")
    private boolean has_more;
    @SerializedName("num_results")
    private int num_results;
    @SerializedName("rank_token")
    private String rank_token;
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

    public int getNum_results() {
        return this.num_results;
    }

    public void setNum_results(int i) {
        this.num_results = i;
    }

    public boolean isHas_more() {
        return this.has_more;
    }

    public void setHas_more(boolean z) {
        this.has_more = z;
    }

    public String getRank_token() {
        return this.rank_token;
    }

    public void setRank_token(String str) {
        this.rank_token = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
