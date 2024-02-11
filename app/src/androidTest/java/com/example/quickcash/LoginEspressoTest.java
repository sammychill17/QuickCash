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

    public ActivityScenario<LoginActivity> scenario;


    @Before
    public void setup() {
        scenario = ActivityScenario.launch(LoginActivity.class);
        scenario.onActivity(activity -> {

        });
    }


    @Test
    public void checkIfEmailIsEmpty() {
        // credit: Barry Carroll (https://medium.com/@baz8080/testing-snackbar-on-android-8fb634e682e3)
        View snackbarLayout = snackbar.getView();
        TextView snackbarText = snackbarLayout.findViewById(R.id.snackbar_text);

        onView(withId(R.id.editTextEmailAddress)).perform(typeText(""));
        onView(withId(R.id.editTextPassword)).perform(typeText("@password"));
        onView(withId(R.id.buttonLogin)).perform(click());
        assertEquals("Email required to login", snackbarTextView.getText().toString());

        Button snackbarAction = snackbarLayout.findViewById(R.id.snackbar_action);
        assertNotNull(snackbarAction);
        assertFalse(snackbarAction.hasOnClickListeners());
        assertEquals(View.GONE, snackbarAction.getVisibility());
    }

    @Test
    public void checkIfAccountDoesNotExist() {
        View snackbarLayout = snackbar.getView();
        TextView snackbarText = snackbarLayout.findViewById(R.id.snackbar_text);

        onView(withId(R.id.editTextEmailAddress)).perform(typeText("testshouldfail@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("@password"));
        onView(withId(R.id.buttonLogin)).perform(click());
        assertEquals("Account not found. Please register", snackbarTextView.getText().toString());

        Button snackbarAction = snackbarLayout.findViewById(R.id.snackbar_action);
        assertNotNull(snackbarAction);
        assertFalse(snackbarAction.hasOnClickListeners());
        assertEquals(View.GONE, snackbarAction.getVisibility());
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        View snackbarLayout = snackbar.getView();
        TextView snackbarText = snackbarLayout.findViewById(R.id.snackbar_text);

        onView(withId(R.id.editTextEmailAddress)).perform(typeText("testshouldfail2@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText(""));
        onView(withId(R.id.buttonLogin)).perform(click());
        assertEquals("Password required to login", snackbarTextView.getText().toString());

        Button snackbarAction = snackbarLayout.findViewById(R.id.snackbar_action);
        assertNotNull(snackbarAction);
        assertFalse(snackbarAction.hasOnClickListeners());
        assertEquals(View.GONE, snackbarAction.getVisibility());
    }

    @Test
    public void checkIfPasswordIsValid() {
        View snackbarLayout = snackbar.getView();
        TextView snackbarText = snackbarLayout.findViewById(R.id.snackbar_text);

        onView(withId(R.id.editTextEmailAddress)).perform(typeText("testshouldpass@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("@password"));
        onView(withId(R.id.buttonLogin)).perform(click());
        assertEquals("Login successful!", snackbarTextView.getText().toString());

        Button snackbarAction = snackbarLayout.findViewById(R.id.snackbar_action);
        assertNotNull(snackbarAction);
        assertFalse(snackbarAction.hasOnClickListeners());
        assertEquals(View.GONE, snackbarAction.getVisibility());
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        View snackbarLayout = snackbar.getView();
        TextView snackbarText = snackbarLayout.findViewById(R.id.snackbar_text);

        onView(withId(R.id.editTextEmailAddress)).perform(typeText("testshouldfail2@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("@password"));
        onView(withId(R.id.buttonLogin)).perform(click());
        assertEquals("Incorrect password", snackbarTextView.getText().toString());

        Button snackbarAction = snackbarLayout.findViewById(R.id.snackbar_action);
        assertNotNull(snackbarAction);
        assertFalse(snackbarAction.hasOnClickListeners());
        assertEquals(View.GONE, snackbarAction.getVisibility());
    }
}
 */
