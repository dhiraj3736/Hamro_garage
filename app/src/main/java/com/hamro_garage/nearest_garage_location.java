package com.hamro_garage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hamro_garage.GarageDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class nearest_garage_location extends Fragment implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_LOCATION = 1;
    private GoogleMap mMap;
    private JSONArray locationsArray;
    private HashMap<Integer, JSONObject> garageDetailsMap; // Store garage details with index as key
    // Haversine class implementation
    private static class Haversine {

        private static final double EARTH_RADIUS = 6371; // Earth's radius in kilometers

        public static double calculateDistance(double startLat, double startLng, double endLat, double endLng) {
            double dLat = Math.toRadians(endLat - startLat);
            double dLng = Math.toRadians(endLng - startLng);

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) *
                            Math.sin(dLng / 2) * Math.sin(dLng / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            return EARTH_RADIUS * c;
        }
    }
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
                                float zoomLevel = 13.0f;
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));

                                // Fetch and display garage locations
                                fetchGarageLocations(currentLocation);
                            }
                        }
                    });
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
        // Nothing to do here since we set the map ready callback in the member variable 'callback'
    }

    private void fetchGarageLocations(LatLng currentLocation) {
        // Make a request to fetch the garage locations from the server
        String URL = Endpoints.FETCH_LOCATIONS;
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("u_id", getUserId()); // Replace with your user ID retrieval logic
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the response and add markers for each garage location
                        processGarageLocations(response, currentLocation);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response if needed
                    }
                });

        queue.add(request);
    }

    private void processGarageLocations(JSONObject response, LatLng currentLocation) {
        try {
            locationsArray = response.getJSONArray("locations");
            garageDetailsMap = new HashMap<>(); // Initialize the garage details HashMap

            double minDistance = Double.MAX_VALUE;
            LatLng nearestGarageLatLng = null;

            for (int i = 0; i < locationsArray.length(); i++) {
                JSONObject locationObject = locationsArray.getJSONObject(i);
                double latitude = locationObject.getDouble("latitude");
                double longitude = locationObject.getDouble("longitude");
                LatLng garageLatLng = new LatLng(latitude, longitude);

                // Add marker for each garage location
                Marker marker = mMap.addMarker(new MarkerOptions().position(garageLatLng).title("Garage Location").snippet(String.valueOf(i)));

                // Store the garage details in the HashMap using the marker index as the key
                garageDetailsMap.put(i, locationObject);

                // Calculate distance using Haversine formula
                double distance = Haversine.calculateDistance(currentLocation.latitude, currentLocation.longitude,
                        garageLatLng.latitude, garageLatLng.longitude);

                if (distance < minDistance) {
                    minDistance = distance;
                    nearestGarageLatLng = garageLatLng;
                }
            }

            // Add a marker for the nearest garage
            if (nearestGarageLatLng != null) {
                mMap.addMarker(new MarkerOptions().position(nearestGarageLatLng).title("Nearest Garage"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Add a marker click listener to the GoogleMap to handle the click event.
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String snippet = marker.getSnippet();
                if (snippet != null && !snippet.isEmpty()) {
                    int index = Integer.parseInt(snippet);
                    JSONObject selectedGarageDetails = garageDetailsMap.get(index);
                    openGarageDetailsActivity(selectedGarageDetails);
                }
                return true;
            }
        });
    }

    private void openGarageDetailsActivity(JSONObject garageDetails) {
        if (garageDetails != null) {
            double latitude = 0.0;
            double longitude = 0.0;
            try {
                latitude = garageDetails.getDouble("latitude");
                longitude = garageDetails.getDouble("longitude");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Create an intent to start GarageDetailsActivity and pass the necessary data as extras.
            Intent intent = new Intent(requireContext(), GarageDetailsActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("garageDetails", garageDetails.toString()); // Pass the garage details as a JSON string
            startActivity(intent);
        }
    }

    private String getUserId() {
        // Replace this with your user ID retrieval logic
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id", ""); // Provide a default value if user ID is not found
    }
}
