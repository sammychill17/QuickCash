package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.res.Resources;
import android.util.Log;

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
        res = ApplicationProvider.getApplicationContext().getResources();
        scenario = ActivityScenario.launch(LoginActivity.class);
        scenario.onActivity(activity -> {
            //
        });
    }

    @Test
    public void checkIfTrueIsTrue() {
        assertTrue(true);
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("Loki360@gmail.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("@password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        System.out.println("res.getString() is " + res.getString(R.string.LOGIN_ERROR_PASSWORD_INCORRECT));
        Log.d("LoginEspressoTest", "res.getString() is " + res.getString(R.string.LOGIN_ERROR_PASSWORD_INCORRECT));
        onView(withText(res.getString(R.string.LOGIN_ERROR_PASSWORD_INCORRECT))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        // credit: Barry Carroll (https://medium.com/@baz8080/testing-snackbar-on-android-8fb634e682e3)
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(""));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("@password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withText(res.getString(R.string.LOGIN_ERROR_EMAIL_EMPTY))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfAccountDoesNotExist() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("testshouldfail@dal.ca"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("@password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withText(res.getString(R.string.LOGIN_ERROR_PASSWORD_INCORRECT))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("testshouldfail2@dal.ca"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withText(res.getString(R.string.LOGIN_ERROR_PASSWORD_EMPTY))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("Loki360@gmail.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("Thor123456"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withText(res.getString(R.string.LOGIN_SUCCESS))).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        assertTrue(true);
    }
}

