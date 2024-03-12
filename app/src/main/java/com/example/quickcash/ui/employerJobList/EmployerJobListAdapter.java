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
import java.util.List;

public class EmployerJobListAdapter extends RecyclerView.Adapter<EmployeeJobListHolder> {

    Context c;
    ArrayList<Job> models = new ArrayList<>();

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
        if(models==null){ //I added this on suggestion from ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
            return 0;
        }
        return models.size();
    }

    /*
    * This entire method was added by suggestion of ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
     */
    public void updateJobs(List<Job> jobs) {
        this.models = (ArrayList<Job>) jobs;
    }

}
