package com.example.ainhoa.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ainhoa on 31/01/18.
 */

public class User implements Parcelable{
    private String _id;
    private ArrayList<ProgresoHistoria> progresoHistorias;

    public User(){
        _id = "Ruben";
        progresoHistorias = new ArrayList<ProgresoHistoria>();

        progresoHistorias.add(new ProgresoHistoria());
    }

    public User(Parcel in) {
        _id=in.readString();
        progresoHistorias= in.createTypedArrayList(ProgresoHistoria.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeTypedList(progresoHistorias);
    }

    @Override
    public String toString() {
        return "User: "+this._id+" progresoHistoria:"+progresoHistorias.toString();
    }
}
