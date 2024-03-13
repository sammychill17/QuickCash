package com.example.quickcash.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.BusinessLogic.CredentialValidator;
import com.example.quickcash.FirebaseStuff.DatabaseScrounger;
import com.example.quickcash.FirebaseStuff.QuickCashDBObject;
import com.example.quickcash.Objects.Employee;
import com.example.quickcash.Objects.Employer;
import com.example.quickcash.Objects.PreferredJobs;
import com.example.quickcash.Objects.User;
import com.example.quickcash.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicReference;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    CredentialValidator validator = new com.example.quickcash.BusinessLogic.CredentialValidator();

    FirebaseDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupRegistrationButton();
        initializeDatabaseAccess();

        TextView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            startActivity(intent);
            finish();
        });
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

    public void setupRegistrationButton() {
        Button registerButton = findViewById(R.id.submitButton);
        registerButton.setOnClickListener(this);
    }

    public void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DATABASE_URL));
    }

    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    @Override
    public void onClick(View view) {
        String email = getEmailAddress();
        String name = getName();
        String password = getPassword();
        String role = getRole();
        DatabaseReference userRef = database.getReference("Users");
        if (validator.isAnyFieldEmpty(email, name, password, role)) {
            setStatusMessage(getResources().getString(R.string.EMPTY_FIELD_ERROR));
        } else if (!validator.isValidEmailAddress(email)) {
            setStatusMessage(getResources().getString(R.string.INVALID_EMAIL_ERROR));
        } else {
            doesEmailExist(email, new EmailExistenceCallback() {
                @Override
                public void onResult(boolean exists) {
                    if (exists) {
                        setStatusMessage(getResources().getString(R.string.DUPLICATE_EMAIL_ERROR).trim());
                    } else {
                        User currentUser;
                        AtomicReference<DatabaseReference> preferredRef = new AtomicReference<DatabaseReference>();
                        preferredRef.set(database.getReference("PreferredJobs"));
                        if(role.equals("Employee")){
                            currentUser = new Employee(email, password, name, role);
                        }
                        else{
                            currentUser = new Employer(email, password, name, role);
                        }
                        /*
                        Uses push() to generate a unique key for the user and to save the user data
                         */
                        userRef.push().setValue(currentUser, (error, ref) -> {
                            if (error == null) {
                                /*
                                 indicates that data has been saved successfully.
                                 */
                                setStatusMessage(getResources().getString(R.string.REGISTRATION_SUCCESS_MESSAGE).trim());
                                if(role.equals("Employee")) {
                                    PreferredJobs jobPref = new PreferredJobs(email);
                                    preferredRef.get().push().setValue(jobPref);
                                }
                                /*
                                Transitioning to LocationActivity,
                                starts up the LocationActivity and passes the email
                                for database purposes.
                                 */
                                Intent intent = new Intent(RegistrationActivity.this, LocationActivity.class);
                                /*
                                passes the email to LocationActivity in the code line below.
                                 */
                                intent.putExtra(String.valueOf(R.string.user_email), email);
                                startActivity(intent);
                            } else {
                                setStatusMessage(getResources().getString(R.string.DATABASE_REGISTRATION_ERROR) + error.toString());
                            }
                        });
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

