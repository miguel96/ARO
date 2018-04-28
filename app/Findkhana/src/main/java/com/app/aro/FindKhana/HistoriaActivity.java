package com.app.aro.FindKhana;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoriaActivity extends AppCompatActivity {
    Retrofit retrofit;
    HistoriaService historiaService;
    ObjectsApplication objects;
    ProgresoHistoria progresoHistoria;
    Historia historia;
    User user;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        objects = (ObjectsApplication) getApplication();
        TextView tituloHistoria = findViewById(R.id.txtTituloHistoria);
        user = objects.usuario;
        historia = objects.historia;
        //requestUpdatesMapa();

        tituloHistoria.setText(historia.getTituloHistoria());

        // Funciones del botón
        final Button buttonToMapa = findViewById(R.id.btnToMapa);
        buttonToMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoriaActivity.this, MapActivity.class);


                startActivity(intent);
            }
        });

        // display de la historia
        ListView listView = findViewById(R.id.listPistas);
        // fragmentos de la historia

        ArrayList<String> frags = new ArrayList<String>();
        frags.add(historia.getIdHistoria());
        for(int i=0;i<user.getProgresoHistoria(historia.getIdHistoria()).getPistasCompletadas().size();i++){
            frags.add(user.getProgresoHistoria(historia.getIdHistoria()).getPistasCompletadas().get(i).getIdPista());
        }
        //frags.add(historia.getDescripcion());

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, frags);
        listView.setAdapter(adapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestUpdatesMapa() {
        LocationManager locationManager;
        LocationListener locationListener;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
            return;
        }

        locationManager.requestLocationUpdates("gps", 1, 0, locationListener);
    }


    private void getHistoria() throws IOException {
        this.retrofit=new Retrofit.Builder()
                .baseUrl("http://gpi2unavarra.hopto.org:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.historiaService = retrofit.create(HistoriaService.class);

        Call<Historia> call = historiaService.getHistoriaById(this.historia.getIdHistoria(), new Callback<Historia>() {
            @Override
            public void onResponse(Call<Historia> call, Response<Historia> response) {
                historia = response.body();
            }

            @Override
            public void onFailure(Call<Historia> call, Throwable t) {

            }
        });

    }

}
