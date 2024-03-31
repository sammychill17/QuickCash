package com.example.quickcash.ui.employerJobPost;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickcash.BusinessLogic.PushNotifHandler;
import com.example.quickcash.FirebaseStuff.LocationTable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quickcash.FirebaseStuff.LocationTable;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobApplicants;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.Objects.UserLocation;
import com.example.quickcash.databinding.FragmentEmployerjobpostBinding;
import com.example.quickcash.ui.employerHome.EmployerHomeFragment;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostViewModel;
import com.example.quickcash.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
* This fragment is designed to allow an Employer to post a job.
 */
public class EmployerJobPostFragment extends Fragment {

    private FragmentEmployerjobpostBinding binding;
    private Calendar selectedDate;
    private double currentLatitude;
    private double currentLongitude;
    double salaryNum;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPostViewModel EmployerJobPostViewModel =
                new ViewModelProvider(this).get(EmployerJobPostViewModel.class);

        binding = FragmentEmployerjobpostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Spinner spinner = binding.jobTypeSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.job_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        binding.jobPostButton.setOnClickListener(v -> postJob());

        Button backButton = binding.backButton;
        backButton.setOnClickListener(view -> getParentFragmentManager().popBackStack());
        selectedDate = Calendar.getInstance();
        Button dateButton = binding.jobDateButton;
        dateButton.setOnClickListener(view -> showDatePickerDialog());
        return root;
    }


    /*
    Date picker dialog which brings up an instance of calendar from which
    the employer can choose the date from which the job will start
     */
    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getContext(), dateSetListener, year, month, day).show();
    }

    /*
    sets the employer's preferred date
     */
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, monthOfYear);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
    };

    /*
    postJob method essentially takes in the logic for employer email,
    job duration, title, description, salary, jobtype as well as location
    coordinates- longitude and latitude, allows the employer to post the job
    as soon as 2 fields are filled up (2 from desc, title, duration and/or salary).
    Posting the job also saves the posted job's details into firebase
     */
    public void postJob() {
        /*
        Retrieves the employer email from shared preferences
         */
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("session_login", MODE_PRIVATE);
        String employerEmail = sharedPrefs.getString("email", "");

        /*
        Retrieves the current location coordinates from the database
         */
        LocationTable locationTable = new LocationTable();
        locationTable.retrieveLocationFromDatabase(employerEmail, location -> {
            /*
             Checks whether location was successfully retrieved
             */
            if (location != null) {
                /*
                Stores the current latitude and longitude of the employer
                 */
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

                /*
                 Get job details from the employer/user's input
                 */
                String title = ((EditText) getView().findViewById(R.id.jobTitleField)).getText().toString();
                String description = ((EditText) getView().findViewById(R.id.jobDescField)).getText().toString();
                String selectedJobType = ((Spinner) getView().findViewById(R.id.jobTypeSpinner)).getSelectedItem().toString().toUpperCase(Locale.ROOT).replace("/", "_");
                JobTypes jobType = JobTypes.valueOf(selectedJobType);
                String salaryString = ((EditText) getView().findViewById(R.id.jobSalaryField)).getText().toString();
                /*
                converting string salary into double
                puts forward an exception if salary format is anything different
                from a number.
                 */
                try {
                    if (!salaryString.isEmpty()) {
                        salaryNum = Double.parseDouble(salaryString);
                    }
                } catch (NumberFormatException ex) {
                    Toast.makeText(getContext(), "Salary format not applicable", Toast.LENGTH_SHORT).show();
                    return;
                }

                String durationString = (((EditText) getView().findViewById(R.id.jobDurationField)).getText().toString());
                long durationLong = 0;
                /*
                converts durationString to durationLong
                 */
                if (!durationString.isEmpty()) {
                    durationLong = Long.parseLong(durationString);
                }
                /*
                duration for hours
                 */
                Duration durationHours = Duration.ofHours(durationLong);

                /*
                Checks if at least two fields are filled (no pun intended) at the very least
                 */
                int filledFieldsCount = 0;
                if (!title.isEmpty()) filledFieldsCount++;
                if (!description.isEmpty()) filledFieldsCount++;
                if (!salaryString.isEmpty()) filledFieldsCount++;
                if (!durationHours.isZero() && !durationHours.isNegative()) filledFieldsCount++;


                /*
                 Creates a Job object if 2 fields are filled, alongside storing
                 a unique job key and the longitude, latitude of the employer
                 */
                if (filledFieldsCount >= 2) {
                    Job newJob = new Job(title, description, jobType, salaryNum, Duration.ofHours(durationLong), employerEmail, new Date(), currentLatitude, currentLongitude);

                    /*
                     Saving the job to Firebase
                     */
                    DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("Posted Jobs").push();
                    String jobKey = jobRef.getKey();
                    newJob.setKey(jobKey);
                    jobRef.setValue(newJob, (databaseError, databaseReference) -> {
                        /*
                         Checks if the job was successfully posted
                         */
                        if (databaseError == null) {
                            Toast.makeText(getContext(), getResources().getString(R.string.JOB_POSTING_SUCCESSFUL), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.JOB_POSTING_FAILED), Toast.LENGTH_LONG).show();
                        }
                    });
                    DatabaseReference applicantsRef = FirebaseDatabase.getInstance().getReference("Job Applicants").push();
                    JobApplicants jobApp = new JobApplicants(jobKey);
                    applicantsRef.child(jobKey).setValue(jobApp, (databaseError, databaseReference) -> {

                        if (databaseError == null) {
                            /*
                             Navigates back to the previous screen- employer home/dashboard page
                             */
                            NavController navController = NavHostFragment.findNavController(EmployerJobPostFragment.this);
                            navController.popBackStack();
                        }
                        else {
                            Toast.makeText(getContext(), getResources().getString(R.string.DATABASE_REGISTRATION_ERROR) + databaseError.toString(), Toast.LENGTH_LONG).show();
                        }

                    });

                    PushNotifHandler pushNotifHandler = new PushNotifHandler(requireContext().getApplicationContext(),
                            getResources().getString(R.string.PUSH_NOTIFICATION_ENDPOINT),
                            getResources().getString(R.string.FIREBASE_SERVER_KEY));
                    pushNotifHandler.sendNotification(jobKey, currentLatitude, currentLongitude);

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.LACK_OF_FILLED_FIELDS), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Location couldn't be retrieved.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


