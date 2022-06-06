package com.example.music_application;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.music_application.entity.Customer;
import com.example.music_application.viewmodel.CustomerViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mapView;   // Add view for google map
    private CustomerViewModel customerViewModel;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        backBtn = findViewById(R.id.back_to_main_btn);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle loginBundle = getIntent().getExtras();
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                intent.putExtras(loginBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap gMap) {

        googleMap = gMap;

        Bundle loginBundle = getIntent().getExtras();
        String currentCustomerEmail = loginBundle.get("0").toString();

        customerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CustomerViewModel.class);
        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            LatLng currentCustomerAddress = new LatLng(1, 1);
            boolean foundCustomerFlag = false;
            @Override
            public void onChanged(@Nullable final List<Customer> customers) {
                for (Customer customer : customers) {
                    if (customer.emailAddress.equals(currentCustomerEmail)) {
                        currentCustomerAddress = getLocationFromAddress(getApplicationContext(), customer.address);
                        googleMap.addMarker(new MarkerOptions()
                                .position(currentCustomerAddress)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentCustomerAddress));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                        foundCustomerFlag = true;
                    }
                }
            }
        });
    }

    public LatLng getLocationFromAddress (Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList;
        LatLng latLng = null;

        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if (address != null) {
                Address location = addressList.get(0);
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return latLng;
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}