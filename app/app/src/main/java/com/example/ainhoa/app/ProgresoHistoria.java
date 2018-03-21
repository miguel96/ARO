package com.example.ainhoa.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class ProgresoHistoria implements Parcelable{

    private String idHistoria;
    private ArrayList<PistasCompletadas> pistasCompletadas;
    private Date inicioHistoria;

    public ProgresoHistoria(){
        idHistoria = "La historia";
        pistasCompletadas = new ArrayList<PistasCompletadas>();
        for(int i=0;i<3;i++)
            pistasCompletadas.add(new PistasCompletadas(i));
        inicioHistoria = new Date();
    }

    protected ProgresoHistoria(Parcel in) {
        idHistoria = in.readString();
        in.createTypedArray(PistasCompletadas.CREATOR);
        inicioHistoria=(java.util.Date) in.readSerializable();
    }

    public static final Creator<ProgresoHistoria> CREATOR = new Creator<ProgresoHistoria>() {
        @Override
        public ProgresoHistoria createFromParcel(Parcel in) {
            return new ProgresoHistoria(in);
        }

        @Override
        public ProgresoHistoria[] newArray(int size) {
            return new ProgresoHistoria[size];
        }
    };

    public String getIdHistoria(){
        return this.idHistoria;
    }

    public ArrayList<PistasCompletadas> getPistasCompletadas() {
        return this.pistasCompletadas;
    }
    public Date getInicioHistoria(){
        return this.inicioHistoria;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idHistoria);
        parcel.writeTypedList(pistasCompletadas);
        parcel.writeSerializable(inicioHistoria);
    }

    @Override
    public String toString() {
        return "Historia:"+idHistoria+"\nStarted:"+inicioHistoria+"\n"+pistasCompletadas;
    }
}
