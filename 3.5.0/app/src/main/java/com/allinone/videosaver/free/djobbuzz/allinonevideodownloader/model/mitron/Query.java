
package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.mitron;

import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("id")
    private String mId;

    public String getId() {
        return mId;
    }

    public static class Builder {

        private String mId;

        public Query.Builder withId(String id) {
            mId = id;
            return this;
        }

        public Query build() {
            Query query = new Query();
            query.mId = mId;
            return query;
        }

    }

}
