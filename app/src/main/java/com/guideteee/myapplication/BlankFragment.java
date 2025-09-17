package com.guideteee.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements OnMapReadyCallback{
MapView mapView;
View view;
    Context context;
    MapboxMap mapboxMap;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view =inflater.inflate(R.layout.fragment_blank, null);
      mapView=(MapView )view.findViewById(R.id.mapView2);
      mapView.onCreate(savedInstanceState);
      mapView.getMapAsync(this);
      return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context=getActivity().getApplicationContext();
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.mapboxMap  =mapboxMap;
    }
}
