package com.example.quickcash;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class MainActivity extends AppCompatActivity implements PermissionsUtil.PermissionResultCallback {

    /*
    FusedLocationProviderClient is used to receive location updates.
    */
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Initializes the FusedLocationProviderClient.
        */
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        /*
        this checks for location permission using the java class PermissionUtils.
        */
        if (PermissionsUtil.checkLocationPermission(this)) {
            /*
            permission has been granted already,
            Thus, start location updates.
            */
            startLocationUpdates();
        } else {
            /*
            Permission has not been not granted,
            Thus, requests for it.
            */
            PermissionsUtil.requestLocationPermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        /*
        Handle the result of the permission request result using java class PermissionUtils.
        */
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.handlePermissionsResult(requestCode, grantResults, this);
    }

    @Override
    public void onPermissionGranted() {
        /*
        Called when location permission has already been granted.
        */
        startLocationUpdates();
    }

    @Override
    public void onPermissionDenied() {
        /*
        Called when location permission has been denied.
        */
        Toast.makeText(this, "Permission rejected", Toast.LENGTH_SHORT).show();
    }

    private void startLocationUpdates() {
        if (PermissionsUtil.checkLocationPermission(this)) {
            LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build();

            final com.example.quickcash.Location[] lastLocation = {null}; // Array to hold the last known location

            LocationCallback locationCallback = new LocationCallback() {

                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null || locationResult.getLocations().isEmpty()) {
                        Toast.makeText(MainActivity.this, "No Location Detected", Toast.LENGTH_SHORT).show();
                    } else {
                        for (android.location.Location location : locationResult.getLocations()) {
                            com.example.quickcash.Location newLoc = new com.example.quickcash.Location(location.getLatitude(), location.getLongitude());

                            /*
                             Checks if the new location is significantly different from the last saved location
                             Note: checkEquals is not necessary as though I believe,
                             there remains a possibility that 2 users can have the same location
                             hence, it stores same co-ordinates for 2 different users.
                             utilized it here to test out something different.
                             */
                            if (lastLocation[0] == null || !lastLocation[0].checkEquals(newLoc)) {
                                LocationTable locationTable = new LocationTable();
                                locationTable.addLocationToDatabase(newLoc);
                                /*
                                 Updates the last known location
                                 */
                                lastLocation[0] = newLoc;
                            }
                        }
                    }
                }
            };
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }
}

