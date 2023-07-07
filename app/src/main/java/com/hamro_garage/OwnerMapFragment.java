package com.hamro_garage;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class OwnerMapFragment extends Fragment implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_LOCATION = 1;
    private GoogleMap mMap;
    private LatLng garageLocation;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
                return;
            }
            mMap.setMyLocationEnabled(true);

            FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            locationProviderClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                                float zoomLevel = 14.0f;
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,zoomLevel));

                                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                    @Override
                                    public void onMapClick(LatLng latLng) {
                                        mMap.clear(); // Clear any existing markers on the map

                                        mMap.addMarker(new MarkerOptions().position(latLng).title("Garage Location"));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                        saveGarageLocation(latLng.latitude, latLng.longitude);
                                    }
                                });
                            }
                        }
                    });

            // Add the following code here
            if (garageLocation != null) {
                mMap.addMarker(new MarkerOptions().position(garageLocation).title("Garage Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(garageLocation));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onMapReady(mMap);
            } else {
                // Location permission denied
                // Handle this case, such as displaying a message or disabling location functionality
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private void saveGarageLocation(double latitude, double longitude) {
        // Check if the garage location is already set
        if (garageLocation != null) {
            // Show a dialog to confirm location change
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Location Change");
            builder.setMessage("Are you sure you want to change your garage location?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Proceed with saving the new location
                    performSaveGarageLocation(latitude, longitude);
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        } else {
            // Save the location directly
            performSaveGarageLocation(latitude, longitude);
        }
    }

    private void performSaveGarageLocation(double latitude, double longitude) {
        String URL = Endpoints.SAVE_LOCATION;
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        Map<String, String> params = new HashMap<>();
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("u_id", StaticValues.garageid);

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            // Handle success response
                            Toast.makeText(requireContext(), "Location saved successfully", Toast.LENGTH_SHORT).show();
                            // Update the garage location variable
                            garageLocation = new LatLng(latitude, longitude);

                        } else {
                            // Handle error response
                            Toast.makeText(requireContext(), "Failed to save location", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response if needed
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        queue.add(request);
    }
}
