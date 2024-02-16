package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    CredentialValidator validator = new CredentialValidator();

    FirebaseDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupRegistrationButton();
        initializeDatabaseAccess();
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

    //This section of code was written by chatGPT https://chat.openai.com/share/0d2020e5-d854-44e2-8fca-0eb3cb3e6aff
    /*
    * This method checks to see if the input email exists already in the database as a User.
    * It returns nothing but does return a boolean value into it's callback should it find an object.
    * Otherwise it returns an error should it find an error.
     */
    protected void doesEmailExist(String email, EmailExistenceCallback callback){
        DatabaseScrounger scrounger = new DatabaseScrounger("Users");
        scrounger.getObjectByEmail(email, new DatabaseScrounger.ObjectCallback() {
            @Override
            public void onObjectReceived(QuickCashDBObject object) {
                // If object is not null, email exists
                callback.onResult(object != null);
            }

            @Override
            public void onError(DatabaseError error) {
                // Handle error, possibly assuming email does not exist or indicating an error
                callback.onError(error);
            }
        });
    }

    /*
    * This interface is simple and will use a boolean value "exists" if it finds a result and return an error otherwise.
     */
    interface EmailExistenceCallback {
        void onResult(boolean exists);
        void onError(DatabaseError error);
    }

    protected void setupRegistrationButton() {
        Button registerButton = findViewById(R.id.submitButton);
        registerButton.setOnClickListener(this);
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DATABASE_URL));
    }

    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    @Override
    public void onClick(View view){
        String email = getEmailAddress();
        String name = getName();
        String password = getPassword();
        String role = getRole();
        DatabaseReference userRef = database.getReference("Users");
        if (validator.isAnyFieldEmpty(email, name, password, role)){
            setStatusMessage(getResources().getString(R.string.EMPTY_FIELD_ERROR));
        } else if (!validator.isValidEmailAddress(email)) {
            setStatusMessage(getResources().getString(R.string.INVALID_EMAIL_ERROR));
        }
        else {
            //This section of code was written partially by chatGPT https://chat.openai.com/share/0d2020e5-d854-44e2-8fca-0eb3cb3e6aff
            /*
            * This calling of doesEmailExist uses the EmailExistanceCallback and the doesEmailExist
            * method to get a boolean value for if the requested email was found, and then it prints
            * the appropriate message to the status label.
             */
            doesEmailExist(email, new EmailExistenceCallback() {
                @Override
                public void onResult(boolean exists) {
                    if (exists) {
                        setStatusMessage(getResources().getString(R.string.DUPLICATE_EMAIL_ERROR).trim());
                    } else {
                        User currentUser = new User(email, password, name, role);
                        userRef.push().setValue(currentUser);
                        setStatusMessage(getResources().getString(R.string.REGISTRATION_SUCCESS_MESSAGE).trim());
                    }
                }

                @Override
                public void onError(DatabaseError error) {
                    setStatusMessage(getResources().getString(R.string.DATABASE_REGISTRATION_ERROR) + error.toString());
                }
            });

        }
    }
}