package com.example.quickcash.ui.employerJobPage;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.databinding.FragmentEmployerjobpageBinding;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

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
        Job j = (Job) b.getSerializable("job");

        TextView jTitle = binding.jobPageTitle;
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
        String employerStr = jEmployer.getText()+" "+j.getEmployer();

        jTitle.setText(j.getTitle());
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

        new GeocodeAsyncTask().execute(j.getLatitude(), j.getLongitude());

        jAddress.setText(address);

        Button backButton = binding.jobPageBackBtn;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        return root;
    }

    private class GeocodeAsyncTask extends AsyncTask<Double, Void, String> {
        @Override
        protected String doInBackground(Double... params){
            double latitude = params[0];
            double longitude = params[1];

            return EmployerJobPageGeocoder.getGeocodeAddress(latitude, longitude);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            address = result;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}