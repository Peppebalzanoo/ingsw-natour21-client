package com.example.natour2.model;

public class Notification {
    private Itinerary itinerary;
    private User userSegnalatore;

    public Notification(){

    }

    public Notification(Itinerary itinerary, User userSegnalatore){
        this.itinerary = itinerary;
        this.userSegnalatore = userSegnalatore;
    }

    /* *************** Momentanero ******************* */
    private String itinerario;
    private String utenteSegnalatore;
    public Notification(String itinerary, String userSegnalatore){
        this.itinerario = itinerary;
        this.utenteSegnalatore = userSegnalatore;
    }

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public String getUtenteSegnalatore() {
        return utenteSegnalatore;
    }

    public void setUtenteSegnalatore(String utenteSegnalatore) {
        this.utenteSegnalatore = utenteSegnalatore;
    }

    /* ********************************************* */



    public User getUserSegnalatore() {
        return userSegnalatore;
    }

    public void setUserSegnalatore(User userSegnalatore) {
        this.userSegnalatore = userSegnalatore;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        itinerary = itinerary;
    }
}
