package com.example.ihaveadream0528.finalprogarm;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

public class GoogleMap_fragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMapLongClickListener,
        LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private View rootView;
    public MapView mMapView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap Map;
    private boolean mPermissionDenied = false;
    private LocationManager locationMgr;
    public LatLng mylocation;
    public String startLocation;

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        // This is view
        rootView = inflater.inflate(R.layout.google_map_fragment, container, false);
        try {

            MapsInitializer.initialize(this.getActivity());


            //mMapView.getMapAsync((OnMapReadyCallback) this);
        } catch (InflateException e) {
            Log.d("mMapView Error:", e.getMessage().toString());
        }
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                mMapView = (MapView) rootView.findViewById(R.id.google_map);
                mMapView.onCreate(saveInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if (mMapView != null) {
                    mMapView.getMapAsync(this);

                }
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
        }
        this.locationMgr = (LocationManager) this.getActivity().getSystemService(LOCATION_SERVICE);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*String provider = this.locationMgr.getBestProvider(new Criteria(), true);
        provider = LocationManager.NETWORK_PROVIDER;
        if (ContextCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION_GRANTED","permission has something wrong!");
            return;
        }
        else{
            this.locationMgr.requestLocationUpdates(provider, 1000, 0, (android.location.LocationListener) this);
            Location location = this.locationMgr.getLastKnownLocation(provider);
            LatLng myloc = new LatLng(location.getLatitude(), location.getLongitude());
        }

        //m.add(myloc.toString());*/
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng fcu = new LatLng(24.180312, 120.644974);
        Map.addMarker(new MarkerOptions().position(fcu).title("You are here!"));
        Map.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        googleMap.setOnMapLongClickListener(this);
        Map.moveCamera(CameraUpdateFactory.newLatLngZoom(fcu,15));
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            /*PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);*/
            Log.d("PERMISSION_GRANTED","Map permission has something wrong!");
        } else if (Map != null) {
            Map.setMyLocationEnabled(true);
            Map.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }
}
