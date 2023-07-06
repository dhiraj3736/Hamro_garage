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
import com.hamro_garage.module.service;

import java.util.List;

public class MyAdaptor extends ArrayAdapter<service> {
    Context context;
    List<service> arrayListservice;


    public MyAdaptor(@NonNull Context context, List<service> arrayListservice) {
        super(context, R.layout.custome_list_item, arrayListservice);
        this.context=context;
        this.arrayListservice=arrayListservice;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_list_item, null, true);
        TextView tvID=view.findViewById(R.id.txt_id);
        TextView tvname=view.findViewById(R.id.txt_name);

        tvID.setText(arrayListservice.get(position).getId());
        tvname.setText(arrayListservice.get(position).getService());
        return view;
    }
}
