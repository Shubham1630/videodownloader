
package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.mitron;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("categoryId")
    private Long mCategoryId;
    @SerializedName("commentCount")
    private Long mCommentCount;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private String mId;
    @SerializedName("likeCount")
    private Long mLikeCount;
    @SerializedName("liked")
    private Boolean mLiked;
    @SerializedName("privacyMode")
    private Long mPrivacyMode;
    @SerializedName("shareCount")
    private Long mShareCount;
    @SerializedName("soundId")
    private String mSoundId;
    @SerializedName("thumbUrl")
    private String mThumbUrl;
    @SerializedName("userId")
    private String mUserId;
    @SerializedName("videoUrl")
    private String mVideoUrl;
    @SerializedName("viewCount")
    private Long mViewCount;

    public Long getCategoryId() {
        return mCategoryId;
    }

    public Long getCommentCount() {
        return mCommentCount;
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

    public Long getLikeCount() {
        return mLikeCount;
    }

    public Boolean getLiked() {
        return mLiked;
    }

    public Long getPrivacyMode() {
        return mPrivacyMode;
    }

    public Long getShareCount() {
        return mShareCount;
    }

    public String getSoundId() {
        return mSoundId;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public Long getViewCount() {
        return mViewCount;
    }

    public static class Builder {

        private Long mCategoryId;
        private Long mCommentCount;
        private String mCreatedAt;
        private String mDescription;
        private String mId;
        private Long mLikeCount;
        private Boolean mLiked;
        private Long mPrivacyMode;
        private Long mShareCount;
        private String mSoundId;
        private String mThumbUrl;
        private String mUserId;
        private String mVideoUrl;
        private Long mViewCount;

        public Video.Builder withCategoryId(Long categoryId) {
            mCategoryId = categoryId;
            return this;
        }

        public Video.Builder withCommentCount(Long commentCount) {
            mCommentCount = commentCount;
            return this;
        }

        public Video.Builder withCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public Video.Builder withDescription(String description) {
            mDescription = description;
            return this;
        }

        public Video.Builder withId(String id) {
            mId = id;
            return this;
        }

        public Video.Builder withLikeCount(Long likeCount) {
            mLikeCount = likeCount;
            return this;
        }

        public Video.Builder withLiked(Boolean liked) {
            mLiked = liked;
            return this;
        }

        public Video.Builder withPrivacyMode(Long privacyMode) {
            mPrivacyMode = privacyMode;
            return this;
        }

        public Video.Builder withShareCount(Long shareCount) {
            mShareCount = shareCount;
            return this;
        }

        public Video.Builder withSoundId(String soundId) {
            mSoundId = soundId;
            return this;
        }

        public Video.Builder withThumbUrl(String thumbUrl) {
            mThumbUrl = thumbUrl;
            return this;
        }

        public Video.Builder withUserId(String userId) {
            mUserId = userId;
            return this;
        }

        public Video.Builder withVideoUrl(String videoUrl) {
            mVideoUrl = videoUrl;
            return this;
        }

        public Video.Builder withViewCount(Long viewCount) {
            mViewCount = viewCount;
            return this;
        }

        public Video build() {
            Video video = new Video();
            video.mCategoryId = mCategoryId;
            video.mCommentCount = mCommentCount;
            video.mCreatedAt = mCreatedAt;
            video.mDescription = mDescription;
            video.mId = mId;
            video.mLikeCount = mLikeCount;
            video.mLiked = mLiked;
            video.mPrivacyMode = mPrivacyMode;
            video.mShareCount = mShareCount;
            video.mSoundId = mSoundId;
            video.mThumbUrl = mThumbUrl;
            video.mUserId = mUserId;
            video.mVideoUrl = mVideoUrl;
            video.mViewCount = mViewCount;
            return video;
        }

    }

}
