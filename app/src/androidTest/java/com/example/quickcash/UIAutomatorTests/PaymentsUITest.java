package com.example.quickcash.UIAutomatorTests;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;
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

public class PaymentsUITest {
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
    public void seeIfPaymentPageVisible() throws UiObjectNotFoundException, InterruptedException {
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
        Thread.sleep(4000);
        device.click(device.getDisplayWidth()/2, device.getDisplayHeight()-40);
        Thread.sleep(2000);
        device.findObject(new UiSelector().resourceIdMatches(".*meFragmentButton")).click();
        Thread.sleep(1000);
        device.findObject(new UiSelector().textContains("Pay")).click();
        Thread.sleep(1000);
        assertTrue(device.findObject(new UiSelector().textContains("Pay Employee")).exists());
    }

    @Test
    public void checkIfAmountEmpty() throws UiObjectNotFoundException, InterruptedException {
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
        Thread.sleep(4000);
        device.click(device.getDisplayWidth()/2, device.getDisplayHeight()-40);
        Thread.sleep(2000);
        device.findObject(new UiSelector().resourceIdMatches(".*meFragmentButton")).click();
        Thread.sleep(1000);
        device.findObject(new UiSelector().textContains("Pay")).click();
        Thread.sleep(1000);
        assertTrue(device.findObject(new UiSelector().textContains("Pay Employee")).exists());
        Thread.sleep(1000);
        device.findObject(new UiSelector().textContains("Pay using PayPal")).click();
        String expectedMessage = ApplicationProvider.getApplicationContext().getString(R.string.empty_amount);
        assertTrue(device.findObject(new UiSelector().textContains(expectedMessage)).exists());
    }

    @Test
    public void checkIfPaymentModeVisible() throws UiObjectNotFoundException, InterruptedException {
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
        Thread.sleep(4000);
        device.click(device.getDisplayWidth()/2, device.getDisplayHeight()-40);
        Thread.sleep(2000);
        device.findObject(new UiSelector().resourceIdMatches(".*meFragmentButton")).click();
        Thread.sleep(1000);
        device.findObject(new UiSelector().textContains("Pay")).click();
        Thread.sleep(1000);
        assertTrue(device.findObject(new UiSelector().textContains("Pay Employee")).exists());
        Thread.sleep(1000);
        UiObject amountBox = device.findObject(new UiSelector().resourceId("com.example.quickcash:id/paymentAmountEditText"));
        amountBox.setText("50");
        device.findObject(new UiSelector().textContains("Pay using PayPal")).click();
    }
}
