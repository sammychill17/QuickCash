package com.example.quickcash.Objects.Filters;

import com.google.firebase.database.Query;

public class DistanceFilter implements IFilter{
    private int Distance;
    @Override
    public void setValue(Object value){
        Distance= (Integer) value;
    };
    @Override
    public Object getValue(){
        return Distance;
    }
    @Override
    public Query filter(Query query) {
        return query;
//       return query.orderByChild("distance").endAt(Distance);
    }

}
