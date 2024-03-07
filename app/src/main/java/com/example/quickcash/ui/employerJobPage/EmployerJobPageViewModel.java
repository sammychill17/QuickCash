package com.example.quickcash.ui.employerJobPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmployerJobPageViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EmployerJobPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is employer job page fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}