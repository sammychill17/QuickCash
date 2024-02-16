package com.example.quickcash;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.quickcash";
    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
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
        passwordBox.setText("xy881245");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("abc123@dal.ca");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
//        device.wait(LAUNCH_TIMEOUT);
        UiObject dashboardPage = device.findObject(new UiSelector().textContains("Dashboard"));
        assertTrue(dashboardPage.exists());
    }

    @Test
    public void checkIfLongLatIsVisible() throws UiObjectNotFoundException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("8097@dal.ca");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("thing");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject longitudeLabel = device.findObject(new UiSelector().textContains("Longitude"));
        assertTrue(longitudeLabel.exists());
        UiObject latitudeLabel = device.findObject(new UiSelector().textContains("Latitude"));
        assertTrue(latitudeLabel.exists());
    }

    @Test
    public void checkIfEmployeeDashboardIsVisible() throws UiObjectNotFoundException {
        UiObject loginButton = device.findObject(new UiSelector().textContains("LOG IN"));
        loginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
        assertTrue(emailIDBox.exists());
        emailIDBox.setText("emailJohnson@website.ru");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("orange");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
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
        emailIDBox.setText("johnappleseed@website.ru");
        UiObject passwordBox = device.findObject(new UiSelector().textContains("Password"));
        assertTrue(passwordBox.exists());
        passwordBox.setText("orange");
        UiObject anotherLoginButton = device.findObject(new UiSelector().className(Button.class));
        assertTrue(anotherLoginButton.exists());
        anotherLoginButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject employeeLabel = device.findObject(new UiSelector().textContains("employee"));
        assertFalse(employeeLabel.exists());
        UiObject employerLabel = device.findObject(new UiSelector().textContains("employer"));
        assertTrue(employerLabel.exists());
    }

//    @Test
//    public void checkIfMoved2WelcomePage() throws UiObjectNotFoundException {
//        UiObject netIDBox = device.findObject(new UiSelector().textContains("Net ID"));
//        netIDBox.setText("xy881245");
//        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Email"));
//        emailIDBox.setText("abc123@dal.ca");
//        UiObject roleSpinner = device.findObject(new UiSelector().textContains("Select your role"));
//        roleSpinner.click();
//        List<UiObject2> roles = device.findObjects(By.res("android:id/text1"));
//        roles.get(1).click();
//        UiObject registerButton = device.findObject(new UiSelector().text("REGISTER"));
//        registerButton.clickAndWaitForNewWindow();
//        UiObject welcomeLabel = device.findObject(new UiSelector().textContains("Welcome"));
//        assertTrue(welcomeLabel.exists());
//    }
}
