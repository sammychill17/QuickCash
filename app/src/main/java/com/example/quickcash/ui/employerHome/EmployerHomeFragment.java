package com.example.quickcash.ui.employerHome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerhomeBinding;
import com.example.quickcash.ui.employerJobList.EmployerJobListFragment;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

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

        jobPostButton.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(EmployerHomeFragment.this);
            navController.navigate(R.id.employerJobPostFragment);
        });


        final Button myJobsButton = binding.myJobsButton;

        myJobsButton.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(EmployerHomeFragment.this);
            navController.navigate(R.id.employerJobListFragment);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}