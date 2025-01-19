
package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.mitron;

import com.google.gson.annotations.SerializedName;

public class PageProps {

    @SerializedName("socialShareMsg")
    private String mSocialShareMsg;
    @SerializedName("socialShareUrl")
    private String mSocialShareUrl;
    @SerializedName("sound")
    private Sound mSound;
    @SerializedName("user")
    private User mUser;
    @SerializedName("video")
    private Video mVideo;

    public String getSocialShareMsg() {
        return mSocialShareMsg;
    }

    public String getSocialShareUrl() {
        return mSocialShareUrl;
    }

    public Sound getSound() {
        return mSound;
    }

    public User getUser() {
        return mUser;
    }

    public Video getVideo() {
        return mVideo;
    }

    public static class Builder {

        private String mSocialShareMsg;
        private String mSocialShareUrl;
        private Sound mSound;
        private User mUser;
        private Video mVideo;

        public PageProps.Builder withSocialShareMsg(String socialShareMsg) {
            mSocialShareMsg = socialShareMsg;
            return this;
        }

        public PageProps.Builder withSocialShareUrl(String socialShareUrl) {
            mSocialShareUrl = socialShareUrl;
            return this;
        }

        public PageProps.Builder withSound(Sound sound) {
            mSound = sound;
            return this;
        }

        public PageProps.Builder withUser(User user) {
            mUser = user;
            return this;
        }

        public PageProps.Builder withVideo(Video video) {
            mVideo = video;
            return this;
        }

        public PageProps build() {
            PageProps pageProps = new PageProps();
            pageProps.mSocialShareMsg = mSocialShareMsg;
            pageProps.mSocialShareUrl = mSocialShareUrl;
            pageProps.mSound = mSound;
            pageProps.mUser = mUser;
            pageProps.mVideo = mVideo;
            return pageProps;
        }

    }

}
