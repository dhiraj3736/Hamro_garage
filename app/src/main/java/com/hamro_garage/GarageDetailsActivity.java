package com.hamro_garage;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

        String URL = Endpoints.garage_detail_for_activity;
        Log.d("latitude",""+String.valueOf(latitude));
        Log.d("longitude",""+String.valueOf(longitude));
        // Create a JSON object with latitude and longitude to send in the POST request

        // Make a POST request to your PHP API to fetch the garage details
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String sucess= jsonObject.getString("success");
                    JSONArray jsonArray= jsonObject.getJSONArray("data");

                    if (sucess.equals("1")){
//                retrieveData();
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String garage_name1=object.getString("garage_name");
                            String mobile1=object.getString("mobile");
                            String service1=object.getString("service");
                            String location1=object.getString("location");


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
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("latitude", String.valueOf(latitude));
                data.put("longitude",String.valueOf(longitude));
                return data;
            }
        };

        queue.add(request);
    }




}

