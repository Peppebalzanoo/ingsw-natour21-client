package com.example.natour2.model;


import java.util.List;

public class PointOfInterest {


    private Integer id;
    private String type;
    private Double coordX;
    private Double coordY;
    private Itinerary itinerary;

    public PointOfInterest(Integer id, String type, Double coordX, Double coordY, Itinerary itinerary) {
        this.id = id;
        this.type = type;
        this.coordX = coordX;
        this.coordY = coordY;
        this.itinerary = itinerary;
    }

    public PointOfInterest(Integer id, String type, Itinerary itinerary) {
        this.id = id;
        this.type = type;
        this.itinerary = itinerary;
    }

    public Double getCoordY() {
        return coordY;
    }

    public void setCoordY(Double coordY) {
        this.coordY = coordY;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCoordX() {
        return coordX;
    }

    public void setCoordX(Double coordX) {
        this.coordX = coordX;
    }
}
