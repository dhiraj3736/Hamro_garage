package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class chooseuser extends AppCompatActivity {
   ImageView customer,mechanic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseuser);
        customer=findViewById(R.id.customerbtn);
        mechanic=findViewById(R.id.mechanicbtn);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName="customer";
                Intent intent=new Intent(chooseuser.this,activity_login.class);
                intent.putExtra("user",userName);
                startActivity(intent);
            }
        });
        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName="mechanic";
                Intent intent=new Intent(chooseuser.this,activity_login.class);
                intent.putExtra("user",userName);
                startActivity(intent);
            }
        });

    }
}