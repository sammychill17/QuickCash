package com.example.quickcash.Objects.Filters;

import android.util.Log;

import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.UserLocation;
import com.google.firebase.database.Query;

public class DistanceFilter implements IFilter{
    private int distance;
    private UserLocation currentLocation;
    @Override
    public void setValue(Object value){
        distance = (Integer) value;
    };
    @Override
    public Object getValue(){
        return distance;
    }

    public UserLocation getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(UserLocation location) { currentLocation = location; }

    @Override
    public Query filter(Query query) {
        throw new IllegalStateException("DistanceFilter.filter(Query) is not implemented, and should not be called.");
    }

    @Override
    public boolean shouldRetain(Job job) {
        double doubleDistance = distance;
        double jobDistance = getDistanceFromLatLonInKm(
                job.getLatitude(),
                job.getLongitude(),
                currentLocation.getLatitude(),
                currentLocation.getLongitude()
        );
        Log.d("DistanceFilter", "You are apparently at long=" + currentLocation.getLongitude() + ", lat=" + currentLocation.getLatitude());
        Log.d("DistanceFilter", "Distance is apparently " + jobDistance);
        return ((jobDistance * 1000) <= doubleDistance);
    }

    // Distance formula from SO: https://stackoverflow.com/a/27943
    // (I suck at math)
    double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        int earthRadius = 6371;
        double dLat = deg2rad(lat2-lat1);
        double dLon = deg2rad(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadius * c;
    }

    double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

}
