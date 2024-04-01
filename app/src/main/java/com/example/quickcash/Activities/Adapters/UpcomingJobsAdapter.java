package com.example.quickcash.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quickcash.R;
import com.example.quickcash.Objects.Job;

import java.util.List;

public class UpcomingJobsAdapter extends ArrayAdapter<Job> {

    private List<Job> upcomingJobsList;
    private Context context;
    public UpcomingJobsAdapter(Context context, List<Job> upcomingJobsList) {
        super(context, 0, upcomingJobsList);
        this.context = context;
        this.upcomingJobsList = upcomingJobsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.fragment_employeejobhistory, parent, false);
        }
        // Get the current job item
        Job currentJob = upcomingJobsList.get(position);

        TextView titleTextView = itemView.findViewById(R.id.jobTitleField);
        titleTextView.setText(currentJob.getTitle());

        return itemView;
    }
}
