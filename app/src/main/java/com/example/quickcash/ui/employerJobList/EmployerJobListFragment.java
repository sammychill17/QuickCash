package com.example.quickcash.ui.employerJobList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.FirebaseStuff.EmployerDBHelper;
import com.example.quickcash.FirebaseStuff.JobDBHelper;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.example.quickcash.Objects.UserLocation;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjoblistBinding;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageViewModel;
import com.google.firebase.database.DatabaseError;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class EmployerJobListFragment extends Fragment{

    RecyclerView mRecyclerView;
    EmployerJobListAdapter myAdapter;
    private FragmentEmployerjoblistBinding binding;
    private UserLocation currentLocation;
    private double currentLatitude;
    private double currentLongitude;


    SharedPreferences sp;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPageViewModel EmployerJobListViewModel =
                new ViewModelProvider(this).get(EmployerJobPageViewModel.class);

        binding = FragmentEmployerjoblistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button backButton = binding.backButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        Context c = getContext();

        sp = c.getApplicationContext().getSharedPreferences("session_login", c.MODE_PRIVATE);

        mRecyclerView = binding.jobList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        myAdapter = new EmployerJobListAdapter(this.getContext(), null); //This was changed to be initially set to Null on suggestion by ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
        mRecyclerView.setAdapter(myAdapter);
        getEmployerJobList(); //This method is called following the suggestion of ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838

        return root;
    }

    public void onLocationDataReceived(UserLocation location) {
        if (location != null) {
            currentLocation = location;
            double currentLatitude = location.getLatitude();
            double currentLongitude = location.getLongitude();
        } else {
            Toast.makeText(getContext(), "Location could not be retrieved.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * This method was redone on suggestion by ChatGPT: https://chat.openai.com/share/51eb0c15-b549-4b73-b76e-b28aa718b838
     */
    private void getEmployerJobList(){
        EmployerDBHelper helper = new EmployerDBHelper();
        String spEmail = sp.getString("email", "");
        helper.getJobsByEmployer(spEmail, new EmployerDBHelper.JobObjectCallback() {
            @Override
            public void onJobsReceived(List<Job> jobs) {
                myAdapter.updateJobs(jobs);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(DatabaseError error) {
                Toast.makeText(getContext(), getResources().getString(R.string.DATABASE_REGISTRATION_ERROR) + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}