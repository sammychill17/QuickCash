package com.example.quickcash;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

import android.widget.Button;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Test;

public class EmployeeHistoryUiTest {
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.quickcash";
    private UiDevice device;



    @Test
    public void checkEmployeeUpcomingJobsExist() throws UiObjectNotFoundException, InterruptedException{
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
        UiObject jobHistoryButton = device.findObject(new UiSelector().textContains("Job History"));
        assertTrue(jobHistoryButton.exists());
        jobHistoryButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject upcomingJobs = device.findObject(new UiSelector().textContains("Upcoming Jobs"));
        assertTrue(upcomingJobs.exists());

    }
    @Test
    public void checkEmployeePreviousJobsExist() throws UiObjectNotFoundException, InterruptedException{
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
        UiObject jobHistoryButton = device.findObject(new UiSelector().textContains("Job History"));
        assertTrue(jobHistoryButton.exists());
        jobHistoryButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject previousJobs = device.findObject(new UiSelector().textContains("Previous Jobs"));
        assertTrue(previousJobs.exists());


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
