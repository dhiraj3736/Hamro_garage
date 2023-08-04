package com.hamro_garage;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hamro_garage.model.listofcomment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class garage_dashbord extends AppCompatActivity {
    LinearLayout garagedetail,addlocation,creategarage;
    String sessionid;
    TextView profilebtn;
    ImageView moreOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_dashbord);

        garagedetail=findViewById(R.id.garage_detail);
        addlocation=findViewById(R.id.addlocationbtn);
        profilebtn=findViewById(R.id.profilebtn);
        creategarage=findViewById(R.id.creategarage);
        moreOptions=findViewById(R.id.moreOptions);
        registerForContextMenu(moreOptions);

        String url=Endpoints.getgarageid;

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String sucess=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if (sucess.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String g_id=object.getString("g_id");
                            StaticValues.g_id=g_id;


                        }
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    Toast.makeText(garage_dashbord.this, errorMessage, Toast.LENGTH_SHORT).show();
                } else {
                    // If the error message is null or empty, you can provide a default error message.
                    Toast.makeText(garage_dashbord.this, "An error occurred.", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();


                data.put("u_id", StaticValues.garageid);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

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
                Intent intent=new Intent(garage_dashbord.this,show_feedback.class);
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