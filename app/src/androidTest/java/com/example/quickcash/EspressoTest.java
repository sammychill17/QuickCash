package com.example.quickcash;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertFalse;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {
    public ActivityScenario<RegistrationActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(RegistrationActivity.class);
        scenario.onActivity(activity -> {
            activity.setupRegistrationButton();
            activity.initializeDatabaseAccess();
        });
    }

    @Test //correct
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.emailText)).perform(typeText(""));
        onView(withId(R.id.nameText)).perform(typeText("Mary"));
        onView(withId(R.id.passText)).perform(typeText("Qwerty1234"));
        onView(withId(R.id.employeeRole)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_FIELD_ERROR)));
    }
}
