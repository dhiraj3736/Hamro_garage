package com.hamro_garage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GarageDetailsActivity extends AppCompatActivity {

    private TextView garageNameTextView;
    private TextView mobileTextView;
    private TextView serviceTextView;
    private TextView locationTextView;
    Button call,submitfeedbackbtn;
    EditText feedbackedittext;
    String garage_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_details);

        // Initialize TextView references
        garageNameTextView = findViewById(R.id.garageNameTextView);
        mobileTextView = findViewById(R.id.mobileTextView);
        serviceTextView = findViewById(R.id.serviceTextView);
        locationTextView = findViewById(R.id.locationTextView);
        call=findViewById(R.id.callbut);
        feedbackedittext = findViewById(R.id.feedbackedittext);
        submitfeedbackbtn = findViewById(R.id.submitfeedbackbtn);

        String Source=getIntent().getStringExtra("Source");
        // Retrieve the latitude and longitude from the intent
        if(Source.equals("Map")) {
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

            String URL = Endpoints.garage_detail_for_activity;
            Log.d("latitude", "" + String.valueOf(latitude));
            Log.d("longitude", "" + String.valueOf(longitude));
            // Create a JSON object with latitude and longitude to send in the POST request

            // Make a POST request to your PHP API to fetch the garage details
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        if (success.equals("1")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String garage_name1 = object.getString("garage_name");
                                String mobile1 = object.getString("mobile");
                                String service1 = object.getString("service");
                                String location1 = object.getString("location");
                                garage_id = object.getString("id");

                                garageNameTextView.setText(garage_name1);
                                mobileTextView.setText(mobile1);
                                serviceTextView.setText(service1);
                                locationTextView.setText(location1);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("latitude", String.valueOf(latitude));
                    data.put("longitude", String.valueOf(longitude));
                    return data;
                }
            };

            queue.add(request);
        }else if(Source.equals("Search")){
            String garage_name=getIntent().getStringExtra("garage_name");


            // Display the garage details

            String searchURL = Endpoints.garage_detail_for_activity_search;

            // Create a JSON object with latitude and longitude to send in the POST request

            // Make a POST request to your PHP API to fetch the garage details
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, searchURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.d("Detail Info",""+response);
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        if (success.equals("1")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String garage_name1 = object.getString("garage_name");
                                String mobile1 = object.getString("mobile");
                                String service1 = object.getString("service");
                                String location1 = object.getString("location");
                                garage_id = object.getString("id");

                                garageNameTextView.setText(garage_name1);
                                mobileTextView.setText(mobile1);
                                serviceTextView.setText(service1);
                                locationTextView.setText(location1);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("garage_name", garage_name);
                    return data;
                }
            };
            queue.add(request);
        }

        submitfeedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback = feedbackedittext.getText().toString();
                String customer_id = StaticValues.garageid;

                StringRequest request = new StringRequest(Request.Method.POST, Endpoints.save_feedback, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("success")) {
                            Toast.makeText(GarageDetailsActivity.this, "com.hamro_garage.Feedback added successfully", Toast.LENGTH_SHORT).show();
                            feedbackedittext.setText("");
                        }
                        else {
                            Toast.makeText(GarageDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> data=new HashMap<>();
                        data.put("customer_id", customer_id);
                        data.put("garage_id",garage_id);
                        data.put("feedback", feedback);
                        return data;
                    }
                };
                RequestQueue requestQueue=Volley.newRequestQueue(GarageDetailsActivity.this);
                requestQueue.add(request);
            }
        });
        // Set a click listener on the mobileTextView to initiate a phone call
        call.setOnClickListener(v -> {
            String mobileNumber = mobileTextView.getText().toString();
            if (!mobileNumber.isEmpty()) {
                // Remove any non-digit characters (e.g., spaces, hyphens)
                mobileNumber = mobileNumber.replaceAll("[^0-9]", "");

                // Initiate the phone call intent
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNumber));
                if (ActivityCompat.checkSelfPermission(GarageDetailsActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                } else {
                    // Request the CALL_PHONE permission if it's not granted
                    ActivityCompat.requestPermissions(GarageDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                }
            }
        });
    }

    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initiate the phone call again
                String mobileNumber = mobileTextView.getText().toString().replaceAll("[^0-9]", "");
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNumber));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            } else {
                // Permission denied. You can show a toast or display a message to the user.
                Toast.makeText(this, "Phone call permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}