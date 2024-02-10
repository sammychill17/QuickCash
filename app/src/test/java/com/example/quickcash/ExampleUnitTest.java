package com.example.quickcash;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    CredentialValidator validator;
    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    @Test
    public void checkIfEmailIsValid(){
        assertTrue(validator.isValidEmailAddress("abc123@email.com"));
        assertTrue(validator.isValidEmailAddress("aa123456@dal.ca"));
    }
    @Test
    public void checkIfEmailIsInvalid() {
        assertFalse(validator.isValidEmailAddress("abc"));
        assertFalse(validator.isValidEmailAddress("ab.c"));
        assertFalse (validator.isValidEmailAddress ("abc@12345")) ;
        assertFalse(validator.isValidEmailAddress("88mh1819@"));
    }
    @Test
    public void checkIfNameIsValid() {
        assertTrue(validator.isValidName("Alice"));
        assertTrue(validator.isValidName("Jon Smith"));
    }
    @Test
    public void checkIfNameIsInvalid() {
        assertFalse(validator.isValidName("b"));
        assertFalse(validator.isValidName("Jon47"));
    }
    @Test
    public void checkIfPasswordIsValid() {
        assertTrue(validator.isValidPassword("hEll0123"));
        assertTrue(validator.isValidPassword("PASSword4"));
    }
    @Test
    public void checkIfPasswordIsInvalid() {
        assertFalse(validator.isValidPassword("hEll0"));
        assertFalse(validator.isValidPassword("hellohello"));
        assertFalse(validator.isValidPassword("123456789"));
    }

}