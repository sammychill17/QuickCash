package com.example.quickcash.ui.employerJobList;

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

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjoblistBinding;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageViewModel;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class EmployerJobListFragment extends Fragment{

    RecyclerView mRecyclerView;
    EmployerJobListAdapter myAdapter;
    private FragmentEmployerjoblistBinding binding;

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

        mRecyclerView = binding.jobList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        myAdapter = new EmployerJobListAdapter(this.getContext(), getEmployerJobList());
        mRecyclerView.setAdapter(myAdapter);

        return root;
    }

    private ArrayList<Job> getEmployerJobList(){
        Job job1 = new Job("Burma", "I want to change Myanmar back to Burma! It was better that way!", JobTypes.HITMAN, 120.0, Duration.ofHours(12), "blake.march@villain.fr", new Date());
        Job job2 = new Job("Olive Oil!", "I want to be allowed to drink olive oil! Straight from the bottle!", JobTypes.YARDWORK, 20.0, Duration.ofHours(2), "blake.april@villain.fr", new Date());
        Job job3 = new Job("Please fix my pc!", "I have 129739 viruses on my PC and I need them removed!", JobTypes.TECH, 200.0, Duration.ofHours(8), "blake.january@villain.fr", new Date());
        ArrayList<Job> adapter = new ArrayList<>();
        adapter.add(job1);
        adapter.add(job2);
        adapter.add(job3);
        return adapter;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}