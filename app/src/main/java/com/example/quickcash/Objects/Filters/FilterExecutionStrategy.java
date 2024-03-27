package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;

import java.util.List;
import java.util.Set;

/**
 * The interface for filter execution strategy.
 * We will have two strategies for exexuting our queries: from server-side or from client-side.
 */
public interface FilterExecutionStrategy {
    /**
     * Gets the list of currently applied filters.
     *
     * @return the list of currently applied filters
     */
    public List<IFilter> getFilters();

    /**
     * Sets the list of filters you wish to use in the search.
     *
     * @param filters the list of filters you wish to use in the search
     */
    public void setFilters(List<IFilter> filters);

    /**
     * Gets the callback.
     *
     * @return the callback
     */
    public FilterHelper.FilterHelperCallback getCallback();

    /**
     * Sets the callback.
     *
     * @param callback the callback
     */
    public void setCallback(FilterHelper.FilterHelperCallback callback);

    /**
     * Execute the query. Upon completion the FilterHelperCallback specified in setCallback() will be invoked.
     */
    public void execute();
}
