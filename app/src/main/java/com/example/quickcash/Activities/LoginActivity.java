package com.example.quickcash.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.BusinessLogic.LoginHandler;
import com.example.quickcash.BusinessLogic.LoginHandlerAdapter;
import com.example.quickcash.R;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginbutton = (Button) findViewById(R.id.buttonGotoLogin);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = getEmail();
                String password = getPassword();
                LoginHandlerAdapter adapter = new LoginHandlerAdapter() {
                    @Override
                    public void onLoginSuccess() {
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onLoginFailure(String message) {
                        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
                    }
                };
                LoginHandler loginHandler = new LoginHandler(email,password,getApplicationContext()
                        ,v, adapter);
                loginHandler.handleLogin();
            }
        });

        TextView makeAnAccount = findViewById(R.id.loginTextView_makeAnAccount);
        makeAnAccount.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            startActivity(intent);
            finish();
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
