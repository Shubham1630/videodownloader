package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Edge implements Serializable {
    @SerializedName("node")
    private Node node;

    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
