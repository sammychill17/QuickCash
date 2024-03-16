package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.JobTypes;
import com.google.firebase.database.Query;

public class JobTypeFilter implements IFilter{
    private JobTypes jobType;
    @Override
    public void setValue(Object value) {
        jobType = (JobTypes) value;
    }

    @Override
    public Object getValue() {
        return jobType;
    }

    @Override
    public Query filter(Query query) {
//        return query;
        return query.orderByChild("jobType").equalTo(jobType.name());
    }

    @Override
    public boolean shouldRetain(Job job) {
        return (job.getJobType() == jobType);
    }
}
