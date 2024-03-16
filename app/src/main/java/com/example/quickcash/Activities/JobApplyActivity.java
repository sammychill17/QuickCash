package com.example.quickcash.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.FirebaseStuff.LocationTable;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobApplicants;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjobpageBinding;
import com.example.quickcash.ui.employerJobApplicants.JobApplicantsDirections;
import com.example.quickcash.ui.employerJobApplicants.JobApplicantsFragment;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageFragment;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JobApplyActivity extends AppCompatActivity {

    private JobApplicants jobApplicants;

    private double currentLatitude;
    private double currentLongitude;

    DecimalFormat df = new DecimalFormat("#,###.00");

    Context c = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);

        Intent intent = getIntent();

        Job j = (Job) intent.getSerializableExtra("job");

        /*
        * I have taken the liberty of using JobDBHelper to retrieve the associated Job Applicants List for you.
        * This method I have called should initialise it for you to be the associated one.
        * If it is not working as intended, please let me know.
        *
        * I also recommend taking a look at the EmployerApplicantPage fragment.
        * It has some code that I used to update Jobs in the database to choose the applicant.
        *
        * I think some similar logic (modified slightly) could be used here as well.
         */
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

        // assertion here to prevent NullException
        assert j != null;
        String salaryStr = jSalary.getText()+" $"+df.format(j.getSalary());
        String dateStr = jDate.getText()+" "+j.getDate().toString();
        /*
        * We are going to need to figure out a new solution to getting the Employer Email, as I was unable to do so while making US2.
        * Perhaps we need to refactor the currently Posted jobs?
        * I was unsure why they have "employer" listed instead of "employerEmail". I could find no reason for this,
        * and I suspect it is why I was encountering errors when trying to do j.getEmployer() returning an empty string.
         */

//        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("session_login", MODE_PRIVATE);
//        String employeeEmail = sharedPrefs.getString("email", "");
//        String employerStr = jEmployer.getText()+" "+employeeEmail;

        jTitle.setText(j.getTitle());
        jJobType.setText("Job Type: "+j.getJobType().toString());
        jSalary.setText(salaryStr);
        jDate.setText(dateStr);
//        jEmployer.setText(employerStr);
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

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            applyJob();
            }
        });

    }
    public void applyJob() {
        /*
        Retrieves the employer email from shared preferences
         */
        SharedPreferences sharedPrefs = getSharedPreferences("session_login", MODE_PRIVATE);
        String employeeEmail = sharedPrefs.getString("email", "");

        Intent intent = getIntent();
        Job j = (Job) intent.getSerializableExtra("job");

        // Get the job key and title
        String jobKey = j.getKey();
        String jobTitle = j.getTitle();

        DatabaseReference applicationsRef = FirebaseDatabase.getInstance().getReference("Job Applicants");
        // Ensure the job key is set only once
        applicationsRef.child(jobKey).child("key").setValue(jobKey);

        /*
        Store the applicant's email under the Applicants node below the job key
        and avoid duplicates
        */
        DatabaseReference applicantsNodeRef = applicationsRef.child(jobKey).child("Applicants");

        applicantsNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean emailExists = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String email = dataSnapshot.getValue(String.class);
                    if (email != null && email.equals(employeeEmail)) {
                        emailExists = true;
                        break;
                    }
                }
                if (!emailExists) {
                    applicantsNodeRef.push().setValue(employeeEmail);
                    Toast.makeText(JobApplyActivity.this, "Applied for job: " + jobTitle, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(JobApplyActivity.this, "You have already applied for this job", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobApplyActivity.this, "Failed to apply for job: " + jobTitle, Toast.LENGTH_SHORT).show();
            }
        });
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
