package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelImagesVer implements Serializable {

    @SerializedName("candidates")
    private ArrayList<ModelCandidate> candidates;

    public ArrayList<ModelCandidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<ModelCandidate> candidates) {
        this.candidates = candidates;
    }
}
