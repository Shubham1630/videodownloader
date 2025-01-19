package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemModel implements Serializable {
    @SerializedName("can_reply")
    private boolean can_reply;
    @SerializedName("can_reshare")
    private boolean can_reshare;
    @SerializedName("caption_is_edited")
    private boolean caption_is_edited;
    @SerializedName("caption_position")
    private int caption_position;
    @SerializedName("client_cache_key")
    private String client_cache_key;
    @SerializedName("code")
    private String code;
    @SerializedName("device_timestamp")
    private long device_timestamp;
    @SerializedName("expiring_at")
    private long expiring_at;
    @SerializedName("filter_type")
    private int filter_type;
    @SerializedName("has_audio")
    private boolean has_audio;
    @SerializedName("id")
    private String id;
    @SerializedName("image_versions2")
    private ImageVersionModel image_versions2;
    @SerializedName("is_reel_media")
    private boolean is_reel_media;
    @SerializedName("media_type")
    private int media_type;
    @SerializedName("organic_tracking_token")
    private String organic_tracking_token;
    @SerializedName("original_height")
    private int original_height;
    @SerializedName("original_width")
    private int original_width;
    @SerializedName("photo_of_you")
    private boolean photo_of_you;
    @SerializedName("pk")
    private long pk;
    @SerializedName("taken_at")
    private long taken_at;
    @SerializedName("video_duration")
    private double video_duration;
    @SerializedName("video_versions")
    private ArrayList<VideoVersionModel> video_versions;

    public long getTaken_at() {
        return this.taken_at;
    }

    public void setTaken_at(long j) {
        this.taken_at = j;
    }

    public long getPk() {
        return this.pk;
    }

    public void setPk(long j) {
        this.pk = j;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public long getDevice_timestamp() {
        return this.device_timestamp;
    }

    public void setDevice_timestamp(long j) {
        this.device_timestamp = j;
    }

    public int getMedia_type() {
        return this.media_type;
    }

    public void setMedia_type(int i) {
        this.media_type = i;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getClient_cache_key() {
        return this.client_cache_key;
    }

    public void setClient_cache_key(String str) {
        this.client_cache_key = str;
    }

    public int getFilter_type() {
        return this.filter_type;
    }

    public void setFilter_type(int i) {
        this.filter_type = i;
    }

    public ImageVersionModel getImage_versions2() {
        return this.image_versions2;
    }

    public void setImage_versions2(ImageVersionModel imageVersionModel) {
        this.image_versions2 = imageVersionModel;
    }

    public int getOriginal_width() {
        return this.original_width;
    }

    public void setOriginal_width(int i) {
        this.original_width = i;
    }

    public int getOriginal_height() {
        return this.original_height;
    }

    public void setOriginal_height(int i) {
        this.original_height = i;
    }

    public ArrayList<VideoVersionModel> getVideo_versions() {
        return this.video_versions;
    }

    public void setVideo_versions(ArrayList<VideoVersionModel> arrayList) {
        this.video_versions = arrayList;
    }

    public boolean isHas_audio() {
        return this.has_audio;
    }

    public void setHas_audio(boolean z) {
        this.has_audio = z;
    }

    public double getVideo_duration() {
        return this.video_duration;
    }

    public void setVideo_duration(double d) {
        this.video_duration = d;
    }

    public boolean isCaption_is_edited() {
        return this.caption_is_edited;
    }

    public void setCaption_is_edited(boolean z) {
        this.caption_is_edited = z;
    }

    public int getCaption_position() {
        return this.caption_position;
    }

    public void setCaption_position(int i) {
        this.caption_position = i;
    }

    public boolean isIs_reel_media() {
        return this.is_reel_media;
    }

    public void setIs_reel_media(boolean z) {
        this.is_reel_media = z;
    }

    public boolean isPhoto_of_you() {
        return this.photo_of_you;
    }

    public void setPhoto_of_you(boolean z) {
        this.photo_of_you = z;
    }

    public String getOrganic_tracking_token() {
        return this.organic_tracking_token;
    }

    public void setOrganic_tracking_token(String str) {
        this.organic_tracking_token = str;
    }

    public long getExpiring_at() {
        return this.expiring_at;
    }

    public void setExpiring_at(long j) {
        this.expiring_at = j;
    }

    public boolean isCan_reshare() {
        return this.can_reshare;
    }

    public void setCan_reshare(boolean z) {
        this.can_reshare = z;
    }

    public boolean isCan_reply() {
        return this.can_reply;
    }

    public void setCan_reply(boolean z) {
        this.can_reply = z;
    }
}
