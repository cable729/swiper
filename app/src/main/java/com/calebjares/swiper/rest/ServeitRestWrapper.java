package com.calebjares.swiper.rest;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ServeitRestWrapper {
    @POST("cards.json")
    ApiCard createCard(@Query("text") String text, @Query("latitude") float latitude, @Query("longitude") float longitude);
    @GET("cards/new.json")
    ApiCard newCard(@Query("text") String text, @Query("latitude") float latitude, @Query("longitude") float longitude);
    @GET("cards.json")
    List<ApiCard> getCards(@Query("limit") int limit);
    @GET("cards/{id}.json")
    ApiCard getCard(@Path("id") int id);
}
