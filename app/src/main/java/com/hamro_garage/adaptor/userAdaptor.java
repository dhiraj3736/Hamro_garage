package com.hamro_garage.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hamro_garage.Endpoints;
import com.hamro_garage.R;
import com.hamro_garage.approve;
import com.hamro_garage.model.listofbusiness;
import com.hamro_garage.model.listofuser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userAdaptor extends ArrayAdapter<listofuser> {

    Context context;
    List<listofuser>arraylistuser;

    public userAdaptor(@NonNull Context context, List<listofuser>arraylistuser) {
        super(context, R.layout.user_list,arraylistuser);
        this.context=context;
        this.arraylistuser=arraylistuser;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,null,true);
        TextView tvID=view.findViewById(R.id.id);
        TextView tvname=view.findViewById(R.id.name);
        TextView tvaddress=view.findViewById(R.id.address);
        TextView tvmobile=view.findViewById(R.id.mobile);
        TextView tvemail=view.findViewById(R.id.email);
        TextView tvtype=view.findViewById(R.id.type);


        tvID.setText(arraylistuser.get(position).getId());
        tvname.setText(arraylistuser.get(position).getName());
        tvaddress.setText(arraylistuser.get(position).getAddress());
        tvmobile.setText(arraylistuser.get(position).getMobile());
        tvemail.setText(arraylistuser.get(position).getEmail());
        tvtype.setText(arraylistuser.get(position).getType());






        return view;
    }
}
