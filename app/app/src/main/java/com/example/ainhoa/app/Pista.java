package com.example.ainhoa.app;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

/**
 * Created by ainhoa on 31/01/18.
 */

public class Pista{

    private String idPista;
    private String nombre;
    private String texto;
    private Localizacion localizacion;
    private String respuesta;

    public Pista(int i){
            idPista = "Pista "+i;
            nombre = "Esta es la pista "+i;
            texto = "No hace falta ";
            localizacion = new Localizacion();
            respuesta = null;
    }

    public String getID(){ return this.idPista; }

    public String getNombre(){ return this.nombre; }

    public String getTexto(){ return this.texto; }

    public Localizacion getLocalizacion(){ return this.localizacion; }

    public String getRespuesta(){ return this.respuesta; }


}
