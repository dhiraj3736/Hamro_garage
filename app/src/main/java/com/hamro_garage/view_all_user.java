package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hamro_garage.adaptor.businessAdaptor;
import com.hamro_garage.adaptor.userAdaptor;
import com.hamro_garage.model.listofbusiness;
import com.hamro_garage.model.listofuser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class view_all_user extends AppCompatActivity {
    GridView gridView;
    userAdaptor adaptor;
    String url = Endpoints.get_user_detail;
    listofuser user;
    public static ArrayList<listofuser>arrayListuser=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_user);
        gridView=findViewById(R.id.gridview);
        adaptor=new userAdaptor(this,arrayListuser);
        gridView.setAdapter(adaptor);
        retriveData();
    }

    public void retriveData(){

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayListuser.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String sucess=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if (sucess.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String id=object.getString("id");
                            String name=object.getString("name");
                            String address=object.getString("address");
                            String mobile=object.getString("mobile");
                            String email=object.getString("email");
                            String type=object.getString("type");
                            user=new listofuser(id,name,address,mobile,email,type);
                            arrayListuser.add(user);
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
                Toast.makeText(view_all_user.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}