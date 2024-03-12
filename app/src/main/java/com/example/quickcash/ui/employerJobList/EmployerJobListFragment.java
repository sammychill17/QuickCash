package com.example.quickcash.ui.employerJobList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.FirebaseStuff.EmployerDBHelper;
import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjoblistBinding;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageViewModel;
import com.google.firebase.database.DatabaseError;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;

public class EmployerJobListFragment extends Fragment{

    RecyclerView mRecyclerView;
    EmployerJobListAdapter myAdapter;
    private FragmentEmployerjoblistBinding binding;

    SharedPreferences sp;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPageViewModel EmployerJobListViewModel =
                new ViewModelProvider(this).get(EmployerJobPageViewModel.class);

        binding = FragmentEmployerjoblistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button backButton = binding.backButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        Context c = getContext();

        sp = c.getApplicationContext().getSharedPreferences("session_login", c.MODE_PRIVATE);

        mRecyclerView = binding.jobList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        myAdapter = new EmployerJobListAdapter(this.getContext(), getEmployerJobList());
        mRecyclerView.setAdapter(myAdapter);

        return root;
    }

    private ArrayList<Job> getEmployerJobList(){
        final ArrayList<String>[] keyAdapter = new ArrayList[1]; // This is a list which will contain all of the jobs that contain the currently logged in employer.
        EmployerDBHelper helper = new EmployerDBHelper(); // This is an EmployerDBHelper
        String spEmail = sp.getString("email", ""); // This should be retrieving the email of the currently logged in user (Parker helped me with this)
        final int[] size = {0};
        helper.getJobsByEmployer(spEmail, new EmployerDBHelper.JobObjectCallback() { //This should retrieve the list of Job Keys that are associated with jobs that have the logged in user as the employerEmail.
            @Override
            public void onObjectReceived(ArrayList<String> keys) {
                keyAdapter[0] = keys;
                size[0] = keys.size();
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });

        final ArrayList<Job>[] jobAdapter = new ArrayList[1]; //This is the adapter that will contain the Job Objects that are associated with the keys in keyAdapter.
        JobDBHelper jobHelper = new JobDBHelper(); //This is the DB Helper object that will assist us in what it is that we have to do.
        for(int i = 0; i < size[0]; i++) { //For every key in the keyAdapter...
            jobHelper.getJobByKey(keyAdapter[0].get(i), new JobDBHelper.JobObjectCallback()  { //A new call to the getJobByKey function, from JobDBHelper
                @Override
                public void onObjectReceived(Job object) { //When a job is found...
                    jobAdapter[0].add(object); //We add it to the jobAdapter.
                }

                @Override
                public void onError(DatabaseError error) {

                }
            });
        }
        return jobAdapter[0]; //We return the adapter with all of the associated jobs.
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}