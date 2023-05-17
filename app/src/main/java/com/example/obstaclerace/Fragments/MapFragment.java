package com.example.obstaclerace.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.obstaclerace.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textview.MaterialTextView;

public class MapFragment extends Fragment {

    private MaterialTextView map_LBL_title;
    private GoogleMap googleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
               googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                   @Override
                   public void onMapClick(@NonNull LatLng latLng) {
                       MarkerOptions markerOptions = new MarkerOptions();
                       markerOptions.position(latLng);
                       markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                       googleMap.clear();
                       googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                       googleMap.addMarker(markerOptions);

                   }

               });
            }
        });
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        map_LBL_title = view.findViewById(R.id.map_LBL_title);
    }


    public void zoomOnRecord(Double coordinateX, Double coordinateY) {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(coordinateX,coordinateY);

        markerOptions.title(coordinateX + ":" + coordinateY);
        markerOptions.position(latLng);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
        googleMap.clear();
        googleMap.animateCamera(cameraUpdate);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(markerOptions);
    }
}