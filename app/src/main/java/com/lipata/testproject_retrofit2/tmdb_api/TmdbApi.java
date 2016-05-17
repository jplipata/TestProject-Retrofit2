package com.lipata.testproject_retrofit2.tmdb_api;

import android.util.Log;

import com.lipata.testproject_retrofit2.APIKeys;
import com.lipata.testproject_retrofit2.MainActivityFragment;
import com.lipata.testproject_retrofit2.tmdb_api.model.ApiResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jlipatap on 7/18/15.
 */
public class TmdbApi {

    public static final String LOG_TAG = "TmdbApi";

    public static final String BASE_URL = "http://api.themoviedb.org/";

    public static String TMDB_SORT_BY_POPULARITY = "popularity.desc";
    public static String TMDB_SORT_BY_RATING = "vote_average.desc";

    MainActivityFragment mMainFragment;

    public TmdbApi(MainActivityFragment mainFragment) {
        this.mMainFragment = mainFragment;
    }

    public void callTmdbApi(){


        // Logger
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(httpLoggingInterceptor);
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EndpointInterface apiService =
                retrofit.create(EndpointInterface.class);

        Call<ApiResponse> call = apiService.getMovies(TMDB_SORT_BY_POPULARITY, APIKeys.Tmdb.getKEY());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                // WORKING
                ApiResponse apiResponse = response.body();
                mMainFragment.setTmdbText("TMDB API response received, "+apiResponse.getResults().size());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(LOG_TAG, "Retrofit FAILURE");
                mMainFragment.setTmdbText("TMDB API FAILURE");

            }
        });
    }


}
