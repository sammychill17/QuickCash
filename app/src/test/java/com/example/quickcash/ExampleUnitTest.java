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
    public void checkIfEmailIsEmpty(){
        assertTrue(validator.isEmptyEmail(""));
        assertFalse(validator.isEmptyEmail("email@email.com"));
    }

    @Test
    public void checkIfNameIsEmpty(){
        assertTrue(validator.isEmptyName(""));
        assertFalse(validator.isEmptyName("John Doe"));
    }

    @Test
    public void checkIfPasswordIsEmpty(){
        assertTrue(validator.isEmptyPassword(""));
        assertFalse(validator.isEmptyPassword("password123"));
    }

    @Test
    public void checkIfRoleIsEmpty(){
        assertTrue(validator.isEmptyRole(""));
        assertFalse(validator.isEmptyRole("Employee"));
    }
    @Test
    public void checkIfAnyEmpty(){
        assertTrue(validator.isAnyFieldEmpty("","","",""));
        assertTrue(validator.isAnyFieldEmpty("email@email.com","","password","Employee"));
        assertTrue(validator.isAnyFieldEmpty("","name","password","Employee"));
        assertTrue(validator.isAnyFieldEmpty("email@email.com","name","","Employee"));
        assertTrue(validator.isAnyFieldEmpty("email@email.com","name","password",""));
        assertFalse(validator.isAnyFieldEmpty("email@email.com","name","password","Employee"));
    }
}