//package com.example.quickcash;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import static org.mockito.Mockito.*;
//
//import com.example.quickcash.Activities.LocationActivity;
//import com.example.quickcash.FirebaseStuff.LocationTable;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.example.quickcash.Objects.UserLocation;
//
//import static org.junit.Assert.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LocationUnitTesting {
//
//    @Mock
//    private FusedLocationProviderClient mockFusedLocationProviderClient;
//
//    @Mock
//    private DatabaseReference mockDatabaseReference;
//    @Mock
//    private FirebaseDatabase mockFirebaseDatabase;
//    @Mock
//    private Task<Void> mockTask;
//
//    private LocationActivity locationActivity;
//    private LocationTable locationTable;
//
//    @Before
//    public void setUp() {
//        /*
//         setting up mock the database reference
//         */
//        when(mockFirebaseDatabase.getReference("User Locations")).thenReturn(mockDatabaseReference);
//        when(mockDatabaseReference.child(anyString())).thenReturn(mockDatabaseReference);
//        when(mockDatabaseReference.setValue(any())).thenReturn(mockTask);
//
//        /*
//         Triggering the initiation of the LocationTable with a mocked FirebaseDatabase
//         */
//        locationTable = new LocationTable();
//
//        /*
//        Creates an instance of LocationActivity and
//        injecting the mocked FusedLocationProviderClient
//         */
//        locationActivity = new LocationActivity();
//        locationActivity.fusedLocationClient = mockFusedLocationProviderClient;
//    }
//
//    /*
//    Test for checking whether adding Location to the database
//     is done correctly.
//     */
//    @Test
//    public void addLocationToDatabase_SavesDataCorrectly() {
//        UserLocation userLocation = new UserLocation("test@example.com", 10.0, 20.0);
//        locationTable.addLocationToDatabase(userLocation);
//        verify(mockDatabaseReference).setValue(userLocation);
//    }
//
//}
