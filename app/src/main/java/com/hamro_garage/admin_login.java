package com.hamro_garage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class admin_login extends AppCompatActivity {
    EditText lemail,lpassword; String user;

    String email, password;

    private static String url = Endpoints.ADMIN_LOGIN_URL;


    Button lloginbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email=password="";
        lemail=findViewById(R.id.logemail);
        lpassword=findViewById(R.id.logpassword);
        lloginbtn=findViewById(R.id.loginbtn);


    }
    public void login(View view){
        email=lemail.getText().toString().trim();
        password=lpassword.getText().toString().trim();
        if(email.isEmpty()){
            Toast toast = Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT);
            TextView toastMessage = toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
        }
        if(password.isEmpty()){
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
        }
        if (!email.equals("")&& !password.equals("")){
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                            if(response.equals("success")) {
                                Intent intent2 = new Intent(admin_login.this, admin_dashboard.class);
//                                intent.putExtra("session_id",sessionID);
                                startActivity(intent2);

                                finish();


                        } else if (response.equals("fail")) {
                            Toast.makeText(admin_login.this, "Invalid Login Id/Password", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }





                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(admin_login.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String ,String> data=new HashMap<>();
                    data.put("email",email);
                    data.put("password",password);
                    data.put("type",String.valueOf(user));
                    return data;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
    }
}