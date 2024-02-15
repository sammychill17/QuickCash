package com.example.quickcash;

import static android.app.PendingIntent.getActivity;

import com.example.quickcash.Constants;

import com.google.android.material.snackbar.Snackbar;
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
                LoginHandler loginHandler = new LoginHandler(getEmail(),getPassword(),
                        getApplicationContext(),v);
                loginHandler.handleLogin();
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
}
