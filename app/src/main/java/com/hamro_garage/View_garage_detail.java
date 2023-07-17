
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

public class View_garage_detail extends AppCompatActivity {
    TextView garagename,time,mobile,service,location;
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_garage_detail);
        retrieveData();
        button=findViewById(R.id.edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(View_garage_detail.this,edit_garage_detail.class);

                startActivity(intent);
            }
        });

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
                        retrieveData();
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
                Toast.makeText(View_garage_detail.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(View_garage_detail.this);
        requestQueue.add(stringRequest);



    }
}
