package com.example.quickcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginHandler {
    private String email;
    private String password;

    private Context context;
    private View view;

    public LoginHandler(String email, String password, Context context, View view) {
        this.email = email;
        this.password = password;
        this.context = context;
        this.view = view;
    }

    public void handleLogin() {
        FirebaseDatabase.getInstance(Constants.firebaseUrl).getReference().child("Users").
                addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                    if (accountSnapshot.hasChild("email") && accountSnapshot.hasChild(
                            "password")) {
                        String accEmail = String.valueOf(accountSnapshot.child("email")
                                .getValue());
                        String accPassword = String.valueOf(accountSnapshot.child("password")
                                .getValue());
                        if (accEmail.equals(email) && accPassword.equals(password)) {
                            handleSp();
                            Snackbar.make(view.findViewById(R.id.buttonLogin), (CharSequence)
                                    context.getString(R.string.LOGIN_SUCCESS), -1).show();
                        } else if (email.isEmpty()) {
                            Snackbar.make(view.findViewById(R.id.buttonLogin), (CharSequence)
                                    context.getString(R.string.LOGIN_ERROR_EMAIL_EMPTY),
                                    -1).show();
                        } else if (password.isEmpty()) {
                            Snackbar.make(view.findViewById(R.id.buttonLogin), (CharSequence)
                                    context.getString(R.string.LOGIN_ERROR_PASSWORD_EMPTY),
                                    -1).show();
                        } else if (accEmail.equals(email) && !accPassword.equals(password)) {
                            Snackbar.make(view.findViewById(R.id.buttonLogin), (CharSequence)
                                    context.getString(R.string.LOGIN_ERROR_PASSWORD_INCORRECT),
                                    -1).show();
                        } else {
                            Snackbar.make(view.findViewById(R.id.buttonLogin), (CharSequence)
                                    context.getString(R.string.LOGIN_ERROR_EMAIL_INVALID),
                                    -1).show();
                        }
                    }
                }
            }

            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void handleSp() {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.sessionData_spID, Context.MODE_PRIVATE);

        sp.edit().putString("email",email).commit();
        sp.edit().putString("password",password).commit();
    }
}