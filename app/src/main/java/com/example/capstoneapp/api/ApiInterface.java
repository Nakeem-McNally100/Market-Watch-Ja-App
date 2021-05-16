package com.example.capstoneapp.api;

import com.example.capstoneapp.newsmodels.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines?category=business")
    Call<News> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );


}
