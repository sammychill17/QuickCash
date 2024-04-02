package com.example.quickcash.UIAutomatorTests;


import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.Matchers.describedAs;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static java.lang.Thread.sleep;

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

import androidx.appcompat.app.AppCompatActivity;
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

import com.example.quickcash.Objects.Filters.IFilter;
import com.example.quickcash.Objects.Filters.JobTypeFilter;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class PermissionHandler {

    private UiDevice device;

    public PermissionHandler() {
        this.device = UiDevice.getInstance(getInstrumentation());
    }

    /*
      this method clicks on "While using the app" button of the location permissions system prompt.
      and throws UiObjectNotFoundException if the permissions dialog is not found
     */
    public void allowPermissionsIfNeeded() throws UiObjectNotFoundException {
        UiObject allowPermissionsButton = device.findObject(new UiSelector().text("While using the app"));
        if (allowPermissionsButton.exists()) {
            allowPermissionsButton.click();
            return;
        }

        UiObject allowPermissionsButtonKasuga = device.findObject(new UiSelector().text("Allow only while using the app"));
        if (allowPermissionsButtonKasuga.exists()) {
            allowPermissionsButtonKasuga.click();
        }
    }

}
