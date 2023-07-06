package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class garage_dashbord extends AppCompatActivity {
    LinearLayout addbusiness,addlocation;
String sessionid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_dashbord);

        addbusiness=findViewById(R.id.addbtn);
        addlocation=findViewById(R.id.addlocationbtn);


        Intent intent=getIntent();
        sessionid=intent.getStringExtra("session_id");
        Log.d("session_id",""+sessionid);

        addbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(garage_dashbord.this,detail_services.class);
                intent1.putExtra("session_id",sessionid);
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
    }
}