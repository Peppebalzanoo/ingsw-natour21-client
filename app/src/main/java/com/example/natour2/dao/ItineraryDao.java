package com.example.natour2.dao;

import com.example.natour2.model.Itinerary;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


public interface ItineraryDao {

    @GET("itinerary/")
    public  Call<List<Itinerary>> getActiveUserItineraries(@Header("Authorization") String token);

}
