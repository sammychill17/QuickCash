package com.example.quickcash.ui.employerJobPost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmployerJobPostViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EmployerJobPostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is employer job post fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}