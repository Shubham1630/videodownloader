
package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.mitron;

import com.google.gson.annotations.SerializedName;

public class Props {

    @SerializedName("pageProps")
    private PageProps mPageProps;
    @SerializedName("__N_SSP")
    private Boolean m_NSSP;

    public PageProps getPageProps() {
        return mPageProps;
    }

    public Boolean get_NSSP() {
        return m_NSSP;
    }

    public static class Builder {

        private PageProps mPageProps;
        private Boolean m_NSSP;

        public Props.Builder withPageProps(PageProps pageProps) {
            mPageProps = pageProps;
            return this;
        }

        public Props.Builder with_NSSP(Boolean _NSSP) {
            m_NSSP = _NSSP;
            return this;
        }

        public Props build() {
            Props props = new Props();
            props.mPageProps = mPageProps;
            props.m_NSSP = m_NSSP;
            return props;
        }

    }

}
