package com.example.ainhoa.app;

import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class Historia {

    private String titulo;
    private HashMap<String, Pista> pistas;

    public Historia(){

    }

    public String getTituloHistoria(){
        return this.titulo;
    }

    public Pista getPista(String code){
        return pistas.get(code);
    }

}
