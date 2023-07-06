package com.hamro_garage.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hamro_garage.R;
import com.hamro_garage.module.profile;

import java.util.ArrayList;
import java.util.List;

public class MyprofileAdaptor extends ArrayAdapter<profile> {
    Context context;
    List<profile> arrayListprofile;


    public MyprofileAdaptor(@NonNull Context context, ArrayList<profile> arrayListprofile) {
        super(context,  R.layout.myprofile, arrayListprofile);
        this.context=context;
        this.arrayListprofile=arrayListprofile;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myprofile, null, true);
        TextView fullname=view.findViewById(R.id.full_name);
      TextView address=view.findViewById(R.id.caddress);
        TextView mobile=view.findViewById(R.id.mobile);
        TextView email=view.findViewById(R.id.email);
        TextView password=view.findViewById(R.id.password);

        fullname.setText(arrayListprofile.get(position).getFullname());
        address.setText(arrayListprofile.get(position).getAddress());
        mobile.setText(arrayListprofile.get(position).getMobile());
        email.setText(arrayListprofile.get(position).getEmail());
        password.setText(arrayListprofile.get(position).getPassword());

        return view;
    }
}
