package com.example.quickcash.ui.employerApplicantPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmployerApplicantPageViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EmployerApplicantPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is employer applicant page fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}