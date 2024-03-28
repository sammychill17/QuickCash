package com.example.quickcash.Activities.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

public class MyEmployeesViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    Button payButton;
    Button rateButton;
    ImageView imageView;
    View itemView;
    public MyEmployeesViewHolder(@NonNull View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.nameBr);
        payButton = (Button) itemView.findViewById(R.id.payButton);
        rateButton = (Button) itemView.findViewById(R.id.rateButton);
        imageView = (ImageView) itemView.findViewById(R.id.imageBr);
        this.itemView = itemView;
    }
}
