package com.example.buidemapp.ui.Mapa;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.buidemapp.MainActivity;
import com.example.buidemapp.R;
import com.example.buidemapp.ui.Maquinas.Maquina;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapaFragment extends Fragment {

    private final int ZoomToAddress = 15;

    private Maquina machine;
    private ArrayList<Maquina> _machines;

    private MapaViewModel mapaViewModel;

    MapView mMapView;
    private GoogleMap googleMap;

    public MapaFragment() {

    }

    public MapaFragment(Maquina _machine) {
        this.machine = _machine;
    }

    public MapaFragment(ArrayList<Maquina> _maquinas){
        _machines = _maquinas;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mapa, container, false);

        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        InitializeMap();

        return root;
    }

    private void InitializeMap() {
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                ApplyPermissions();

                if (machine != null) {
                    LatLng _mark = getLocationFromAddress(machine.get_direccion() + " " + machine.get_poblacion() + " " + machine.get_codigoPostal());
                    if (_mark != null) {
                        googleMap.addMarker(new MarkerOptions().position(_mark).title(machine.get_nombreCliente()).snippet(machine.get_direccion() + ", " + machine.get_poblacion() + ", " + machine.get_codigoPostal()).draggable(true).icon(getMarkerIcon(((MainActivity) getActivity())._data.SearchColorByIdOfTypeMachine(machine.get_tipoMaquina()))));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(_mark).zoom(ZoomToAddress).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Direccion no encontrada...", Snackbar.LENGTH_SHORT).show();
                    }
                }

                if(_machines != null){
                    if(_machines.size() > 0) {
                        for (Maquina _machine : _machines) {
                            LatLng _mark = getLocationFromAddress(_machine.get_direccion() + " " + _machine.get_poblacion() + " " + _machine.get_codigoPostal());
                            if (_mark != null) {
                                googleMap.addMarker(new MarkerOptions().position(_mark).title(_machine.get_nombreCliente()).snippet(_machine.get_direccion() + ", " + _machine.get_poblacion() + ", " + _machine.get_codigoPostal()).draggable(true).icon(getMarkerIcon(((MainActivity) getActivity())._data.SearchColorByIdOfTypeMachine(_machine.get_tipoMaquina()))));
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(_mark).zoom(5).build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }
                        }
                    }
                    else{
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "No tienes maquinas en la zona...", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void ApplyPermissions() {
        //check if the permissions are enabled to use google maps
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    private LatLng getLocationFromAddress(String _AddressOrZone) {
        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        LatLng _lanlat = null;

        try {
            address = coder.getFromLocationName(_AddressOrZone, 5);
            if (address.size() <= 0) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            _lanlat = new LatLng((double) (location.getLatitude()),
                    (double) (location.getLongitude()));

        } catch (IOException e) {
            _lanlat = new LatLng((double) (0),
                    (double) 0);
        }

        return _lanlat;
    }

    private BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}