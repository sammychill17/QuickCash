package com.example.quickcash.FirebaseStuff;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.quickcash.Activities.JobApplyActivity;
import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.BusinessLogic.PushNotifHandler;
import com.example.quickcash.Objects.UserLocation;
import com.example.quickcash.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

//code lifted from Usmi Mukherjee's Tutorial 10 repo (for now):
//https://git.cs.dal.ca/umukherjee/csci3130_push_notifications/-/tree/main

//class is for showing the notifications on the very top
//firebase messaging service from the build.gradle file
//runs in the background

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;
/*
hopefully this foundation of code works for our cause
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private FirebasePreferredJobsHelper preferredJobsHelper;
    private LocationTable locationTable;
    private PushNotifHandler pushNotifHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        preferredJobsHelper = new FirebasePreferredJobsHelper();
        locationTable = new LocationTable();
        pushNotifHandler = new PushNotifHandler(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        /*
         this exits early if the message doesn't contain data payload
         */
        if (message.getData().isEmpty()) {
            return;
        }

        String jobType = message.getData().get("job_type");
        String jobLocation = message.getData().get("job_location");

        /*
         this exits if jobType or jobLocation are missing in the data payload
         */
        if (jobType == null || jobLocation == null) {
            return;
        }

        /*
         this retrieves the user's email from SharedPreferences (check getEmail method below)
         */
        String userEmail = getEmployeeEmail();
        if (userEmail == null) return;

        /*
         this retrieves preferred jobs and user location
         to decide whether to show the notification
         */
        preferredJobsHelper.retrievePreferredJobs(userEmail, preferredJobs -> {
            if (preferredJobs.contains(jobType)) {
                locationTable.retrieveLocationFromDatabase(userEmail, userLocation -> {
                    if (isWithinRange(userLocation, jobLocation)) {
                        String title = "New Job Posted in Your Preferred Category";
                        String body = "A new " + jobType + " job is available near you. Check it out now!";
                        try {
                            /*
                             creates and send the notification
                             */
                            JSONObject notificationJSON = pushNotifHandler.createNotificationJSON();
                            pushNotifHandler.sendNotification(notificationJSON);
                        } catch (JSONException e) {
                            Log.e("MyFirebaseMessagingService", "Error creating notification JSON", e);
                        }
                    }
                });
            }
        });
    }

    private String getEmployeeEmail() {
        SharedPreferences prefs = getSharedPreferences("app_preferences", MODE_PRIVATE);
        return prefs.getString("session_login", null);
    }

    private boolean isWithinRange(UserLocation userLocation, String jobLocation) {
        // TODO: Implement the logic to check if the job location is within the user's preferred range
        return true;
    }
}

