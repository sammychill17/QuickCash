package com.example.quickcash.Objects.Filters;

import com.google.firebase.database.Query;

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
        return query;
//        return query.orderByChild("title").equalTo(title);
    }
}
