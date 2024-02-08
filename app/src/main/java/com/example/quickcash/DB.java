package com.example.quickcash;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DB{
    private DatabaseReference databaseReference;

    public DB() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addUserToDatabase(User user, DatabaseReference.CompletionListener listener) {
        String userId = databaseReference.child("Users").push().getKey();
        databaseReference.child("Users").child(userId).setValue(user, listener);
    }

}
