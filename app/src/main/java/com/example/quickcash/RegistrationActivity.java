package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
    DatabaseReference emailRef = database.getReference("Email");
    DatabaseReference nameRef = database.getReference("Name");
    DatabaseReference passwordRef = database.getReference("Password");
    DatabaseReference roleRef = database.getReference("Role");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button registerButton = findViewById(R.id.submitButton);
        registerButton.setOnClickListener(this);
    }

    protected String getEmailAddress(){
        EditText emailForm = findViewById(R.id.emailText);
        return emailForm.getText().toString().trim();
    }

    protected String getName(){
        EditText nameForm = findViewById(R.id.nameText);
        return nameForm.getText().toString().trim();
    }

    protected String getPassword(){
        EditText passForm = findViewById(R.id.passText);
        return passForm.getText().toString().trim();
    }

    protected String getRole(){
        RadioButton employeeButton = findViewById(R.id.employeeRole);
        RadioButton employerButton = findViewById(R.id.employerRole);
        if(employerButton.isChecked()){
            return "Employer";
        }
        else if(employeeButton.isChecked()){
            return "Employee";
        }
        else{
            return "";
        }
    }
    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }
    protected boolean isEmptyName(String name) {
        return name.isEmpty();
    }
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }
    protected boolean isEmptyRole(String role) {
        return role.isEmpty();
    }
    protected boolean isValidEmailAddress(String emailAddress) {
        // Check if email is not null and meets certain criteria
        if (emailAddress != null && !emailAddress.isEmpty()) {
            // Check if email matches the pattern
            return emailAddress.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        } else {
            return false; // Return false for null or empty email
        }
    }

    @Override
    public void onClick(View view){
        String email = getEmailAddress();
        String name = getName();
        String password = getPassword();
        String role = getRole();

        String errorMessage = new String();

        if (isEmptyEmail(email) || isEmptyName(name) || isEmptyPassword(password) || isEmptyRole(role)){
            errorMessage="Please enter all fields!".trim();
        } else if (!isValidEmailAddress(email)) {
            errorMessage="Invalid email!".trim();
        } else {
            emailRef.push().setValue(email);
            nameRef.push().setValue(name);
            passwordRef.push().setValue(password);
            roleRef.push().setValue(role);
            errorMessage="Registration successful :)".trim();

            // No errors, move to the welcome window and save info to Firebase
//            move2WelcomeWindow(netID, email, role);
//            saveInfoToFirebase(netID, email, role);
        }
        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
        toast.show();
//        setStatusMessage(errorMessage);
    }

}
