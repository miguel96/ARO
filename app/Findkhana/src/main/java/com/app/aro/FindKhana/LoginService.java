package com.app.aro.FindKhana;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by miguel on 1/03/18.
 */

public interface LoginService {
    @POST("login/android")
    Call<User> loginUser(@Body Token token);
}
