package com.hamro_garage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class detail_services extends AppCompatActivity {
    Button SubmitButton;

    ListView listView;
    ArrayList<String> mylist;
    EditText editText;
    String sessionid;
    private static String url = Endpoints.SERVICES_REQUESTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_services);
        listView = findViewById(R.id.myListView);
        mylist = new ArrayList<String>();
        SubmitButton = findViewById(R.id.addbtn);
        editText=findViewById(R.id.addtxt);
        adaptor=new MyAdaptor(this,servicesArrayList);
        listView.setAdapter(adaptor);
        retrieveData();

//        Intent intent=getIntent();
//        sessionid=intent.getStringExtra("session_id");
//        Log.d("session_id",""+sessionid);

    }
    public void save(View view){
       String services=editText.getText().toString().trim();

        if (services.isEmpty()) {
            Toast.makeText(this, "Please enter services", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest checkDuplicateRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("success")){
                            Toast.makeText(detail_services.this, "Add successfully", Toast.LENGTH_SHORT).show();
                            retrieveData();
                            editText.setText("");

                        }
                        else if (response.equals("failure")) {
                            Toast.makeText(detail_services.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(detail_services.this, "Registration Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("service",services);
                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(checkDuplicateRequest);


    }

    MyAdaptor adaptor;
    public static ArrayList<service> servicesArrayList=new ArrayList<>();
    private static String url1 = Endpoints.GET_SERVICES;

    service serv;


    public void retrieveData(){

        StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                servicesArrayList.clear();
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String sucess= jsonObject.getString("success");
                    JSONArray jsonArray= jsonObject.getJSONArray("data");

                    if (sucess.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String id=object.getString("id");
                            String service=object.getString("service");

                            serv=new service(String.valueOf(i+1),service);
                            servicesArrayList.add(serv);
                            adaptor.notifyDataSetChanged();
                        }
                    }


                }
                catch (JSONException e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(detail_services.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}