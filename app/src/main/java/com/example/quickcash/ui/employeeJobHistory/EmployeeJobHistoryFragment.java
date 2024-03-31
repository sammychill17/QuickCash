package com.example.quickcash.ui.employeeJobHistory;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Activities.Adapters.PreviousJobsAdapter;
import com.example.quickcash.Activities.Adapters.UpcomingJobsAdapter;
import com.example.quickcash.R;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeJobHistoryFragment extends Fragment {

    private ListView previousJobListView;
    private ListView upcomingJobListView;
    private PreviousJobsAdapter previousAdapter;
    private UpcomingJobsAdapter upcomingAdapter;
    private List<Job> previousJobList;
    private List<Job> upcomingJobList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employeejobhistory, container, false);

        previousJobListView = rootView.findViewById(R.id.previousJobListView);
        upcomingJobListView = rootView.findViewById(R.id.upcomingJobListView);

        previousJobList = new ArrayList<>();
        upcomingJobList = new ArrayList<>();

        previousAdapter = new PreviousJobsAdapter(getContext(), previousJobList);
        upcomingAdapter = new UpcomingJobsAdapter(getContext(), upcomingJobList);

        previousJobListView.setAdapter(previousAdapter);
        upcomingJobListView.setAdapter(upcomingAdapter);

        loadPreviousJobsFromFirebase();
        loadUpcomingJobsFromFirebase();
        return rootView;
    }
    /*
     * Retrieve and load the upcoming jobs of the current employee from the database
     */
    private void loadUpcomingJobsFromFirebase() {
        String employeeEmail = getEmployeeEmailFromSharedPreferences();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posted Jobs");
        databaseReference.orderByChild("assigneeEmail").equalTo(employeeEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upcomingJobList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Job job = snapshot.getValue(Job.class);
                    if (job != null && job.isAssigned() && upcomingJobs(job)) {
                        upcomingJobList.add(job);
                    }
                }
                upcomingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    /*
     * Retrieve and load the previous jobs of the current employee from the database
     */
    private void loadPreviousJobsFromFirebase() {
        String employeeEmail = getEmployeeEmailFromSharedPreferences();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posted Jobs");
        databaseReference.orderByChild("assigneeEmail").equalTo(employeeEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                previousJobList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Job job = snapshot.getValue(Job.class);
                    if (job != null && job.isAssigned() && pastJobs(job)) {
                        previousJobList.add(job);
                    }
                }
                previousAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private String getEmployeeEmailFromSharedPreferences() {
        SharedPreferences sharedPrefs = requireContext().getSharedPreferences("session_login", MODE_PRIVATE);
        return sharedPrefs.getString("email", "");
    }

    /*
    check if the job is in the past
     */
    private boolean pastJobs(Job job) {
        long epochTime = job.getEpoch();
        Date jobDate = new Date(epochTime);
        return jobDate.before(new Date());
    }
    /*
    check if the job is upcoming
     */

    private boolean upcomingJobs(Job job) {
        long epochTime = job.getEpoch();
        Date jobDate = new Date(epochTime);
        return jobDate.after(new Date());
    }
}
