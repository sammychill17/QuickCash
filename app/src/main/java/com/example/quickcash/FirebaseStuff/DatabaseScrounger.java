/*
* This code was originally written by James Gaultois but heavily modified by chatGTP: https://chat.openai.com/share/0d2020e5-d854-44e2-8fca-0eb3cb3e6aff
*
* It was slightly modified further by James Gaultois for ease of use.
*
* This code is designed to allow classes in this application to more easily grab quickCashDbObjects from the Database.
*
* It's constructor specifies the path within the database to look, and the only method used is able to retrieve specified objects via their email
*
* Due to the asynchronous nature of Databases, these functions must include interfaces and cannot be standard methods or functions.
*
* You can implement this into other classes by including an interface in this format:
* interface EmailExistenceCallback {
        void onResult(whatReturnsOnResult);
        void onError(DatabaseError error);
    }
*
* additionally, calls to this method in other classes should fit this format:
* scrounger.getObjectByEmail(email, new dbScrounger.ObjectCallback() {
            @Override
            public void onObjectReceived(quickCashDbObject object) {
                // If object is not null, email exists
                callback.onResult(whatReturnsOnResult);
            }

            @Override
            public void onError(DatabaseError error) {
                // Handle error, possibly assuming email does not exist or indicating an error
                callback.onError(error);
            }
        });
 * See RegistrationActivity.java for example of implementation.
 */

package com.example.quickcash.FirebaseStuff;

import androidx.annotation.NonNull;

import com.example.quickcash.Objects.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Assuming quickCashDbObject is an abstract class, make sure it can be deserialized properly from Firebase.
// This often requires a default constructor and public getters for all properties.

public class DatabaseScrounger {

    FirebaseDatabase database;
    DatabaseReference reference;

    public DatabaseScrounger(String path) {
        String fireBaseURL = "https://quickcash-6941c-default-rtdb.firebaseio.com/"; //For some reason I was getting null pointer exceptions when I tried to reference strings.xml so I manually added the link here.
        this.database = FirebaseDatabase.getInstance(fireBaseURL);
        this.reference = database.getReference(path);
    }

    public interface ObjectCallback {
        void onObjectReceived(QuickCashDBObject object);
        void onError(DatabaseError error);
    }

    public interface EmployeeCallback {
        void onObjectReceived(Employee object);
        void onError(DatabaseError error);
    }

    public void getObjectByEmail(String email, ObjectCallback callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                QuickCashDBObject result = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming you have a way to deserialize snapshot to a quickCashDbObject
                    // This often involves manually parsing the DataSnapshot based on your object structure
                    QuickCashDBObject obj = snapshot.getValue(QuickCashDBObject.class); // This might need adjustment
                    if (obj != null && email.equals(obj.getEmail())) {
                        result = obj;
                        break;
                    }
                }
                callback.onObjectReceived(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    public void getEmployeeByEmail(String email, EmployeeCallback callback) {
        reference.orderByChild("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Employee result = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Employee obj = snapshot.getValue(Employee.class);

                    if (obj != null && email.equals(obj.getEmail())) {
                        result = obj;
                        break;
                    }
                }
                callback.onObjectReceived(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }
}

