package com.example.quickcash.ui.JobApplicantsPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.databinding.FragmentEmployerjobapplicantsBinding;

public class JobApplicantsPageFragment extends Fragment{

    private FragmentEmployerjobapplicantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JobApplicantsPageViewModel JobApplicantsViewModel =
                new ViewModelProvider(this).get(JobApplicantsPageViewModel.class);

        binding = FragmentEmployerjobapplicantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}