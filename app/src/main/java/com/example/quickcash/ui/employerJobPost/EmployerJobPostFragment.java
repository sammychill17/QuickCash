package com.example.quickcash.ui.employerJobPost;

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

import com.example.quickcash.databinding.FragmentEmployerjobpostBinding;
import com.example.quickcash.ui.employerJobPost.EmployerJobPostViewModel;
import com.example.quickcash.R;

public class EmployerJobPostFragment extends Fragment{

    private FragmentEmployerjobpostBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmployerJobPostViewModel EmployerJobPostViewModel =
                new ViewModelProvider(this).get(EmployerJobPostViewModel.class);

        binding = FragmentEmployerjobpostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Spinner spinner = binding.jobTypeSpinner;
        Resources res = getResources();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.job_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}