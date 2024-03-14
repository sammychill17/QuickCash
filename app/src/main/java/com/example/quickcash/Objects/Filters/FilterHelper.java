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
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com");
        filteredResults = new HashMap<>();
        semaphore = filters.size();
        for (IFilter filter : filters) {
            DatabaseReference jobsReference = database.getReference("Posted Jobs");
            Query query = filter.filter(jobsReference);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("FilterHelper", "I am a " + filter.getClass().getName() + " filter qwertyuiop, value is " + filter.getValue());
//                    Log.d("FilterHelper", "snapshot is " + String.valueOf(snapshot.getValue()));
                    Set<String> localSearchResults = new HashSet<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Log.d("FilterHelper", snapshot1.getValue().toString());
                        Job obj = snapshot1.getValue(Job.class); // This might need adjustment
                        if (obj != null ) {
                            filteredResults.put(obj.getKey(), obj);
                            localSearchResults.add(obj.getKey());
                            Log.d("FilterHelper", "Added " + obj.getTitle() + " to the job map");
                        } else {
                            Log.d("FilterHelper", "Cannot convert above job to a Job instance.");
                        }
                    }
                    Log.d("FilterHelper", "Thats it for this onDataChange (" + snapshot.getKey() + ")");

                    if (searchResults != null) {
                        Log.d("FilterHelper", "I want to list all the jobs before retainAll()");
                        for (String key : searchResults) {
                            Log.d("FilterHelperSemaphore", "Key is " + key);
                        }
                        Log.d("FilterHelper", "Doing retainAll()");
                        boolean changed = searchResults.retainAll(localSearchResults);
                        Log.d("FilterHelper", "Set changed after retainALl()? " + changed);
                        Log.d("FilterHelper", "I want to list all the jobs after retainAll()");
                        for (String key : searchResults) {
                            Log.d("FilterHelperSemaphore", "Key is " + key);
                        }
                        Log.d("FilterHelper", "Lets move on...");
                    } else {
                        searchResults = new HashSet<>();
                        searchResults.addAll(localSearchResults);
                        Log.d("FilterHelper", "Created new set and populated its values");
                    }

                    semaphore--;
                    if (semaphore == 0) {
                        Set<Job> jobSet = new HashSet<>();
                        for (String key : searchResults) {
                            Job job = filteredResults.get(key);
                            if (job != null) {
                                jobSet.add(job);
                            }
                        }
                        callCallback(jobSet);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

//        callCallback(Collections.emptySet());
    }

    private void callCallback(Set<Job> searchResult) {
        if (callback != null) { callback.onResult(searchResult); }
    }
}
