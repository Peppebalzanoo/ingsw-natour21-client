package com.example.natour2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User{
    public long id;
    public String username;
    public String email;
    //public String image;
    //public String token;
    public String idString;

}
