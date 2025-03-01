package com.example.quickcash.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobApplicants;
import com.example.quickcash.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/*
* This method is designed to take in a Job and populate a layout with information about it.
* It also is designed to allow an Employee to apply to a given Job.
* 
* Currently, the Employer Email does not appear, but otherwise this page is fully functional.
 */
public class JobApplyActivity extends AppCompatActivity {

    private JobApplicants jobApplicants;

    DecimalFormat df = new DecimalFormat("#,###.00");

    Context c = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);

        Intent intent = getIntent();

        Job j = (Job) intent.getSerializableExtra("job");

        assert j != null;
        getApplicantsList(j);


        TextView jTitle = findViewById(R.id.jobPageTitle);
        TextView jJobType = findViewById(R.id.jobPageType);
        TextView jAddress = findViewById(R.id.jobPageAddress);
        TextView jSalary = findViewById(R.id.jobPageSalary);
        TextView jDate = findViewById(R.id.jobPageDate);
        TextView jEmployer = findViewById(R.id.jobPageEmployerName);
        TextView jApplicant = findViewById(R.id.jobPageApplicant);
        TextView jDesc = findViewById(R.id.jobPageDesc);

        assert j != null;
        String salaryStr = jSalary.getText()+" $"+df.format(j.getSalary());
        String dateStr = jDate.getText()+" "+j.getDate().toString();

        String employerEmail = j.getEmployerEmail();
        String employerStr = jEmployer.getText()+" "+employerEmail;

        jTitle.setText(j.getTitle());
        jJobType.setText("Job Type: "+j.getJobType().toString());
        jSalary.setText(salaryStr);
        jDate.setText(dateStr);
        jEmployer.setText(employerStr);
        if (j.getAssigneeEmail().equals("")) {
            jApplicant.setText("No applicant chosen yet");
        } else {
            String applicantStr = jApplicant.getText()+" "+j.getAssigneeEmail();
            jApplicant.setText(applicantStr);
        }
        jDesc.setText(j.getDescription());
        String address = "";
        Geocoder geocoder;
        List<Address> addresses = null;
        Context c = this.getApplicationContext();
        geocoder = new Geocoder(c, Locale.getDefault());
        double lat = j.getLatitude();
        double lon = j.getLongitude();
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
            } else {
                address = "Error! Cannot find Address";
            }
        }
        catch(IOException e) {
            //idk why an error keeps getting thrown, but it is.
            address = "Error! Cannot find Address";
        }
        jAddress.setText(address);

        Button backButton = findViewById(R.id.jobPageBackBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button applyButton = findViewById(R.id.applyButton);
        applyButton.setEnabled(j.isAssigned());
        applyButton.setEnabled(true);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyJob();
                applyButton.setEnabled(false);
            }
        });

    }
    public void applyJob() {
        /*
        Retrieves the employee email from shared preferences
         */
        SharedPreferences sharedPrefs = getSharedPreferences("session_login", MODE_PRIVATE);
        String employeeEmail = sharedPrefs.getString("email", "");

        Intent intent = getIntent();
        Job j = (Job) intent.getSerializableExtra("job");

        // Get the job key and title
        String jobKey = j.getKey();
        String jobTitle = j.getTitle();

        DatabaseReference applicationsRef = FirebaseDatabase.getInstance().getReference("Job Applicants");

        applicationsRef.child(jobKey).child("key").setValue(jobKey);
        /*
        Store the applicant's email under the Applicants node below the job key
        and avoid duplicates
        */

        if(!jobApplicants.ifContainsApplicant(employeeEmail)) {
            jobApplicants.addApplicant(employeeEmail);
            applicationsRef.child(jobKey).setValue(jobApplicants, (databaseError, databaseReference) -> {

                if(databaseError == null) {
                    TextView statusLabel = findViewById(R.id.jobApplyStatusLabel);
                    statusLabel.setText("Applied for job: " + jobTitle);
                }
                else {
                    TextView statusLabel = findViewById(R.id.jobApplyStatusLabel);
                    statusLabel.setText("Failed to apply for job: " + jobTitle);
                }
            });
        }
        else{
            TextView statusLabel = findViewById(R.id.jobApplyStatusLabel);
            statusLabel.setText("You have already applied for this job");
        }

    }

    private void getApplicantsList(Job j){
        JobDBHelper helper = new JobDBHelper();
        helper.getApplicantsByKey(j.getKey(), new JobDBHelper.ApplicantsObjectCallback() {

            @Override
            public void onObjectReceived(JobApplicants object) {
                if(object!=null){
                    jobApplicants = object;
                }
            }

            @Override
            public void onError(DatabaseError error) {
                Toast.makeText(c, getResources().getString(R.string.DATABASE_REGISTRATION_ERROR) + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
