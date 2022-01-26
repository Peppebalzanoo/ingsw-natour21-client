package com.example.natour2.model;

import java.util.List;

public class Itinerary {

    private long id;
    private String name;
    private Integer duration;
    private Integer difficulty;
    private String description;
    private String gpx;
    private Boolean disabledAccess;
    private User author; // Questo dovrebbe essere di tipo User
    private List<Report> reports;

    public Itinerary(){

    }

    public Itinerary(long id, String name, Integer duration, Integer difficulty, String description, String gpx, Boolean disabledAccessUser, User author, List<Report> reports){
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.difficulty = difficulty;
        this.description = description;
        this.gpx = gpx;
        this.author = author;
        this.disabledAccess = disabledAccessUser;
        this.reports = reports;
    }

    public Itinerary(String name, Integer duration, Integer difficulty, String description, String gpx, Boolean disabledAccessUser){
        this.name = name;
        this.duration = duration;
        this.difficulty = difficulty;
        this.description = description;
        this.gpx = gpx;
        this.disabledAccess = disabledAccessUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGpx() {
        return gpx;
    }

    public void setGpx(String gpx) {
        this.gpx = gpx;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Boolean getDisabledAccess() {
        return disabledAccess;
    }

    public void setDisabledAccess(Boolean disabledAccess) {
        this.disabledAccess = disabledAccess;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
