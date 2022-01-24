package com.example.natour2.model;

import com.example.natour2.dao.UserDao;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;

import retrofit2.Call;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User{
    private long id;
    private String username;
    private String email;
    //public String image;
    //public String token;
    private  String idString;

    public User(){

    }

    public User(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }
}
