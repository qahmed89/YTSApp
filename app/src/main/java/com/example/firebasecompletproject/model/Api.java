package com.example.firebasecompletproject.model;

import com.example.firebasecompletproject.model.api.Yts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api  {
    @GET("list_movies.json?page=")
    Call<Yts> getDate(@Query("page")int page);

}

