package com.example.natour2.model;


import java.util.List;

public class PointOfInterest {


    private Integer id;
    private String type;
    private String typeName;
    private String typeIcon;
    private Double coordX;
    private Double coordY;
    private Itinerary itinerary;

    public PointOfInterest(){

    }

    public PointOfInterest(Integer id, String type, Double coordX, Double coordY, String typeName, String typeIcon, Itinerary itinerary) {
        this.id = id;
        this.type = type;
        this.typeName = typeName;
        this.typeIcon = typeIcon;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }
}
