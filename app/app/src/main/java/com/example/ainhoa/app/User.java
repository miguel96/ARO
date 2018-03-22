package com.example.ainhoa.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Parcelable{
    private String _id;
    private String googleId;
    private ArrayList<ProgresoHistoria> progresoHistoria;

    public User(Parcel in) {
        _id=in.readString();
        googleId=in.readString();
        progresoHistoria= in.createTypedArrayList(ProgresoHistoria.CREATOR);
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

    public String get_id(){
        return this._id;
    }

    public ArrayList<ProgresoHistoria> getProgresoHistoria(){
        return this.progresoHistoria;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(googleId);
        parcel.writeTypedList(progresoHistoria);
    }

    @Override
    public String toString() {
        System.out.println(progresoHistoria);
        return "User: "+this._id+" progresoHistoria:"+progresoHistoria.toString();
    }

    public String getGoogleId() {
        return googleId;
    }
}
