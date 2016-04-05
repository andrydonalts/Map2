package com.andry.map2;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements View.OnClickListener {

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    private boolean isMapReady = false;

    private Button mapButton;
    private Button satelliteButton;
    private Button hybridButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mapButton = (Button) findViewById(R.id.activity_maps_map);
        satelliteButton = (Button) findViewById(R.id.activity_maps_satellite);
        hybridButton = (Button) findViewById(R.id.activity_maps_hybrid);

        mapButton.setOnClickListener(this);
        satelliteButton.setOnClickListener(this);
        hybridButton.setOnClickListener(this);

        //MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #map} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #map} is not null.
     */
    private void setUpMap() {
        isMapReady = true;

        LatLng newYork = new LatLng(40.7484, -73.9853);
        LatLng statOfLiberty = new LatLng(40.6875553, -74.0453398);
        LatLng centralParkZoo = new LatLng(40.7695233, -73.9686489);

        MarkerOptions statOfLibertyMarker = new MarkerOptions().title("Statue of Liberty").position(statOfLiberty);
        MarkerOptions centralParkZooMarker = new MarkerOptions().title("Central Park Zoo")
                .position(centralParkZoo)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));


        CameraPosition target = CameraPosition.builder().target(newYork).zoom(14).tilt(65).build();

        map.addMarker(statOfLibertyMarker);
        map.addMarker(centralParkZooMarker);
        map.addPolyline(new PolylineOptions()
                .add(statOfLiberty)
                .add(centralParkZoo))
                .setColor(Color.BLUE);
        map.addCircle(new CircleOptions().center(statOfLiberty).radius(1000).strokeColor(Color.GREEN).fillColor(Color.argb(64, 0, 255, 0)));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(isMapReady) {
            if (id == R.id.activity_maps_map) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            } else if (id == R.id.activity_maps_satellite) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            } else if (id == R.id.activity_maps_hybrid) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        }

    }
}
