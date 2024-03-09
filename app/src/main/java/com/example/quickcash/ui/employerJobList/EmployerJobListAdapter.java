package com.example.quickcash.ui.employerJobList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;

import java.util.ArrayList;

public class EmployerJobListAdapter extends RecyclerView.Adapter<EmployeeJobListHolder> {

    Context c;
    ArrayList<Job> models;

    public EmployerJobListAdapter(Context c, ArrayList<Job> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public EmployeeJobListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.employer_job_item_list, null);
        return new EmployeeJobListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeJobListHolder holder, int position) {

        EmployeeJobListHolder.title.setText(models.get(position).getTitle());
        EmployeeJobListHolder.desc.setText(models.get(position).getDescription());
        EmployeeJobListHolder.imageView.setImageResource(R.drawable.best_logo_ever);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
