package com.example.ainhoa.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class PistasCompletadas implements Parcelable{

    private String idPista;
    private Date fechaCompletada;


    protected PistasCompletadas(Parcel in) {
        idPista = in.readString();
        fechaCompletada= (Date) in.readSerializable();
    }

    public static final Creator<PistasCompletadas> CREATOR = new Creator<PistasCompletadas>() {
        @Override
        public PistasCompletadas createFromParcel(Parcel in) {
            return new PistasCompletadas(in);
        }

        @Override
        public PistasCompletadas[] newArray(int size) {
            return new PistasCompletadas[size];
        }
    };

    public String getIdPista(){
        return this.idPista;
    }

    public Date getFechaCompletada(){
        return this.fechaCompletada;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idPista);
        parcel.writeSerializable(fechaCompletada);
    }

    @Override
    public String toString() {
        return "idPista: "+idPista+" fechaCompletada:"+fechaCompletada;
    }
}
