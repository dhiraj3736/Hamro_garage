package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("HamroGarage", Context.MODE_PRIVATE);
        String usertype = sharedPreferences.getString("userType", null);
        String userId = sharedPreferences.getString("session_id", null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userId != null) {
                    Log.d("UserType", usertype);
                    if(usertype.equals("customer")) {
                        Intent intent2 = new Intent(splash_screen.this, user_dashboard.class);
//                                intent.putExtra("session_id",sessionID);
                        startActivity(intent2);

                        finish();
                    }
                    else if (usertype.equals("mechanic")){
                        Intent intent = new Intent(splash_screen.this, garage_dashbord.class);
//                                intent.putExtra("session_id",sessionID);
                        startActivity(intent);
                        finish();

                    }

                } else {
                    Intent intent= new Intent(splash_screen.this,chooseuser.class);
                    startActivity(intent);
                    finish();
                }


                finish();
            }
        },4000);

    }
}