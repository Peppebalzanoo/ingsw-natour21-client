package com.example.natour2.dao;

import com.example.natour2.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserDao {

    @GET("user/")
    public Call<User> getActiveUser(@Header("Authorization") String token);

    @POST("user/signup/")
    public Call<User> signUp(@Header("Authorization") String token);

    @PATCH("user/updateFmcToken/")
    public Call<User> setUser(@Header("Authorization") String token, @Query("fmc_token") String fcm_token);

    @GET("user/all/")
    public Call<List<User>> getAllUsers(@Header("Authorization") String token);

    @GET("user/username/{username}")
    public Call<User> getUserByUsername(@Header("Authorization") String token, @Path("username") String username);

    @Multipart
    @PATCH("user/updateProfilePic")
    public Call<Void> updateProfilePic(@Header("Authorization") String token, @Part MultipartBody.Part image);

    @POST("newsletter/subscribe/")
    public Call<Void> setSubscribe(@Header("Authorization") String token);

    @POST("newsletter/sendEmailToAll/")
    public Call<Void> sendEmailToAll(@Query("subject") String subject, @Query("message") String message, @Header("Authorization") String token);

}
