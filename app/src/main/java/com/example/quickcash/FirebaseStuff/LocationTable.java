package com.example.quickcash.FirebaseStuff;


import com.example.quickcash.Objects.UserLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.quickcash.Objects.User;

/*
CRUD -
Create - addLocationToDatabase
Retrieve - retrieveLocationFromDatabase
Update - updateLocationInDatabase
Delete - deleteLocationFromDatabase
 */


/*
   Class LocationTable encapsulates CRUD operations for user locations in Firebase.
 */

public class LocationTable {
    /*
    References to the User Locations node in Firebase Realtime Database.
    */
    private DatabaseReference databaseReference;

    /*
    This constructor initializes the DatabaseReference to the "User Locations" path in Firebase.
    */
    public LocationTable() {
        databaseReference = FirebaseDatabase.getInstance().getReference("User Locations");
    }

    /*
    creates or updates a user's location in the database.
    C in CRUD
    parameter- (userLocation), The UserLocation object to be saved.
    */
    public void addLocationToDatabase(UserLocation userLocation) {
        String sanitizedEmail = sanitizeEmail(userLocation.getEmail());
        databaseReference.child(sanitizedEmail).setValue(userLocation);
    }

    /*
    works on retrieving user locations from the database and passes them to the provided listener.
    R in CRUD.
    parameter- (listener), The listener that handles the retrieved UserLocation objects.
    NOTE: Currently unused, possibly useful in next iteration.
    */
    public void retrieveLocationFromDatabase(String email, final OnLocationDataReceivedListener listener) {
        String sanitizedEmail = sanitizeEmail(email);
        databaseReference.child(sanitizedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserLocation loc = dataSnapshot.getValue(UserLocation.class);
                    listener.onLocationDataReceived(loc);
                } else {
                    listener.onLocationDataReceived(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /*
    updates a user's location in the database. (Update in CRUD)
    parameter-(userLocation), The UserLocation object to be updated.
    */
    public void updateLocationInDatabase(UserLocation userLocation) {
        String sanitizedEmail = sanitizeEmail(userLocation.getEmail());
        databaseReference.child(sanitizedEmail).setValue(userLocation);
    }

    /*
    Deletes a user's location from the database.
    D in CRUD.
    @param userEmail The email address of the user whose location is to be deleted.
    */
    public void deleteLocationFromDatabase(String userEmail) {
        String sanitizedEmail = sanitizeEmail(userEmail);
        databaseReference.child(sanitizedEmail).removeValue();
    }

    /*
    Sanitizes (or splices/simplifies) the email address to be used,
    as a Firebase key by removing the domain part and replacing periods.
    parameter- (email), the email address to be sanitized.
    @return A sanitized string suitable for use as a Firebase key.
    */
    private String sanitizeEmail(String email) {
        /*
         Extracting the username part of the email before the "@"
         */
        String key = email.substring(0, email.indexOf('@'));
        /*
         Replacing periods as Firebase keys can't contain '.' character
         */
        return key.replace(".", ",");
    }

    /*
    interface that is to be implemented by listeners for receiving UserLocation data.
    */
    public interface OnLocationDataReceivedListener {
        void onLocationDataReceived(UserLocation location);
    }
}