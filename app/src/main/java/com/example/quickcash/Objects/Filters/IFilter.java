package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;
import com.google.firebase.database.Query;

/**
 * IFilter
 * <p>
 * Base interface for multiple types of filters.
 */
public interface IFilter {
    /**
     * Sets the value for the filter.
     *
     * @param value the value
     */
    void setValue(Object value);

    /**
     * Gets the value for the filter.
     *
     * @return the value
     */
    Object getValue();

    /**
     * Filters the query by applying constraints on it.
     * This method is used for server-side filtering
     * (i.e. sending a highly-constrained query there and reading the results back)
     *
     * @param query the initial query to constraint
     * @return the constrained query
     */
    Query filter(Query query);

    /**
     * Returns true if the job matches the query.
     * This method is used for client-side filtering
     * (i.e. looping through all the jobs, retaining only those that this method returns true)
     *
     * @param job the job to match for
     * @return true if the job sastifies this filter, false otherwise
     */
    boolean shouldRetain(Job job);
}
