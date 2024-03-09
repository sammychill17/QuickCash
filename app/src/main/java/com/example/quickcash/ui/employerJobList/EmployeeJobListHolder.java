package com.example.quickcash.ui.employerJobList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

public class EmployeeJobListHolder extends RecyclerView.ViewHolder{
    static ImageView imageView;
    static TextView title;
    static TextView desc;

    public EmployeeJobListHolder(@NonNull View itemView){
        super(itemView);

        this.imageView = itemView.findViewById(R.id.imageIv);
        this.title = itemView.findViewById(R.id.titleTv);
        this.desc = itemView.findViewById(R.id.descriptionTv);
    }
}
