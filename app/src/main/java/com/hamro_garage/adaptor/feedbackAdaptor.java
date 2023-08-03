package com.hamro_garage.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hamro_garage.R;
import com.hamro_garage.model.listofbusiness;
import com.hamro_garage.model.listofcomment;

import java.util.List;

public class feedbackAdaptor extends ArrayAdapter<listofcomment> {

    Context context;
    List<listofcomment> arraylistcomment;

    public feedbackAdaptor(@NonNull Context context, List<listofcomment>arraylistcomment) {
        super(context, R.layout.listfeedback,arraylistcomment);
        this.context=context;
        this.arraylistcomment=arraylistcomment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listfeedback,null,true);
        TextView tvname=view.findViewById(R.id.userNameTextView);
        TextView tvfeedback=view.findViewById(R.id.feedbackTextView);


        tvname.setText(arraylistcomment.get(position).getName());
        tvfeedback.setText(arraylistcomment.get(position).getFeedback());







        return view;
    }
}
