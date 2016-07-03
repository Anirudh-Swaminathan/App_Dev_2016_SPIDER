package com.anirudh.anirudhswami.spider_2016_3.rest;

import com.anirudh.anirudhswami.spider_2016_3.model.Movie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Anirudh Swami on 03-07-2016.
 */
public interface ApiInterface {
    @GET("/")
    Call<Movie> getMovieTitle(@QueryMap Map<String, String> options);
}
