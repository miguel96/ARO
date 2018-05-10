package com.app.aro.FindKhana;

import java.util.ArrayList;

/**
 * Created by ainhoa on 31/01/18.
 */

public class User{
    private String _id;
    private String googleId;
    private ArrayList<ProgresoHistoria> progresoHistoria;



    public ArrayList<String> getHistorias(){
        ArrayList<String> historias = new ArrayList<String>();
        for(int i=0;i<progresoHistoria.size();i++){
            historias.add(progresoHistoria.get(i).getIdHistoria());
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
        return this.progresoHistoria;
    }

    public ProgresoHistoria getProgresoHistoria(String idHistoria){
        for(int i=0;i<this.progresoHistoria.size();i++)
            if(this.progresoHistoria.get(i).getIdHistoria().equals(idHistoria))
                return this.progresoHistoria.get(i);
        return null;
    }

    @Override
    public String toString() {
        return "User: "+this._id+" progresoHistoria:"+progresoHistoria.toString();
    }
}
