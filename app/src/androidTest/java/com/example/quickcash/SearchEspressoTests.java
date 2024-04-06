package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.doesNotHaveFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
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
public class SearchEspressoTests {
    public ActivityScenario<SearchActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(SearchActivity.class);
        scenario.onActivity(activity -> {
//            onView(withId(R.id.searchBar)).perform(clearText());
        });
    }

    @Test
    public void checkOuiJobTitle() {
        String textToType = "Teach me Design Patterns";
        onView(withId(R.id.searchBar)).perform(typeText(textToType));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.jobItem_title)).check(matches(withText(textToType)));
    }

    @Test
    public void checkHitmanJobTitle() {
        String textToType = "Walk my animals";
        onView(withId(R.id.searchBar)).perform(typeText(textToType));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.jobItem_title)).check(matches(withText(textToType)));
    }

    @Test
    public void checkOuiJobTitleComplement() {
        String textToType = "Teach me Design Patterns";
        onView(withId(R.id.searchBar)).perform(typeText(textToType));
        Espresso.closeSoftKeyboard();
        onView(withText("Walk my animals")).check(doesNotExist());
    }

    @Test
    public void checkHitmanJobTitleComplement() {
        String textToType = "Walk my animals";
        onView(withId(R.id.searchBar)).perform(typeText(textToType));
        Espresso.closeSoftKeyboard();
        onView(withText("Teach me Design Patterns")).check(doesNotExist());
    }
}
