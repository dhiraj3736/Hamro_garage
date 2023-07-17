
package com.hamro_garage;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

public class edit_garage_detail extends AppCompatActivity {
    TextView garagename,time,mobile,service,location;
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_garage_detail);
        garagename = findViewById(R.id.garage_name);
        time = findViewById(R.id.time);
        mobile = findViewById(R.id.mobile);
        service = findViewById(R.id.service);
        location=findViewById(R.id.location);;
        retrieveData();
    }

    public void retrieveData(){

        garagename = findViewById(R.id.garage_name);
        time = findViewById(R.id.time);
        mobile = findViewById(R.id.mobile);
        service = findViewById(R.id.service);
        location=findViewById(R.id.location);

        String url = Endpoints.get_garage_detail;


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
                            String garagename1=object.getString("garage_name");
                            String time1=object.getString("available_time");
                            String mobile1=object.getString("mobile");
                            String service1=object.getString("service");
                            String location1=object.getString("location");


                            garagename.setText(garagename1);
                            time.setText(time1);
                            mobile.setText(mobile1);
                            service.setText(service1);
                            location.setText(location1);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(edit_garage_detail.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(edit_garage_detail.this);
        requestQueue.add(stringRequest);



    }

    public void update(View view){
        String url1 = Endpoints.get_edit_garage_detail;
        String garagename1 = garagename.getText().toString().trim();
        String time1 = time.getText().toString().trim();
        String mobile1 = mobile.getText().toString().trim();
        String service1 = service.getText().toString().trim();
        String location1 = location.getText().toString().trim();

        if (garagename1.isEmpty()) {
            Toast.makeText(this, "garage name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (time1.isEmpty()) {
            Toast.makeText(this, " Please enter time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobile1.isEmpty()) {
            Toast.makeText(this, "Please enter mobile", Toast.LENGTH_SHORT).show();
            return;
        }
        if (service1.isEmpty()) {
            Toast.makeText(this, "Please enter service", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location1.isEmpty()) {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest checkDuplicateRequest=new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("success")){
                            Toast.makeText(edit_garage_detail.this, "Added", Toast.LENGTH_SHORT).show();



                        }
                        else if (response.equals("failure")) {
                            Toast.makeText(edit_garage_detail.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(edit_garage_detail.this, "Registration Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
