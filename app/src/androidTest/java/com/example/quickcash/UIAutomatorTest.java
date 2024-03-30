package com.example.quickcash;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.describedAs;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static java.lang.Thread.sleep;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.graphics.Rect;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.quickcash.BusinessLogic.PushNotifHandler;
import com.example.quickcash.FirebaseStuff.LocationTable;
import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.JobTypeFilter;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobApplicants;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest  {

    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.quickcash";
    private UiDevice device;

    @Before
    public void setup() {
        /*
        https://stackoverflow.com/questions/18686655/how-to-get-context-in-uiautomator-test-case
         */
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        sp.edit().clear().commit();
        final Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackage);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(appIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkIfWelcomePageIsVisible() {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        assertTrue(loginButton.exists());
    }

    @Test
    public void checkIfRegistrationButtonIsVisible(){
        UiObject registrationButton = device.findObject(new UiSelector().textContains("REGISTER"));
        assertTrue(registrationButton.exists());
    }

    @Test
    public void checkIfLoginPageIsVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject anotherLoginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        assertTrue(anotherLoginButton.exists());
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
    }

    @Test
    public void checkIfDashboardPageIsVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
    }

    @Test
    public void checkIfSearchPageIsVisibleEmployee() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
    }
    @Test
    public void checkIfSearchPageIsNotVisibleEmployer() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("Thor123456");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("Loki360@gmail.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertFalse(makeMoneyButton.exists());
    }

    @Test
    public void checkIfSearchBarExists() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
    }
    @Test
    public void jobSearchTestWithTitle() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        searchBar.setText("Make me Dinner!");
        sleep(5000);
        UiObject searchResult = device.findObject(new UiSelector().textContains("Make me Dinner!").className(TextView.class));
        assertTrue(searchResult.exists());
    }
    @Test
    public void jobSearchTestWithTitle2() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        searchBar.setText("Make me Dinner!");
        sleep(5000);
        UiObject searchResult = device.findObject(new UiSelector().textContains("Hitman for hire").className(TextView.class));
        assertFalse(searchResult.exists());
        UiObject searchResultCorrect = device.findObject(new UiSelector().textContains("Make me Dinner!").className(TextView.class));
        assertTrue(searchResultCorrect.exists());
    }
    @Test
    public void jobFilterJobType() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(ImageButton.class));
        assertTrue(filterButton.exists());
        filterButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject jobTypeFilter = device.findObject(new UiSelector().className(Spinner.class));
        jobTypeFilter.click();
        UiObject politicianItem = device.findObject(new UiSelector().text("POLITICIAN"));
        politicianItem.click();
        UiObject applyFilterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(applyFilterButton.exists());
    }
    @Test
    public void jobFilterSalary() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(ImageButton.class));
        assertTrue(filterButton.exists());
        filterButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject salaryFilter = device.findObject(new UiSelector().className(EditText.class));
        salaryFilter.setText("250000");
        UiObject applyFilterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(applyFilterButton.exists());
    }
    @Test
    public void jobFilterDuration() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(ImageButton.class));
        assertTrue(filterButton.exists());
        filterButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject durationFilter = device.findObject(new UiSelector().className(EditText.class));
        durationFilter.setText("6");
        UiObject applyFilterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(applyFilterButton.exists());
    }

    @Test
    public void jobFilterDate() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(ImageButton.class));
        assertTrue(filterButton.exists());
        filterButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject datePicker = device.findObject(new UiSelector().className(Button.class));
        assertTrue(datePicker.exists());
        datePicker.click();
        device.findObject(By.desc("12 March 2024")).click();
        sleep(2000);
        device.findObject(By.clazz(Button.class)).click();
        sleep(2000);
        UiObject applyFilterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(applyFilterButton.exists());
    }
    @Test
    public void jobFilterDistance() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(ImageButton.class));
        assertTrue(filterButton.exists());
        filterButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject distanceBar = device.findObject(new UiSelector().className(SeekBar.class));
        assertTrue(distanceBar.exists());
        Rect bounds = distanceBar.getBounds();
        int middleX = bounds.centerX();
        int centerY = bounds.centerY();
        device.swipe(bounds.left, centerY, middleX, centerY, 5);
        UiObject applyFilterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(applyFilterButton.exists());
    }

    @Test
    public void checkIfLoseYourselfButtonIsVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("parker@morrison.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject postJobs = device.findObject(new UiSelector().className(ImageButton.class));
        assertTrue(postJobs.exists());
    }
    @Test
    public void checkIfJobPostIsVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("parker@morrison.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject postJobs = device.findObject(new UiSelector().className(ImageButton.class));
        postJobs.click();
        UiObject loseYourselfButton = device.findObject(new UiSelector().textContains("LOSE YOURSELF (Post Job)"));
        UiObject title = device.findObject(new UiSelector().textContains("Title"));
        UiObject description = device.findObject(new UiSelector().textContains("Description"));
        UiObject typeSpinner = device.findObject(new UiSelector().className(Spinner.class));
        UiObject salaryRange = device.findObject(new UiSelector().textContains("Salary range"));
        UiObject durationField = device.findObject(new UiSelector().textContains("Duration (hours)"));
        UiObject selectDateButton = device.findObject(new UiSelector().textContains("Select date"));
        UiObject backButton = device.findObject(new UiSelector().textContains("Go Back to the previous page"));
        assertTrue("No loseYourselfButton", loseYourselfButton.exists());
        assertTrue("No title", title.exists());
        assertTrue("No description", description.exists());
        assertTrue("No typeSpinner", typeSpinner.exists());
        assertTrue("No salaryRange", salaryRange.exists());
        assertTrue("No durationField", durationField.exists());
        assertTrue("No selectDateButton", selectDateButton.exists());
        assertTrue("No backButton", backButton.exists());
    }

    @Test
    public void checkIfMyJobsIsVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("parker@morrison.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject myJobsButton = device.findObject(new UiSelector().textContains("My Jobs (In the middle of my jobs)"));
        myJobsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject anJob = device.findObject(new UiSelector().textContains("I want to see a magic show!"));
        assertTrue(anJob.exists());
    }

    @Test
    public void checkJobDetailsPage() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("parker@morrison.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject myJobsButton = device.findObject(new UiSelector().textContains("My Jobs (In the middle of my jobs)"));
        myJobsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject jobDetailsButton = device.findObject(new UiSelector().textContains("Details"));

        jobDetailsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject applicantsButton = device.findObject(new UiSelector().textContains("View Applicants"));
        assertTrue(applicantsButton.exists());
    }

    @Test
    public void checkJobApplicants() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("parker@morrison.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject myJobsButton = device.findObject(new UiSelector().textContains("My Jobs (In the middle of my jobs)"));
        myJobsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject jobDetailsButton = device.findObject(new UiSelector().textContains("Details"));
        jobDetailsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject applicantsButton = device.findObject(new UiSelector().textContains("View Applicants"));
        applicantsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject applicantDetailsButton = device.findObject(new UiSelector().textContains("Details"));
        assertTrue(applicantDetailsButton.exists());
    }

    @Test
    public void checkJobApplicantsChoose() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("parker@morrison.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject myJobsButton = device.findObject(new UiSelector().textContains("My Jobs (In the middle of my jobs)"));
        myJobsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject jobDetailsButton = device.findObject(new UiSelector().textContains("Details"));
        jobDetailsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject applicantsButton = device.findObject(new UiSelector().textContains("View Applicants"));
        applicantsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject applicantDetailsButton = device.findObject(new UiSelector().textContains("Details"));
        applicantDetailsButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject chooseApplicantsButton = device.findObject(new UiSelector().textContains("ACCEPT APPLICANT"));
        assertTrue(chooseApplicantsButton.exists());
    }
    @Test
    public void navigateToMapAndCheckForMarker() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("sam@chai.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically

        UiObject makeMoneyButton = device.findObject(new UiSelector().className(ImageButton.class));
        assertTrue(makeMoneyButton.exists());

        UiObject makeMoneyOption = device.findObject(new UiSelector().text("Make Money"));
        makeMoneyOption.clickAndWaitForNewWindow();

        UiObject mapButton = device.findObject(new UiSelector().text("See on map"));
        mapButton.clickAndWaitForNewWindow();

        UiObject marker=device.findObject(new UiSelector().descriptionContains("Your Location"));
        marker.click();
        assertTrue(marker.exists());
    }

    /*
    currently checks if a push notification exists on the user's end
     */
    @Test
    public void checkIfPushNotifIsDisplayed() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("peterparker22@outlook.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("iamspiderman");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        LocationTable locationTable = new LocationTable();
        locationTable.retrieveLocationFromDatabase("peterparker22@outlook.com", location -> {
            if (location != null) {
                double currentLatitude = location.getLatitude();
                double currentLongitude = location.getLongitude();

                Job newJob = new Job("Test Job", "desc", JobTypes.YARDWORK,
                        500, Duration.ofHours(16), "parker@morrison.com",
                        new Date(), currentLatitude, currentLongitude);

                DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("Posted Jobs").push();
                String jobKey = jobRef.getKey();
                newJob.setKey(jobKey);
                jobRef.setValue(newJob, (databaseError, databaseReference) -> {

                });
                DatabaseReference applicantsRef = FirebaseDatabase.getInstance().getReference("Job Applicants").push();
                JobApplicants jobApp = new JobApplicants(jobKey);
                assert jobKey != null;
                applicantsRef.child(jobKey).setValue(jobApp, (databaseError, databaseReference) -> {

                });

                Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

                PushNotifHandler pushNotifHandler = new PushNotifHandler(context);
                pushNotifHandler.sendNotification(jobKey);

                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                assertEquals("Job Available Near You!", manager.getActiveNotifications()[0].getNotification().extras.getString(Notification.EXTRA_TEXT));
            }
        });
    }

    /*
    clicks the "While using the app" button of the location permissions system prompt
     */
    private void allowPermissionsIfNeeded() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermissions = device.findObject(new UiSelector().text("While using the app"));
        if (allowPermissions.exists()) {
            allowPermissions.click();
        }
    }
}
