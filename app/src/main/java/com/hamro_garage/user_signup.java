package com.hamro_garage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class user_signup extends AppCompatActivity {
    private EditText mfullname, maddress, mmobile, memail, mpassword, mrepassword;
    private Button msignupbtn;
    private TextView mloginbtn, mstatus;
    private ProgressBar mprogressbar;
    private static String url = Endpoints.SIGNUP_URL;
    String user;

    String fullname, address, mobile, email, password, repassword, status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup);

        mfullname = findViewById(R.id.fullname);
        maddress = findViewById(R.id.contactaddress);
        mmobile = findViewById(R.id.mobileno);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        mrepassword = findViewById(R.id.repassword);
        mstatus = findViewById(R.id.status);
        msignupbtn = findViewById(R.id.signupbtn);
        mloginbtn = findViewById(R.id.logbtn);
        mprogressbar = findViewById(R.id.progressBar);

        fullname = address = mobile = email = password = repassword = "";

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(user_signup.this, activity_login.class);
                intent1.putExtra("user", user);
                startActivity(intent1);
            }
        });
    }

    public void save(View view) {
        fullname = mfullname.getText().toString().trim();
        address = maddress.getText().toString().trim();
        mobile = mmobile.getText().toString().trim();
        email = memail.getText().toString().trim();
        password = mpassword.getText().toString().trim();
        repassword = mrepassword.getText().toString().trim();

        String Emailmatch = memail.getText().toString().trim();
        String Mobilematch = mmobile.getText().toString().trim();
        String passwordmatch = mpassword.getText().toString().trim();

        if (fullname.isEmpty()) {
            showCustomToast("Please Enter Full Name");
            return;
        }
        if (address.isEmpty()) {
            showCustomToast("Please Enter Address");
            return;
        }
        if (mobile.isEmpty()) {
            Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Mobilematch.matches("^(97|98)\\d{8}$")) {
            Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Emailmatch.matches("^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)@[a-z]+(.[a-z]+)(.[a-z]{2,})$")) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest checkDuplicateRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Username or Email already registered!!")) {
                            Toast.makeText(user_signup.this, "Email or phone number already exists!", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("success")) {
                            Toast.makeText(user_signup.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                            mfullname.setText("");
                            maddress.setText("");
                            memail.setText("");
                            mmobile.setText("");
                            mpassword.setText("");
                            mrepassword.setText("");
                            msignupbtn.setClickable(false);
                        } else if (response.equals("failure")) {
                            Toast.makeText(user_signup.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(user_signup.this, "Registration Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("fullname", fullname);
                data.put("address", address);
                data.put("mobile", mobile);
                data.put("email", email);
                data.put("password", password);
                data.put("repassword", repassword);
                data.put("type", String.valueOf(user));
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(checkDuplicateRequest);
        Log.d("user", "" + user);
    }
    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_layout));

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
