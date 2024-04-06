package com.example.quickcash.FirebaseStuff;

import androidx.annotation.NonNull;

import com.example.quickcash.BusinessLogic.SanitizeEmail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.Objects.JobTypes;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

public class FirebasePreferredJobsHelper {

    private final DatabaseReference databaseRef;

    public interface PreferredJobsCallback {
        /*
        This method will be called when the preferred jobs have been successfully fetched from Firebase.
         */
        void onPreferredJobsRetrieved(List<String> preferredJobs);
    }
    public FirebasePreferredJobsHelper() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("Preferred Jobs");
    }
    /*
     Saves or updates preferred jobs for a user in Firebase.
    */
    public void savePreferredJobs(PreferredJobs preferredJobs) {
        String sanitizedEmail = SanitizeEmail.sanitizeEmail(preferredJobs.getEmail());
        DatabaseReference userRef = databaseRef.child(sanitizedEmail);
        userRef.child("email").setValue(preferredJobs.getEmail());
        userRef.child("Preferences").setValue(preferredJobs.getPreferredJobs());
    }

    /*
     retrieves a user's preferred jobs from Firebase.
     parameters:
     userEmail - The email of the user whose preferred jobs are to be retrieved.
     callback- A callback to be invoked with the retrieved preferred jobs data.
     */
    public void retrievePreferredJobs(String userEmail, final PreferredJobsCallback callback) {
        String sanitizedEmail = SanitizeEmail.sanitizeEmail(userEmail);
        DatabaseReference userRef = databaseRef.child(sanitizedEmail).child("Preferences");
        /*
        attaches a listener to read the data at the user's preferences reference
         */
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                deserializes the snapshot into a list of preferred job strings.
                GenericTypeIndicator is a workaround that preserves the generic type information for Firebase to
                deserialize the data correctly.
                 */
                List<String> preferredJobs = dataSnapshot.getValue(new GenericTypeIndicator<List<String>>() {});
                /*
                If data exists, invoke the callback with the fetched preferred jobs
                 */
                if (preferredJobs != null) {
                    callback.onPreferredJobsRetrieved(preferredJobs);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //This error is swallowed
            }
        });
    }

}