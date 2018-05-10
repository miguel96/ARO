package com.app.aro.FindKhana;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ainhoa on 31/01/18.
 */

public class ProgresoHistoria{

    private String idHistoria;
    private ArrayList<PistasCompletadas> pistasCompletadas;
    private Date inicioHistoria;

    public ProgresoHistoria(){
        idHistoria = "1";
        pistasCompletadas = new ArrayList<>();
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

    public void aumentarProgreso(String idPista){
        pistasCompletadas.add(new PistasCompletadas(idPista));
    }

    @Override
    public String toString() {
        return "Historia:"+ idHistoria +"\nStarted:"+inicioHistoria+"\n"+pistasCompletadas;
    }
}
