package com.example.ainhoa.app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;

// classes needed to add a marker
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class MapAdminActivity extends AppCompatActivity {

    private MapView mapView;

    // variables for adding a marker
    private Marker destinationMarker;
    private LatLng originCoord;
    private LatLng destinationCoord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiYWluaG9hc2FudHgiLCJhIjoiY2pjdXMxeDNsMDcwZzJ3czZhMGRpZ2xubiJ9.G3GTG00jwyegbkNuxa17aA");
        setContentView(R.layout.activity_map_admin);
        mapView = (MapView) findViewById(R.id.mapAdminView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                originCoord = new LatLng(originCoord.getLatitude(), originCoord.getLongitude());

                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng point) {

                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = point;

                        destinationMarker = mapboxMap.addMarker(new MarkerOptions()
                                .position(destinationCoord)
                        );

                    };
                });
            }
        });
    }

        @Override
        public void onStart() {
            super.onStart();
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
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            mapView.onSaveInstanceState(outState);
        }

}
