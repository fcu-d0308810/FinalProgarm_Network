package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class GoogleMap_fragment extends Fragment {
    private MapView mapView;
    private GoogleMap map;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.google_map_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.map);

        return view;
    }
}
