package com.example.quickcash;
import java.util.function.Consumer;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

/*
Class which refactored duplicate and reusable codes previously used
in LocationActivity (previously MainActivity declared in a different branch).
 */
public class PermissionsUtil {

    /*
     constant declared for location permission request code
     */
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

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
    Handles the result of permission request using a lambda expression
    Note: lambda expressions make code more readable and easier to follow through.
     */
    public static void handlePermissionsResult(int requestCode, int[] grantResults, Consumer<Boolean> callback) {
        boolean isGranted = requestCode == LOCATION_PERMISSION_REQUEST_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        callback.accept(isGranted);
    }
}
