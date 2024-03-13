package com.example.quickcash.ui.employerApplicantPage;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.quickcash.FirebaseStuff.DatabaseScrounger;
import com.example.quickcash.Objects.Employee;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjobapplicantpageBinding;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostFragment;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployerApplicantPageFragment extends Fragment{

    private FragmentEmployerjobapplicantpageBinding binding;

    private Job job;
    private String email;

    private Employee employee;
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
        getEmployee(email);
        Button assignButton = binding.jobApplicantAcceptButton;
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("session_login", MODE_PRIVATE);
        String employerEmail = sharedPrefs.getString("email", "");
        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job.assignJob(email);
                DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("Posted Jobs");
                String jobKey = job.getKey();
                job.setEmployerEmail(employerEmail);
                jobRef.child(jobKey).setValue(job);
                getParentFragmentManager().popBackStack();
            }
        });

        Button backButton = binding.backToApplicantsButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        return root;
    }

    private void getEmployee(String email){
        DatabaseScrounger dbScrounger = new DatabaseScrounger("Users");
        dbScrounger.getEmployeeByEmail(email, new DatabaseScrounger.EmployeeCallback() {
            @Override
            public void onObjectReceived(Employee object) { //This override was modified by ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
                employee = object;
                if(employee!=null){
                    updateUIWithEmployeeInfo();
                }
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }

    /*
    * This method was created on suggestion by ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
     */
    private void updateUIWithEmployeeInfo() {
        if (getActivity() == null) {
            return;
        }

        getActivity().runOnUiThread(() -> {
            TextView empName = binding.jobPageTitle;
            if (employee != null) {
                empName.setText(employee.getName());
            } else {
                empName.setText("Employee not found");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}