package com.example.quickcash;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.example.quickcash.BusinessLogic.PushNotifHandler;

public class PushNotifTest {

    @Mock
    private RequestQueue mockRequestQueue;
    @Mock
    private Context mockContext;

    private PushNotifHandler pushNotifHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockContext.getApplicationContext()).thenReturn(mockContext);
        pushNotifHandler = new PushNotifHandler(mockContext);
    }

    @Test
    public void createNotificationJSON_createsCorrectNotificationJSON() throws JSONException {
        JSONObject notificationJSON = pushNotifHandler.createNotificationJSON();

        assertNotNull("Notification JSON should not be null", notificationJSON);
        assertTrue("Notification JSON should have a 'notification' key", notificationJSON.has("notification"));
        assertTrue("Notification JSON should have a 'data' key", notificationJSON.has("data"));

        JSONObject notification = notificationJSON.getJSONObject("notification");
        assertEquals("Title should match", "Job Available Near You!", notification.getString("title"));
        assertEquals("Body should match", "A new job has been posted in your area.", notification.getString("body"));

        JSONObject data = notificationJSON.getJSONObject("data");
        assertEquals("Job location should match", "Halifax", data.getString("jobLocation"));
        assertEquals("Job ID should match", "HF-128369", data.getString("job_id"));
    }

}