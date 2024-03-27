package com.example.quickcash;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.Objects.CustomViewAction;

import org.junit.Before;
import org.junit.Test;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.SharedPreferences;

public class EmployerJobApplicantsEspressoTest {

    Context c = getApplicationContext();

    @Before
    public void launchFragment() {
        SharedPreferences sp = c.getSharedPreferences("session_login", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> {
            InstrumentationRegistry.getInstrumentation().getUiAutomation()
                    .grantRuntimePermission(activity.getPackageName(), "android.permission.ACCESS_COARSE_LOCATION");
            InstrumentationRegistry.getInstrumentation().getUiAutomation()
                    .grantRuntimePermission(activity.getPackageName(), "android.permission.ACCESS_FINE_LOCATION");

        });
    }

    @Test
    public void testJobApplicantsPageTransition() throws InterruptedException {
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.myJobsButton)).perform(click());
        onView(withId(R.id.jobList)).perform(actionOnItemAtPosition(0, CustomViewAction.
                clickChildViewWithId(R.id.buttonTv)));
        Thread.sleep(4000);
        onView(withId(R.id.applicantsBtn)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.applyButton)).check(doesNotExist());
    }

    @Test
    public void testJobApplicantsDetailsPage() throws InterruptedException{
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.myJobsButton)).perform(click());
        onView(withId(R.id.jobList)).perform(actionOnItemAtPosition(0, CustomViewAction.
                clickChildViewWithId(R.id.buttonTv)));
        Thread.sleep(4000);
        onView(withId(R.id.applicantsBtn)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.jobApplicantList)).perform(actionOnItemAtPosition(0, CustomViewAction.
                clickChildViewWithId(R.id.buttonJa)));
        Thread.sleep(4000);
        onView(withId(R.id.jobApplicantList)).check(doesNotExist());
    }
}
