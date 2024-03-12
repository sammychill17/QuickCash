package com.example.quickcash;

import static android.app.PendingIntent.getActivity;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmployerJobPostEspressoTest {

    @Before
    public void launchFragment() {
        ActivityScenario<MainActivity> activityScenario = launch(MainActivity.class);
        activityScenario.onActivity(activity -> {
            getInstrumentation().getUiAutomation()
                    .grantRuntimePermission(activity.getPackageName(), "android.permission.ACCESS_COARSE_LOCATION");
            getInstrumentation().getUiAutomation()
                    .grantRuntimePermission(activity.getPackageName(), "android.permission.ACCESS_FINE_LOCATION");

        });
    }

    @Test
    public void testSpinnerSelectionForJobPost() {
        FragmentScenario<EmployerJobPostFragment> scenario = FragmentScenario.launchInContainer(EmployerJobPostFragment.class);
        onView(withId(R.id.jobTypeSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Yardwork"))).perform(click());
        onView(withId(R.id.jobTypeSpinner)).check(matches(withSpinnerText(containsString("Yardwork"))));
    }

    @Test
    public void testingTextFieldsForJobPost() {
        FragmentScenario<EmployerJobPostFragment> scenario = FragmentScenario.launchInContainer(EmployerJobPostFragment.class);
        onView(withId(R.id.jobTitleField)).perform(typeText("Hiring"));
        onView(withId(R.id.jobTitleField)).check(matches(withText(containsString("Hiring"))));
        onView(withId(R.id.jobDescField)).perform(typeText("Hiring for money"));
        onView(withId(R.id.jobDescField)).check(matches(withText(containsString("Hiring for money"))));
        onView(withId(R.id.jobSalaryField)).perform(typeText("500"));
        onView(withId(R.id.jobSalaryField)).check(matches(withText(containsString("500"))));
    }

    @Test
    public void testForJobPostFail() {
        FragmentScenario.launchInContainer(EmployerJobPostFragment.class);
        onView(withId(R.id.jobTitleField)).perform(typeText("No"), closeSoftKeyboard());
        onView(withId(R.id.jobPostButton)).perform(click());
        String toastText = getInstrumentation().getTargetContext().getResources().getString(R.string.LACK_OF_FILLED_FIELDS);
        onView(withText(toastText))
                .check(matches(withText(containsString("Fill up 2 or more fields to post a job"))));
    }


    @Test
    public void testForJobPostSuccess() {
        FragmentScenario.launchInContainer(EmployerJobPostFragment.class);
        onView(withId(R.id.jobTitleField)).perform(typeText("No"), closeSoftKeyboard());
        onView(withId(R.id.jobDescField)).perform(typeText("No, I have a job for you"), closeSoftKeyboard());
        onView(withId(R.id.jobPostButton)).perform(click());
        onView(withText(R.string.JOB_POSTING_SUCCESSFUL)).check(matches(isDisplayed()));
    }

}
