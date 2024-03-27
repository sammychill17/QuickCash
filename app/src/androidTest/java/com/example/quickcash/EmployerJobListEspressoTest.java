package com.example.quickcash;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiObjectNotFoundException;

import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.Objects.CustomViewAction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmployerJobListEspressoTest {

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
    public void testMyJobsButton() throws UiObjectNotFoundException, InterruptedException {
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.myJobsButton)).perform(click());
    }

    @Test
    public void testJobPageButton() throws InterruptedException {
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
    }
}
