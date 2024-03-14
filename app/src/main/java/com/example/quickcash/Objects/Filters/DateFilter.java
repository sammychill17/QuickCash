package com.example.quickcash.Objects.Filters;

import android.util.Log;

import com.google.firebase.database.Query;

import java.util.Date;

public class DateFilter implements IFilter{
    private Date date;
    @Override
    public void setValue(Object value) {
        date = (Date) value;
    }

    @Override
    public Object getValue() {
        return date;
    }

    @Override
    public Query filter(Query query) {
        Log.d("DateFilter", "Old year is " + date.getYear());
        date.setYear(date.getYear() - 1900);
        Log.d("DateFilter", "New year is " + date.getYear());
        long epoch = date.getTime();
        return query.orderByChild("epoch").startAt(epoch);
    }
}
