package com.hamro_garage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hamro_garage.adaptor.businessAdaptor;
import com.hamro_garage.adaptor.feedbackAdaptor;
import com.hamro_garage.model.listofbusiness;
import com.hamro_garage.model.listofcomment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class show_feedback extends AppCompatActivity {
    ListView listView;
    feedbackAdaptor adaptor;
    String url = Endpoints.getfeedback;
    listofcomment comment;
    public static ArrayList<listofcomment>arrayListcomment=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feedback);
        listView=findViewById(R.id.showfeedback);
        adaptor=new feedbackAdaptor(this,arrayListcomment);
        listView.setAdapter(adaptor);
        retriveData();
    }

    public void retriveData(){

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayListcomment.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String sucess=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if (sucess.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String name=object.getString("fullname");
                            String feedback=object.getString("feedback");

                            comment=new listofcomment(name,feedback);
                            arrayListcomment.add(comment);
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
                Toast.makeText(show_feedback.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();


                data.put("g_id", StaticValues.g_id);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}