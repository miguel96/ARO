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

    public Historia(){
        titulo = "Asesinato";
        idHistoria = "La historia";
        descripcion = "Un asesino anda suelto por las calles de Pamplona";
        propietario = "Ruben";
        pistas = new ArrayList<Pista>();
        for(int i=0;i<5;i++)
            pistas.add(new Pista(i));
    }

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

    public ArrayList<Pista> getPistas(){return this.pistas;}

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
