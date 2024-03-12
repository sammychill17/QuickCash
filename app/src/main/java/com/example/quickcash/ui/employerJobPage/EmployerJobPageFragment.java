package com.example.quickcash.ui.employerJobPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentEmployerjobpageBinding;
import com.example.quickcash.ui.employerJobPage.EmployerJobPageViewModel;

public class EmployerJobPageFragment extends Fragment{

    private FragmentEmployerjobpageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPageViewModel EmployerJobPageViewModel =
                new ViewModelProvider(this).get(EmployerJobPageViewModel.class);

        binding = FragmentEmployerjobpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button backButton = binding.jobPageBackBtn;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}