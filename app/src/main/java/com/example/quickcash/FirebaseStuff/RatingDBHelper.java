package com.example.quickcash.FirebaseStuff;

import androidx.annotation.NonNull;

import com.example.quickcash.Objects.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingDBHelper {

    FirebaseDatabase database;
    DatabaseReference ratingsReference;

    public RatingDBHelper(){
        database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
    }

    public static class onRatingReceivedCallback {
        void onRatingReceived(Rating rating){}
        void onError(DatabaseError error){}
    }

    public void getRatingsByEmployee(String email, RatingDBHelper.onRatingReceivedCallback callback) {
        ratingsReference = database.getReference("Ratings/"+email);
        ratingsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Rating rating = snapshot.getValue(Rating.class);
                    if (rating != null && rating.getEmployeeEmail().equals(email)) {
                        if(callback!=null) {
                            callback.onRatingReceived(rating);
                        }
                        break;
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
