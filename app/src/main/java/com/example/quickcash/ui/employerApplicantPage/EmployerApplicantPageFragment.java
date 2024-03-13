package com.example.quickcash.ui.employerApplicantPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.FirebaseStuff.DatabaseScrounger;
import com.example.quickcash.FirebaseStuff.QuickCashDBObject;
import com.example.quickcash.Objects.Employee;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.User;
import com.example.quickcash.databinding.FragmentEmployerjobapplicantpageBinding;
import com.example.quickcash.ui.employerApplicantPage.EmployerApplicantPageViewModel;
import com.google.firebase.database.DatabaseError;

public class EmployerApplicantPageFragment extends Fragment{

    private FragmentEmployerjobapplicantpageBinding binding;

    private Job job;
    private String email;

    private Employee employee = new Employee("","","","");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerApplicantPageViewModel EmployerApplicantPageViewModel =
                new ViewModelProvider(this).get(EmployerApplicantPageViewModel.class);

        binding = FragmentEmployerjobapplicantpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle e = getArguments();
        assert e != null;
        job = (Job) e.getSerializable("job");
        email = (String) e.getSerializable("email");
        getEmployee(email, employee);
        TextView empName = binding.jobPageTitle;
        empName.setText(employee.getName());
        Button assignButton = binding.jobApplicantAcceptButton;

        return root;
    }

    private void getEmployee(String email, Employee emp){
        final Employee[] tEmp = {new Employee("", "", "", "")};
        DatabaseScrounger dbScrounger = new DatabaseScrounger("Users");
        dbScrounger.getEmployeeByEmail(email, new DatabaseScrounger.EmployeeCallback() {
            @Override
            public void onObjectReceived(Employee object) {
                if(object!=null){
                    tEmp[0] = object;
                }
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
        emp = tEmp[0];
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}