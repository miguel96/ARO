package com.example.ainhoa.app;

/**
 * Created by miguel on 1/03/18.
 */
public class User {
    private String _id;
    private String id;
    private String name;
    private String givenNave;
    private  String familyName;
    private String imageUrl;
    private String email;
    private String idToken;

    public String toString(){
        return "_id:"+id+"\nemail:"+email+"\nname:"+name;
    }
}