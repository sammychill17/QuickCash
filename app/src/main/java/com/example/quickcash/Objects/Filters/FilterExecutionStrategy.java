package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;

import java.util.List;
import java.util.Set;

public interface FilterExecutionStrategy {
    public List<IFilter> getFilters();
    public void setFilters(List<IFilter> filters);

    public FilterHelper.FilterHelperCallback getCallback();
    public void setCallback(FilterHelper.FilterHelperCallback callback);

    public void execute();
}
