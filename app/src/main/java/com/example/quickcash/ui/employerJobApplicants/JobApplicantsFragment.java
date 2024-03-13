package com.example.quickcash.ui.employerJobApplicants;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.test.InstrumentationRegistry.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.UserLocation;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjobapplicantsBinding;
import com.example.quickcash.databinding.FragmentEmployerjoblistBinding;
import com.example.quickcash.ui.employerJobList.EmployerJobListAdapter;
import com.example.quickcash.ui.employerJobApplicants.JobApplicantsViewModel;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class JobApplicantsFragment extends Fragment {

    RecyclerView mRecyclerView;
    EmployerJobListAdapter myAdapter;
    private FragmentEmployerjobapplicantsBinding binding;

    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JobApplicantsViewModel jobApplicantsViewModel =
                new ViewModelProvider(this).get(JobApplicantsViewModel.class);

        binding = FragmentEmployerjobapplicantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Context c = getContext();
        sp = c.getApplicationContext().getSharedPreferences("session_login", c.MODE_PRIVATE);

        return root;
    }

    private void getApplicantsList(){
//        JobDBHelper helper = new JobDBHelper();
//        //helper.getApplicantsByKey(, new JobDBHelper.ApplicantsObjectCallback() {
//            @Override
//            public void onJobsReceived(List<Job> jobs) {
//                myAdapter.updateJobs(jobs);
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(DatabaseError error) {
//                Toast.makeText(getContext(), getResources().getString(R.string.DATABASE_REGISTRATION_ERROR) + error.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
