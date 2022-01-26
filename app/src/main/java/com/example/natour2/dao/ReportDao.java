package com.example.natour2.dao;

import com.example.natour2.model.Report;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ReportDao {

@GET("report/all/")
public Call<List<Report>> getAllReport(@Header("Authorization") String token);


}
