package com.example.quickcash.FirebaseStuff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployerDBHelper extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference jobsReference;
    private ArrayList<String> keys = new ArrayList<String>();

    public EmployerDBHelper(){
        database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
        jobsReference = database.getReference("Posted Jobs");
    }

    public interface JobObjectCallback {
        void onObjectReceived(ArrayList<String> keys);
        void onError(DatabaseError error);
    }

    public void getJobsByEmployer(String email, EmployerDBHelper.JobObjectCallback callback) {
        jobsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> jobs = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming you have a way to deserialize snapshot to a quickCashDbObject
                    // This often involves manually parsing the DataSnapshot based on your object structure
                    Job obj = snapshot.getValue(Job.class); // This might need adjustment
                    if (obj != null && email.equals(obj.getEmployer())) {
                        jobs.add(obj.getKey());
                    }
                }
                callback.onObjectReceived(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }
}
