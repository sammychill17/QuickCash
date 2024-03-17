package com.example.quickcash.FirebaseStuff;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.Objects.*;
import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobDBHelper extends AppCompatActivity {
    private Job job;
    FirebaseDatabase database;
    DatabaseReference applicantsReference;
    DatabaseReference jobsReference;
    public interface JobListCallback {
        void onJobsReceived(List<Job> jobs);
        void onError(DatabaseError error);
    }


    public JobDBHelper(Job job){
        this.job = job;
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DATABASE_URL));
        applicantsReference = database.getReference("Job Applicants");
        jobsReference = database.getReference("Posted Jobs");
    }
    public JobDBHelper(){
        database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
        applicantsReference = database.getReference("Job Applicants");
        jobsReference = database.getReference("Posted Jobs");
    }

    public interface JobObjectCallback {
        void onObjectReceived(Job object);
        void onError(DatabaseError error);
    }

    public interface ApplicantsObjectCallback {
        void onObjectReceived(JobApplicants object);
        void onError(DatabaseError error);
    }

    public void getJobByKey(String key, JobDBHelper.JobObjectCallback callback) {
        jobsReference.orderByChild("key").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Job result = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming you have a way to deserialize snapshot to a quickCashDbObject
                    // This often involves manually parsing the DataSnapshot based on your object structure
                    Job obj = snapshot.getValue(Job.class); // This might need adjustment
                    if (obj != null && key.equals(obj.getKey())) {
                        result = obj;
                        break;
                    }
                }
                callback.onObjectReceived(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    public void getApplicantsByKey(String key, JobDBHelper.ApplicantsObjectCallback callback) {
        applicantsReference.orderByChild("applicants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                JobApplicants result = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming you have a way to deserialize snapshot to a quickCashDbObject
                    // This often involves manually parsing the DataSnapshot based on your object structure
                    JobApplicants obj = snapshot.getValue(JobApplicants.class); // This might need adjustment
                    if (obj != null && key.equals(obj.getKey())) {
                        result = obj;
                        break;
                    }
                }
                callback.onObjectReceived(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    public void pushJobToDB(){
        jobsReference.push().setValue(this.job);
    }
    public void getAllJobsWithCoordinates(final JobDBHelper.JobListCallback callback) {
        jobsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Job> jobList = new ArrayList<>();
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (job != null && job.getLatitude() != 0 && job.getLongitude() != 0) {
                        jobList.add(job);
                    }
                }
                callback.onJobsReceived(jobList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }
    public void updateJobInDB(String key){
        //TODO: Make method to update specified job in database with variable job info
    }
}
