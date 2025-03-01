package com.example.quickcash.FirebaseStuff;

import androidx.annotation.NonNull;
import com.example.quickcash.Objects.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*
* A massive majority of this class was writen by ChatGPT based on an original version I had made which was not working.
*
* https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
*
* This class is designed to retrieve a list of jobs given an employer email.
 */

public class EmployerDBHelper {

    FirebaseDatabase database;
    DatabaseReference jobsReference;

    public EmployerDBHelper() {
        database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
        jobsReference = database.getReference("Posted Jobs");
    }

    public interface JobObjectCallback {
        void onJobsReceived(List<Job> jobs);
        void onError(DatabaseError error);
    }

    public void getJobsByEmployer(String email, JobObjectCallback callback) {
        jobsReference.orderByChild("employerEmail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Job> jobs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Job job = snapshot.getValue(Job.class);
                    if (job != null) {
                        jobs.add(job);
                    }
                }
                callback.onJobsReceived(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    public interface SingleJobObjectCallback {
        void onJobReceived(Job job);
        void onError(DatabaseError error);
    }

    public void getJobByEmployer(String email, SingleJobObjectCallback callback) {
        jobsReference.orderByChild("employerEmail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Job job = new Job();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    job = snapshot.getValue(Job.class);
                    if (job != null) {
                        break;
                    }
                }
                callback.onJobReceived(job);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }
}
