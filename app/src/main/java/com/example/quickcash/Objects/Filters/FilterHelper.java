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

/**
 * The Helper class for running filters. You run filters through this class.
 */

public class FilterHelper {
    private List<IFilter> filters;
    private FilterHelperCallback callback;

    /**
     * Filter completion callback.
     */
    public static class FilterHelperCallback {
        /**
         * This method is called when the search is completed with a result.
         *
         * @param searchResult the search result
         */
        public void onResult(Set<Job> searchResult) {
            //This method is overridden when it is called
        }
    }

    /**
     * Instantiates a new Filter helper.
     */
    public FilterHelper() {}

    /**
     * Instantiates a new Filter helper with the specified callback.
     *
     * @param callback the callback
     */
    public FilterHelper(FilterHelperCallback callback) {
        this.callback = callback;
    }

    /**
     * Set the list filters you wish to apply.
     *
     * @param value the list of filters you wish to apply
     */
    public void setFilters(List<IFilter> value){
        filters = value;
    }

    /**
     * Get the list of filters you wish to apply.
     *
     * @return the list of filters you wish to apply
     */
    public List<IFilter> getFilters(){
        return filters;
    }

    /**
     * Appends a filter to the list of active filters you have.
     *
     * @param filter the filter you wish to add
     */
    public void addFilters(IFilter filter){
        filters.add(filter);
    }

    /**
     * Clears the list of active filters.
     */
    public void clearFilters(){
        filters.clear();
    }

    /**
     * Sets completion callback.
     *
     * @param callback the completion callback
     */
    public void setCallback(FilterHelperCallback callback) { this.callback = callback; }

    /**
     * Clears callback.
     */
    public void clearCallback() { this.callback = null; }
    private Set<String> searchResults = null;
    private Map<String, Job> filteredResults;
    private int semaphore = 0;

    /**
     * Runs the search query.
     */
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
