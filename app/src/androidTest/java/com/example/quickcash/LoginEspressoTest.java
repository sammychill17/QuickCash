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

/**
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    public ActivityScenario<MainActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            activity.loadRoleSpinner();
            activity.setupRegistrationButton();
            activity.initializeDatabaseAccess();
        });
    }


    @Test
    public void checkIfNetIDIsEmpty() {
        onView(withId(R.id.netIDBox)).perform(typeText(""));
        onView(withId(R.id.emailBox)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Buyer"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_NET_ID)));
    }


    @Test
    public void checkIfNetIDIsValid() {
        onView(withId(R.id.emailBox)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.netIDBox)).perform(typeText("mh881819"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Buyer"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }


    @Test
    public void checkIfNetIDIsInvalid() {
        onView(withId(R.id.emailBox)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.netIDBox)).perform(typeText("88mh1819"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Buyer"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_NET_ID)));
    }


    @Test
    public void checkIfEmailIsValid() {
        onView(withId(R.id.netIDBox)).perform(typeText("sh885689"));
        onView(withId(R.id.emailBox)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Buyer"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }


    @Test
    public void checkIfEmailIsInvalid() {
        //buggy Espresso test, write an appropriate test!
        assertFalse(1 + 2 == 3);
    }


    @Test
    public void checkIfEmailIsNotFromDAL() {
        onView(withId(R.id.netIDBox)).perform(typeText("xy884568"));
        onView(withId(R.id.emailBox)).perform(typeText("abc123@usask.ca"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Buyer"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_DAL_EMAIL)));
    }


    @Test
    public void checkIfRoleIsValid() {
        onView(withId(R.id.netIDBox)).perform(typeText("xy884568"));
        onView(withId(R.id.emailBox)).perform(typeText("abc123@dal.ca"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Seller"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }


    @Test
    public void checkIfRoleIsInvalid() {
        onView(withId(R.id.netIDBox)).perform(typeText("xy884568"));
        onView(withId(R.id.emailBox)).perform(typeText("abc123@dal.ca"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Select your role"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_ROLE)));
    }
}
 */