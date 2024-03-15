package com.example.quickcash.ui.employerJobApplicants;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;
import com.example.quickcash.ui.employerApplicantPage.EmployerApplicantPageFragment;
import com.example.quickcash.ui.employerJobList.EmployerJobListFragment;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageFragment;

import java.util.ArrayList;
import java.util.List;

public class JobApplicantsAdapter extends RecyclerView.Adapter<JobApplicantsHolder> {

    Context c;
    ArrayList<String> models;
    private JobApplicantsFragment mFragment;

    public JobApplicantsAdapter(Context c, ArrayList<String> models, JobApplicantsFragment fragment) {
        this.c = c;
        this.models = models;
        this.mFragment = fragment;
    }

    @NonNull
    @Override
    public JobApplicantsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_applicant_item_list, parent, false);
        return new JobApplicantsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobApplicantsHolder holder, int position) {
        JobApplicantsHolder.name.setText(models.get(position));

        JobApplicantsHolder.button.setOnClickListener(v -> {
            EmployerApplicantPageFragment jobPage = new EmployerApplicantPageFragment();
            String email = models.get(position);
            Job job = mFragment.job;
            Bundle e = new Bundle();
            e.putSerializable("email", email);
            e.putSerializable("job", job);
            jobPage.setArguments(e);

            NavController navController = NavHostFragment.findNavController(mFragment);
            navController.navigate(new com.example.quickcash.ui.employerJobApplicants.EmployerApplicantPageDirections(email, job));
        });
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
    public void updateApplicants(List<String> applicants) {
        this.models = (ArrayList<String>) applicants;
    }
}
