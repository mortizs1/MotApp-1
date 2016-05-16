package mma.motapp.model;

import java.util.ArrayList;


public class Motel {
    private int id;
    private String title;
    private double latitud, longitud;

    public Motel() {
    }

    public Motel(int id, String title, double latitud, double longitud) {
        this.id = id;
        this.title = title;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitud(){
        return latitud;
    }

    public void setLatitud(double latitud){
        this.latitud = latitud;
    }

    public double getLongitud(){
        return longitud;
    }

    public void setLongitud(double longitud){
        this.longitud = longitud;
    }
}
