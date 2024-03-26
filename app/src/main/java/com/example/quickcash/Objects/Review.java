package com.example.quickcash.Objects;

/**
 * This data object class is designed to contain all the requisite information needed
 * for an Employee to be reviewed for a job.
 *
 * It has a ratingStars (which is an integer from 1-5)
 *
 * It has a String description which the Employer will provide.
 *
 * And it also has an associated Job Key from the Job it is associated with.
 */
public class Review {
    private int ratingStars;
    private String description;
    private String jobKey;

    public Review(int ratingStars, String description, String jobKey){
        this.ratingStars = ratingStars;
        this.description = description;
        this.jobKey = jobKey;
    }
    public Review(){
        this.ratingStars = 0;
        this.description = "";
        this.jobKey = "";
    }
    public int getRatingStars() {
        return ratingStars;
    }
    public String getJobKey() {
        return jobKey;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }
    public void setRatingStars(int ratingStars) {
        this.ratingStars = ratingStars;
    }


}