package com.example.ainhoa.app;


import android.os.Parcel;
import android.os.Parcelable;

public class Localizacion{

    private double latitud;
    private double longitud;

    public Localizacion(){
        latitud = 32.1;
        longitud = 37.4;
    }


    public double getLatitud(){ return this.latitud; }

    public double getLongitud(){ return this.longitud; }
}