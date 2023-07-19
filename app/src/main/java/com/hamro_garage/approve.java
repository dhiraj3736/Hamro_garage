package com.hamro_garage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class approve extends AppCompatActivity {
    Button approve,notapprove;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_list);
        approve=findViewById(R.id.approve);


    }
    public void approve(View view){

        String status1="approve";



      String url = Endpoints.approve;
        StringRequest checkDuplicateRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("success")){
                            Toast.makeText(approve.this, "Add successfully", Toast.LENGTH_SHORT).show();


                        }
                        else if (response.equals("failure")) {
                            Toast.makeText(approve.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(approve.this, "Registration Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("status", status1);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(checkDuplicateRequest);


    }
}
