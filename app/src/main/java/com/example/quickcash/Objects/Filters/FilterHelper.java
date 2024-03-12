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
    public void run(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com");
        Set<Job> searchResults = null;
        Map<String, Job> filteredResults = new HashMap<>();

        for (IFilter filter : filters) {
            DatabaseReference jobsReference = database.getReference("Posted Jobs");
            Query query = filter.filter(jobsReference);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot != null) {
                        Log.d("FilterHelper", "snapshot is " + String.valueOf(snapshot.getValue()));
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Log.d("FilterHelper", snapshot1.getValue().toString());
                            Job obj = snapshot1.getValue(Job.class); // This might need adjustment
                            if (obj != null ) {
                                filteredResults.put(obj.getKey(), obj);
                                Log.d("FilterHelper", "Added " + obj.getTitle() + " to the job map");
                            } else {
                                Log.d("FilterHelper", "Cannot convert above job to a Job instance.");
                            }
                        }
                        Log.d("FilterHelper", "Thats it for this onDataChange (" + snapshot.getKey() + ")");
                    } else {
                        Log.d("FilterHelper", "Snapshot is null! I am sad :(");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        callCallback(Collections.emptySet());
    }

    private void callCallback(Set<Job> searchResult) {
        if (callback != null) { callback.onResult(searchResult); }
    }
}
