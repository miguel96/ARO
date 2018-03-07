package com.example.ainhoa.app;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class Historia {

    private String titulo;
    private String idHistoria;
    private String descripcion;
    private String propietario;
    private ArrayList<Pista> pistas;


    public String getTituloHistoria(){
        return this.titulo;
    }

    public String getIdHistoria() { return this.idHistoria; }

    public String getDescripcion() { return this.descripcion; }

    public String getPropietario() { return this.propietario; }




}
