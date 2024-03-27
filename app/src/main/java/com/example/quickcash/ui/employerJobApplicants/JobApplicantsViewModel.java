package com.example.quickcash.ui.employerJobApplicants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quickcash.R;

public class JobApplicantsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public JobApplicantsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is job applicants list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

