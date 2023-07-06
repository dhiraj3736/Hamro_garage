package com.hamro_garage;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;


public class editprofile extends AppCompatActivity {

    EditText fullname,address,mobile,email;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        return inflater.inflate(R.layout.activity_editprofile, container, false);
//    }

    @Override
    public void onCreate(  Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        fullname = findViewById(R.id.full_name);
        address = findViewById(R.id.caddress);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);

        String url = Endpoints.GET_edituserprofile;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess = jsonObject.getString("success");


                    if (sucess.equals("1")) {

                            JSONObject object = jsonObject.getJSONObject("data");
                            String fullname1 = object.getString("fullname");
                            String address1 = object.getString("address");
                            String mobile1 = object.getString("mobile");
                            String email1 = object.getString("email");

                        fullname.setText(fullname1);
                        address.setText(address1);
                        mobile.setText(mobile1);
                        email.setText(email1);


                        }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editprofile.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {


        };
        RequestQueue requestQueue = Volley.newRequestQueue(editprofile.this);
        requestQueue.add(stringRequest);


    }
}

















