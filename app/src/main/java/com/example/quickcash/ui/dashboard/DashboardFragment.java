package com.example.quickcash.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.test.espresso.matcher.ViewMatchers;

import com.example.quickcash.Activities.MyEmployeesActivity;
import com.example.quickcash.Activities.MyMoneyActivity;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentDashboardBinding;
import com.example.quickcash.ui.employeeHome.EmployeeHomeFragment;
import com.example.quickcash.ui.employeeJobHistory.EmployeeRatingsHistoryFragment;

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
            binding.meFragmentButton.setImageResource(R.drawable.your_employees);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myEmployees = new Intent(requireContext(), MyEmployeesActivity.class);
                    startActivity(myEmployees);
                }
            };
            binding.meFragmentButton.setOnClickListener(onClickListener);
            binding.title.setOnClickListener(onClickListener);
            binding.employeeHistoryCardView.setVisibility(View.GONE);
            binding.myMoneyButton.setOnClickListener(null);
        } else {
            // I am an Employee
            binding.meFragmentButton.setImageResource(R.drawable.ratingbutton);

            binding.meFragmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = NavHostFragment.findNavController(DashboardFragment.this);
                    navController.navigate(R.id.employeeRatingsHistoryFragment);
                }
            });

            binding.employeeHistoryCardView.setVisibility(View.VISIBLE);
            binding.myMoneyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent evilIntent = new Intent(requireContext(), MyMoneyActivity.class);
                    startActivity(evilIntent);
                }
            });
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}