package com.example.quickcash.ui.employerJobList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjoblistBinding;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.util.ArrayList;

public class EmployerJobListFragment extends Fragment{

    private FragmentEmployerjoblistBinding binding;
    private String firebaseURL = "https://quickcash-6941c-default-rtdb.firebaseio.com";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPageViewModel EmployerJobListViewModel =
                new ViewModelProvider(this).get(EmployerJobPageViewModel.class);

        SharedPreferences sp = requireContext().getSharedPreferences("session_login",
                Context.MODE_PRIVATE);

        binding = FragmentEmployerjoblistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Job> jobArrayList = new ArrayList<Job>();
        ListView list = root.findViewById(R.id.jobList);
        FirebaseDatabase.getInstance(firebaseURL).getReference().child("Jobs").
                addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                            if (jobSnapshot.hasChild("employer")) {
                                String jobEmail = String.valueOf(jobSnapshot.child("employer")
                                        .getValue());
                                String jobTitle = String.valueOf(jobSnapshot.child("title")
                                        .getValue());
                                String jobDesc = String.valueOf(jobSnapshot.child("description")
                                        .getValue());
                                JobTypes jobType = JobTypes.valueOf(String.valueOf(
                                        jobSnapshot.child("jobType").getValue()));
                                Double jobSalary = Double.parseDouble(String.valueOf(
                                        jobSnapshot.child("salary").getValue()));
                                Duration jobDuration = Duration.ofHours(40); // hardcoded to 40 until handling of Duration object is done

                                if (jobEmail.equals(sp.getString("email", "default"))) {
                                    Job j = new Job(jobTitle, jobDesc, jobType, jobSalary, jobDuration,
                                            jobEmail);
                                    jobArrayList.add(j);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // we will need to handle this in a better way later, but for now just moving titles to a different arraylist
        ArrayList<String> jobTitles = new ArrayList<>();

        for (Job job : jobArrayList) {
            jobTitles.add(job.getTitle());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,
                jobTitles);
        list.setAdapter(arrayAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}