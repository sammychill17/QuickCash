package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.doesNotHaveFocus;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertFalse;


import com.example.quickcash.Activities.RegistrationActivity;
import com.example.quickcash.Activities.SearchActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EmployeeJobApplyEspressoTest {
    public ActivityScenario<SearchActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(SearchActivity.class);
    }

    @Test
    public void checkBackButton() throws InterruptedException {
        onView(withId(R.id.filterButton)).perform(click());
        onView(withId(R.id.applyFilterButton)).perform(click());
        Espresso.pressBack();
        Thread.sleep(2000);
        onView(withId(R.id.searchResultsRecycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);
        onView(withId(R.id.jobPageBackBtn)).perform(click());
        onView(withId(R.id.searchBar)).check(matches(isDisplayed()));
    }

}
