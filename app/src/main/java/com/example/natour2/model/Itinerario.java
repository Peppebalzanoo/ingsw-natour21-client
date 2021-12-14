package com.example.natour2.model;

public class Itinerario {

    private String name;
    private String durata;
    private String diff;
    private String descrizione;
    private String autore; // Questo dovrebbe essere di tipo User
    private String image;

    public Itinerario(String name, String durata, String diff, String descrizione, String autore, String image){
        this.name = name;
        this.durata = durata;
        this.diff = diff;
        this.descrizione = descrizione;
        this.autore = autore;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

}
