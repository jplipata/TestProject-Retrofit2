package com.lipata.testproject_retrofit2.yelp_api;

import android.util.Log;

import com.lipata.testproject_retrofit2.APIKeys;
import com.lipata.testproject_retrofit2.MainActivityFragment;
import com.lipata.testproject_retrofit2.yelp_api.model.YelpResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by jlipata on 5/15/16.
 */
public class YelpApi {

    public static final String LOG_TAG = "YelpApi";
    public static final String BASE_URL = "https://api.yelp.com/";

    MainActivityFragment mMainFragment;

    public YelpApi(MainActivityFragment mMainFragment) {
        this.mMainFragment = mMainFragment;
    }

    public void callYelpApi(String term, String location, String radius){

        // OAuth
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(APIKeys.Yelp.CONSUMER_KEY, APIKeys.Yelp.CONSUMER_SECRET);
        consumer.setTokenWithSecret(APIKeys.Yelp.TOKEN, APIKeys.Yelp.TOKEN_SECRET);

        // Logger
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .addInterceptor(httpLoggingInterceptor) // As per tutorial: We recommend to add logging as the last interceptor, because this will also log the information which you added with previous interceptors to your request.
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EndpointInterface apiService = retrofit.create(EndpointInterface.class);

        Call<YelpResponse> call = apiService.getBusinesses(term, location, radius);
        call.enqueue(new Callback<YelpResponse>() {
            @Override
            public void onResponse(Call<YelpResponse> call, Response<YelpResponse> response) {

                YelpResponse apiResponse = response.body();
                mMainFragment.setYelpText("Yelp API response received, "+apiResponse.getBusinesses().size());
            }

            @Override
            public void onFailure(Call<YelpResponse> call, Throwable t) {
                Log.e(LOG_TAG, "Retrofit FAILURE", t);
                t.printStackTrace();
                mMainFragment.setYelpText("Yelp API FAILURE");

            }
        });

    }
}
