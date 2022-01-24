package com.example.natour2.dao;

import com.example.natour2.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserDao {

    @GET("user/")
    public Call<User> getUserByUsername(@Header("Authorization") String token);

    @POST("user/signup/")
    public Call<User> setUser(@Header("Authorization") String token);


}
