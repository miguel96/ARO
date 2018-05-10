package com.app.aro.FindKhana;

/**
 * Created by ruben on 15/03/2018.
 */

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoriaService {

        @GET("historias/{id}")
        Call<Historia> getHistoriaById(@Path("id") String idHistoria);

}
