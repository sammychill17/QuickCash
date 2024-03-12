package com.example.quickcash;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.example.quickcash.Activities.DashboardActivity;
import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.ui.employerJobList.EmployerJobListFragment;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.SharedPreferences;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmployerJobListEspressoTest {

    Context c = getApplicationContext();

    @Before
    public void launchFragment() {
        SharedPreferences sp = c.getSharedPreferences("session_login", c.MODE_PRIVATE);
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
    public void testMyJobsButton() throws InterruptedException {
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.myJobsButton)).perform(click());
    }

    public void testJobDetailsButton() {
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.myJobsButton)).perform(click());
        //onView(withId(R.id.buttonTv)).perform(click());
    }

    public void testJobApplicantsButton() {
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.myJobsButton)).perform(click());
        //onView(withId(R.id.buttonTv)).perform(click());
        //onView(withId(R.id.seeApplicantsButton)).perform(click());
    }

    public void testChooseApplicantPage(){
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.myJobsButton)).perform(click());
        //onView(withId(R.id.buttonTv)).perform(click());
        //onView(withId(R.id.seeApplicantsButton)).perform(click());
        //onView(withId(R.id.buttonTv)).perform(click());
    }

}
