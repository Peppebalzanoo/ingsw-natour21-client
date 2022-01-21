package com.example.natour2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User{
    public String nickname;
    //public String image;
    public String email;
    //public String token;
    public String id;

}
