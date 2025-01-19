package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.igtv;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class IGTVNewModel implements Serializable {
    @SerializedName("edges")
    private ArrayList<NodeModel> edgeModel;

    public ArrayList<NodeModel> getEdgeModel() {
        return this.edgeModel;
    }

    public void setEdgeModel(ArrayList<NodeModel> arrayList) {
        this.edgeModel = arrayList;
    }
}
