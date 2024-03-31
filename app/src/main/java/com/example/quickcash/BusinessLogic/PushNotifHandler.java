package com.example.quickcash.BusinessLogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.FirebaseStuff.LocationTable;
import com.example.quickcash.Objects.Filters.DistanceFilter;
import com.example.quickcash.Objects.UserLocation;
import com.example.quickcash.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//code lifted from Usmi Mukherjee's Tutorial 10 repo (for now):
//https://git.cs.dal.ca/umukherjee/csci3130_push_notifications/-/tree/main

public class PushNotifHandler extends AppCompatActivity {

    private Context context;
    private RequestQueue requestQueue;
    private String pushNotificationEndpoint;
    private String firebaseServerKey;
    private UserLocation userLocation;

    public PushNotifHandler(Context context, String pushNotificationEndpoint, String firebaseServerKey) {
        this.context = context;
        this.pushNotificationEndpoint = pushNotificationEndpoint;
        this.firebaseServerKey = firebaseServerKey;
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void sendNotification(String jobKey, Double jobLat, Double jobLong) {
        //try catch block for JSON exception
        try {
            //the first json object - to
            JSONObject notificationJSONBody = new JSONObject();
            notificationJSONBody.put("title", "Job Available Near You!");
            notificationJSONBody.put("body", "A new job has been posted in your area.");

            // Create the data JSON object
            JSONObject dataJSONBody = new JSONObject();
            dataJSONBody.put("job_id", jobKey);
            dataJSONBody.put("job_lat", jobLat);
            dataJSONBody.put("job_long", jobLong);

            // Create the message JSON object and attach notification and data
            JSONObject messageJSONBody = new JSONObject();
            messageJSONBody.put("topic", "Posted Jobs");
            messageJSONBody.put("notification", notificationJSONBody);
            messageJSONBody.put("data", dataJSONBody);

            // Create the final push notification JSON object and attach the message
            JSONObject pushNotificationJSONBody = new JSONObject();
            pushNotificationJSONBody.put("message", messageJSONBody);
            //parameters sent in the request:
            //type of request - post- sending data to firebase
            //url - push notification endpoint
            //data - body of the notification
            //toast message
            //error listener
            Log.d("LOG", pushNotificationJSONBody.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    pushNotificationEndpoint,
                    pushNotificationJSONBody,
                    //lambda syntax
                    response ->
                            Toast.makeText(getApplicationContext(),
                                    "Notification Sent",
                                    Toast.LENGTH_SHORT).show(),
                    //method reference
                    Throwable::printStackTrace) {

                //adding the header to the endpoint
                //parameters used:
                //type of data
                //using the bearer token for authentication of the network request
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + firebaseServerKey);
                    Log.d("headers", headers.toString());
                    return headers;
                }
            };
            //add the request to the queue
            requestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /*
        this method gets a reference to the current user's location by way of the location table
        (which uses email as a unique identifier), then compares it to the job's location
        if it is less than or equal to 5 kilometres in distance, it is within range
        otherwise it is NOT within range
     */
    private boolean isWithinRange(Double jobLat, Double jobLong) {
        Context c = getApplicationContext();
        SharedPreferences sharedPrefs = c.getSharedPreferences("session_login", MODE_PRIVATE);

        LocationTable locationTable = new LocationTable();
        String userEmail = sharedPrefs.getString("email", "");

        locationTable.retrieveLocationFromDatabase(userEmail, new LocationTable.OnLocationDataReceivedListener() {
            @Override
            public void onLocationDataReceived(UserLocation location) {
                userLocation = location;
            }
        });

        DistanceFilter distanceFilter = new DistanceFilter();
        distanceFilter.setValue(5);

        double comp = (double) distanceFilter.getValue();
        double diff = distanceFilter.getDistanceFromLatLonInKm(jobLat, jobLong,
                userLocation.getLatitude(), userLocation.getLongitude());
        return ((diff) <= comp);
    }
}
