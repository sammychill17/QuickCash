package com.example.quickcash.FirebaseStuff;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeDBHelper extends AppCompatActivity {

    FirebaseDatabase database;
    static PreferredJobs oldPref;
    PreferredJobs newPref;
    DatabaseScrounger scrounger = new DatabaseScrounger("JobApplicants");

    public EmployeeDBHelper(){
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DATABASE_URL));
    }

    interface EmailExistenceCallback {
        static void onResult(PreferredJobs preferredJobs){
            oldPref = preferredJobs;
        };
        static void onError(DatabaseError error){

        };
    }
    public void updatePreferencesInDB(PreferredJobs newPref){
        String email = newPref.getEmail();
        this.newPref = newPref;
        scrounger.getObjectByEmail(email, new DatabaseScrounger.ObjectCallback() {
            @Override
            public void onObjectReceived(QuickCashDBObject object) {
                // If object is not null, email exists
                EmailExistenceCallback.onResult((PreferredJobs) object);
            }

            @Override
            public void onError(DatabaseError error) {
                // Handle error, possibly assuming email does not exist or indicating an error
                EmailExistenceCallback.onError(error);
            }
        });

    }
}
