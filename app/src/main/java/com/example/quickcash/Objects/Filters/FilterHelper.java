package com.example.quickcash.Objects.Filters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.Objects.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class FilterHelper {
    private List<IFilter> filters;
    private FilterHelperCallback callback;

    public static class FilterHelperCallback {
        public void onResult(Set<Job> searchResult) {}
    }

    public FilterHelper() {}

    public FilterHelper(FilterHelperCallback callback) {
        this.callback = callback;
    }

    public void setFilters(List<IFilter> value){
        filters = value;
    }
    public List<IFilter> getFilters(){
        return filters;
    }
    public void addFilters(IFilter filter){
        filters.add(filter);
    }
    public void clearFilters(){
        filters.clear();
    }
    public void setCallback(FilterHelperCallback callback) { this.callback = callback; }
    public void clearCallback() { this.callback = null; }
    private Set<String> searchResults = null;
    private Map<String, Job> filteredResults;
    private int semaphore = 0;

    public void run(){
        FilterExecutionStrategyFactory factory = new FilterExecutionStrategyFactory(filters);
        FilterExecutionStrategy strategy = factory.getPreferredExecutionStrategy();
        strategy.setCallback(new FilterHelperCallback() {
            @Override
            public void onResult(Set<Job> results) {
                callCallback(results);
            }
        });
        strategy.execute();
    }

    private void callCallback(Set<Job> searchResult) {
        if (callback != null) { callback.onResult(searchResult); }
    }
}
