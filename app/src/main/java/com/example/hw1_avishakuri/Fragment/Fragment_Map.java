package com.example.hw1_avishakuri.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.hw1_avishakuri.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_Map extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private LatLng userLatLong;
    private double lat;
    private double lon;
    private Marker lastMarker;
    private SupportMapFragment mapFragment;
    private Boolean newPlace = false;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Log.d("aaaa"," IN MAP");
        mapFragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if(mapFragment!=null)
        mapFragment.getMapAsync(this);

        return view;
    }

    private void markerOnMap(GoogleMap googleMap) {
        userLatLong = new LatLng(lat, lon);
        if(lastMarker!=null){
            lastMarker.remove();
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLong, 10));
        lastMarker = googleMap.addMarker(new MarkerOptions().position(userLatLong).title("I am there"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));
    }


    public void setLatLong(double lat,double lon) {
        this.lat = lat;
        this.lon = lon;
        newPlace = true;
        if(newPlace == true){
            markerOnMap(googleMap);
            newPlace = false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

            this.googleMap = googleMap;

            //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        markerOnMap(googleMap);

    }
}

