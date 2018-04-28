package com.app.aro.FindKhana;

/**
 * Created by miguel on 14/03/18.
 */

public class Token {
    String token;

    Token(String token){
        this.token=token;
    }
    String getToken(){
        return this.token;
    }

    @Override
    public String toString() {
        return token;
    }
}
