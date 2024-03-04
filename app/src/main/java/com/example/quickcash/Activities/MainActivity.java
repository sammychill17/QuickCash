package com.example.quickcash.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.BusinessLogic.LoginHandler;
import com.example.quickcash.BusinessLogic.LoginHandlerAdapter;
import com.example.quickcash.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getApplicationContext().getSharedPreferences(
                getResources().getString(R.string.sessionData_spID), Context.MODE_PRIVATE);

        Button gotologinbutton = (Button) findViewById(R.id.buttonGotoLogin);
        gotologinbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        Button gotoRegisterButton = (Button) findViewById(R.id.buttonGotoRegister);
        gotoRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });

        String spEmail = sp.getString("email","");
        String spPassword = sp.getString("password","");

        // check if user has SP values on app start; if so, attempt to log them in
        if (spEmail.equals("") && spPassword.equals("")) {
            // SP is empty, bring the user to login screen
            // Kash: we shouldn't be doing that! Let them stay in the welcome screen.
            // Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            // startActivity(loginIntent);
        } else {
            LoginHandlerAdapter adapter = new LoginHandlerAdapter() {
                @Override
                public void onLoginSuccess() {
                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                @Override
                public void onLoginFailure(String message) {
                    Snackbar.make(gotologinbutton, message, Snackbar.LENGTH_SHORT).show();
                }
            };
            LoginHandler spLoginHandler = new LoginHandler(spEmail, spPassword,
                    getApplicationContext(), gotologinbutton, adapter);
            spLoginHandler.handleLogin();
        }
    }
}

