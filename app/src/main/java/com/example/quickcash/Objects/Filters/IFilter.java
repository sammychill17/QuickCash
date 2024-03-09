package com.example.quickcash.Objects.Filters;

import com.google.firebase.database.Query;

public interface IFilter {
    void setValue(Object value);
    Object getValue();
    Query filter(Query query);
}
