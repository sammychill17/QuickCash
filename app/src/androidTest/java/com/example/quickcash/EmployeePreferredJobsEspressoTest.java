package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static org.hamcrest.Matchers.is;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.quickcash.ui.employeePreferredJobs.EmployeePreferredJobsFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmployeePreferredJobsEspressoTest {
    @Before
    public void setUp() {
        FragmentScenario<EmployeePreferredJobsFragment> scenario = FragmentScenario.launchInContainer(
                EmployeePreferredJobsFragment.class
        );

        scenario.onFragment(fragment -> {
            fragment.setTestEmail("oingoboingo@example.com");
        });
    }
    @Test
    public void testBackButton() {
        FragmentScenario<EmployeePreferredJobsFragment> scenario = FragmentScenario.launchInContainer(EmployeePreferredJobsFragment.class);
        onView(withId(R.id.backButtonFromPreferences)).check(matches(isDisplayed()));
        onView(withId(R.id.backButtonFromPreferences)).perform(click());

    }

    @Test
    public void testSavePreferencesButton() {
        FragmentScenario<EmployeePreferredJobsFragment> scenario = FragmentScenario.launchInContainer(EmployeePreferredJobsFragment.class);
        onView(withId(R.id.backButtonFromPreferences)).check(matches(isDisplayed()));
        onView(withTagValue(is("YARDWORK"))).perform(click());
        onView(withId(R.id.backButtonFromPreferences)).perform(click());
    }

    @Test
    public void testClickOnSpecificCheckBox() {
        FragmentScenario<EmployeePreferredJobsFragment> scenario = FragmentScenario.launchInContainer(EmployeePreferredJobsFragment.class);
        onView(withTagValue(is("YARDWORK"))).perform(click());
    }

    @Test
    public void testUncheckItemInCheckBox() {
        FragmentScenario<EmployeePreferredJobsFragment> scenario = FragmentScenario.launchInContainer(EmployeePreferredJobsFragment.class);
        onView(withTagValue(is("YARDWORK"))).perform(click()).perform(click());
        onView(withId(R.id.backButtonFromPreferences)).perform(click());
    }

}