package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hamro_garage.adaptor.businessAdaptor;
import com.hamro_garage.model.listofbusiness;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class view_allgarage extends AppCompatActivity {
ListView listView;
businessAdaptor adaptor;
    String url = Endpoints.get_business;
    listofbusiness business;
public static ArrayList<listofbusiness>arrayListbusiness=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_allgarage);
        listView=findViewById(R.id.business);
        adaptor=new businessAdaptor(this,arrayListbusiness);
        listView.setAdapter(adaptor);
        retriveData();
    }

    public void retriveData(){

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayListbusiness.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String sucess=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if (sucess.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String id=object.getString("id");
                            String garage_name=object.getString("garage_name");
                            String time=object.getString("available_time");
                            String mobile=object.getString("mobile");
                            String service=object.getString("service");
                            String location=object.getString("location");
                            business=new listofbusiness(id,garage_name,time,mobile,service,location);
                            arrayListbusiness.add(business);
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
                Toast.makeText(view_allgarage.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}