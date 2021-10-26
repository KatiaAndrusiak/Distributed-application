package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {
    FusedLocationProviderClient client;
    LatLng currentLatLng;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }else {
            getCurrentLocation();
        }

        TextView addressText = view.findViewById(R.id.addressText);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        setAddressInfo(googleMap, addressText, currentLatLng);
                    }
                });
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        setAddressInfo(googleMap, addressText, latLng);
                    }
                });
            }
        });

        Button confirmButton = view.findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, new ProblemsFragment())
                                .commit();
                    }
                }
        );

        return view;
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if(location != null){
                                currentLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                            }else{

                                locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER, 10, 1,
                                    new LocationListener() {
                                        @Override
                                        public void onLocationChanged(@NonNull Location location) {
                                            currentLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                                        }
                                    });
                            }
                        }
                    }
            );
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(  requestCode == 1 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED))
        {
            getCurrentLocation();
          //  callNextActivity();
//            startActivity(new Intent(getActivity(), getActivity().getClass()));
        }else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

//    public void callNextActivity()
//    {
//        Intent ss = new Intent(getActivity(), this.getClass());
//        ss.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        ss.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ss.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        ss.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(ss);
//
//    }

    private void setAddressInfo(GoogleMap googleMap, TextView addressText, LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        String addressString = getCompleteAddressString(latLng.latitude,latLng.longitude);
        markerOptions.title(addressString);
        addressText.setText(addressString);
        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                latLng, 15
        ));

        googleMap.addMarker(markerOptions);
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                System.out.println("My Current loction address"+ strReturnedAddress.toString());
            } else {
                System.out.println( "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Canont get Address!");
        }
        return strAdd;
}
}