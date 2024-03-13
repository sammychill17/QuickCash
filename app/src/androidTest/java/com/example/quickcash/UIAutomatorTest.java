
package com.example.quickcash;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.JobTypeFilter;
import com.example.quickcash.Objects.JobTypes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
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
        //Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        //Context context = ApplicationProvider.getApplicationContext();
        //SharedPreferences sp = context.getSharedPreferences(getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
        sp.edit().clear().commit();
        final Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackage);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(appIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkIfWelcomePageIsVisible() {
//        UiObject netIDBox = device.findObject(new UiSelector().textContains("Net ID"));
//        assertTrue(netIDBox.exists());
//        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
//        assertTrue(emailIDBox.exists());
//        UiObject roleSpinner = device.findObject(new UiSelector().textContains("Select your role"));
//        assertTrue(roleSpinner.exists());
//        UiObject registerButton = device.findObject(new UiSelector().text("REGISTER"));
//        assertTrue(registerButton.exists());

        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        assertTrue(loginButton.exists());
    }

    @Test
    public void checkIfLoginPageIsVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
//        device.wait(LAUNCH_TIMEOUT);
        UiObject anotherLoginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        assertTrue(anotherLoginButton.exists());
        UiObject loginLabel = device.findObject(new UiSelector().textContains("Log in!"));
        assertTrue(loginLabel.exists());
    }

    @Test
    public void checkIfDashboardPageIsVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
//        device.wait(LAUNCH_TIMEOUT);
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
//        device.wait(LAUNCH_TIMEOUT);
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
    }

    @Test
    public void checkIfEmployeeDashboardIsVisible() throws UiObjectNotFoundException {
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
        UiObject employeeLabel = device.findObject(new UiSelector().textContains("employee"));
        assertTrue(employeeLabel.exists());
        UiObject employerLabel = device.findObject(new UiSelector().textContains("employer"));
        assertFalse(employerLabel.exists());
    }

    @Test
    public void checkIfEmployerDashboardIsVisible() throws UiObjectNotFoundException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("Loki360@gmail.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("Thor123456");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        allowPermissionsIfNeeded();
        UiObject employeeLabel = device.findObject(new UiSelector().textContains("employee"));
        assertFalse(employeeLabel.exists());
        UiObject employerLabel = device.findObject(new UiSelector().textContains("employer"));
        assertTrue(employerLabel.exists());
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
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
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
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(filterButton.exists());
        UiObject durationFilter = device.findObject(new UiSelector().className(Button.class));
        durationFilter.setText("6");
        sleep(5000);
        UiObject searchResultDuration = device.findObject(new UiSelector().textContains("I want you to become my new MP!").className(TextView.class));
        assertTrue(searchResultDuration.exists());
    }
    @Test
    public void jobFilterDurationFail() throws UiObjectNotFoundException, InterruptedException {
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
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(filterButton.exists());
        UiObject durationFilter = device.findObject(new UiSelector().className(Button.class));
        durationFilter.setText("24000");
        sleep(5000);
        UiObject searchResultDuration = device.findObject(new UiSelector().textContains("Make me Dinner!").className(TextView.class));
        assertFalse(searchResultDuration.exists());
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
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(filterButton.exists());
        UiObject jobTypeFilter = device.findObject(new UiSelector().className(Button.class));
        jobTypeFilter.setText("POLITICIAN");
        sleep(5000);
        UiObject searchResultJobType = device.findObject(new UiSelector().textContains("I want you to become my new MP!").className(TextView.class));
        assertTrue(searchResultJobType.exists());
    }
    @Test
    public void jobFilterJobTypeFail() throws UiObjectNotFoundException, InterruptedException {
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
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(filterButton.exists());
        UiObject jobTypeFilter = device.findObject(new UiSelector().className(Button.class));
        jobTypeFilter.setText("24000");
        sleep(5000);
        UiObject searchResultJobType = device.findObject(new UiSelector().textContains("I want to see a magic show!").className(TextView.class));
        assertFalse(searchResultJobType.exists());
    }
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
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(filterButton.exists());
        UiObject salaryFilter = device.findObject(new UiSelector().className(Button.class));
        salaryFilter.setText("1000000");
        sleep(5000);
        UiObject searchResultSalary = device.findObject(new UiSelector().textContains("\"Rake\" my \"Leafs\"").className(TextView.class));
        assertTrue(searchResultSalary.exists());
    }
    @Test
    public void jobFilterSalaryFail() throws UiObjectNotFoundException, InterruptedException {
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
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().textContains("Make Money"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(filterButton.exists());
        UiObject salaryFilter = device.findObject(new UiSelector().className(Button.class));
        salaryFilter.setText("1000000");
        sleep(5000);
        UiObject searchResultSalary = device.findObject(new UiSelector().textContains("Make me Dinner!").className(TextView.class));
        assertFalse(searchResultSalary.exists());
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

