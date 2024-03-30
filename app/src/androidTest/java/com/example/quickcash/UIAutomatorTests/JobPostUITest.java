package com.example.quickcash.UIAutomatorTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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

public class JobPostUITest {
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
        permissionHandler.allowPermissionsIfNeeded();
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
        permissionHandler.allowPermissionsIfNeeded();
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
}
