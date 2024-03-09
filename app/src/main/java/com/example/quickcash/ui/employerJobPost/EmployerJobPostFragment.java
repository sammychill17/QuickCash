package com.example.quickcash.ui.employerJobPost;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.databinding.FragmentEmployerjobpostBinding;
import com.example.quickcash.R;
import com.example.quickcash.ui.employerHome.EmployerHomeFragment;

public class EmployerJobPostFragment extends Fragment{

    private FragmentEmployerjobpostBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPostViewModel EmployerJobPostViewModel =
                new ViewModelProvider(this).get(EmployerJobPostViewModel.class);

        binding = FragmentEmployerjobpostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sp = requireContext().getSharedPreferences("session_login",
                Context.MODE_PRIVATE);

        final Spinner spinner = binding.jobTypeSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.job_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button backBtn = root.findViewById(R.id.goBackButton);
        Button dateBtn = root.findViewById(R.id.jobDateButton);
        Button postBtn = root.findViewById(R.id.jobPostButton);
        EditText titleTxt = root.findViewById(R.id.jobTitleField);
        EditText descTxt = root.findViewById(R.id.jobDescField);
        EditText salaryTxt = root.findViewById(R.id.jobSalaryField);
        EditText durationTxt = root.findViewById(R.id.jobDurationField);

        AtomicReference<Date> curDate = new AtomicReference<>(new Date());

        // rudimentary back button functionality to help with testing.
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.dashboardCardView, EmployerHomeFragment.class, null);
                fragmentTransaction.commit();
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(), (view, year1, monthOfYear, dayOfMonth) -> {
                            Calendar selectedDate = Calendar.getInstance();
                            selectedDate.set(Calendar.YEAR, year1);
                            selectedDate.set(Calendar.MONTH, monthOfYear);
                            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            curDate.set(selectedDate.getTime());
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job job = new Job(titleTxt.getText().toString(), descTxt.getText().toString(),
                        JobTypes.valueOf(spinner.getSelectedItem().toString()),
                        Double.parseDouble(String.valueOf(salaryTxt.getText())),
                        Duration.ofHours(Integer.parseInt(durationTxt.getText().toString())),
                        sp.getString("email", "default@failedtest.com"));
                JobDBHelper jobDBHelper = new JobDBHelper(job);
                jobDBHelper.pushJobToDB();
                Snackbar.make(postBtn, "Successfully posted job!", Snackbar.LENGTH_SHORT).show();
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