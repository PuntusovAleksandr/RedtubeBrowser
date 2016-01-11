package com.tuccro.redtubebrowser.api;

import com.tuccro.redtubebrowser.model.Categories;
import com.tuccro.redtubebrowser.model.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tuccro on 1/9/16.
 */
public interface RedTubeService {


    @GET("?data=redtube.Categories.getCategoriesList&output=json")
    Call<Categories> getCategories();

    @GET("?data=redtube.Videos.searchVideos&output=json&thumbsize=medium")
    Call<Videos> getVideosByCategory(@Query("category") String category, @Query("page") int page);

    @GET("?data=redtube.Videos.searchVideos&output=json&thumbsize=medium")
    Call<Videos> searchVideosByCategory(@Query("search") String searchQuery, @Query("category") String category,@Query("page") int page);
}
