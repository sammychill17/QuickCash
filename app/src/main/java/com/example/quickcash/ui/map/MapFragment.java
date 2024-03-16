package com.example.quickcash.ui.map;

import static android.content.Context.MODE_PRIVATE;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentEmployeeMapActivityBinding binding;
    private GoogleMap googleMap;
    /*
    this will hold the user's current location
     */
    private UserLocation userCurrentLocation;

    private ArrayList<Job> jobList;
    public MapFragment(ArrayList<Job> jobs){
        this.jobList = jobs;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEmployeeMapActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    private void displayJobMarkers(List<Job> jobs) {
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
            googleMap.addMarker(new MarkerOptions().position(jobPosition).title(job.getTitle()));
        }
    }

    private void setupLocationListener() {
        LocationTable locationTable = new LocationTable();
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("session_login", MODE_PRIVATE);
        String userEmail = sharedPrefs.getString("email", "");
        userCurrentLocation = new UserLocation();
        /*
        retrieves the current user's (employee) location from the database
         */
        locationTable.retrieveLocationFromDatabase(userEmail, this::setUserCurrentLocation);
        moveCameraToUserLocation();
    }

    /*
    moves camera towards the User's (employee) Location
     */
    private void moveCameraToUserLocation() {
        if (googleMap != null && userCurrentLocation != null) {
            LatLng currentUserLatLng = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLng, 15)); // Adjust the zoom level
        }
    }

    /*
    adds marker to the employee's location
     */
    private void addMarkerForCurrentUserLocation() {
        if (googleMap != null && userCurrentLocation != null) {
            LatLng userLatLng = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(userLatLng).title("Your Location"));
        }
    }

    public void setUserCurrentLocation(UserLocation location) {
        userCurrentLocation = location;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        /*
        starts listening for the user's location
         */
        setupLocationListener();
        displayJobMarkers(jobList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

