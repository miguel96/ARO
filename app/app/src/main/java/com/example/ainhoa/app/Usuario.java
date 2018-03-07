package com.example.ainhoa.app;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class Usuario {

    private String idUsuario;
    private ArrayList<ProgresoHistoria> progresoHistoria;


    public String getIdUsuario(){
        return this.idUsuario;
    }

    public ArrayList<ProgresoHistoria> getProgresoHistoria(){
        return this.progresoHistoria;
    }

}
