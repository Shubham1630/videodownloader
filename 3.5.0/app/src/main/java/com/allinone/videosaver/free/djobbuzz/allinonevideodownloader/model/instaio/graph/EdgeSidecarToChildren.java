package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EdgeSidecarToChildren implements Serializable {
    @SerializedName("edges")
    private List<Edge> edges;

    public List<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(List<Edge> list) {
        this.edges = list;
    }
}
