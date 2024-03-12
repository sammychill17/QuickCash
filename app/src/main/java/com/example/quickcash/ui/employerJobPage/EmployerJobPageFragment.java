package com.example.quickcash.ui.employerJobPage;

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

import java.text.DecimalFormat;

public class EmployerJobPageFragment extends Fragment{

    private FragmentEmployerjobpageBinding binding;
    DecimalFormat df = new DecimalFormat("#,###.00");

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

        Button backButton = binding.jobPageBackBtn;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
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