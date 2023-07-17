package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class garage_dashbord extends AppCompatActivity {
    LinearLayout garagedetail,addlocation,creategarage;
String sessionid;
TextView profilebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_dashbord);

        garagedetail=findViewById(R.id.garage_detail);
        addlocation=findViewById(R.id.addlocationbtn);
        profilebtn=findViewById(R.id.profilebtn);
        creategarage=findViewById(R.id.creategarage);

//
//        Intent intent=getIntent();
//        sessionid=intent.getStringExtra("session_id");
//        Log.d("session_id",""+sessionid);

        garagedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(garage_dashbord.this,View_garage_detail.class);
//                intent1.putExtra("session_id",sessionid);
                startActivity(intent1);

            }

        });
        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(garage_dashbord.this,detail_location.class);
                startActivity(intent);
            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(garage_dashbord.this,garage_owner_profile.class);
                startActivity(intent);
            }
        });
        creategarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(garage_dashbord.this,create_garage.class);
                startActivity(intent);
            }
        });
    }
}