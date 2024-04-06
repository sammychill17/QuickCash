package com.example.quickcash.UIAutomatorTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
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

public class FilterActivityUITest {
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
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().resourceIdMatches(".*makeMoneyButton"));
        assertTrue(makeMoneyButton.exists());
        makeMoneyButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search"));
        assertTrue(searchBar.exists());
        UiObject filterButton = device.findObject(new UiSelector().className(ImageButton.class));
        assertTrue(filterButton.exists());
        filterButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject jobTypeFilter = device.findObject(new UiSelector().className(Spinner.class));
        jobTypeFilter.click();
        UiObject politicianItem = device.findObject(new UiSelector().text("TUTORING"));
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
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().resourceIdMatches(".*makeMoneyButton"));
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
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().resourceIdMatches(".*makeMoneyButton"));
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
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().resourceIdMatches(".*makeMoneyButton"));
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
        device.findObject(By.desc("12 April 2024")).click();
        sleep(5000);
        device.findObject(By.clazz(Button.class)).click();
        sleep(5000);
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
        permissionHandler.allowPermissionsIfNeeded();
        Thread.sleep(1000); // Give me some time to click manually if it cant click automatically
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
        UiObject makeMoneyButton = device.findObject(new UiSelector().resourceIdMatches(".*makeMoneyButton"));
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
}
