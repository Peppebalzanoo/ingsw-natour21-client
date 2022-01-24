package com.example.natour2.dao;

import com.example.natour2.model.Itinerary;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ItineraryDao {

    @GET("itinerary/")
    public  Call<List<Itinerary>> getActiveUserItineraries(@Header("Authorization") String token);

    @POST("itinerary/")
    public Call<Itinerary> uploadItinerary(@Body Itinerary itinerary, @Header("Authorization") String token);


}
