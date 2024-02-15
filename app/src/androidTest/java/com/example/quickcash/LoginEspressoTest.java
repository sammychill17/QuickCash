package com.example.quickcash;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertFalse;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class LoginEspressoTest {

    public ActivityScenario<LoginActivity> scenario;
    Resources res = ApplicationProvider.getApplicationContext().getResources();

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(LoginActivity.class);
        scenario.onActivity(activity -> {

        });
    }


    @Test
    public void checkIfEmailIsEmpty() {
        // credit: Barry Carroll (https://medium.com/@baz8080/testing-snackbar-on-android-8fb634e682e3)
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(""));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("@password"));
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withText(res.getString(R.string.error_email_empty))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfAccountDoesNotExist() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("testshouldfail@dal.ca"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("@password"));
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withText(res.getString(R.string.error_email_invalid))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("testshouldfail2@dal.ca"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText(""));
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withText(res.getString(R.string.error_password_empty))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("testshouldpass@dal.ca"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("@password"));
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withText(res.getString(R.string.success))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("testshouldfail2@dal.ca"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("@password"));
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withText(res.getString(R.string.error_password_incorrect))).check(matches(isDisplayed()));
    }
}

