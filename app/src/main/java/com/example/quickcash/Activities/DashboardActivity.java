package com.example.quickcash.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quickcash.R;
import com.example.quickcash.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_dashboard);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        
        SharedPreferences sp = getApplicationContext().getSharedPreferences(getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);

        String currentRole = sp.getString("role", "error!");
        if (currentRole.equals("Employee")) {
            navController.setGraph(R.navigation.mobile_navigation_employee);
        } else if (currentRole.equals("Employer")) {
            navController.setGraph(R.navigation.mobile_navigation_employer);
        } else {
            Snackbar.make(navView, "Error - unrecognized role!", Snackbar.LENGTH_SHORT).show();
        }

        NavigationUI.setupWithNavController(binding.navView, navController);
        Snackbar.make(navView, (CharSequence) getApplicationContext().getString(R.string.LOGIN_SUCCESS), -1).show();

        Intent locationIntent = new Intent(getApplicationContext(), LocationActivity.class);
        locationIntent.putExtra(String.valueOf(R.string.user_email), sp.getString("email", "error!"));
        startActivity(locationIntent);
    }

}