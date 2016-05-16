package com.lipata.testproject_retrofit2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lipata.testproject_retrofit2.tmdb_api.TmdbApi;
import com.lipata.testproject_retrofit2.yelp_api.YelpApi;

public class MainActivityFragment extends Fragment {

    TmdbApi mTmdbApi;
    YelpApi mYelpApi;
    TextView mTextView_Tmdb;
    TextView mTextView_Yelp;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mTextView_Tmdb = (TextView) getActivity().findViewById(R.id.tmdb_text_view);
        mTextView_Yelp = (TextView) getActivity().findViewById(R.id.yelp_text_view);

        // Call TMDB API
        mTmdbApi = new TmdbApi(this);
        mTmdbApi.callTmdbApi();

        // Call Yelp API
        mYelpApi = new YelpApi(this);
        mYelpApi.callYelpApi("lunch","40.7225576,-73.8427793" , "1000");

    }

    public void setTmdbText(String text){
        mTextView_Tmdb.setText(text);
    }

    public void setYelpText(String text){
        mTextView_Yelp.setText(text);
    }
}
