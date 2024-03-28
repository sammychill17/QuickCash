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

public class JobHistoryListEspressoTest {
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
    public void testJobHistoryPage() throws InterruptedException {
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("peterparker22@outlook.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("iamspiderman"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.myJobHistoryButton)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.employeeJobHistoryFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.upcomingJobHeader)).check(matches(withText(
                containsString("Upcoming Jobs"))));
        onView(withId(R.id.previousJobHeader)).check(matches(withText(
                containsString("Previous Jobs"))));
    }
}
