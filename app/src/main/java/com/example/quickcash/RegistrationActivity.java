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
    DatabaseReference roleRef = database.getReference("Email");

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

    @Override
    public void onClick(View view){
        String email = getEmailAddress();
        String name = getName();
        String password = getPassword();
        String role = getRole();

        Toast test = Toast.makeText(this, "Email: "+email+" Name: "+name+" Password: "+password+" Role: "+role, Toast.LENGTH_SHORT);

        test.show();
        emailRef.setValue(email);
        nameRef.setValue(name);
        passwordRef.setValue(password);
        roleRef.setValue(role);
    }

}
