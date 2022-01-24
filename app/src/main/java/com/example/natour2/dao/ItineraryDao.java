package com.example.natour2.dao;

import com.example.natour2.model.Itinerary;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ItineraryDao {

    @GET("itinerary/")
    public  Call<List<Itinerary>> getActiveUserItineraries(@Header("Authorization") String token);

    @POST("itinerary/")
    public Call<Itinerary> uploadItinerary(@Body Itinerary itinerary, @Header("Authorization") String token);

    @GET("itinerary/all/")
    public  Call<List<Itinerary>> getAllItineraries(@Query("name") String name,
                                                    @Query("duration") Integer duration,
                                                    @Query("durationLessThan") Integer durationLessThan,
                                                    @Query("durationGreaterThan") Integer durationGreaterThan,
                                                    @Query("difficulty") Integer difficulty,
                                                    @Query("difficultyLessThan") Integer difficultyLessThan,
                                                    @Query("difficultyGreaterThan") Integer difficultyGreaterThan,
                                                    @Query("disabledAccess") Boolean disabledAccess,
                                                    @Query("sort") Integer sort,
                                                    @Header("Authorization") String token);

}
