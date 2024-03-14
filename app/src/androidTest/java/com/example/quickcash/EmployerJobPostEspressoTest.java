package com.example.quickcash;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import android.widget.DatePicker;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmployerJobPostEspressoTest {

    @Before
    public void setUp() {
        FragmentScenario<EmployerJobPostFragment> scenario = FragmentScenario.launchInContainer(
                EmployerJobPostFragment.class
        );
        scenario.onFragment(fragment -> {
            NavController mockNavController = mock(NavController.class);
            Navigation.setViewNavController(fragment.requireView(), mockNavController);
        });
    }

    @Test
    public void testSpinnerSelectionForJobPost() {
        onView(withId(R.id.jobTypeSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Yardwork"))).perform(click());
        onView(withId(R.id.jobTypeSpinner)).check(matches(withSpinnerText(containsString("Yardwork"))));
    }

    @Test
    public void testingTextFieldsForJobPost() {
        onView(withId(R.id.jobTitleField)).perform(typeText("Hiring"));
        onView(withId(R.id.jobTitleField)).check(matches(withText(containsString("Hiring"))));
        onView(withId(R.id.jobDescField)).perform(typeText("Hiring for money"));
        onView(withId(R.id.jobDescField)).check(matches(withText(containsString("Hiring for money"))));
        onView(withId(R.id.jobSalaryField)).perform(typeText("500"));
        onView(withId(R.id.jobSalaryField)).check(matches(withText(containsString("500"))));
    }

    @Test
    public void testForJobPostFail() {
        onView(withId(R.id.jobTitleField)).perform(typeText("No"), closeSoftKeyboard());
        onView(withId(R.id.jobPostButton)).perform(click());
    }


    @Test
    public void testForJobPostSuccess() {
        onView(withId(R.id.jobTitleField)).perform(typeText("Hi"), closeSoftKeyboard());
        onView(withId(R.id.jobDescField)).perform(typeText("Hi, TA Vatsal, You are the best TA i've ever had in my entire academic journey!"), closeSoftKeyboard());
        onView(withId(R.id.jobPostButton)).perform(click());

    }

    @Test
    public void datePickerTest() {
        onView(withId(R.id.jobDateButton)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(12, 12, 12));
        onView(withId(android.R.id.button1)).perform(click());
    }
}
