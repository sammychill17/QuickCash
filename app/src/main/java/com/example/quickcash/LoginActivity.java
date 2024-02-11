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
                handleLogin();
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
        FirebaseDatabase.getInstance(Constants.firebaseUrl).getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
        public void onDataChange(DataSnapshot snapshot) {
            for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                if (accountSnapshot.hasChild("email") && accountSnapshot.hasChild("password")) {
                    String accEmail = String.valueOf(accountSnapshot.child("email").getValue());
                    String accPassword = String.valueOf(accountSnapshot.child("password").getValue());
                    if (accEmail.equals(LoginActivity.this.getEmail()) && accPassword.equals(LoginActivity.this.getPassword())) {
                        LoginActivity.this.handleSp();
                        Snackbar.make(LoginActivity.this.findViewById(R.id.buttonLogin), (CharSequence) "Login successful!", -1).show();
                    } else if (LoginActivity.this.getEmail().isEmpty()) {
                        Snackbar.make(LoginActivity.this.findViewById(R.id.buttonLogin), (CharSequence) "Email required to login", -1).show();
                    } else if (LoginActivity.this.getPassword().isEmpty()) {
                        Snackbar.make(LoginActivity.this.findViewById(R.id.buttonLogin), (CharSequence) "Password required to login", -1).show();
                    } else if (accEmail.equals(LoginActivity.this.getEmail()) && !accPassword.equals(LoginActivity.this.getPassword())) {
                        Snackbar.make(LoginActivity.this.findViewById(R.id.buttonLogin), (CharSequence) "Incorrect password", -1).show();
                    }
                } else {
                    Snackbar.make(LoginActivity.this.findViewById(R.id.buttonLogin), (CharSequence) "Account not found. Please register", -1).show();
                }
            }
        }

        public void onCancelled(DatabaseError error) {

        }
        });
    }

    private void handleSp() {
        Context context = getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences(
                Constants.sessionData_spID, Context.MODE_PRIVATE);

        sp.edit().putString("email",getEmail()).commit();
        sp.edit().putString("password",getPassword()).commit();
    }
}
