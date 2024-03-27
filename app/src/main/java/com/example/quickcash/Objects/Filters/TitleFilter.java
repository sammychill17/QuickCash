package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;
import com.google.firebase.database.Query;

/**
 * Title filter
 */

public class TitleFilter implements IFilter {

    private String title;

    @Override
    public void setValue(Object value) {
        title = String.valueOf(value);
    }

    @Override
    public Object getValue() {
        return title;
    }

    @Override
    public Query filter(Query query) {
//        return query;
        return query.orderByChild("title").equalTo(title);
    }

    @Override
    public boolean shouldRetain(Job job) {
        return (job.getTitle().toLowerCase().contains(title.toLowerCase()));
    }
}
