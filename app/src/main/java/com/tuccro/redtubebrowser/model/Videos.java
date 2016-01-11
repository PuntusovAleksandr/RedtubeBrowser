package com.tuccro.redtubebrowser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuccro on 1/10/16.
 */
public class Videos {

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @SerializedName("videos")
    @Expose
    List<Video> videos;
}
