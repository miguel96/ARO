package com.example.ainhoa.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class Historia implements Parcelable{

    private String titulo;
    private String idHistoria;
    private String descripcion;
    private String propietario;
    private ArrayList<Pista> pistas;

    public Historia(Parcel in){
        titulo = in.readString();
        idHistoria = in.readString();
        descripcion = in.readString();
        propietario = in.readString();
        in.readTypedList(pistas,Pista.CREATOR);
    }

    public static final Creator<Historia> CREATOR = new Creator<Historia>(){

        @Override
        public Historia createFromParcel(Parcel parcel) {
            return new Historia(parcel);
        }

        @Override
        public Historia[] newArray(int i) {
            return new Historia[i];
        }
    };

    public String getTituloHistoria(){
        return this.titulo;
    }

    public String getIdHistoria() { return this.idHistoria; }

    public String getDescripcion() { return this.descripcion; }

    public String getPropietario() { return this.propietario; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(idHistoria);
        parcel.writeString(descripcion);
        parcel.writeString(propietario);
        parcel.writeTypedList(pistas);
    }
}
