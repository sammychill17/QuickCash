package com.example.quickcash.Objects;

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
