package com.example.quickcash;

import android.location.Location;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocationTable {
    /*
    Making a reference to the Firebase Realtime Database location
    */
    private DatabaseReference databaseReference;

    public LocationTable() {
        /*
        Initializing the databaseReference with the path to the 'Locations' node/branch in the Firebase database
        */
        databaseReference = FirebaseDatabase.getInstance().getReference("Locations");
    }


    public void addLocationToDatabase(com.example.quickcash.Location loc) {
        /*
        Push a new location to the 'Locations' node in Firebase.
        The push() method creates a auto-generated ID for each new entry
        instead of auto-generated ID, we need to use our primary key(email) here.
        */
        databaseReference.child("user_email").setValue(loc);
    }

    public void retrieveLocationFromDatabase(final OnLocationDataReceivedListener listener) {
        /*
        Retrieves the location data from Firebase and notifies the event listener once the data is fetched.
        */
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                For loop to go through all snapshots in the 'Locations' node, converting them to Location objects,
                and pass them to the event listener.
                */
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

    /*
    an interface that is used by components that wish to receive location data accordingly.
    */
    public interface OnLocationDataReceivedListener {
        void onLocationDataReceived(Location location);
    }
}
