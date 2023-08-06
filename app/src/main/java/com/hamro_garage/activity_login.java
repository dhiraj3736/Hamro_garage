package com.hamro_garage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class activity_login extends AppCompatActivity {
    EditText lemail,lpassword; String user;

    String email, password;

    private static String url = Endpoints.LOGIN_URL;


    Button lloginbtn;
    TextView lsignupbtn;
    String sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=password="";
        lemail=findViewById(R.id.logemail);
        lpassword=findViewById(R.id.logpassword);
        lloginbtn=findViewById(R.id.loginbtn);
        lsignupbtn=findViewById(R.id.logsignbtn);



        Intent intent=getIntent();
        user=intent.getStringExtra("user");
        Log.d("user",""+user);



        lsignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(activity_login.this,user_signup.class);
                intent1.putExtra("user",user);
                startActivity(intent1);
            }
        });




        lloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=lemail.getText().toString().trim();
                password=lpassword.getText().toString().trim();

                if (email.isEmpty()) {
                    showCustomToast("Please enter email");
                } else if (password.isEmpty()) {
                    showCustomToast("Please enter password");
                }
                if (!email.equals("")&& !password.equals("")){
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                if(jsonObject.has("session_id") && jsonObject.getString("result").equals("sucess")){
                                    SharedPreferences sharedPreferences= getSharedPreferences("HamroGarage", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();

                                    sessionID=jsonObject.getString("session_id");
                                    StaticValues.garageid=sessionID;
                                    editor.putString("session_id", sessionID);
                                    editor.putString("userType",user);
                                    Log.d("Session_id",""+sessionID);
                                    editor.apply();

                                    if(user.equals("customer")) {
                                        Intent intent2 = new Intent(activity_login.this, user_dashboard.class);
//                                intent.putExtra("session_id",sessionID);
                                        startActivity(intent2);

                                    }
                                    else if (user.equals("mechanic")){
                                        Intent intent = new Intent(activity_login.this, garage_dashbord.class);
                                        intent.putExtra("session_id",sessionID);
                                        startActivity(intent);



                                    }
                                } else if (jsonObject.getString("result").equals("fail")) {
                                    showCustomToast("Invalid Login Id/Password");

                                }else {
                                    showCustomToast("Invalid Login Id/Password");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                showCustomToast("Error parsing server response");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(activity_login.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
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
        });
    }
    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_layout));

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}