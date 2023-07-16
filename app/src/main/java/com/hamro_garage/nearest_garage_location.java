package com.hamro_garage;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonObjectRequest;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class nearest_garage_location extends Fragment implements OnMapReadyCallback {

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
                                float zoomLevel = 10.0f;
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,zoomLevel));
                            }
                        }
                    });

            // Fetch and display garage locations
            fetchGarageLocations();
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

    private void fetchGarageLocations() {
        // Make a request to fetch the garage locations from the server
        String URL = Endpoints.GET_edituserprofile;
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
                        processGarageLocations(response);
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

    private void processGarageLocations(JSONObject response) {
        try {
            JSONArray locationsArray = response.getJSONArray("locations");
            Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < locationsArray.length(); i++) {
                JSONObject locationObject = locationsArray.getJSONObject(i);
                double latitude = locationObject.getDouble("latitude");
                double longitude = locationObject.getDouble("longitude");
                LatLng garageLatLng = new LatLng(latitude, longitude);

                // Add marker for each garage location
                mMap.addMarker(new MarkerOptions().position(garageLatLng).title("Garage Location"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getUserId() {
        // Replace this with your user ID retrieval logic
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id", ""); // Provide a default value if user ID is not found
    }
}
