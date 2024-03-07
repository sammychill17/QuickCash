package com.example.quickcash.ui.employerHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.databinding.FragmentEmployerhomeBinding;

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
        final Button myJobsButton = binding.myJobsButton;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}