package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PostFeedModel implements Serializable {

    @SerializedName("items")
    private ArrayList<PostModel> items;

    public ArrayList<PostModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<PostModel> items) {
        this.items = items;
    }


    public class PostModel implements Serializable {

        @SerializedName("id")
        private String id;

        @SerializedName("media_type")
        private String mediaType;

        @SerializedName("media_url")
        private String mediaUrl;

        @SerializedName("caption")
        private String caption;

        @SerializedName("like_count")
        private int likeCount;

        @SerializedName("comment_count")
        private int commentCount;

        @SerializedName("timestamp")
        private String timestamp;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

}
