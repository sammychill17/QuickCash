package com.example.quickcash.ui.employerJobApplicants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;

public class JobApplicantsDirections implements NavDirections {

    private final Bundle args;

    public JobApplicantsDirections(Job j){
        args = new Bundle();
        args.putSerializable("job", j);
    }
    @Override
    public int getActionId() {
        return R.id.jobApplicantsDestFragment;
    }

    @NonNull
    @Override
    public Bundle getArguments() {
        return args;
    }
}
