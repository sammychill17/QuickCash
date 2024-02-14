package com.example.quickcash;

import android.location.Location;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocationTable {
    private DatabaseReference databaseReference;

    public LocationTable() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Locations");
    }

    public void addLocationToDatabase(com.example.quickcash.Location loc) {
        databaseReference.push().setValue(loc);
    }

    public void retrieveLocationFromDatabase(final OnLocationDataReceivedListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    Location loc = locationSnapshot.getValue(Location.class);
                    listener.onLocationDataReceived(loc);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface OnLocationDataReceivedListener {
        void onLocationDataReceived(Location location);
    }
}
