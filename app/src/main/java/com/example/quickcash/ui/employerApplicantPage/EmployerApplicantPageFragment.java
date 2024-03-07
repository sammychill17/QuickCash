package com.example.quickcash.ui.employerApplicantPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.databinding.FragmentEmployerjobapplicantpageBinding;
import com.example.quickcash.ui.employerApplicantPage.EmployerApplicantPageViewModel;

public class EmployerApplicantPageFragment extends Fragment{

    private FragmentEmployerjobapplicantpageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerApplicantPageViewModel EmployerApplicantPageViewModel =
                new ViewModelProvider(this).get(EmployerApplicantPageViewModel.class);

        binding = FragmentEmployerjobapplicantpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}