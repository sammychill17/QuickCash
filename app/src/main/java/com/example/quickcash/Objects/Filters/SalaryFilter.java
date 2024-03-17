package com.example.quickcash.Objects.Filters;

import com.example.quickcash.Objects.Job;
import com.google.firebase.database.Query;

/**
 * Salary filter
 */
public class SalaryFilter implements IFilter{

    public Double salary;
    @Override
    public void setValue(Object value) {
        salary = (Double) value;
    }

    @Override
    public Object getValue() {
        return salary;
    }

    @Override
    public Query filter(Query query) {
//        return  query;
        return query.orderByChild("salary").startAt(salary);
    }

    @Override
    public boolean shouldRetain(Job job) {
        return (job.getSalary() >= salary);
    }
}
