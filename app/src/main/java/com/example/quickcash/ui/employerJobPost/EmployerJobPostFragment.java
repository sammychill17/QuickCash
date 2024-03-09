package com.example.quickcash.ui.employerJobPost;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.databinding.FragmentEmployerjobpostBinding;
import com.example.quickcash.ui.employerHome.EmployerHomeFragment;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostViewModel;
import com.example.quickcash.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class EmployerJobPostFragment extends Fragment {

    private FragmentEmployerjobpostBinding binding;

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
        binding.jobPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJob();
            }
        });

        Button backButton = binding.backButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        return root;
    }


    public void postJob() {
        String title = ((EditText) getView().findViewById(R.id.jobTitleField)).getText().toString();
        String description = ((EditText) getView().findViewById(R.id.jobDescField)).getText().toString();
        String selectedJobType = ((Spinner) getView().findViewById(R.id.jobTypeSpinner)).getSelectedItem().toString().toUpperCase(Locale.ROOT).replace("/","_");
        JobTypes jobType = JobTypes.valueOf(selectedJobType);
        String salaryString = ((EditText) getView().findViewById(R.id.jobSalaryField)).getText().toString();
        double salaryNum = 0;
        if (!salaryString.isEmpty()) {
            salaryNum = Double.parseDouble(salaryString);
        }

        String dateString = ((Button) getView().findViewById(R.id.jobDateButton)).getText().toString();

        String durationString = (((EditText) getView().findViewById(R.id.jobDurationField)).getText().toString());
        long durationLong = 0;
        if (!durationString.isEmpty()) {
            durationLong = Long.parseLong(durationString);
        }
        Duration durationHours = Duration.ofHours(durationLong);

        // Checks if two fields are filled (no pun intended) at the very least
        int filledFieldsCount = 0;
        if (!title.isEmpty()) filledFieldsCount++;
        if (!description.isEmpty()) filledFieldsCount++;
        if (!selectedJobType.isEmpty()) filledFieldsCount++;
        if (!salaryString.isEmpty()) filledFieldsCount++;
        if (!dateString.isEmpty()) filledFieldsCount++;
        if (!durationHours.isZero() && !durationHours.isNegative()) filledFieldsCount++;

        if (filledFieldsCount >= 2) {
            // Creates a new Job object with the provided details accordingly
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences("session_login", Context.MODE_PRIVATE);
            String employerEmail = sharedPrefs.getString("email", "");
            //TODO: Make sure that date picker is getting a date. Also make date picker.
            Job newJob = new Job(title, description, jobType, salaryNum, durationHours, employerEmail, new Date());
            newJob.setTitle(title);
            newJob.setDescription(description);
            newJob.setJobType(JobTypes.valueOf(selectedJobType));
            newJob.setDuration(durationLong);
            newJob.setEmployerEmail(employerEmail);

            //newJob.setDate();

            //newJob.setLocation();

            DatabaseReference newJobRef = FirebaseDatabase.getInstance().getReference("Posted Jobs").push();
            newJobRef.setValue(newJob);

            newJobRef.setValue(newJob, (databaseError, databaseReference) -> {
                if (databaseError == null) {
                    Toast.makeText(getContext(), "Job has been successfully posted", Toast.LENGTH_LONG).show();

                    NavController navController = NavHostFragment.findNavController(EmployerJobPostFragment.this);
                    navController.popBackStack();
                } else {
                    Toast.makeText(getContext(), "Failed to post the job: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

