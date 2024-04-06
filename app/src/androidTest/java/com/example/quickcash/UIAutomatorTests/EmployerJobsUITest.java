package com.example.quickcash.UIAutomatorTests;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.quickcash.R;

import org.junit.Before;
import org.junit.Test;

public class EmployerJobsUITest {
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.quickcash";
    private UiDevice device;
    private PermissionHandler permissionHandler;

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
        permissionHandler = new PermissionHandler();
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
        Thread.sleep(3000);
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        Thread.sleep(3000);
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject myJobsButton = device.findObject(new UiSelector().textContains("My Jobs"));
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
        Thread.sleep(3000);
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject myJobsButton = device.findObject(new UiSelector().textContains("My Jobs"));
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
        Thread.sleep(3000);
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject myJobsButton = device.findObject(new UiSelector().textContains("My Jobs"));
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
        Thread.sleep(3000);
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject myJobsButton = device.findObject(new UiSelector().textContains("My Jobs"));
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
}
