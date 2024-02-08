package com.example.quickcash;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationTable {
    private DatabaseReference databaseReference;

    public void locationTable() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Locations");
    }

    public void addLocationToDatabase(location loc) {
        databaseReference.push().setValue(loc);
    }
}
