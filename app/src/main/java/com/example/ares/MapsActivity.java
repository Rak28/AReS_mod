package com.example.ares;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ares.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

//    LatLng sydney = new LatLng(-34, 151);
    LatLng pharmacy1 = new LatLng(13.050677255461547, 80.24029838763987);
    LatLng NewCastle = new LatLng(-32.916668, 151.750000);
    LatLng Brisbane = new LatLng(-27.470125, 153.021072);

    private ArrayList<LatLng> locationArrayList;
    private ArrayList<String> placeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationArrayList = new ArrayList<>();
        placeNames = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
//        locationArrayList.add(sydney);
        locationArrayList.add(new LatLng(13.050677255461547, 80.24029838763987));
        locationArrayList.add(new LatLng(12.925340062637735, 80.10132900016308));
        locationArrayList.add(new LatLng(13.059198575667038, 80.19416061805711));
        locationArrayList.add(new LatLng(13.130287222822307, 80.24121880312471));

        placeNames.add("Muthu Pharmacy - T.Nagar, Thirumalai Pillai Road");
        placeNames.add("TAMILNADU PHARMACY");
        placeNames.add("RB Pharmacy");
        placeNames.add("Sri Kumaran Pharmacy");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < locationArrayList.size(); i++) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(placeNames.get(i)));
            builder.include(marker.getPosition());
//            mMap.moveCamera(CameraUpdateFactory.zoomTo(18.0f));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }
        // Add a marker in Sydney and move the camera
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.20); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mMap.animateCamera(cu);
    }

}