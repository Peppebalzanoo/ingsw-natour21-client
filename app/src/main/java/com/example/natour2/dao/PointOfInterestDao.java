package com.example.natour2.dao;

import com.example.natour2.model.Itinerary;
import com.example.natour2.model.PointOfInterest;
import com.example.natour2.utilities.POITypeMapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PointOfInterestDao {

    @POST("pointOfInterest/")
    public Call<PointOfInterest> uploadPointOfInterest(@Body Itinerary itinerary, @Query("coord_y") Double coord_y, @Query("coord_x") Double coord_x, @Query("type") String type, @Header("Authorization") String token);


    @GET("pointOfInterest/types")
    public Call<List<POITypeMapper>> getAllType(@Header("Authorization") String token);
}
