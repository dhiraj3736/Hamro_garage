package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class user_dashboard extends AppCompatActivity {

LinearLayout ubike;
LinearLayout user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        ubike=findViewById(R.id.bikebtn);
        user_profile=findViewById(R.id.profilebtn);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        Fragment myFragment = new UserMapsFragment();
        fragmentTransaction.add(R.id.map,myFragment);



//        Intent intent=getIntent();
//        sessionid=intent.getStringExtra("session_id");
//        Log.d("session_id",""+sessionid);

        ubike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(user_dashboard.this,activity_login.class));
                Intent intent1 = new Intent(user_dashboard.this, nearest_garage.class);
//                intent1.putExtra("sessionID",sessionid);
                startActivity(intent1);
                finish();

            }
        });
        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(user_dashboard.this,user_profile.class));
                Intent intent2 = new Intent(user_dashboard.this, user_profile.class);
//                intent2.putExtra("sessionID",sessionid);
                startActivity(intent2);
                finish();
            }
        });
    }
}