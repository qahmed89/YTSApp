package com.example.firebasecompletproject.model;

import com.example.firebasecompletproject.model.details.YTSDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api_Details {
    @GET("movie_details.json?movie_id=&with_images=&with_cast=")
    Call<YTSDetails> getDates(@Query("movie_id") int movie_id,
                              @Query("with_images")boolean With_IMage,
                              @Query("with_cast")  boolean with_cast);

}
