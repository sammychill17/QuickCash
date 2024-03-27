package com.example.quickcash.Objects.Filters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.Objects.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Loop strategy - do the processing client-side, loop through every job in the database.
 * You must use this strategy for some filters where server-side processing is impossible
 */
public class FilterExecutionLoopStrategy implements FilterExecutionStrategy{

    private List<IFilter> filters;
    private FilterHelper.FilterHelperCallback callback;

    @Override
    public List<IFilter> getFilters() {
        return filters;
    }

    @Override
    public void setFilters(List<IFilter> filters) {
        this.filters = filters;
    }

    @Override
    public FilterHelper.FilterHelperCallback getCallback() {
        return callback;
    }

    @Override
    public void setCallback(FilterHelper.FilterHelperCallback callback) {
        this.callback = callback;
    }

    @Override
    public void execute() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com");
        DatabaseReference reference = database.getReference("Posted Jobs");
        Set<Job> resultSet = new HashSet<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    // Run each filter
                    Log.d("LoopStrategy", "Loop");
                    for (IFilter filter : filters) {
                        Log.d("LoopStrategy", "I am a " + filter.getClass().getName() + " with value " + filter.getValue());
                    }
                    Job obj = snapshot1.getValue(Job.class); // This might need adjustment
                    if (obj != null ) {
                        Log.d("LoopStrategy", "I see " + obj.getTitle());
                        boolean shouldAddToResultSet = true;
                        for (IFilter filter : filters) {
                            Log.d("LoopStrategy", "Running against " + filter.getClass().getName() + " (" + filter.getValue() + ") - " + filter.shouldRetain(obj));
                            if (!filter.shouldRetain(obj)) {
                                shouldAddToResultSet = false;
                                break;
                            }
                        }
                        if (shouldAddToResultSet) {
                            if (!obj.isAssigned()) {
                                resultSet.add(obj);
                                Log.d("LoopStrategy", "Added " + obj.getTitle());
                            } else {
                                Log.d("LoopStrategy", obj.getTitle() + " is already assigned");
                            }
                        }
                    } else {
                        Log.d("FilterHelper", "Cannot convert above job to a Job instance.");
                    }
                }
                callCallback(resultSet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void callCallback(Set<Job> searchResult) {
        if (callback != null) { callback.onResult(searchResult); }
    }
}
