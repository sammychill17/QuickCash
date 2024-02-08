package com.example.quickcash;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

    public class ExampleUnitTest {

    @Test
    public void latitudeIsValid() {
        assertTrue(LocationUtil.isValidLatitude("34.0522"));
        assertTrue(LocationUtil.isValidLatitude("-34.0522"));
        assertFalse(LocationUtil.isValidLatitude("invalid"));
        assertFalse(LocationUtil.isValidLatitude("95"));
    }

    @Test
    public void longitudeIsValid() {
        assertTrue(LocationUtil.isValidLongitude("-118.2437"));
        assertTrue(LocationUtil.isValidLongitude("118.2437"));
        assertFalse(LocationUtil.isValidLongitude("invalid"));
        assertFalse(LocationUtil.isValidLongitude("-200"));
    }
}
