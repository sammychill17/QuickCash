package com.example.quickcash.UIAutomatorTests;

import static org.junit.Assert.assertFalse;
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

public class RatingsActivityUITest {
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
    public void seeIfMyEmployeesButtonExists() throws UiObjectNotFoundException, InterruptedException {
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
        Thread.sleep(1000);
        device.findObject(new UiSelector().textContains("Me")).click();
        device.click(device.getDisplayWidth()/2, device.getDisplayHeight()-40);
        Thread.sleep(2000);
        assertTrue(device.findObject(new UiSelector().textContains("My Employees")).exists());
        assertFalse(device.findObject(new UiSelector().textContains("My Ratings")).exists());
    }

    @Test
    public void seeIfMyRatingsButtonExists() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("employee@website.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000);
        device.findObject(new UiSelector().textContains("Me")).click();
        device.click(device.getDisplayWidth()/2, device.getDisplayHeight()-40);
        Thread.sleep(2000);
        assertTrue(device.findObject(new UiSelector().textContains("My Ratings")).exists());
        assertFalse(device.findObject(new UiSelector().textContains("My Employees")).exists());
    }

    @Test
    public void seeIfMyEmployeesPageVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("employer@website.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(4000);
        device.click(device.getDisplayWidth()/2, device.getDisplayHeight()-40);
        Thread.sleep(2000);
        device.findObject(new UiSelector().textContains("My Employees")).click();
        assertTrue(device.findObject(new UiSelector().textContains("Rate")).exists());
    }

    @Test
    public void seeIfRatePageVisible() throws UiObjectNotFoundException, InterruptedException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("employer@website.com");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("password");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(4000);
        device.click(device.getDisplayWidth()/2, device.getDisplayHeight()-40);
        Thread.sleep(2000);
        device.findObject(new UiSelector().textContains("My Employees")).click();
        Thread.sleep(1000);
        device.findObject(new UiSelector().textContains("Rate")).click();
        Thread.sleep(1000);
        assertTrue(device.findObject(new UiSelector().textContains("Rate this fool!")).exists());
    }
}
