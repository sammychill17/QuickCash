package com.example.quickcash.ui.employeeHome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.Activities.SearchActivity;
import com.example.quickcash.FirebaseStuff.LocationTable;
import com.example.quickcash.Objects.UserLocation;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployeehomeBinding;
import com.google.android.material.snackbar.Snackbar;

public class EmployeeHomeFragment extends Fragment {

    private FragmentEmployeehomeBinding binding;
    private boolean isLocationFetched = false;
    private UserLocation location;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployeeHomeViewModel employeeHomeViewModel =
                new ViewModelProvider(this).get(EmployeeHomeViewModel.class);

        binding = FragmentEmployeehomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences sp = requireContext().getSharedPreferences(getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);

        final String userEmail = sp.getString("email", "Error - cannot get email");
        final String userName = sp.getString("username", "Error - cannot get username");

        final TextView textView = binding.dashboardTextViewWelcome;
        textView.setText("Dashboard");
        final TextView roleView = binding.dashboardTextViewRoleLabel;
        roleView.setText(userName + " is an employee!");

        final ImageButton makeMoneyButton = binding.makeMoneyButton;
        View.OnClickListener goToSearchPage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocationFetched) {
                    Intent intent = new Intent(requireContext(), SearchActivity.class);
                    intent.putExtra("currLong", location.getLongitude());
                    intent.putExtra("currLat", location.getLatitude());
                    startActivity(intent);
                } else {
                    Snackbar.make(makeMoneyButton, "Please wait as we try to fetch your location.", Snackbar.LENGTH_SHORT);
                }
            }
        };
        makeMoneyButton.setOnClickListener(goToSearchPage);
        binding.title.setOnClickListener(goToSearchPage);

        LocationTable locationTable = new LocationTable();
        locationTable.retrieveLocationFromDatabase(userEmail, location -> {
            if (location != null) {
                locationTable.updateLocationInDatabase(location);
                this.location = location;
                isLocationFetched = true;
            } else {
                // Location not available!
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