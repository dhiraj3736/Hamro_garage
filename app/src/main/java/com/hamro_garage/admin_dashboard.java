package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class admin_dashboard extends AppCompatActivity {
LinearLayout business,userdetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        business=findViewById(R.id.business);

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(admin_dashboard.this,view_allgarage.class);
//                intent1.putExtra("session_id",sessionid);
                startActivity(intent1);
            }
        });
    }
}