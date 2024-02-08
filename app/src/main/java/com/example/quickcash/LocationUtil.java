package com.example.quickcash;

/*
 utility class for location testing and edge cases
*/
public class LocationUtil {
   /*
   checks if latitude is valid and catches error if
   latitude shouldn't have co-ordinates
   that stretches beyond 90 and stays between -90 and 90.
    */
    public static boolean isValidLatitude(String latitude) {
        try {
            double lat = Double.parseDouble(latitude);
            return lat >= -90 && lat <= 90;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*
   checks if longitude is valid and catches error if
   longitude shouldn't have co-ordinates
   that stretches beyond 180 and stays between -180 and 180.
    */
    public static boolean isValidLongitude(String longitude) {
        try {
            double lon = Double.parseDouble(longitude);
            return lon >= -180 && lon <= 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
