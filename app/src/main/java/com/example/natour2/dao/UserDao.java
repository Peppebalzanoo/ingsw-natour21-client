package com.example.natour2.dao;

import com.example.natour2.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserDao {

    @GET("user/")
    public Call<User> getUserByUsername(@Header("Authorization") String token);

    @POST("user/signup/")
    public Call<User> setUser(@Header("Authorization") String token, @Query("fcm_token") String fcm_token);

    @GET("user/all/")
    public Call<List<User>> getAllUsers(@Header("Authorization") String token);

}
