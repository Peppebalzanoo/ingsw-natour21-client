package com.example.natour2.model;

import java.util.Date;

public class Reporting {


    private long id;
    private String reasonTitle;
    private String reasonDescription;
    private String providedExplanation;
    private Date openOn;
    private Itinerary itinerary;


    public Reporting(){
    }

    public Reporting(long id, String reasonTitle, String reasonDescription, String providedExplanation, Date openOn, Itinerary itinerary) {
        this.id = id;
        this.reasonTitle = reasonTitle;
        this.reasonDescription = reasonDescription;
        this.providedExplanation = providedExplanation;
        this.openOn = openOn;
        this.itinerary = itinerary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReasonTitle() {
        return reasonTitle;
    }

    public void setReasonTitle(String reasonTitle) {
        this.reasonTitle = reasonTitle;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    public String getProvidedExplanation() {
        return providedExplanation;
    }

    public void setProvidedExplanation(String providedExplanation) {
        this.providedExplanation = providedExplanation;
    }

    public Date getOpenOn() {
        return openOn;
    }

    public void setOpenOn(Date openOn) {
        this.openOn = openOn;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
}
