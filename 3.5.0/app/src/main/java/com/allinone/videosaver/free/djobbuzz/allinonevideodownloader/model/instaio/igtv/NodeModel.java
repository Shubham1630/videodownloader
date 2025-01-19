package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.igtv;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NodeModel implements Serializable {
    @SerializedName("node")
    private NodeDataModel nodeDataModel;

    public NodeDataModel getNodeDataModel() {
        return this.nodeDataModel;
    }

    public void setNodeDataModel(NodeDataModel nodeDataModel) {
        this.nodeDataModel = nodeDataModel;
    }
}
