package com.example.quickcash.ui.employerJobPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjobpageBinding;
import com.example.quickcash.ui.employerJobApplicants.JobApplicantsDirections;
import com.example.quickcash.ui.employerJobApplicants.JobApplicantsFragment;
import com.google.firebase.database.DatabaseError;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/*
* This class is designed to list information about a posted job as well as list the applicants so the Employer can choose.
 */
public class EmployerJobPageFragment extends Fragment{

    private FragmentEmployerjobpageBinding binding;
    DecimalFormat df = new DecimalFormat("#,###.00");

    private String address = "";
    private Job job;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPageViewModel EmployerJobPageViewModel =
                new ViewModelProvider(this).get(EmployerJobPageViewModel.class);

        binding = FragmentEmployerjobpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle b = getArguments();
        assert b != null;
        job = (Job) b.getSerializable("job");

        updateTextView();

        Button backButton = binding.jobPageBackBtn;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        JobApplicantsFragment fragment = new JobApplicantsFragment();
        Button applicantsButton = binding.applicantsBtn;
        EmployerJobPageFragment current = this;
        applicantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("job", job);
                fragment.setArguments(b);
                NavController navController = NavHostFragment.findNavController(current);
                navController.navigate(new JobApplicantsDirections(job));
            }
        });

        return root;
    }

    private void updateTextView() {
        TextView jTitle = binding.jobPageTitle;
        TextView jJobType = binding.jobPageType;
        TextView jAddress = binding.jobPageAddress;
        TextView jSalary = binding.jobPageSalary;
        TextView jDate = binding.jobPageDate;
        TextView jEmployer = binding.jobPageEmployerName;
        TextView jApplicant = binding.jobPageApplicant;
        TextView jDesc = binding.jobPageDesc;

        // assertion here to prevent NullException
        assert job != null;
        String salaryStr = "Min. salary: $"+df.format(job.getSalary());
        String dateStr = "Start date: "+job.getDate().toString();
        String employerEmail = job.getEmployerEmail();
        if (employerEmail.length() == 0) {
            SharedPreferences sp = requireContext().getSharedPreferences(getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
            employerEmail = sp.getString("email", "Error - cannot get email");
        }
        String employerStr = "Employer: " + employerEmail;

        jTitle.setText(job.getTitle());
        jJobType.setText("Job Type: "+job.getJobType().toString());
        jSalary.setText(salaryStr);
        jDate.setText(dateStr);
        jEmployer.setText(employerStr);
        if (job.getAssigneeEmail().equals("")) {
            jApplicant.setText("No applicant chosen yet");
        } else {
            String applicantStr = "Assignee: "+job.getAssigneeEmail();
            jApplicant.setText(applicantStr);
        }
        jDesc.setText(job.getDescription());
        Geocoder geocoder;
        List<Address> addresses = null;
        Context c = this.requireContext();
        geocoder = new Geocoder(c, Locale.getDefault());
        try {
            double lat = job.getLatitude();
            double lon = job.getLongitude();
            addresses = geocoder.getFromLocation(lat, lon, 1);
            address = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            address = e.getStackTrace().toString();
        }
        jAddress.setText(address);
    }

    private void updateJobInfoFromInternet() {
        JobDBHelper jobDBHelper = new JobDBHelper();
        jobDBHelper.getJobByKey(job.getKey(), new JobDBHelper.JobObjectCallback() {
            @Override
            public void onObjectReceived(Job object) {
                Log.d("Logcat-chan", "I recieved something from the internet!");
                if (object != null) {
                    job = object;
                    updateTextView();
                    Log.d("Logcat-chan", "The thing is magical enough for the app to update itself! AssigneeEmail is " + job.getAssigneeEmail() + " (" + object.getAssigneeEmail() + ")");
                } else {
                    Log.d("Logcat-chan", "... but the thing is null :(");
                }
            }

            @Override
            public void onError(DatabaseError error) {
                Toast.makeText(getContext(), "Error: cannot update job information: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        updateJobInfoFromInternet();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}