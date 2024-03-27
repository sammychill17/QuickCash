package com.example.quickcash;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertFalse;


import android.content.res.Resources;

import com.example.quickcash.Activities.DashboardActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EmployerDashboardEspressoTest {

    public ActivityScenario<DashboardActivity> scenario;

    Resources res;
    @Before
    public void setup() {
        res = getApplicationContext().getResources();
        scenario = ActivityScenario.launch(DashboardActivity.class);
        scenario.onActivity(activity -> {
            //
        });
    }

    @Test
    public void checkIfPasswordIsInvalid() throws InterruptedException {

    }
}
