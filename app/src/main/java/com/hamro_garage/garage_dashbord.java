package com.hamro_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class garage_dashbord extends AppCompatActivity {
    LinearLayout garagedetail,showfeedbackbtn,creategarage;
    String sessionid;
    TextView profilebtn;
    ImageView moreOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_dashbord);

        garagedetail=findViewById(R.id.garage_detail);
//        showfeedbackbtn=findViewById(R.id.showfeedbackbtn);
        profilebtn=findViewById(R.id.profilebtn);
        creategarage=findViewById(R.id.creategarage);
        moreOptions=findViewById(R.id.moreOptions);
        registerForContextMenu(moreOptions);


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
//        showfeedbackbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(garage_dashbord.this,);
//                startActivity(intent);
//            }
//        });
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder=new AlertDialog.Builder(garage_dashbord.this);
            builder.setTitle("Logout");
            builder.setMessage("Do you want to log out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences sharedPreferences=getSharedPreferences("HamroGarage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.remove("userType");
                    editor.remove("session_id");
                    editor.commit();
                    Intent intent=new Intent(garage_dashbord.this,chooseuser.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}