package com.example.quickcash;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

public class PermissionsUtil {

    /*
    Constant for location permission request code
    */
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    /*
    Interface for permission result callbacks
    */
    public interface PermissionResultCallback {
        void onPermissionGranted();
        void onPermissionDenied();
    }

    /*
    Checks if location permission is granted
    */
    public static boolean checkLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /*
    Requests location permission
    */
    public static void requestLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    /*
    Handles the result of permission request
    */
    public static void handlePermissionsResult(int requestCode, int[] grantResults, PermissionResultCallback callback) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onPermissionGranted();
            } else {
                callback.onPermissionDenied();
            }
        }
    }
}