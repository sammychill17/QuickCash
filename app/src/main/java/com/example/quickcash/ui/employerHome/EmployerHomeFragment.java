package com.example.quickcash.ui.employerHome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.Activities.LocationActivity;
import com.example.quickcash.Activities.RegistrationActivity;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerhomeBinding;
import com.example.quickcash.ui.JobApplicantsPage.JobApplicantsFragment;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;

public class EmployerHomeFragment extends Fragment {

    private FragmentEmployerhomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        com.example.quickcash.ui.employerHome.EmployerHomeViewModel EmployerHomeViewModel =
                new ViewModelProvider(this).get(EmployerHomeViewModel.class);

        binding = FragmentEmployerhomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.dashboardTextViewWelcome;
        textView.setText("Dashboard");
        final TextView roleView = binding.dashboardTextViewRoleLabel;
        roleView.setText("I'm an employer!");

        final ImageButton jobPostButton = binding.loseYourselfButton;

        jobPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.dashboardCardView, EmployerJobPostFragment.class, null);
                fragmentTransaction.commit();
            }
        });

        final Button myJobsButton = binding.myJobsButton;

        myJobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.dashboardCardView, JobApplicantsFragment.class, null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}