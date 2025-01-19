
package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.mitron;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("bio")
    private String mBio;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("famous")
    private Boolean mFamous;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("followed")
    private Boolean mFollowed;
    @SerializedName("followerCount")
    private Long mFollowerCount;
    @SerializedName("followingCount")
    private Long mFollowingCount;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("profilePic")
    private String mProfilePic;
    @SerializedName("profileViewCount")
    private Long mProfileViewCount;
    @SerializedName("totalVideoCount")
    private Long mTotalVideoCount;
    @SerializedName("userId")
    private String mUserId;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("videoLikeCount")
    private Long mVideoLikeCount;

    public String getBio() {
        return mBio;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public Boolean getFamous() {
        return mFamous;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public Boolean getFollowed() {
        return mFollowed;
    }

    public Long getFollowerCount() {
        return mFollowerCount;
    }

    public Long getFollowingCount() {
        return mFollowingCount;
    }

    public String getGender() {
        return mGender;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getProfilePic() {
        return mProfilePic;
    }

    public Long getProfileViewCount() {
        return mProfileViewCount;
    }

    public Long getTotalVideoCount() {
        return mTotalVideoCount;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public Long getVideoLikeCount() {
        return mVideoLikeCount;
    }

    public static class Builder {

        private String mBio;
        private String mCreatedAt;
        private Boolean mFamous;
        private String mFirstName;
        private Boolean mFollowed;
        private Long mFollowerCount;
        private Long mFollowingCount;
        private String mGender;
        private String mLastName;
        private String mProfilePic;
        private Long mProfileViewCount;
        private Long mTotalVideoCount;
        private String mUserId;
        private String mUsername;
        private Long mVideoLikeCount;

        public User.Builder withBio(String bio) {
            mBio = bio;
            return this;
        }

        public User.Builder withCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public User.Builder withFamous(Boolean famous) {
            mFamous = famous;
            return this;
        }

        public User.Builder withFirstName(String firstName) {
            mFirstName = firstName;
            return this;
        }

        public User.Builder withFollowed(Boolean followed) {
            mFollowed = followed;
            return this;
        }

        public User.Builder withFollowerCount(Long followerCount) {
            mFollowerCount = followerCount;
            return this;
        }

        public User.Builder withFollowingCount(Long followingCount) {
            mFollowingCount = followingCount;
            return this;
        }

        public User.Builder withGender(String gender) {
            mGender = gender;
            return this;
        }

        public User.Builder withLastName(String lastName) {
            mLastName = lastName;
            return this;
        }

        public User.Builder withProfilePic(String profilePic) {
            mProfilePic = profilePic;
            return this;
        }

        public User.Builder withProfileViewCount(Long profileViewCount) {
            mProfileViewCount = profileViewCount;
            return this;
        }

        public User.Builder withTotalVideoCount(Long totalVideoCount) {
            mTotalVideoCount = totalVideoCount;
            return this;
        }

        public User.Builder withUserId(String userId) {
            mUserId = userId;
            return this;
        }

        public User.Builder withUsername(String username) {
            mUsername = username;
            return this;
        }

        public User.Builder withVideoLikeCount(Long videoLikeCount) {
            mVideoLikeCount = videoLikeCount;
            return this;
        }

        public User build() {
            User user = new User();
            user.mBio = mBio;
            user.mCreatedAt = mCreatedAt;
            user.mFamous = mFamous;
            user.mFirstName = mFirstName;
            user.mFollowed = mFollowed;
            user.mFollowerCount = mFollowerCount;
            user.mFollowingCount = mFollowingCount;
            user.mGender = mGender;
            user.mLastName = mLastName;
            user.mProfilePic = mProfilePic;
            user.mProfileViewCount = mProfileViewCount;
            user.mTotalVideoCount = mTotalVideoCount;
            user.mUserId = mUserId;
            user.mUsername = mUsername;
            user.mVideoLikeCount = mVideoLikeCount;
            return user;
        }

    }

}
