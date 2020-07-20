package com.example.acetumor.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.acetumor.R;
import com.example.acetumor.components.GetNearbyPlaces;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import static com.example.acetumor.util.Constants.MAPVIEW_BUNDLE_KEY;


public class CompassFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MapView mMapView;
    GoogleMap map;
    int PROXIMITY_RADIUS = 10000;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location location = null;
    double latitude,longitude;

    public CompassFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompassFragment newInstance(String param1, String param2) {
        CompassFragment fragment = new CompassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLastKnownLocation();
        View view =  inflater.inflate(R.layout.fragment_compass, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        final Button hbutton = (Button)view.findViewById(R.id.B_Hospital);
        hbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Object dataTransfer[] = new Object[2];
                //first object will be mMap , scnd will be url

                GetNearbyPlaces getNearbyPlacesData = new GetNearbyPlaces();
                if (v.getId() == R.id.B_Hospital) {
                    String hospital = "hospital";
                    String url = getUrl(latitude, longitude, hospital);


                    dataTransfer[0] = map;
                    dataTransfer[1] = url;

                    getNearbyPlacesData.execute(dataTransfer);
                }
            }
        });
        initGoogleMap(savedInstanceState);
        return view;
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }
    /**
     * get user last known location
     */
    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;
        final Task location = mFusedLocationClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Location currentlocation = (Location)task.getResult();
                    latitude = currentlocation.getLatitude();
                    longitude = currentlocation.getLongitude();
                    moveCamera(new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude()),
                            15f);
                }
                else{
                    Toast.makeText(getActivity(),"unable to get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveCamera(LatLng latLng, float zoom){
        if(map == null){
            this.onStart();
        }
        else {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        getLastKnownLocation();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
        getLastKnownLocation();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap gmap) {
        map = gmap;
        getLastKnownLocation();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;
        if (location != null) map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
    }

    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location"+"="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyB7wgG-4YvZm_i2aoAeUTIU2ACxl6PvrN8");

        return googlePlaceUrl.toString();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
