package com.tuccro.redtubebrowser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tuccro on 1/10/16.
 */
public class Category {

    public void setCategory(String category) {
        this.category = category;
    }

    @SerializedName("category")
    @Expose
    private String category;

    @Override
    public String toString() {
        return category;
    }
}
