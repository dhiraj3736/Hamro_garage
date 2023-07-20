package com.hamro_garage.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hamro_garage.Endpoints;
import com.hamro_garage.R;
import com.hamro_garage.approve;
import com.hamro_garage.model.listofbusiness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class businessAdaptor extends ArrayAdapter<listofbusiness> {

    Context context;
    List<listofbusiness>arraylistbusiness;

    public businessAdaptor(@NonNull Context context, List<listofbusiness>arraylistbusiness) {
        super(context, R.layout.garage_list,arraylistbusiness);
        this.context=context;
        this.arraylistbusiness=arraylistbusiness;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.garage_list,null,true);
        TextView tvID=view.findViewById(R.id.id);
        TextView tvgaragename=view.findViewById(R.id.garagename);
        TextView tvtime=view.findViewById(R.id.time);
        TextView tvmobile=view.findViewById(R.id.mobile);
        TextView tvservice=view.findViewById(R.id.service);
        TextView tvlocation=view.findViewById(R.id.location);
        Button app=view.findViewById(R.id.approve);
        Button notapp=view.findViewById(R.id.notapprove);

         tvID.setText(arraylistbusiness.get(position).getId());
        tvgaragename.setText(arraylistbusiness.get(position).getGarage_name());
        tvtime.setText(arraylistbusiness.get(position).getAvailable_time());
        tvmobile.setText(arraylistbusiness.get(position).getMobile());
        tvservice.setText(arraylistbusiness.get(position).getService());
        tvlocation.setText(arraylistbusiness.get(position).getLocation());


        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("approve", " "+position);

                String status1="approve";



                String url = Endpoints.approve;
                StringRequest checkDuplicateRequest=new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("success")){
                            Log.d("garage approve", "done");



                                }
                                else if (response.equals("failure")) {
                                    Log.d("garage approve", "faile");

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("garage approve", "error");

                            }
                        }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();

                        data.put("status", status1);
                        data.put("id",String.valueOf(tvID.getText()));
                        return data;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(checkDuplicateRequest);
            }
        });

        notapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("approve", " "+position);

                String status1="not approve";



                String url = Endpoints.approve;
                StringRequest checkDuplicateRequest=new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("success")){
                                    Log.d("garage approve", "done");


                                }
                                else if (response.equals("failure")) {
                                    Log.d("garage approve", "faile");

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("garage approve", "error");

                            }
                        }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();

                        data.put("status", status1);
                        data.put("id",String.valueOf(tvID.getText()));
                        return data;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(checkDuplicateRequest);
            }
        });
        return view;
    }
}
