package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.ImageVersionModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.UserModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.VideoVersionModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TimelineItemModel implements Serializable {
    @SerializedName("caption")
    private CaptionModel caption;
    @SerializedName("caption_is_edited")
    private boolean caption_is_edited;
    @SerializedName("client_cache_key")
    private String client_cache_key;
    @SerializedName("code")
    private String code;
    @SerializedName("comment_count")
    private int comment_count;
    @SerializedName("device_timestamp")
    private long device_timestamp;
    @SerializedName("filter_type")
    private int filter_type;
    @SerializedName("has_audio")
    private boolean has_audio;
    @SerializedName("has_liked")
    private boolean has_liked;
    @SerializedName("has_more_comments")
    private boolean has_more_comments;
    @SerializedName("id")
    private String id;
    @SerializedName("image_versions2")
    private ImageVersionModel image_versions2;
    @SerializedName("lat")
    private double lat;
    @SerializedName("like_count")
    private int like_count;
    @SerializedName("lng")
    private double lng;
    @SerializedName("location")
    private LocationModel location;
    @SerializedName("media_type")
    private int media_type;
    @SerializedName("organic_tracking_token")
    private String organic_tracking_token;
    @SerializedName("original_height")
    private int original_height;
    @SerializedName("original_width")
    private int original_width;
    @SerializedName("pk")
    private long pk;
    @SerializedName("preview")
    private String preview;
    @SerializedName("taken_at")
    private long taken_at;
    @SerializedName("user")
    private UserModel user;
    @SerializedName("video_duration")
    private double video_duration;
    @SerializedName("video_versions")
    private ArrayList<VideoVersionModel> video_versions;
    @SerializedName("view_count")
    private long view_count;

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

    public long getView_count() {
        return this.view_count;
    }

    public void setView_count(long j) {
        this.view_count = j;
    }

    public LocationModel getLocation() {
        return this.location;
    }

    public void setLocation(LocationModel locationModel) {
        this.location = locationModel;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double d) {
        this.lat = d;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double d) {
        this.lng = d;
    }

    public UserModel getUser() {
        return this.user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public boolean isCaption_is_edited() {
        return this.caption_is_edited;
    }

    public void setCaption_is_edited(boolean z) {
        this.caption_is_edited = z;
    }

    public boolean isHas_more_comments() {
        return this.has_more_comments;
    }

    public void setHas_more_comments(boolean z) {
        this.has_more_comments = z;
    }

    public int getComment_count() {
        return this.comment_count;
    }

    public void setComment_count(int i) {
        this.comment_count = i;
    }

    public int getLike_count() {
        return this.like_count;
    }

    public void setLike_count(int i) {
        this.like_count = i;
    }

    public boolean isHas_liked() {
        return this.has_liked;
    }

    public void setHas_liked(boolean z) {
        this.has_liked = z;
    }

    public CaptionModel getCaption() {
        return this.caption;
    }

    public void setCaption(CaptionModel captionModel) {
        this.caption = captionModel;
    }

    public String getOrganic_tracking_token() {
        return this.organic_tracking_token;
    }

    public void setOrganic_tracking_token(String str) {
        this.organic_tracking_token = str;
    }

    public String getPreview() {
        return this.preview;
    }

    public void setPreview(String str) {
        this.preview = str;
    }
}
