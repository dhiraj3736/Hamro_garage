package com.hamro_garage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class create_garage extends AppCompatActivity {
    Button createbtn;

    EditText garagename,time,mobile,service,location;


    String sessionid;
    TextView addlocation;
    private static String url = Endpoints.CREATEGARAGE;
    private static String url1 = Endpoints.checkgarageexit;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_garage);
        createbtn = findViewById(R.id.createbtn);

        StringRequest checkgarageexit=new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("garage",""+response);
                        if (response.equals("true")){
                            createbtn.setVisibility(View.GONE);
                            createbtn.setClickable(false);
//
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(create_garage.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("id", StaticValues.garageid);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(checkgarageexit);






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
        String status1="pending";

        String Mobilematch = mobile.getText().toString().trim();



        if (garagename1.isEmpty()) {
            showCustomToast("Please enter garage name");
            return;
        }
        if (time1.isEmpty()) {
            showCustomToast("Please enter time");
            return;
        }
        if (mobile1.isEmpty()) {
            showCustomToast("Please enter mobile number");
            return;
        }
        if (service1.isEmpty()) {
            showCustomToast("Please enter service");
            return;
        }
        if (location1.isEmpty()) {
            showCustomToast("Please enter location");
            return;
        }
        if (!Mobilematch.matches("^(97|98)\\d{8}$")) {
            showCustomToast("Please enter a valid mobile number");
            return;
        }
        StringRequest checkDuplicateRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("success")){
                            showCustomToast("Add successfully");
//                            retrieveData();
//                            editText.setText("");

                        }
                        else if (response.equals("failure")) {
                            Toast.makeText(create_garage.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Already registered")) {

                            showCustomToast("Garage already registered");
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
                data.put("status", status1);
                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(checkDuplicateRequest);



    }
    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_layout));

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


}