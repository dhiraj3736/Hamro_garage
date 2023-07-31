package com.hamro_garage;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GarageDetailsActivity extends AppCompatActivity {

    private TextView garageNameTextView;
    private TextView mobileTextView;
    private TextView serviceTextView;
    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_details);

        // Initialize TextView references
        garageNameTextView = findViewById(R.id.garageNameTextView);
        mobileTextView = findViewById(R.id.mobileTextView);
        serviceTextView = findViewById(R.id.serviceTextView);
        locationTextView = findViewById(R.id.locationTextView);

        // Retrieve the latitude and longitude from the intent
        double latitude = getIntent().getDoubleExtra("latitude", 0.0);
        double longitude = getIntent().getDoubleExtra("longitude", 0.0);

        // Retrieve the garage details from the intent extras
        String garageDetailsString = getIntent().getStringExtra("garageDetails");
        JSONObject garageDetails;
        try {
            garageDetails = new JSONObject(garageDetailsString);
        } catch (JSONException e) {
            e.printStackTrace();
            garageDetails = null;
        }

        // Display the garage details
        if (garageDetails != null) {
            displayGarageDetails(garageDetails);
        } else {
            // Make a request to fetch the garage details from the server
            fetchGarageDetails(latitude, longitude);
        }
    }

    private void displayGarageDetails(JSONObject garageDetails) {
        try {
            // Parse the JSON response and extract the garage details
            String garageName = garageDetails.getString("garage_name");
            String mobile = garageDetails.getString("mobile");
            String service = garageDetails.getString("service");
            String location = garageDetails.getString("location");

            // Set the garage details to the TextViews
            garageNameTextView.setText("Garage Name: " + garageName);
            mobileTextView.setText("Mobile: " + mobile);
            serviceTextView.setText("Service: " + service);
            locationTextView.setText("Location: " + location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetchGarageDetails(double latitude, double longitude) {
        String URL = Endpoints.garage_detail_for_activity;

        // Create a JSON object with latitude and longitude to send in the POST request
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("latitude", latitude);
            requestObject.put("longitude", longitude);
            // You may also want to add an identifier for the specific garage (e.g., garage ID) here.
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make a POST request to your PHP API to fetch the garage details
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the response and extract the garage details
                        processGarageDetailsResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response if needed
                        Toast.makeText(GarageDetailsActivity.this, "Error fetching garage details", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }

    private void processGarageDetailsResponse(JSONObject response) {
        try {
            // Check if the response has an error field
            if (response.has("error")) {
                String errorMessage = response.getString("error");
                // Show an error message or handle the case where the garage details are not found
                Toast.makeText(GarageDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                // Parse the JSON response and extract the garage details
                String garageName = response.getString("garage_name");
                String mobile = response.getString("mobile");
                String service = response.getString("service");
                String location = response.getString("location");

                // Set the garage details to the TextViews
                garageNameTextView.setText("Garage Name: " + garageName);
                mobileTextView.setText("Mobile: " + mobile);
                serviceTextView.setText("Service: " + service);
                locationTextView.setText("Location: " + location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
