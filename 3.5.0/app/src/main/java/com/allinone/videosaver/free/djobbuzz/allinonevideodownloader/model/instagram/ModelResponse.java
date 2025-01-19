package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelResponse implements Serializable {

    @SerializedName("graphql")
    private ModelGraphql modelGraphql;

    public ModelGraphql getModelGraphql() {
        return modelGraphql;
    }

    public void setModelGraphql(ModelGraphql modelGraphql) {
        this.modelGraphql = modelGraphql;
    }

}
