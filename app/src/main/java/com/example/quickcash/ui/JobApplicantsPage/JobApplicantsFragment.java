package com.example.quickcash.ui.JobApplicantsPage;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.databinding.FragmentEmployerjobapplicantsBinding;
import com.example.quickcash.databinding.FragmentEmployerjobpostBinding;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostViewModel;
import com.example.quickcash.R;

public class JobApplicantsFragment extends Fragment{

    private FragmentEmployerjobapplicantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JobApplicantsViewModel JobApplicantsViewModel =
                new ViewModelProvider(this).get(JobApplicantsViewModel.class);

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