package com.app.aro.FindKhana;

import java.util.ArrayList;

/**
 * Created by ainhoa on 31/01/18.
 */

public class User{
    private String _id;
    private String googleId;
    private ArrayList<ProgresoHistoria> progresoHistorias;

    public User(){
        _id = "Ruben";
        googleId="";
        progresoHistorias = new ArrayList<>();

        progresoHistorias.add(new ProgresoHistoria());
    }

    public ArrayList<String> getHistorias(){
        ArrayList<String> historias = new ArrayList<String>();
        for(int i=0;i<progresoHistorias.size();i++){
            historias.add(progresoHistorias.get(i).getIdHistoria());
        }
        return historias;
    }

    public String get_id(){
        return this._id;
    }

    public String getGoogleId(){
        return this.googleId;
    }
    public ArrayList<ProgresoHistoria> getProgresoHistoria(){
        return this.progresoHistorias;
    }

    public ProgresoHistoria getProgresoHistoria(String idHistoria){
        for(int i=0;i<this.progresoHistorias.size();i++)
            if(this.progresoHistorias.get(i).getIdHistoria().equals(idHistoria))
                return this.progresoHistorias.get(i);
        return null;
    }

    @Override
    public String toString() {
        return "User: "+this._id+" progresoHistoria:"+progresoHistorias.toString();
    }
}
