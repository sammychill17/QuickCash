package com.example.quickcash.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quickcash.Activities.Adapters.SearchItemAdapter;
import com.example.quickcash.databinding.FragmentEmployeeMapActivityBinding;
import com.example.quickcash.databinding.FragmentEmployerjobapplicantsBinding;

public class MapFragment extends Fragment {

    private FragmentEmployeeMapActivityBinding binding;

    SearchItemAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEmployeeMapActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle b = getArguments();
        assert b != null;
        this.adapter = (SearchItemAdapter) b.getSerializable("adapter");

        return root;
    }

}
