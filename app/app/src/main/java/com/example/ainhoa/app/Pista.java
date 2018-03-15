package com.example.ainhoa.app;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

/**
 * Created by ainhoa on 31/01/18.
 */

public class Pista implements Parcelable{

    private String idPista;
    private String nombre;
    private String texto;
    private Localizacion localizacion;
    private String respuesta;


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected Pista(Parcel in) {
        idPista = in.readString();
        nombre = in.readString();
        texto = in.readString();
        localizacion = in.readTypedObject(Localizacion.CREATOR);
        respuesta = in.readString();
    }

    public static final Creator<Pista> CREATOR = new Creator<Pista>() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Pista createFromParcel(Parcel in) {
            return new Pista(in);
        }

        @Override
        public Pista[] newArray(int size) {
            return new Pista[size];
        }
    };

    public String getID(){ return this.idPista; }

    public String getNombre(){ return this.nombre; }

    public String getTexto(){ return this.texto; }

    public Localizacion getLocalizacion(){ return this.localizacion; }

    public String getRespuesta(){ return this.respuesta; }


    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idPista);
        parcel.writeString(nombre);
        parcel.writeString(texto);
        parcel.writeTypedObject(localizacion, i);
        parcel.writeString(respuesta);
    }
}
