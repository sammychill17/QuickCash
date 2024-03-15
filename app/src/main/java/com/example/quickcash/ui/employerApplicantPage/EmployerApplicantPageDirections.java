package com.example.quickcash.ui.employerJobApplicants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;

public class EmployerApplicantPageDirections implements NavDirections {

    private final Bundle args;

    public EmployerApplicantPageDirections(String email, Job job){
        args = new Bundle();
        args.putSerializable("email", email);
        args.putSerializable("job", job);
    }
    @Override
    public int getActionId() {
        return R.id.employerApplicantPageDestFragment;
    }

    @NonNull
    @Override
    public Bundle getArguments() {
        return args;
    }
}
