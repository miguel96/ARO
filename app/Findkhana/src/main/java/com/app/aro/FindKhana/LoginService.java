package com.app.aro.FindKhana;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by miguel on 1/03/18.
 */

public interface LoginService {
    @GET("login/user")
    Call<User> loginUser(@Header("Authorization") Token token);

    @POST("login/register")
    Call<User> registerUser(@Body  UserToRegister user);
}
