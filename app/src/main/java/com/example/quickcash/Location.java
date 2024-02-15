package com.example.quickcash;
//it should extend to quickcash database object
//connect string email (getters and setters) to check whether the same email
//is getting the same co-ordinates
public class Location {
    private double latitude;
    private double longitude;


    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
     Getters and setters
     */
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public boolean checkEquals(Object obj) {
        /*
         Check for reference equality
         */
        if (this == obj){
            return true;
        }
        /*
         Check for null and object type
         */
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Location other = (Location) obj;
        return Double.compare(latitude, other.latitude) == 0 &&
                Double.compare(longitude, other.longitude) == 0;
    }

}
