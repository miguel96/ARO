package com.example.ainhoa.app;


import android.os.Parcel;
import android.os.Parcelable;

public class Localizacion implements Parcelable{

    private double latitud;
    private double longitud;

    public Localizacion(Parcel in){
        latitud = 32.1;
        longitud = 37.4;
    }

    /*protected Localizacion(Parcel in) {
        latitud = in.readDouble();
        longitud = in.readDouble();
    }*/

    public static final Creator<Localizacion> CREATOR = new Creator<Localizacion>() {
        @Override
        public Localizacion createFromParcel(Parcel in) {
            return new Localizacion(in);
        }

        @Override
        public Localizacion[] newArray(int size) {
            return new Localizacion[size];
        }
    };

    public double getLatitud(){ return this.latitud; }

    public double getLongitud(){ return this.latitud; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitud);
        parcel.writeDouble(longitud);
    }
}