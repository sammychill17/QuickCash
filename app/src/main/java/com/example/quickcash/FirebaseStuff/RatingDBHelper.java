package com.example.quickcash.FirebaseStuff;

import androidx.annotation.NonNull;

import com.example.quickcash.BusinessLogic.SanitizeEmail;
import com.example.quickcash.Objects.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This class is designed to assist in the Reading of Ratings objects from the Database.
 *
 * It has one method, and all it does is retrieve the corresponding Ratings object associated with an employee.
 */
public class RatingDBHelper {

    FirebaseDatabase database;
    DatabaseReference ratingsReference;

    public RatingDBHelper(){
        database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
    }

    public static class onRatingReceivedCallback {
        public void onRatingReceived(Rating rating){}

        public void onError(DatabaseError error){}
    }

    public void getRatingsByEmployee(String email, RatingDBHelper.onRatingReceivedCallback callback) {
        String sanitisedEmail = SanitizeEmail.sanitizeEmail(email);
        ratingsReference = database.getReference("Ratings/"+sanitisedEmail);
        ratingsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Rating rating = dataSnapshot.getValue(Rating.class);
                if (rating != null && rating.getEmployeeEmail().equals(email)) {
                    if(callback!=null) {
                        callback.onRatingReceived(rating);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if(callback!=null) {
                    callback.onError(databaseError);
                }
            }
        });
    }

}
