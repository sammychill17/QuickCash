package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;
import com.google.firebase.database.Query;

public class AssignedFilter implements IFilter{
    boolean displayAssignedJobs = false;
    @Override
    public void setValue(Object value) {
        displayAssignedJobs = (boolean) value;
    }

    @Override
    public Object getValue() {
        return displayAssignedJobs;
    }

    @Override
    public Query filter(Query query) {
        throw new IllegalStateException("This filter should never be used on the set intersection strategy");
    }

    @Override
    public boolean shouldRetain(Job job) {
        if (displayAssignedJobs) {
            return true;
        } else {
            return !job.isAssigned();
        }
    }
}
