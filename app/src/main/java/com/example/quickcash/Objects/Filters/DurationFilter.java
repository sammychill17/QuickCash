package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;
import com.google.firebase.database.Query;

public class DurationFilter implements IFilter{

    private int duration;
    @Override
    public void setValue(Object value) {
        duration = (Integer) value;
    }

    @Override
    public Object getValue() {
        return duration;
    }

    @Override
    public Query filter(Query query) {
//        return query;
        return query.orderByChild("duration").endAt(duration);
    }

    @Override
    public boolean shouldRetain(Job job) {
        return (job.getDuration() <= duration);
    }
}
