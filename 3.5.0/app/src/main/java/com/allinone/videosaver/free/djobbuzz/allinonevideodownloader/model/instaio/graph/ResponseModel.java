package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseModel implements Serializable {
    @SerializedName("graphql")
    private Graphql graphql;

    public Graphql getGraphql() {
        return this.graphql;
    }

    public void setGraphql(Graphql graphql) {
        this.graphql = graphql;
    }
}
