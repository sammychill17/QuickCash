package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FilterHelper {
    private List<IFilter> filters;

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
    public Set<Job> run(){
        return Collections.emptySet();
    }
}
