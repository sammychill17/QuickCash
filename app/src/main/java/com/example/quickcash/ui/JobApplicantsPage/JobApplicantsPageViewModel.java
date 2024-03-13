package com.example.quickcash.ui.JobApplicantsPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JobApplicantsPageViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public JobApplicantsPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is employer job applicants fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}