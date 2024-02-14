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
    }

    @Test
    public void latitudeIsInvalid(){
        assertFalse(LocationUtil.isValidLatitude("invalid"));
        assertFalse("Should have returned false when input is " +
                        "greater than maximum allowed value for latitude",
                        LocationUtil.isValidLatitude("90.1"));
        assertFalse("Should have returned false when input is " +
                "less than minimum allowed value for latitude",
                LocationUtil.isValidLatitude("-90.1"));
    }

    @Test
    public void longitudeIsValid() {
        assertTrue(LocationUtil.isValidLongitude("-118.2437"));
        assertTrue(LocationUtil.isValidLongitude("118.2437"));
    }

    @Test
    public void longitudeIsInvalid(){
        assertFalse(LocationUtil.isValidLongitude("invalid"));
        assertFalse(LocationUtil.isValidLongitude("-200"));
        assertFalse("Should have returned false when input is " +
                "less than minimum allowed value for longitude",
                LocationUtil.isValidLongitude("-180.1"));
        assertFalse("Should have returned false when input is " + "greater than" +
                "maximum allowed value for longitude",LocationUtil.isValidLongitude("180.1"));
    }
}
