package com.example.quickcash.ui.employeePreferredJobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;

import java.util.List;

/*
 adapter class for displaying job types with images in a ListView.
 each job type has an associated image that changes based on its checked state.
 */
public class EmployeePreferredAdapter extends ArrayAdapter<JobTypes> {
    private List<Boolean> checkedStates;
    /*
     constructor for initializing the adapter with the context, job types, and their checked states.
     */
    public EmployeePreferredAdapter(@NonNull Context context, @NonNull List<JobTypes> jobTypes, @NonNull List<Boolean> checkedStates) {
        super(context, 0, jobTypes);
        this.checkedStates = checkedStates;
    }
    /*
     essentially, creates the view for each item in the ListView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.preferred_job_type_items, parent, false);
        JobTypes jobType = getItem(position);
        ImageView imageView = convertView.findViewById(R.id.imageViewJobType);
        TextView textView = convertView.findViewById(R.id.textViewJobType);
        /*
        setting a tag on the imageView for testing purposes (don't mind it)
         */
        imageView.setTag(jobType.name());
        /*
         sets the image and text based on the checked state and job type
         */
        int imageResId = getImageResourceForJobType(jobType, checkedStates.get(position));
        imageView.setImageResource(imageResId);
        /*
        sets text according to the job type's name
         */
        textView.setText(jobType.name());

        /*
         onclicklistener to toggle the checked state and update the image and text accordingly
         */
        imageView.setOnClickListener(v -> {
            boolean newState = !checkedStates.get(position);
            checkedStates.set(position, newState);
            int newImageResId = getImageResourceForJobType(jobType, newState);
            imageView.setImageResource(newImageResId);
            /*
             notifies the adapter to refresh the list item view
             */
            notifyDataSetChanged();
        });
        return convertView;
    }

    /*
     helper method to get the correct image resource based on the job type
     and checked state (for example- preferred jobs have heart based icons)
     */
    private int getImageResourceForJobType(JobTypes jobType, boolean isChecked) {
        int imageResId;
        /*
        determines the correct image based on the job type and whether it is checked,
        changing the image once clicked.
         */
        if (isChecked) {
            switch (jobType) {
                case YARDWORK:
                    imageResId = R.drawable.icon_yardwork_preferred;
                    break;
                case BABYSITTING:
                    imageResId = R.drawable.icon_babysitting_preferred;
                    break;
                case TECH:
                    imageResId = R.drawable.icon_tech_preferred;
                    break;
                case ARTS_CREATIVE:
                    imageResId = R.drawable.icon_arts_creative_preferred;
                    break;
                case CHAUFFEUR:
                    imageResId = R.drawable.icon_hitman_preferred;
                    break;
                case COOK:
                    imageResId = R.drawable.icon_cook_preferred;
                    break;
                case PETCARE:
                    imageResId = R.drawable.icon_petcare_preferred;
                    break;
                case TUTORING:
                    imageResId = R.drawable.icon_tutoring_preferred;
                    break;
                case MAGICIAN:
                    imageResId = R.drawable.icon_magician_preferred;
                    break;
                case WELDER:
                    imageResId = R.drawable.icon_politician_preferred;
                    break;
                case MOVING:
                    imageResId = R.drawable.icon_moving_preferred;
                    break;
                default:
                    /*
                    don't mind this
                     */
                    imageResId = R.drawable.best_logo_ever;
                    break;
            }
        } else {
            switch (jobType) {
                case YARDWORK:
                    imageResId = R.drawable.icon_yardwork;
                    break;
                case BABYSITTING:
                    imageResId = R.drawable.icon_babysitting;
                    break;
                case TECH:
                    imageResId = R.drawable.icon_tech;
                    break;
                case ARTS_CREATIVE:
                    imageResId = R.drawable.icon_arts_creative;
                    break;
                case CHAUFFEUR:
                    imageResId = R.drawable.icon_hitman;
                    break;
                case COOK:
                    imageResId = R.drawable.icon_cook;
                    break;
                case PETCARE:
                    imageResId = R.drawable.icon_petcare;
                    break;
                case TUTORING:
                    imageResId = R.drawable.icon_tutoring;
                    break;
                case MAGICIAN:
                    imageResId = R.drawable.icon_magician;
                    break;
                case WELDER:
                    imageResId = R.drawable.icon_politician;
                    break;
                case MOVING:
                    imageResId = R.drawable.icon_moving;
                    break;
                default:
                    imageResId = R.drawable.best_logo_ever;
                    break;
            }
        }
        return imageResId;
    }

    /*
     returns the list of checked states for each job type accordingly
     */
    public List<Boolean> getCheckedStates() {
        return checkedStates;
    }

    /*
     sets the checked states for the job types and notifies the adapter to refresh
     */
    public void setCheckedStates(List<Boolean> checkedStates) {
        this.checkedStates = checkedStates;
        notifyDataSetChanged();
    }
}

