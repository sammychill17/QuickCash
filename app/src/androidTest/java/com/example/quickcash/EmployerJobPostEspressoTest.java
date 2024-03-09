package com.example.quickcash;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.quickcash.Activities.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.ui.employerJobList.EmployerJobListFragment;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmployerJobPostEspressoTest {

    @Before
    public void launchFragment() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> {
            EmployerJobPostFragment employerJobPostFragment = new EmployerJobPostFragment();
            activity.getSupportFragmentManager().beginTransaction().add(R.id.dashboardCardView, employerJobPostFragment).commit();
        });
    }

    @Test
    public void testSpinnerSelectionForJobPost() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.loseYourselfButton)).perform(click());
        onView(withId(R.id.jobTypeSpinner)).perform(ViewActions.click());
        onView(withText("Yard Work")).perform(ViewActions.click());
        onView(withId(R.id.jobPostButton)).perform(click());
    }

    @Test
    public void testTitleForJobPost(){
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.loseYourselfButton)).perform(click());
        onView(withId(R.id.jobTitleField)).perform(typeText("Hitman for hire"));
        onView(withId(R.id.jobPostButton)).perform(click());
    }

    @Test
    public void testDescriptionForJobPost(){
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.loseYourselfButton)).perform(click());
        onView(withId(R.id.jobDescField)).perform(typeText("Hiring hitmen for 500 dolluns"));
        onView(withId(R.id.jobPostButton)).perform(click());
    }

    @Test
    public void testSalaryRangeForJobPost(){
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.loseYourselfButton)).perform(click());
        onView(withId(R.id.jobSalaryField)).perform(typeText("500"));
        onView(withId(R.id.jobPostButton)).perform(click());
    }

    @Test
    public void testDurationRangeForJobPost(){
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.loseYourselfButton)).perform(click());
        onView(withId(R.id.jobDurationField)).perform(typeText("500.55"));
        onView(withId(R.id.jobPostButton)).perform(click());
    }

    @Test
    public void testDateForJobPost(){
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("parker@morrison.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonGotoLogin)).perform(click());
        onView(withId(R.id.loseYourselfButton)).perform(click());
        onView(withId(R.id.jobDateButton)).perform(click());
        onView(withId(R.id.jobPostButton)).perform(click());
    }
}