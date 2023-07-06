package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class Main_dashboard extends AppCompatActivity {
    Button dlogin,dsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        ImageSlider imageslider=findViewById(R.id.imageslider);
        ArrayList<SlideModel>slideModels=new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image5, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image6, ScaleTypes.FIT));

        imageslider.setImageList(slideModels, ScaleTypes.FIT);

        dlogin=findViewById(R.id.daslogin);
        dsignup=findViewById(R.id.dassignup);

        dlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),activity_login.class));
            }
        });

        dsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_dashboard.this,user_signup.class));
            }
        });
    }
}