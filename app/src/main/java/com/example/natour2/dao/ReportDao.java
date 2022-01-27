package com.example.natour2.dao;

import com.example.natour2.model.Itinerary;
import com.example.natour2.model.Report;
import com.example.natour2.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReportDao {

    @GET("report/all/")
    public Call<List<Report>> getAllReport(@Header("Authorization") String token);

    @POST("report/")
    public Call<Report> setReport(@Body Itinerary itinerary, @Query("reason") String reason, @Query("description") String description, @Header("Authorization") String token);


}
