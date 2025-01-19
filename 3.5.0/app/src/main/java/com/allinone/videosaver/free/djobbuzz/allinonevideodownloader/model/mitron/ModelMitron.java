
package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.mitron;

import com.google.gson.annotations.SerializedName;

public class ModelMitron {

    @SerializedName("buildId")
    private String mBuildId;
    @SerializedName("gssp")
    private Boolean mGssp;
    @SerializedName("isFallback")
    private Boolean mIsFallback;
    @SerializedName("page")
    private String mPage;
    @SerializedName("props")
    private Props mProps;
    @SerializedName("query")
    private Query mQuery;

    public String getBuildId() {
        return mBuildId;
    }

    public Boolean getGssp() {
        return mGssp;
    }

    public Boolean getIsFallback() {
        return mIsFallback;
    }

    public String getPage() {
        return mPage;
    }

    public Props getProps() {
        return mProps;
    }

    public Query getQuery() {
        return mQuery;
    }

    public static class Builder {

        private String mBuildId;
        private Boolean mGssp;
        private Boolean mIsFallback;
        private String mPage;
        private Props mProps;
        private Query mQuery;

        public ModelMitron.Builder withBuildId(String buildId) {
            mBuildId = buildId;
            return this;
        }

        public ModelMitron.Builder withGssp(Boolean gssp) {
            mGssp = gssp;
            return this;
        }

        public ModelMitron.Builder withIsFallback(Boolean isFallback) {
            mIsFallback = isFallback;
            return this;
        }

        public ModelMitron.Builder withPage(String page) {
            mPage = page;
            return this;
        }

        public ModelMitron.Builder withProps(Props props) {
            mProps = props;
            return this;
        }

        public ModelMitron.Builder withQuery(Query query) {
            mQuery = query;
            return this;
        }

        public ModelMitron build() {
            ModelMitron modelMitron = new ModelMitron();
            modelMitron.mBuildId = mBuildId;
            modelMitron.mGssp = mGssp;
            modelMitron.mIsFallback = mIsFallback;
            modelMitron.mPage = mPage;
            modelMitron.mProps = mProps;
            modelMitron.mQuery = mQuery;
            return modelMitron;
        }

    }

}
