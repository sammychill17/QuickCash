package com.example.quickcash.ui.employerJobList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quickcash.R;

public class EmployerJobListViewModel {
    private final MutableLiveData<String> mText;

    public EmployerJobListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is employer job list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

