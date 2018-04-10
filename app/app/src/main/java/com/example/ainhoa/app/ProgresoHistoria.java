package com.example.ainhoa.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class ProgresoHistoria{

    private String idHistoria;
    private ArrayList<PistasCompletadas> pistasCompletadas;
    private Date inicioHistoria;

    public ProgresoHistoria(){
        idHistoria = "La historia";
        pistasCompletadas = new ArrayList<>();

        for(int i=0;i<3;i++)
            pistasCompletadas.add(new PistasCompletadas(i));
        inicioHistoria = new Date();
    }


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
    public String toString() {
        return "Historia:"+idHistoria+"\nStarted:"+inicioHistoria+"\n"+pistasCompletadas;
    }
}
