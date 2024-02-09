package com.example.quickcash;

import static android.app.PendingIntent.getActivity;

import com.example.quickcash.Constants;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginbutton = (Button) findViewById(R.id.buttonLogin);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleLogin();
                handleSp();
            }
        });
    }

    private String getEmail() {
        EditText emailBox = (EditText) findViewById(R.id.editTextTextEmailAddress);
        return emailBox.getText().toString();
    }

    private String getPassword() {
        EditText passwordBox = (EditText) findViewById(R.id.editTextTextPassword);
        return passwordBox.getText().toString();
    }

    private void handleLogin() {
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.firebaseUrl);
        DatabaseReference drefEmail = db.getReference("Email");

        drefEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String e = snapshot.getValue(String.class);
                Toast.makeText(LoginActivity.this, e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSp() {
        Context context = getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences(
                Constants.sessionData_spID, Context.MODE_PRIVATE);

        sp.edit().putString("email",getEmail()).commit();
        sp.edit().putString("password",getPassword()).commit();

        Toast.makeText(context, sp.getString("email","ERROR ON EMAIL")
                +", "+sp.getString("password","ERROR ON PASSWORD"),
                Toast.LENGTH_SHORT).show();
    }
}