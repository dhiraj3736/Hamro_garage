package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hamro_garage.adaptor.MyprofileAdaptor;
import com.hamro_garage.module.profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class user_profile extends AppCompatActivity {
    ListView listView;
    MyprofileAdaptor adaptor;
    ArrayList<String> mylist;
    String sessionid;
profile pro1;
    public static ArrayList<profile> profileArrayList=new ArrayList<>();
    private static String url = Endpoints.USER_PROFILE;

    private String loggedInUserId; // Variable to hold the logged-in user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        listView = findViewById(R.id.myListView);
        adaptor=new MyprofileAdaptor(this,profileArrayList);
        listView.setAdapter(adaptor);
        retrieveData();
        mylist = new ArrayList<String>();
        Intent intent=getIntent();
        String sessionid = intent.getStringExtra("session_id");
        Log.d("session_id",""+sessionid);
    }
    public void retrieveData(){

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                profileArrayList.clear();
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String sucess= jsonObject.getString("success");
                    JSONArray jsonArray= jsonObject.getJSONArray("data");

                    if (sucess.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String fullname=object.getString("fullname");
                            String address=object.getString("address");
                            String mobile=object.getString("mobile");
                            String email=object.getString("email");
                            String password=object.getString("password");

                            pro1=new profile(fullname,address,mobile,email,password);
                            profileArrayList.add(pro1);
                            adaptor.notifyDataSetChanged();
                        }
                    }


                }
                catch (JSONException e){
                    e.printStackTrace();
                }


            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(user_profile.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
    public void edit(){
        Intent intent=new Intent(user_profile.this,editprofile.class);
        startActivity(intent);
    }
}
//        {
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data = new HashMap<>();
//
//                data.put("u_id", StaticValues.garageid);
//                return data;
//            }
//        }