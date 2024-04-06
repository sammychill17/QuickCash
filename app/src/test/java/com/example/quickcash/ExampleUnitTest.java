package com.example.quickcash;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.quickcash.BusinessLogic.CredentialValidator;
import com.example.quickcash.BusinessLogic.LocationUtil;
import com.example.quickcash.BusinessLogic.SanitizeEmail;
import com.example.quickcash.Objects.JobApplicants;
import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.Objects.Rating;
import com.example.quickcash.Objects.Review;

import java.util.Arrays;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Rating rating = new Rating();
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
    public void addSingleReview() {
        Review review = new Review(5, "Great service!");
        rating.addReview(review);
        assertEquals("After adding one review, the average rating should be equal to that review's rating.", 5.0, rating.getAverageStarRating(), 0.0);
        assertEquals("The number of reviews should be 1 after adding one review.", 1, rating.getNumReview());
        assertFalse("The review list should not be empty after adding a review.", rating.getReviewList().isEmpty());
        assertEquals("The added review should be the same as the one in the review list.", review, rating.getReviewList().get(0));
    }

    @Test
    public void addMultipleReviews() {
        Review firstReview = new Review(5, "Great service!");
        Review secondReview = new Review(3, "Good, but could be better.");
        rating.addReview(firstReview);
        rating.addReview(secondReview);
        double expectedAverage = (5.0 + 3.0) / 2;
        assertEquals("The average rating should be correct after adding two reviews.", expectedAverage, rating.getAverageStarRating(), 0.0);
        assertEquals("The number of reviews should be 2 after adding two reviews.", 2, rating.getNumReview());
        assertEquals("The review list size should be 2 after adding two reviews.", 2, rating.getReviewList().size());
    }
}



