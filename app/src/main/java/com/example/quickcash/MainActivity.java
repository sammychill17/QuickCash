package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getApplicationContext().getSharedPreferences(
                Constants.sessionData_spID, Context.MODE_PRIVATE);

        Button gotologinbutton = (Button) findViewById(R.id.buttonGotoLogin);
        gotologinbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        String spEmail = sp.getString("email","");
        String spPassword = sp.getString("password","");

        // check if user has SP values on app start; if so, attempt to log them in
        if (spEmail.equals("") && spPassword.equals("")) {
            // SP is empty, bring the user to login screen
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        } else {
            LoginHandler spLoginHandler = new LoginHandler(spEmail, spPassword,
                    getApplicationContext(), gotologinbutton);
            spLoginHandler.handleLogin();
        }
    }
}