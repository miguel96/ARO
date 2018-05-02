package com.app.aro.FindKhana;

import java.util.ArrayList;

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
        idHistoria = "Asesinato en Pamplona";
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
