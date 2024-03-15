package com.example.quickcash.ui.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

import com.example.quickcash.Activities.Adapters.SearchItemAdapter;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.R;

public class MapDirections implements NavDirections {

    private final Bundle args;

    public MapDirections(SearchItemAdapter j) {
        args = new Bundle();
        args.putSerializable("adapter", j);
    }
    @Override
    public int getActionId() {
        return R.id.mapFragment;
    }

    @NonNull
    @Override
    public Bundle getArguments() {
        return args;
    }
}
