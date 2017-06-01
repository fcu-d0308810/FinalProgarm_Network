package com.example.ihaveadream0528.finalprogarm;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.LOCATION_SERVICE;

public class GoogleMap_fragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMapLongClickListener,
        LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback{
    private View rootView;
    public MapView mMapView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap Map;
    private boolean mPermissionDenied = false;
    private LocationManager locationMgr;
    private DatabaseReference databaseReference;
    private FirebaseUser User;
    private Marker myMarker;
    public GoogleMap_fragment(FirebaseUser user){
        this.User = user;
    }
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("location");
        try {

            MapsInitializer.initialize(this.getActivity());


            //mMapView.getMapAsync((OnMapReadyCallback) this);
        } catch (InflateException e) {
            Log.d("mMapView Error:", e.getMessage().toString());
        }
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                Toast.makeText(getActivity(), "You can't find me", Toast.LENGTH_SHORT).show();
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
        addOtherMark();
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
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Map = googleMap;
        enableMyLocation();
        //LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Location selfLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        LatLng user = new LatLng(24.179022, 120.648376);
        //LatLng user = new LatLng(selfLocation.getLatitude(),selfLocation.getLongitude());
        myMarker = Map.addMarker(new MarkerOptions().position(user).title(User.getEmail()));
        Map.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        Map.moveCamera(CameraUpdateFactory.newLatLngZoom(user,15));
        user_location myLocation = new user_location(User.getEmail(), user.latitude, user.longitude);
        databaseReference.child(User.getUid()).setValue(myLocation);
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            /*PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);*/
            Log.d("PERMISSION_GRANTED","Map permission has something wrong!");
        }
        if (Map != null) {
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
        //Toast.makeText(this.getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Acquire the user's location
        Location selfLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        //Move the map to the user's location
        LatLng user = new LatLng(selfLocation.getLatitude(), selfLocation.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(user, 15);
        Map.moveCamera(update);
        myMarker.remove();
        myMarker = Map.addMarker(new MarkerOptions().position(user).title(User.getEmail()));
        user_location myLocation = new user_location(User.getEmail(), user.latitude, user.longitude);
        databaseReference.child(User.getUid()).setValue(myLocation);
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
    private void addOtherMark(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("other location: ",databaseReference.toString());


                user_location otherLocation = dataSnapshot.getValue(user_location.class);
                if(!otherLocation.getUser().equals(User.getEmail())){
                    Marker otherMarker = Map.addMarker(new MarkerOptions()
                            .position(new LatLng(otherLocation.getLatitude(),otherLocation.getLongitude()))
                            .title(otherLocation.getUser()));
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
