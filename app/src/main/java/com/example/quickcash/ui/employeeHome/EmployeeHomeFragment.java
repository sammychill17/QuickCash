package com.example.quickcash.ui.employeeHome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.Constants;
import com.example.quickcash.LocationActivity;
import com.example.quickcash.LocationTable;
import com.example.quickcash.R;
import com.example.quickcash.UserLocation;
import com.example.quickcash.databinding.FragmentEmployeehomeBinding;

public class EmployeeHomeFragment extends Fragment {

    private FragmentEmployeehomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployeeHomeViewModel employeeHomeViewModel =
                new ViewModelProvider(this).get(EmployeeHomeViewModel.class);

        binding = FragmentEmployeehomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences sp = requireContext().getSharedPreferences(Constants.sessionData_spID, Context.MODE_PRIVATE);

        final String userEmail = sp.getString("email", "Error - cannot get email");
        final String userPass = sp.getString("password", "Error - cannot get password");
        final String userName = sp.getString("username", "Error - cannot get username");

        final TextView textView = binding.dashboardTextViewWelcome;
        textView.setText("Dashboard");
        final TextView roleView = binding.dashboardTextViewRoleLabel;
        roleView.setText(userName + " is an employee!");

        final TextView latLabel = binding.textViewLat;
        final TextView longLabel = binding.textViewLong;

        LocationTable locationTable = new LocationTable();
        locationTable.retrieveLocationFromDatabase(location -> {
            locationTable.updateLocationInDatabase(location);
            setLat(String.valueOf(location.getLatitude()), latLabel);
            setLong(String.valueOf(location.getLongitude()), longLabel);
        });
//        setLat("To be implemented");
//        setLong("To be implemented");
        return root;
    }

    private void setLat(String lat, TextView view) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Latitude: ");
        if (lat == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(lat);
        }
//        TextView latLabel = requireActivity().findViewById(R.id.textViewLat);
        view.setText(stringBuilder.toString());
    }

    private void setLong(String longitude, TextView view) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Longitude: ");
        if (longitude == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(longitude);
        }
//        TextView longLabel = requireActivity().findViewById(R.id.textViewLong);
        view.setText(stringBuilder.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}