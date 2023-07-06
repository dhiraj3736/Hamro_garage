package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       Thread thread=new Thread(){
        public  void  run(){
            try {
                sleep(3000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                Intent intent=new Intent(splash_screen.this, chooseuser.class);

                startActivity(intent);
            }
        }
       };thread.start();
    }
}