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
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
// import androidx.test.rule.GrantPermissionRule;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import com.example.quickcash.Activities.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
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
    Resources res = getApplicationContext().getResources();

    //@Rule
    //public GrantPermissionRule locationPerm = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION);

    @Before
    public void setup() {
        res = getApplicationContext().getResources();
        scenario = ActivityScenario.launch(LoginActivity.class);
        scenario.onActivity(activity -> {
            //
        });
    }

    @Test
    public void checkIfPasswordIsInvalid() throws InterruptedException {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("Loki360@gmail.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("@password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        Thread.sleep(1000);
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

    /*
    * This test is antiquated. Before the implementation of Dashboards, it would have worked more effectively
    * But now that we have UI Automator, to test to see if appropriate dashboards are accessed, then the
    * ultimate purpose of this test is lost.
     */
//    @Test
//    public void checkIfPasswordIsValid() throws InterruptedException {
//        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("Loki360@gmail.com"));
//        onView(withId(R.id.editTextTextPassword)).perform(typeText("Thor123456"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.buttonGotoLogin)).perform(click());
//        Thread.sleep(1000);
//        onView(withText(res.getString(R.string.LOGIN_SUCCESS))).check(matches(isDisplayed()));
//    }

    @Test
    public void checkIfTrueIsTrue() {
        assertTrue(true);
    }
}

