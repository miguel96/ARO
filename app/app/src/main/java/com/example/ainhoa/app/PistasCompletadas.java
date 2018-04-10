package com.example.ainhoa.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class PistasCompletadas{

    private String idPista;
    private Date fechaCompletada;

    public PistasCompletadas(int i){
        this.idPista = "Pista "+i;
        this.fechaCompletada = new Date();
    }


    public String getIdPista(){
        return this.idPista;
    }

    public Date getFechaCompletada(){
        return this.fechaCompletada;
    }


    @Override
    public String toString() {
        return "idPista: "+idPista+" fechaCompletada:"+fechaCompletada;
    }
}
