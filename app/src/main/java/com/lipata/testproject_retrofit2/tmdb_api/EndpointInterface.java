package com.lipata.testproject_retrofit2.tmdb_api;

import com.lipata.testproject_retrofit2.tmdb_api.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jlipata on 5/13/16.
 */
public interface EndpointInterface {

    @GET("3/discover/movie")
    Call<ApiResponse> getMovies(@Query("sort_by") String sortParam, @Query("api_key") String apiKey);

}
