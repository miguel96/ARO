package com.app.aro.FindKhana;

/**
 * Created by ruben on 15/03/2018.
 */

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProgresoService {

        @PUT("users/{idUsuario}/historias/{idHistoria}/pistas/{idPista}")
        Call<ProgresoHistoria> putProgresoHistoria(@Path("idUsuario") String idUsuario, @Path("idHistoria") String idHistoria, @Path("idPista") String idPista);

}
