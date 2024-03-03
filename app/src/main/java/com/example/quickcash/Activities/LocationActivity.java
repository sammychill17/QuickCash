package com.example.quickcash.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.BusinessLogic.PermissionsUtil;
import com.example.quickcash.FirebaseStuff.LocationTable;
import com.example.quickcash.R;
import com.example.quickcash.Objects.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

/*
 This class handles location-based operations/activities for the user.
 it works on initiating the location updates
 and reacts to permission results, accordingly.
 */
public class LocationActivity extends AppCompatActivity {

    /*
     enables/provides access to the Fused Location Provider API.
     */
    public FusedLocationProviderClient fusedLocationClient;
    /*
     stores the email passed through the intent from RegistrationActivity.java
     */
    public String userEmail;

    /*
     Important method that initializes the activity, the fusedLocationClient, and starts location updates.
     parameter- (savedInstanceState)
     If the activity is being re-initialized after previously being shut down,
     this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         content view setup logic could be called here, using setContentView,
         for next iteration if needed for google maps integration.
         */

        /*
         initializes the FusedLocationProviderClient for accessing the user's location.
         */
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        /*
         this retrieve the user's email from the intent
         (similar comment few lines above, related to this code written)
         */
        Intent intent = getIntent();
        userEmail = intent.getStringExtra(String.valueOf(R.string.user_email));

        /*
         checking for location permissions and requesting
          them if not already granted.
         */
        if (!PermissionsUtil.checkLocationPermission(this)) {
            PermissionsUtil.requestLocationPermission(this);
        } else {
            /*
             if permissions are already granted,
             location updates should start immediately.
             */
            startLocationUpdates();
        }

//        Intent main = new Intent(LocationActivity.this, MainActivity.class);
//        startActivity(main);
        finish();
    }

    /*
      Handles the result of the permission request initiated in onCreate.
      parameter- (requestCode), The integer request code originally supplied to requestPermissions(),
      which allows us to identify who this result came from.
      parameter- (permissions), The requested permissions.
      parameter- (grantResults), The grant results for the corresponding permissions,
       which is either PERMISSION_GRANTED or PERMISSION_DENIED.
       NOTE, request code and grant results are never null and
       should not be NULL.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*
        this handles the permissions result, initiating location updates if permission has been granted.
         */
        PermissionsUtil.handlePermissionsResult(requestCode, grantResults, granted -> {
            if (granted) {
                startLocationUpdates();
            } else {
                /*
                 if permission was denied, display a toast message to the user.
                 (strings.xml placeholders used)
                 */
                Toast.makeText(LocationActivity.this, R.string.error_location_permission_required, Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
     initiates location updates if permission has been granted.
     Sets up a LocationRequest with high accuracy and a callback to handle location updates.
     */
    private void startLocationUpdates() {
        if (PermissionsUtil.checkLocationPermission(this)) {
            /*
             this creates a location request with high accuracy priority.
             */
            LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY).setIntervalMillis(5000).build();

            /*
             this defines a callback to receive location updates.
             */
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null || locationResult.getLocations().isEmpty()) {
                        Toast.makeText(LocationActivity.this, "No Location Detected", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    /*
                     processes the received location update here.
                     */
                    android.location.Location location = locationResult.getLocations().get(0);
                    /*
                     creates a UserLocation instance and adds it's fields to the LocationTable.
                     */
                    UserLocation userLocation = new UserLocation(userEmail, location.getLatitude(), location.getLongitude());
                    LocationTable locationTable = new com.example.quickcash.FirebaseStuff.LocationTable();
                    locationTable.addLocationToDatabase(userLocation);
                }
            };

            /*
             requests location updates using the fusedLocationClient.
             */
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            /*
             else, requests location permission if not granted yet.
             NOTE: location permission is being requested
             many times here and there,
             this is important so that exceptions related
             to security is not thrown when parsing through parts of the code
             when using the actual app.
             */
            PermissionsUtil.requestLocationPermission(this);
        }
    }
}
