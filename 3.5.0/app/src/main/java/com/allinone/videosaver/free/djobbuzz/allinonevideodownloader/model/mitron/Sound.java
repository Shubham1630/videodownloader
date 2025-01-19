
package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.mitron;

import com.google.gson.annotations.SerializedName;

public class Sound {

    @SerializedName("bookmarked")
    private Boolean mBookmarked;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("thumbUrl")
    private String mThumbUrl;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("userId")
    private String mUserId;
    @SerializedName("videoCount")
    private Long mVideoCount;

    public Boolean getBookmarked() {
        return mBookmarked;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUserId() {
        return mUserId;
    }

    public Long getVideoCount() {
        return mVideoCount;
    }

    public static class Builder {

        private Boolean mBookmarked;
        private String mCreatedAt;
        private String mDescription;
        private String mId;
        private String mName;
        private String mThumbUrl;
        private String mUrl;
        private String mUserId;
        private Long mVideoCount;

        public Sound.Builder withBookmarked(Boolean bookmarked) {
            mBookmarked = bookmarked;
            return this;
        }

        public Sound.Builder withCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public Sound.Builder withDescription(String description) {
            mDescription = description;
            return this;
        }

        public Sound.Builder withId(String id) {
            mId = id;
            return this;
        }

        public Sound.Builder withName(String name) {
            mName = name;
            return this;
        }

        public Sound.Builder withThumbUrl(String thumbUrl) {
            mThumbUrl = thumbUrl;
            return this;
        }

        public Sound.Builder withUrl(String url) {
            mUrl = url;
            return this;
        }

        public Sound.Builder withUserId(String userId) {
            mUserId = userId;
            return this;
        }

        public Sound.Builder withVideoCount(Long videoCount) {
            mVideoCount = videoCount;
            return this;
        }

        public Sound build() {
            Sound sound = new Sound();
            sound.mBookmarked = mBookmarked;
            sound.mCreatedAt = mCreatedAt;
            sound.mDescription = mDescription;
            sound.mId = mId;
            sound.mName = mName;
            sound.mThumbUrl = mThumbUrl;
            sound.mUrl = mUrl;
            sound.mUserId = mUserId;
            sound.mVideoCount = mVideoCount;
            return sound;
        }

    }

}
