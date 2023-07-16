package com.hamro_garage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hamro_garage.adaptor.MyAdaptor;
import com.hamro_garage.module.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class create_garage extends AppCompatActivity {
    Button createbtn;

   EditText garagename,time,mobile,service,location;


    String sessionid;
    TextView addlocation;
    private static String url = Endpoints.CREATEGARAGE;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_garage);
        createbtn = findViewById(R.id.createbtn);
        garagename = findViewById(R.id.garagename);
        time=findViewById(R.id.time);
      mobile=findViewById(R.id.mobileno);
      service=findViewById(R.id.service);
      location=findViewById(R.id.location);
      addlocation=findViewById(R.id.addlocation);


        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(create_garage.this,detail_location.class);
                startActivity(intent);
            }
        });


//        Intent intent=getIntent();
//        sessionid=intent.getStringExtra("session_id");
//        Log.d("session_id",""+sessionid);

    }
    public void save(View view){
        String garagename1 = garagename.getText().toString().trim();
        String time1 = time.getText().toString().trim();
        String mobile1 = mobile.getText().toString().trim();
        String service1 = service.getText().toString().trim();
        String location1 = location.getText().toString().trim();

        if (service1.isEmpty()) {
            Toast.makeText(this, "Please enter services", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest checkDuplicateRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("success")){
                            Toast.makeText(create_garage.this, "Add successfully", Toast.LENGTH_SHORT).show();
//                            retrieveData();
//                            editText.setText("");

                        }
                        else if (response.equals("failure")) {
                            Toast.makeText(create_garage.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(create_garage.this, "Registration Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("garage_name",garagename1);
                data.put("available_time",time1);
                data.put("mobile",mobile1);
                data.put("service",service1);
                data.put("location",location1);
                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(checkDuplicateRequest);



    }






}