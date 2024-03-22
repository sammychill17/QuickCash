package com.example.quickcash.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.Activities.MyEmployeesActivity;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sp = requireContext().getSharedPreferences(getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        if (sp.contains("role") && ("Employer".equals(sp.getString("role", "error!")))) {
            // I am an Employer
            binding.title.setText("My Employees");
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myEmployees = new Intent(requireContext(), MyEmployeesActivity.class);
                    startActivity(myEmployees);
                }
            };
            binding.meFragmentButton.setOnClickListener(onClickListener);
            binding.title.setOnClickListener(onClickListener);
        } else {
            // I am an Employee
            binding.title.setText("My Rating");
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            };
            binding.meFragmentButton.setOnClickListener(onClickListener);
            binding.title.setOnClickListener(onClickListener);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}