package com.example.quickcash.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.Constants;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentDashboardBinding;

public class EmployeeDashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        textView.setText("Me fragment");
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final ImageView imageView = binding.mefragmentNatsu;
        SharedPreferences sp = requireContext().getSharedPreferences(Constants.sessionData_spID, Context.MODE_PRIVATE);
        if (sp.contains("role") && ("Employer".equals(sp.getString("role", "error!")))) {
                imageView.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.reisa_happy));

        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}