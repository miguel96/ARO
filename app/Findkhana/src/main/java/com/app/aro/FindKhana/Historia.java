package com.app.aro.FindKhana;

import java.util.ArrayList;

/**
 * Created by Ruben on 31/01/18.
 */

public class Historia{

    private String titulo;
    private String _id;
    private String descripcion;
    private String propietario;
    private ArrayList<Pista> pistas;


    public Historia(){
        titulo = "Asesinato";
        _id = "Asesinato en Pamplona";
        descripcion = "Un asesino anda suelto por las calles de Pamplona";
        propietario = "Ruben";
        pistas = new ArrayList<>();
        for(int i=0;i<5;i++)
            pistas.add(new Pista(i));
    }

    public String getTituloHistoria(){
        return this.titulo;
    }

    public String get_id() { return this._id; }

    public String getDescripcion() { return this.descripcion; }

    public String getPropietario() { return this.propietario; }

    public ArrayList<Pista> getPistas(){return this.pistas;}


}
