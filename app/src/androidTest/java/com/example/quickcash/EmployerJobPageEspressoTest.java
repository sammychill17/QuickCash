package com.example.quickcash;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.Matchers.containsString;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.Objects.CustomViewAction;

import org.junit.Before;
import org.junit.Test;

public class EmployerJobPageEspressoTest {
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
    public void testJobPage() throws InterruptedException {
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.myJobsButton)).perform(click());
        onView(withId(R.id.jobList)).perform(actionOnItemAtPosition(0, CustomViewAction.
                clickChildViewWithId(R.id.buttonTv)));
        Thread.sleep(1000);
        onView(withId(R.id.employerJobPageFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.jobPageTitle)).check(matches(withText(
                containsString("I want to see a magic show!"))));
        onView(withId(R.id.jobPageType)).check(matches(withText(
                containsString("Job Type: MAGICIAN"))));
        onView(withId(R.id.jobPageAddress)).check(matches(withText(
                containsString("Address: 260 Dingle Rd, Halifax, NS"))));
        onView(withId(R.id.jobPageDate)).check(matches(withText(
                containsString("Start date: Sat Mar 02 18:57:39 -03:00 2024"))));
        onView(withId(R.id.jobPageEmployerName)).check(matches(withText(
                containsString("Employer: parker@morrison.com"))));
        onView(withId(R.id.jobPageApplicant)).check(matches(withText(
                containsString("No applicant set yet"))));
        onView(withId(R.id.jobPageDesc)).check(matches(withText(
                containsString("Do a card trick! Pull a rabbit out of your hat! Anything!"))));
    }
}
