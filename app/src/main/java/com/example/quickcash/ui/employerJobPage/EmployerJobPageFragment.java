package com.example.quickcash.ui.employerJobPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjobpageBinding;
import com.example.quickcash.ui.employerJobApplicants.JobApplicantsDirections;
import com.example.quickcash.ui.employerJobApplicants.JobApplicantsFragment;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPageViewModel EmployerJobPageViewModel =
                new ViewModelProvider(this).get(EmployerJobPageViewModel.class);

        binding = FragmentEmployerjobpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle b = getArguments();
        assert b != null;
        Job j = (Job) b.getSerializable("job");

        TextView jTitle = binding.jobPageTitle;
        TextView jJobType = binding.jobPageType;
        TextView jAddress = binding.jobPageAddress;
        TextView jSalary = binding.jobPageSalary;
        TextView jDate = binding.jobPageDate;
        TextView jEmployer = binding.jobPageEmployerName;
        TextView jApplicant = binding.jobPageApplicant;
        TextView jDesc = binding.jobPageDesc;

        // assertion here to prevent NullException
        assert j != null;
        String salaryStr = jSalary.getText()+" $"+df.format(j.getSalary());
        String dateStr = jDate.getText()+" "+j.getDate().toString();
        String employerEmail = j.getEmployerEmail();
        if (employerEmail.length() == 0) {
            SharedPreferences sp = requireContext().getSharedPreferences(getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);
            employerEmail = sp.getString("email", "Error - cannot get email");
        }
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
            jApplicant.setText("");
        }
        jDesc.setText(j.getDescription());
        Geocoder geocoder;
        List<Address> addresses = null;
        Context c = this.requireContext();
        geocoder = new Geocoder(c, Locale.getDefault());
        try {
            double lat = j.getLatitude();
            double lon = j.getLongitude();
            addresses = geocoder.getFromLocation(lat, lon, 1);
            address = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            address = e.getStackTrace().toString();
        }
        jAddress.setText(address);

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
                bundle.putSerializable("job", j);
                fragment.setArguments(b);
                NavController navController = NavHostFragment.findNavController(current);
                navController.navigate(new JobApplicantsDirections(j));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}