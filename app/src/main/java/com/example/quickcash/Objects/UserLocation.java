package com.example.quickcash.Objects;

import com.example.quickcash.FirebaseStuff.QuickCashDBObject;

public class UserLocation extends QuickCashDBObject {
    private double latitude;
    private double longitude;

    public UserLocation() {
    }

    public UserLocation(String email, double latitude, double longitude) {
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }




    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
