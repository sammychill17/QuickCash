package com.example.quickcash.Objects;

import android.widget.Toast;

import com.example.quickcash.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Rating {

    private double averageStarRating;

    private List<Review> reviewList;
    private String employeeEmail;
    private int numReview;
    private RatingCallback callback;

    public static class RatingCallback {

        public void onComplete(){}
        public void onError(DatabaseError error){}
    }

    public Rating(){
        averageStarRating = 0;
        reviewList = new ArrayList<>();
        employeeEmail = "";
        numReview = 1;
    }

    public Rating(double totalStarRating, List<Review> reviewList, String employeeEmail, int numReview){
        this.averageStarRating = totalStarRating;
        this.reviewList = reviewList;
        this.employeeEmail = employeeEmail;
        this.numReview = numReview;
    }

    public RatingCallback getCallback() {
        return callback;
    }

    public void setCallback(RatingCallback callback) {
        this.callback = callback;
    }

    public void clearCallback(){
        this.callback = null;
    }

    public double getAverageStarRating() {
        return averageStarRating;
    }

    public int getNumReview() {
        return numReview;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public void setNumReview(int numReview) {
        this.numReview = numReview;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public void setAverageStarRating(double averageStarRating) {
        this.averageStarRating = averageStarRating;
    }

    public void addReview(Review review){
        averageStarRating = (averageStarRating*numReview) + review.getRatingStars();
        numReview++;
        averageStarRating = averageStarRating/numReview;
        reviewList.add(review);
    }

    public void pushRatingToDB(RatingCallback callback){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Ratings");
        db.child(employeeEmail).setValue(this, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                if(callback!=null) {
                    callback.onComplete();
                }
            }
            else {
                if(callback!=null) {
                    callback.onError(databaseError);
                }
            }
        });
    }

    public void pushRatingToDB(){
        pushRatingToDB(null);
    }

}
