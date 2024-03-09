package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.JobTypes;
import com.google.firebase.database.Query;

public class JobTypeFilter implements IFilter{
    private JobTypes JobType;
    @Override
    public void setValue(Object value) {
        JobType = (JobTypes) value;
    }

    @Override
    public Object getValue() {
        return JobType;
    }

    @Override
    public Query filter(Query query) {
        return query;
//        return query.orderByChild("jobType").equalTo(JobType.name());
    }
}
