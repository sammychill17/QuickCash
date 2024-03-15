package com.example.quickcash.Objects.Filters;

import com.google.firebase.database.Query;

public class SalaryFilter implements IFilter{

    public Double Salary;
    @Override
    public void setValue(Object value) {
        Salary = (Double) value;
    }

    @Override
    public Object getValue() {
        return Salary;
    }

    @Override
    public Query filter(Query query) {
//        return  query;
        return query.orderByChild("salary").startAt(Salary);
    }
}
