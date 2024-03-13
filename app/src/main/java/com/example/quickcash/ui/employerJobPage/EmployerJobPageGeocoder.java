package com.example.quickcash.ui.employerJobPage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmployerJobPageGeocoder {
    // Replace YOUR_API_KEY with your actual Google Maps Geocoding API key
    private static final String API_KEY = "AIzaSyC5cZia3BFONTVXrHZNN-VjAN3k2oyPvMY";

    public EmployerJobPageGeocoder(){

    }

    public static String getGeocodeAddress(double latitude, double longitude) {
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                + latitude + "," + longitude + "&key=" + API_KEY;

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            connection.disconnect();

            // This response string will contain the JSON response from the Google Maps Geocoding API
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
