package com.app.aro.FindKhana;

import java.util.Date;

/**
 * Created by ainhoa on 31/01/18.
 */

public class PistasCompletadas{

    private String idPista;
    private int fechaCompletada;

    public PistasCompletadas(String pista){
        this.idPista = pista;
        this.fechaCompletada = 1111221;
    }


    public String getIdPista(){
        return this.idPista;
    }

    public int getFechaCompletada(){
        return this.fechaCompletada;
    }


    @Override
    public String toString() {
        return "idPista: "+idPista+" fechaCompletada:"+fechaCompletada;
    }
}
