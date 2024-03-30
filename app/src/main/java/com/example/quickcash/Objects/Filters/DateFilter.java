package com.example.quickcash.Objects.Filters;

import android.util.Log;

import com.example.quickcash.Objects.Job;
import com.google.firebase.database.Query;

import java.util.Date;

/**
 * The Date Filter.
 */
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

    @Override
    public boolean shouldRetain(Job job) {
        Date localDate = (Date) date.clone();
        localDate.setYear(localDate.getYear() - 1900);

        Log.d("DateFilter", "Job date is " + job.getDate() + ", filter target is " + localDate);
        return (job.getDate().compareTo(localDate) >= 0);
    }
}
