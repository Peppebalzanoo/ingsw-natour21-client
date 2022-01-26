package com.example.natour2.model;


import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class User{
    private long id;
    private String username;
    private String email;
    private String profileImagePath;
    private List<Itinerary> itineraries;

    public User(){

    }

    public User(long id, String username, String email, String profileImagePath) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImagePath = profileImagePath;
    }

    public User(long id, String username, String email, String profileImagePath, List<Itinerary> itineraries) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.itineraries = itineraries;
        this.profileImagePath = profileImagePath;
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

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
