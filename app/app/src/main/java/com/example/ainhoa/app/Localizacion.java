package com.example.ainhoa.app;


import android.os.Parcel;
import android.os.Parcelable;

public class Localizacion{

    private double latitud;
    private double longitud;

    public Localizacion(){
        latitud = 42.831951 ;
        longitud = -1.594986;
    }


    public double getLatitud(){ return this.latitud; }

    public double getLongitud(){ return this.longitud; }
}