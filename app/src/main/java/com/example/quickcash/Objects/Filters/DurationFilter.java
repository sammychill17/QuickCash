package com.example.quickcash.Objects.Filters;

import com.google.firebase.database.Query;

import java.time.Duration;

public class DurationFilter implements IFilter{

    private int Duration;
    @Override
    public void setValue(Object value) {
        Duration = (Integer) value;
    }

    @Override
    public Object getValue() {
        return Duration;
    }

    @Override
    public Query filter(Query query) {
//        return query;
        return query.orderByChild("duration").endAt(Duration);
    }
}
