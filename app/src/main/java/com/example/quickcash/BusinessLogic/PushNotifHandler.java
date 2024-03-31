package com.example.quickcash.BusinessLogic;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//code lifted from Usmi Mukherjee's Tutorial 10 repo (for now):
//https://git.cs.dal.ca/umukherjee/csci3130_push_notifications/-/tree/main

public class PushNotifHandler {

    private RequestQueue requestQueue;
    private Context context;

    public PushNotifHandler(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public JSONObject createNotificationJSON() throws JSONException {
        JSONObject notificationJSONBody = new JSONObject();
        notificationJSONBody.put("title", "Job Available Near You!");
        notificationJSONBody.put("body", "A new job has been posted in your area.");

        JSONObject dataJSONBody = new JSONObject();
        dataJSONBody.put("jobLocation", "Halifax");
        dataJSONBody.put("job_id", "HF-128369");

        /*
         this creates the final JSON object that includes both notification content and data
         */
        JSONObject finalJSONBody = new JSONObject();
        finalJSONBody.put("notification", notificationJSONBody);
        finalJSONBody.put("data", dataJSONBody);
        finalJSONBody.put("to", "topic"); //change the topic if you have to

        return finalJSONBody;
    }


    public void sendNotification(JSONObject notificationJSON) {
        String fcmApiUrl = context.getString(R.string.PUSH_NOTIFICATION_ENDPOINT);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, fcmApiUrl, notificationJSON,
                response -> {
                    Log.d("PushNotifHandler", "Notification sent successfully. YAY");
                },
                error -> {
                    Log.e("PushNotifHandler", "Error sending notification. SADGE");
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "key=" + context.getString(R.string.FIREBASE_SERVER_KEY));
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
