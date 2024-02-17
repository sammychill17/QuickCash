package com.example.quickcash.ui.employeeHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.databinding.FragmentEmployeehomeBinding;

public class EmployeeHomeFragment extends Fragment {

    private FragmentEmployeehomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployeeHomeViewModel employeeHomeViewModel =
                new ViewModelProvider(this).get(EmployeeHomeViewModel.class);

        binding = FragmentEmployeehomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.dashboardTextViewWelcome;
        textView.setText("Dashboard");
        final TextView roleView = binding.dashboardTextViewRoleLabel;
        roleView.setText("I'm an employee!");
        setLat("To be implemented");
        setLong("To be implemented");
        return root;
    }

    private void setLat(String lat) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Latitude: ");
        stringBuilder.append(lat);
        binding.textViewLat.setText(stringBuilder.toString());
    }

    private void setLong(String longitude) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Longitude: ");
        stringBuilder.append(longitude);
        binding.textViewLong.setText(stringBuilder.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}