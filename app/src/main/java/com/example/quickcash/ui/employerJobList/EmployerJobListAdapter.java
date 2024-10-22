package com.example.quickcash.ui.employerJobList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.ui.employerHome.EmployerHomeFragment;
import com.example.quickcash.ui.employerJobList.EmployerJobListFragment;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageDirections;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageFragment;

import java.util.ArrayList;
import java.util.List;

/*
* This class is designed to take Job information and populate a recycler view with information about the given job.
 */
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
        setImageIcon(position);

        EmployerJobListHolder.button.setOnClickListener(v -> {
            EmployerJobPageFragment jobPage = new EmployerJobPageFragment();
            Job j = models.get(position);
            Bundle b = new Bundle();
            b.putSerializable("job", j);
            jobPage.setArguments(b);

            NavController navController = NavHostFragment.findNavController(mFragment);
            navController.navigate(new EmployerJobPageDirections(j));
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
        if(currJobType==JobTypes.ARTS_CREATIVE){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_arts_creative);
        }
        else if(currJobType==JobTypes.BABYSITTING){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_babysitting);
        }
        else if(currJobType==JobTypes.COOK){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_cook);
        }
        else if(currJobType==JobTypes.CHAUFFEUR){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_hitman);
        }
        else if(currJobType==JobTypes.MAGICIAN){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_magician);
        }
        else if(currJobType==JobTypes.MOVING){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_moving);
        }
        else if(currJobType==JobTypes.PETCARE){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_petcare);
        }
        else if(currJobType==JobTypes.WELDER){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_politician);
        }
        else if(currJobType==JobTypes.TECH){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_tech);
        }
        else if(currJobType==JobTypes.TUTORING){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_tutoring);
        }
        else if(currJobType==JobTypes.YARDWORK){
            EmployerJobListHolder.imageView.setImageResource(R.drawable.icon_yardwork);
        }
        else{
            EmployerJobListHolder.imageView.setImageResource(R.drawable.best_logo_ever);

        }
    }

    /*
     * This entire method was added by suggestion of ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
     */
    public void updateJobs(List<Job> jobs, View extractView) {
        this.models = (ArrayList<Job>) jobs;

        if (models.isEmpty()) {
            TextView emptyTextView = extractView.findViewById(R.id.emptyJobListTextView);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        else {
            TextView emptyTextView = extractView.findViewById(R.id.emptyJobListTextView);
            emptyTextView.setVisibility(View.INVISIBLE);
        }
    }
}
