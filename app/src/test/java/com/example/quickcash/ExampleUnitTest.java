package com.example.quickcash;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.quickcash.BusinessLogic.CredentialValidator;
import com.example.quickcash.BusinessLogic.LocationUtil;
import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.Objects.JobTypes;

import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    CredentialValidator validator;
    PreferredJobs preferredJobs;
    @Before
    public void setup() {
        validator = new CredentialValidator();
        preferredJobs = new PreferredJobs("macgrubber@yolo.com");
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

    @Test
    public void checkJobAddsJobCorrectly() {
        preferredJobs.checkJob(JobTypes.HITMAN);
        assertTrue("Job should be added to the preferred jobs accordingly.",
                Arrays.asList(preferredJobs.getPreferredJobs()).contains(JobTypes.HITMAN));
    }

    @Test
    public void uncheckJobRemovesJobCorrectly() {
        preferredJobs.checkJob(JobTypes.HITMAN);
        preferredJobs.checkJob(JobTypes.MAGICIAN);
        preferredJobs.uncheckJob(JobTypes.HITMAN);
        assertFalse("Job should be removed from the preferred jobs",
                Arrays.asList(preferredJobs.getPreferredJobs()).contains(JobTypes.HITMAN));
    }

    @Test
    public void doesPreferredReturnsTrueForPreferredJob() {
        preferredJobs.checkJob(JobTypes.MOVING);
        assertTrue("Should return true for checked preferred job",
                preferredJobs.doesPreferred(JobTypes.MOVING));
    }

    @Test
    public void doesPreferredReturnsFalseForNotPreferredJob() {
        assertFalse("Should return false for job which is not checked for preference",
                preferredJobs.doesPreferred(JobTypes.YARDWORK));
    }
}