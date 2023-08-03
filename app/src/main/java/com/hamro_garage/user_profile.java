
package com.hamro_garage;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class user_profile extends AppCompatActivity {
    TextView fullname,address,mobile,email;
   Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        retrieveData();
        button=findViewById(R.id.edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(user_profile.this,edit_user_profile.class);

                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){

        fullname = findViewById(R.id.full_name);
        address = findViewById(R.id.caddress);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);

        String url = Endpoints.USER_PROFILE;


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
                Toast.makeText(user_profile.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(user_profile.this);
        requestQueue.add(stringRequest);



    }
}
