package com.calebjares.swiper.rest;

import retrofit.http.GET;
import retrofit.http.Query;

public interface ServeitRestWrapper {
    @GET("cards/new.json")
    ApiCard newCard(@Query("text") String text, @Query("latitude") float latitude, @Query("longitude") float longitude);
}
