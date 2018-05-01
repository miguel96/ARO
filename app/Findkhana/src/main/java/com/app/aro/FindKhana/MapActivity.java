package com.app.aro.FindKhana;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;


public class MapActivity extends AppCompatActivity {

    public double radioToleranciaPistaEnMetros = 10;
    private MapView mapView;
    private ProgresoHistoria progreso;
    // variables for adding location layer
    private MapboxMap map = null;
    private PermissionsManager permissionsManager;
    private LocationLayerPlugin locationPlugin;
    private LocationEngine locationEngine;
    private Location originLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double miLatitud, miLongitud;
    private Historia historia;
    private boolean movimientoCamara = false;
    private MarkerOptions markerOptions;
    private Marker marker;
    ObjectsApplication objects;
    String provider;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_map);

        objects = (ObjectsApplication) getApplication();
        historia = objects.historia;
        progreso = objects.usuario.getProgresoHistoria(historia.getIdHistoria());
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        final Icon icon = IconFactory.getInstance(MapActivity.this).fromResource(R.drawable.marker_persona);

        markerOptions = new MarkerOptions()
                .icon(icon);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        while (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertaDeGPS();
        }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                miLatitud = location.getLatitude();
                miLongitud = location.getLongitude();

                if (map != null) {
                    if (!movimientoCamara)
                        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(miLatitud, miLongitud)));
                    marker.setPosition(new LatLng(miLatitud, miLongitud));
                }
                movimientoCamara = true;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                while (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    alertaDeGPS();
                }
            }
        };

        Criteria searchProviderCriteria = new Criteria();

        searchProviderCriteria.setPowerRequirement(Criteria.POWER_LOW);
        searchProviderCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        searchProviderCriteria.setCostAllowed(false);
        provider = locationManager.getBestProvider(searchProviderCriteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
            return;
        }
        if (locationManager.getLastKnownLocation(provider)!=null){
            miLatitud = locationManager.getLastKnownLocation(provider).getLatitude();
            miLongitud = locationManager.getLastKnownLocation(provider).getLongitude();
        }else
            locationManager.requestSingleUpdate(provider, locationListener, null);
        locationManager.requestLocationUpdates(provider, 1, 0, locationListener);

        // Add user location to the map
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                map = mapboxMap;
                marker = map.addMarker(markerOptions
                        .position(new LatLng(miLatitud, miLongitud))
                        .title("Mi ubicación"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(miLatitud, miLongitud), 14));
            }
        });



        // ver pista
        final Button buttonPistaMapa = findViewById(R.id.btnPistaAct);
        final TextView txtpista = findViewById(R.id.txtPistaAct);
        buttonPistaMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pista;
                if(progreso.getPistasCompletadas().size()<historia.getPistas().size())
                    pista = historia.getPistas().get(progreso.getPistasCompletadas().size()).getID();
                else
                    pista = "Ya has completado todas las pistas, estás hecho todo un erudito";

                txtpista.setVisibility(View.VISIBLE);
                txtpista.setText(pista);
                final Button btnCierraPista = findViewById(R.id.btnClosePistaAct);
                btnCierraPista.setVisibility(View.VISIBLE);
                btnCierraPista.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtpista.setVisibility(View.INVISIBLE);
                        btnCierraPista.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });


        final Button buttonResolver = findViewById(R.id.btnResolver);
        final TextView txtResolver = findViewById(R.id.txtPistaAct);
        buttonResolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String solucion = "Escriba aqui la respuesta:";
                double distanciaEnMetrosDeMiUbicacionALaPista;
                Pista pistaActual = historia.getPistas().get(progreso.getPistasCompletadas().size());
                Localizacion pistaLoc = historia.getPistas().get(progreso.getPistasCompletadas().size()).getLocalizacion();
                if(pistaActual.getRespuesta()==null){
                    if(pistaLoc!=null){
                        distanciaEnMetrosDeMiUbicacionALaPista = distFrom(miLatitud,miLongitud,pistaLoc.getLatitud(),pistaLoc.getLongitud());

                        if(distanciaEnMetrosDeMiUbicacionALaPista<=radioToleranciaPistaEnMetros){
                            solucion = "Felicidades, estás en el sitio correcto, te había subestimado... ya puedes acceder a la siguiente pista!";
                            //TODO aumentar el progreso.
                            objects.usuario.getProgresoHistoria(historia.getIdHistoria()).aumentarProgreso(pistaActual.getID());
                            progreso = objects.usuario.getProgresoHistoria(historia.getIdHistoria());
                        }
                        else{
                            solucion = "Vuelve a intentarlo, parece que no es el sitio correcto. Estás a "+(int)distanciaEnMetrosDeMiUbicacionALaPista +" metros.";
                        }
                    }
                }
                txtpista.setVisibility(View.VISIBLE);
                txtpista.setText(solucion);
                final Button btnCierraPista = findViewById(R.id.btnClosePistaAct);
                btnCierraPista.setVisibility(View.VISIBLE);
                btnCierraPista.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtpista.setVisibility(View.INVISIBLE);
                        btnCierraPista.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(provider, 10, 0, locationListener);

                    }
        }
    }
    // Add the mapView's own lifecycle methods to the activity's lifecycle methods
    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onStart() {
        super.onStart();
        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStart();
        }
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStop();
        }
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void moverCamara(View view){
        if (map!=null)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(miLatitud, miLongitud),map.getCameraPosition().zoom));
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

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (float) (earthRadius * c);

        return dist;
    }
}