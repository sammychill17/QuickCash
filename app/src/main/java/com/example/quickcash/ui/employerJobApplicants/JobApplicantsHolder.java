package com.example.quickcash.ui.employerJobApplicants;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

public class JobApplicantsHolder extends RecyclerView.ViewHolder{
    static TextView name;
    static Button button;

    public JobApplicantsHolder(@NonNull View itemView){
        super(itemView);

        this.name = itemView.findViewById(R.id.nameJa);
        this.button = itemView.findViewById(R.id.buttonJa);
    }
}
