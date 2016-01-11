package com.tuccro.redtubebrowser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tuccro on 1/10/16.
 */
public class Video {

    public VideoInfo getInfo() {
        return info;
    }

    public void setInfo(VideoInfo info) {
        this.info = info;
    }

    @SerializedName("video")
    @Expose
    private VideoInfo info;
}
