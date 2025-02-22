package com.example.natour2.dao;

import com.example.natour2.model.Itinerary;
import com.example.natour2.model.Report;
import com.example.natour2.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReportDao {

    @GET("report/all/")
    public Call<List<Report>> getAllReport(@Header("Authorization") String token);

    @POST("report/")
    public Call<Report> setReport(@Body Itinerary itinerary, @Query("reason") String reason, @Query("description") String description, @Header("Authorization") String token);

    @DELETE("report/id/{id}")
    public Call<Void> deleteReport(@Header("Authorization") String token, @Path("id") Long id);

    @PATCH("report/provideExplanation/")
    public Call<Void> provideExplanation(@Header("Authorization") String token,@Body Report report ,@Query("explanation") String explanation);

    @GET("report/id/{id}")
    public Call<List<Report>> getActiveUserReport(@Header("Authorization") String token, @Path("id") Long id);
}
