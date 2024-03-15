package com.example.quickcash.ui.employerJobApplicants;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobApplicants;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjobapplicantsBinding;
import com.google.firebase.database.DatabaseError;

public class JobApplicantsFragment extends Fragment {

    RecyclerView mRecyclerView;
    JobApplicantsAdapter myAdapter;
    private FragmentEmployerjobapplicantsBinding binding;

    SharedPreferences sp;

    Job job;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JobApplicantsViewModel jobApplicantsViewModel =
                new ViewModelProvider(this).get(JobApplicantsViewModel.class);

        binding = FragmentEmployerjobapplicantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle b = getArguments();
        assert b != null;
        this.job = (Job) b.getSerializable("job");
        Context c = getContext();
        sp = c.getApplicationContext().getSharedPreferences("session_login", c.MODE_PRIVATE);
        Button backButton = binding.backButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
        mRecyclerView = binding.jobApplicantList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        myAdapter = new JobApplicantsAdapter(this.getContext(), null, this);
        mRecyclerView.setAdapter(myAdapter);
        getApplicantsList();

        return root;
    }

    private void getApplicantsList(){
        JobDBHelper helper = new JobDBHelper();
        helper.getApplicantsByKey(job.getKey(), new JobDBHelper.ApplicantsObjectCallback() {

            @Override
            public void onObjectReceived(JobApplicants object) {
                if(object!=null){
                    myAdapter.updateApplicants(object.getApplicants());
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(DatabaseError error) {
                Toast.makeText(getContext(), getResources().getString(R.string.DATABASE_REGISTRATION_ERROR) + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
