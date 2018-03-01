package com.example.ainhoa.app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by miguel on 1/03/18.
 */

public interface UsersService {
    @GET("users")
    Call<List<User>> getUsers();
}
