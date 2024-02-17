package com.example.quickcash;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginbutton = (Button) findViewById(R.id.buttonGotoLogin);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // handleSp();
                // Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                // startActivity(intent);
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

    private void handleLogin() {
//        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.firebaseUrl);
//        DatabaseReference drefEmail = db.getReference("Email");
//
//        drefEmail.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String e = snapshot.getValue(String.class);
//                Toast.makeText(LoginActivity.this, e, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//        });
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
