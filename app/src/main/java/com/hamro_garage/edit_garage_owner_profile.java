
package com.hamro_garage;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

public class edit_garage_owner_profile extends AppCompatActivity {
    EditText fullname,address,mobile,email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_garage_owner_profile);

        retrieveData1();
    }

    public void retrieveData1(){

        fullname = findViewById(R.id.full_name);
        address = findViewById(R.id.caddress);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);

        String url = Endpoints.GET_GARAGE_WONER_PROFILE;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String sucess= jsonObject.getString("success");
                    JSONArray jsonArray= jsonObject.getJSONArray("data");

                    if (sucess.equals("1")){

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String fullname1=object.getString("fullname");
                            String address1=object.getString("address");
                            String mobile1=object.getString("mobile");
                            String email1=object.getString("email");


                            fullname.setText(fullname1);
                            address.setText(address1);
                            mobile.setText(mobile1);
                            email.setText(email1);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(edit_garage_owner_profile.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(edit_garage_owner_profile.this);
        requestQueue.add(stringRequest);



    }

    public void update(View view){
        String url1 = Endpoints.GET_edituserprofile;
        String fullname1=fullname.getText().toString().trim();
        String address1=address.getText().toString().trim();
        String mobile1=mobile.getText().toString().trim();
        String email1=email.getText().toString().trim();
        String Emailmatch = email.getText().toString().trim();
        String Mobilematch = mobile.getText().toString().trim();

        if (fullname1.isEmpty()) {
            Toast.makeText(this, "name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address1.isEmpty()) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobile1.isEmpty()) {
            Toast.makeText(this, "Please enter mobile", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Mobilematch.matches("^(97|98)\\d{8}$")) {
            Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email1.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Emailmatch.matches("^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)@[a-z]+(.[a-z]+)(.[a-z]{2,})$")) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest checkDuplicateRequest=new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Username or Email already registered!!")) {
                            Toast.makeText(edit_garage_owner_profile.this, "Email or phone number already exists!", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("success")){
                            Toast.makeText(edit_garage_owner_profile.this,"Update successfully",Toast.LENGTH_SHORT).show();
                            retrieveData1();


                        }
                        else if (response.equals("failure")) {
                            Toast.makeText(edit_garage_owner_profile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(edit_garage_owner_profile.this, "Registration Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("fullname",fullname1);
                data.put("address",address1);
                data.put("mobile",mobile1);
                data.put("email",email1);
                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(checkDuplicateRequest);


    }
}
