package com.tuccro.redtubebrowser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuccro on 1/9/16.
 */
public class Categories {

    public List<Category> getCategories() {
        return categories;
    }

    public String[] getCategoriesAsStringArray() {

        String[] categoriesArray = new String[categories.size()];

        for (int i = 0; i < categoriesArray.length; i++) {
            categoriesArray[i] = categories.get(i).toString();
        }
        return categoriesArray;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @SerializedName("categories")
    @Expose
    private List<Category> categories;

    @Override
    public String toString() {

        String result = "!!!";

        for (Category category : categories) {
            result = result.concat(category.toString());
        }

        return result;
    }
}
