package com.app.aro.FindKhana;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuHistoriasActivity extends AppCompatActivity {
    User user;
    ObjectsApplication objects;
    private AdView mAdView;
    Retrofit retrofit;
    HistoriaService historiaService;
    ArrayList<String> historias;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_historias);
        MobileAds.initialize(this,getString(R.string.admob_id));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        objects = (ObjectsApplication)getApplication();
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        comprobarGPS(manager);

        //TODO ESTO SOLO VALE PARA LOCAL.
        user = objects.usuario;
        System.out.println(user.toString());
        //TODO Pasar el Id de la historia clickada.
        ListView listView = findViewById(R.id.listHistorias);
        historias = user.getHistorias();

        System.out.println(historias.get(0));
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historias);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();


                try {
                    getHistoria(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void comprobarGPS(LocationManager manager) {
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertaDeGPS();

        }else{
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
    }

    private void alertaDeGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El GPS del teléfono está desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        alertaDeGPS();
                        return;
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void errorGPS(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El GPS del teléfono está desactivado, actívalo para usar esta función.")
                .setCancelable(true)
                .setNegativeButton("vale", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getHistoria(int posicion) throws IOException {
        this.retrofit=new Retrofit.Builder()
                .baseUrl(getString(R.string.hostBasePath))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.historiaService = retrofit.create(HistoriaService.class);
        System.out.println(historias.get(posicion));

        Call<Historia> call = historiaService.getHistoriaById(historias.get(posicion));
        call.enqueue(new Callback<Historia>() {
            @Override
            public void onResponse(Call<Historia> call, Response<Historia> response) {
                objects.historia = response.body();
                Intent intent = new Intent(MenuHistoriasActivity.this, HistoriaActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Historia> call, Throwable t) {
                System.out.println("Ha fallado recoger la historia.");
            }
        });

    }
}


