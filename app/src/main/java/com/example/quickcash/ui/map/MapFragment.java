package com.example.quickcash.ui.map;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import com.example.quickcash.Activities.JobApplyActivity;
import com.example.quickcash.Activities.SearchActivity;
import com.example.quickcash.FirebaseStuff.LocationTable;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.UserLocation;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployeeMapActivityBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentEmployeeMapActivityBinding binding;
    private GoogleMap googleMap;
    private UserLocation userCurrentLocation;
    private ArrayList<Job> jobList;

    private HashMap<Marker, Job> markerJobMap = new HashMap<>();

    /*
    this will hold the user's current location
     */
    public MapFragment(ArrayList<Job> jobs) {
        this.jobList = jobs;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEmployeeMapActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        //setupViewModel();
//        Button backButton = root.findViewById(R.id.backFromMaps);
//
//        backButton.setOnClickListener(view -> {
//            Intent intent = new Intent(getActivity(), SearchActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(intent);
//        });

        return root;
    }

    private void setupLocationListener() {
        LocationTable locationTable = new LocationTable();
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("session_login", MODE_PRIVATE);
        String userEmail = sharedPrefs.getString("email", "");
        if (userEmail != null && !userEmail.isEmpty()) {
            /*
            retrieves the current user's (employee) location from the database
            */
            locationTable.retrieveLocationFromDatabase(userEmail, location -> {
                if (location != null) {
                    setUserCurrentLocation(location);
                }
            });
        }
    }

    public void setUserCurrentLocation(UserLocation location) {
        userCurrentLocation = location;
        if (googleMap != null) {
            addMarkerForCurrentUserLocation();
            moveCameraToUserLocation();
        }
        /*
        updates job markers as required
         */
        displayJobMarkers(jobList);
    }

    private void addMarkerForCurrentUserLocation() {
        if (googleMap != null && userCurrentLocation != null) {
            LatLng userLatLng = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(userLatLng).title("Your Location"));
        }
    }

    private void moveCameraToUserLocation() {
        if (googleMap != null && userCurrentLocation != null) {
            LatLng currentUserLatLng = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLng, 10));
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private void displayJobMarkers(ArrayList<Job> jobs) {
        if (googleMap != null && jobs != null) {
            /*
            clears existing markers
            */
            googleMap.clear();
            /*
            re-adds the user's location marker
            */
            addMarkerForCurrentUserLocation();
            /*
            loops through all jobs and adds markers for them
            */
            for (Job job : jobs) {
                LatLng jobPosition = new LatLng(job.getLatitude(), job.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(jobPosition).title(job.getTitle());
                Marker marker = googleMap.addMarker(markerOptions);
                marker.setTag(job);
                markerJobMap.put(marker, job);
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @SuppressLint("PotentialBehaviorOverride")
                    @Override
                    public boolean onMarkerClick(@NonNull final Marker marker) {

                        Job job = markerJobMap.get(marker);
                        if(job!=null) {
                            showJobDetailsPrompt(job);
                            return true;
                        }
                        else{
                            showUserPrompt();
                            return true;
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        /*
        starts listening for the user's location
         */
        this.googleMap = googleMap;
        setupLocationListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showJobDetailsPrompt(Job job) {
        new AlertDialog.Builder(getContext())
                .setTitle(job.getTitle())
                .setMessage("Wanna see what I have inside my wallet waltuh??")
                .setPositiveButton("Yes", (dialog, which) -> openJobDetailsActivity(job))
                .setNegativeButton("No", null)
                .show();
    }

    private void showUserPrompt(){
        new AlertDialog.Builder(getContext())
                .setTitle("Current User Location")
                .setMessage("I am you, and you are me. This is where you are!")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show();
    }

    private void openJobDetailsActivity(Job j){
        Intent intent = new Intent(this.getActivity(), JobApplyActivity.class);
        intent.putExtra("job", j);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.getActivity().startActivity(intent);
    }
}


