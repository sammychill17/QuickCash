package com.example.quickcash.Objects.Filters;

import com.google.firebase.database.Query;

import java.util.Date;

public class DateFilter implements IFilter{
    private Date Date;
    @Override
    public void setValue(Object value) {
        Date = (Date) value;
    }

    @Override
    public Object getValue() {
        return Date;
    }

    @Override
    public Query filter(Query query) {
        // TODO: implement DateFilter
        return query;
//        return query.orderByChild("date").endAt(Date);
    }
}
