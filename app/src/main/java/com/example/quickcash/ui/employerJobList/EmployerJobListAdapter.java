package com.example.quickcash.ui.employerJobList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;
import com.example.quickcash.ui.employerHome.EmployerHomeFragment;
import com.example.quickcash.ui.employerJobList.EmployerJobListFragment;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageFragment;

import java.util.ArrayList;
import java.util.List;

public class EmployerJobListAdapter extends RecyclerView.Adapter<EmployerJobListHolder> {

    Context c;
    ArrayList<Job> models;
    private EmployerJobListFragment mFragment;

    public EmployerJobListAdapter(Context c, ArrayList<Job> models, EmployerJobListFragment fragment) {
        this.c = c;
        this.models = models;
        this.mFragment = fragment;
    }

    @NonNull
    @Override
    public EmployerJobListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.employer_job_item_list, null);
        return new EmployerJobListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerJobListHolder holder, int position) {

        EmployerJobListHolder.title.setText(models.get(position).getTitle());
        EmployerJobListHolder.desc.setText(models.get(position).getDescription());
        EmployerJobListHolder.imageView.setImageResource(R.drawable.best_logo_ever);
        EmployerJobListHolder.button.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(mFragment);
            navController.navigate(R.id.employerJobPageFragment);
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
    public void updateJobs(List<Job> jobs) {
        this.models = (ArrayList<Job>) jobs;
    }
}
