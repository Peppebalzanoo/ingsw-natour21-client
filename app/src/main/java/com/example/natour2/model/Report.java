package com.example.natour2.model;

import java.util.Date;
import java.util.List;

public class Report {


    private long id;
    private String reasonTitle;
    private String reasonDescription;
    private String providedExplanation;
    private Date openOn;
    private Object itinerary;

    public Report(){
    }

    public Report(long id, String reasonTitle, String reasonDescription, String providedExplanation, Date openOn, Object itinerary) {
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

    public Object getItinerary() {
        return itinerary;
    }

    public void setItinerary(Object itinerary) {
        this.itinerary = itinerary;
    }
}
