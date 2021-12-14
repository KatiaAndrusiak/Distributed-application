package com.example.jabeda.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jabeda.R;
import com.example.jabeda.entity.Problem;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {
    FusedLocationProviderClient client;
    LatLng currentLatLng;
    private static final String ARG_PARAM1 = "param1";

    private static final Problem problem = new Problem();

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, problem);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        Button confirmButton = view.findViewById(R.id.confirm_button);
        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress);
        Sprite doubleBounce = new DoubleBounce();
        doubleBounce.setColor(Color.parseColor("#bce6d1"));
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);


        client = LocationServices.getFusedLocationProviderClient(getActivity());

            getCurrentLocation();
            TextView addressText = view.findViewById(R.id.addressText);
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
//                        LatLngBounds adelaideBounds = new LatLngBounds(
//                                new LatLng(-35.0, 138.58), // SW bounds
//                                new LatLng(-34.9, 138.61)  // NE bounds
//                        );
//                        googleMap.setLatLngBoundsForCameraTarget(adelaideBounds);
                            setAddressInfo(googleMap, addressText, currentLatLng);
                            confirmButton.setEnabled(true);
                            progressBar.setVisibility(View.GONE);

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
            SearchView searchView = view.findViewById(R.id.sv_location);
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    String location = searchView.getQuery().toString();
                    List<Address> addressList = null;
                    if (location != null || !location.equals("")) {
                        Geocoder geocoder = new Geocoder(getActivity());
                        try {
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(addressList!=null && !addressList.isEmpty()) {
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(@NonNull GoogleMap googleMap) {
                                    googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                                        @Override
                                        public void onMapLoaded() {
                                            setAddressInfo(googleMap, addressText, latLng);
                                        }
                                    });

                                }
                            });
                        }
                        else {
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(@NonNull GoogleMap googleMap) {
                                    googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                                        @Override
                                        public void onMapLoaded() {
                                            setAddressInfo(googleMap, addressText, currentLatLng);
                                        }
                                    });

                                }
                            });
                        }
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


            confirmButton.setWidth((int) (getResources().getDisplayMetrics().widthPixels * 0.8));
            confirmButton.setHeight((int) (getResources().getDisplayMetrics().heightPixels * 0.12));

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
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            } else {

                                locationManager.requestLocationUpdates(
                                        LocationManager.GPS_PROVIDER, 10, 1,
                                        new LocationListener() {
                                            @Override
                                            public void onLocationChanged(@NonNull Location location) {
                                                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                            }
                                        });
                            }
                        }
                    }
            );
        }

    }


    private void setAddressInfo(GoogleMap googleMap, TextView addressText, LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        String addressString = getCompleteAddressString(latLng.latitude, latLng.longitude);
        markerOptions.title(addressString);
        addressText.setText(addressString);
        problem.setAddress(addressString);
        problem.setLatitude(latLng.latitude);
        problem.setLongitude(latLng.longitude);
        googleMap.clear();

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                latLng, 15
        ),1000, null);

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
                    strReturnedAddress.append(returnedAddress.getAddressLine(i));
                }
                strAdd = strReturnedAddress.toString();
                System.out.println("My Current location address" + strReturnedAddress.toString());
            } else {
                System.out.println("No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot get Address!");
        }
        return strAdd;
    }
}