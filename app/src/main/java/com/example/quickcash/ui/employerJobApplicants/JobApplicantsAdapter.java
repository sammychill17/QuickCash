package com.example.quickcash.ui.employerJobApplicants;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.ui.employerJobList.EmployerJobListFragment;
import com.example.quickcash.ui.employerJobList.EmployerJobListHolder;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageDirections;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageFragment;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

public class JobApplicantsAdapter extends RecyclerView.Adapter<JobApplicantsHolder> {

    Context c;
    ArrayList<Job> models;
    private EmployerJobListFragment mFragment;

    public void EmployerJobListAdapter(Context c, ArrayList<Job> models, EmployerJobListFragment fragment) {
        this.c = c;
        this.models = models;
        this.mFragment = fragment;
    }

    @NonNull
    @Override
    public JobApplicantsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_job_item_list, parent, false);
        return new JobApplicantsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobApplicantsHolder holder, int position) {
        JobApplicantsHolder.name.setText(models.get(position).getTitle());

        JobApplicantsHolder.button.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        if(models==null){ //I added this on suggestion from ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
            return 0;
        }
        return models.size();
    }

    public void setImageIcon(int pos){
        JobTypes currJobType = models.get(pos).getJobType();
        JobApplicantsHolder.imageView.setImageResource(R.drawable.best_logo_ever);
    }

    /*
     * This entire method was added by suggestion of ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
     */
    public void updateJobs(List<Job> jobs) {
        this.models = (ArrayList<Job>) jobs;
    }
}
