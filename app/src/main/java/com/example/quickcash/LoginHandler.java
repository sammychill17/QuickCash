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
//    private String email;
//    private String password;
    private User user;

    private Context context;
    private View view;

    private LoginHandlerAdapter loginHandlerAdapter;

    public LoginHandler(String email, String password, Context context, View view, LoginHandlerAdapter loginHandlerAdapter) {
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        this.context = context;
        this.view = view;
        this.loginHandlerAdapter = loginHandlerAdapter;
    }

    public void handleLogin() {
        FirebaseDatabase.getInstance(Constants.firebaseUrl).getReference().child("Users").
                addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                boolean accountFound = false;
                for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                    if (accountSnapshot.hasChild("email") && accountSnapshot.hasChild(
                            "password")) {
                        String accEmail = String.valueOf(accountSnapshot.child("email")
                                .getValue());
                        String accPassword = String.valueOf(accountSnapshot.child("password")
                                .getValue());
                        String accName = String.valueOf(accountSnapshot.child("name").getValue());
                        String accRole = String.valueOf(accountSnapshot.child("role").getValue());
                        User newUser = new User(accEmail, accPassword, accName, accRole);
                        if (accEmail.equals(user.getEmail()) && accPassword.equals(user.getPassword())) {
                            user = newUser;
                            handleSp();
                            Snackbar.make(view, (CharSequence)
                                    context.getString(R.string.LOGIN_SUCCESS), -1).show();
                            loginHandlerAdapter.onLoginSuccess();
                            accountFound = true;
                            break;
                        } else if (user.getEmail().isEmpty()) {
                            Snackbar.make(view, (CharSequence)
                                    context.getString(R.string.LOGIN_ERROR_EMAIL_EMPTY),
                                    -1).show();
                            accountFound = true;
                            break;
                        } else if (user.getPassword().isEmpty()) {
                            Snackbar.make(view, (CharSequence)
                                    context.getString(R.string.LOGIN_ERROR_PASSWORD_EMPTY),
                                    -1).show();
                            accountFound = true;
                            break;
                        }
                    }
                }
                if (!accountFound) {
                    Snackbar.make(view, (CharSequence) context.getString(R.string.LOGIN_ERROR_PASSWORD_INCORRECT), -1).show();
                }
            }

            public void onCancelled(DatabaseError error) {
                loginHandlerAdapter.onLoginFailure(error.getCode() + " - " + error.getMessage());
            }
        });
    }

    private void handleSp() {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.sessionData_spID, Context.MODE_PRIVATE);

        sp.edit().putString("email",user.getEmail()).commit();
        sp.edit().putString("password",user.getPassword()).commit();
        sp.edit().putString("username", user.getName()).commit();
        sp.edit().putString("role", user.getRole()).commit();
    }
}