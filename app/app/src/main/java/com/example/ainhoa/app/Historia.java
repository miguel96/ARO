package com.example.ainhoa.app;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class Historia{

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
        pistas = new ArrayList<>();
        for(int i=0;i<5;i++)
            pistas.add(new Pista(i));
    }

    public String getTituloHistoria(){
        return this.titulo;
    }

    public String getIdHistoria() { return this.idHistoria; }

    public String getDescripcion() { return this.descripcion; }

    public String getPropietario() { return this.propietario; }

    public ArrayList<Pista> getPistas(){return this.pistas;}

}
