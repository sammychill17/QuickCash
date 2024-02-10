package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//This section of code was written by chatGPT https://chat.openai.com/share/4d1331e6-eecc-44fe-83c6-fae04bf91cb7
interface EmailExistCallback {
    void onCallback(boolean exists);
}

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://quickcash-6941c-default-rtdb.firebaseio.com/");
    CredentialValidator validator = new CredentialValidator();
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

    //This section of code was written by chatGPT https://chat.openai.com/share/4d1331e6-eecc-44fe-83c6-fae04bf91cb7
    protected void doesEmailExist(DatabaseReference ref, String email, EmailExistCallback callback){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean exists = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Now snapshot represents each user under Users
                    User user = snapshot.getValue(User.class);
                    if (user != null && email.equals(user.getEmail())) {
                        exists = true;
                        break; // Email found, no need to continue the loop
                    }
                }
                callback.onCallback(exists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Log error or handle cancellation
                callback.onCallback(false);
            }
        });
    }

    @Override
    public void onClick(View view){
        String email = getEmailAddress();
        String name = getName();
        String password = getPassword();
        String role = getRole();
        DatabaseReference userRef = database.getReference("Users");
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        if (validator.isAnyFieldEmpty(email, name, password, role)){
            Snackbar snackbar = Snackbar.make(constraintLayout, getResources().getString(R.string.EMPTY_FIELD_ERROR).trim(), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (!validator.isValidEmailAddress(email)) {
            Snackbar snackbar = Snackbar.make(constraintLayout, getResources().getString(R.string.INVALID_EMAIL_ERROR).trim(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            //This section of code was written by chatGPT https://chat.openai.com/share/4d1331e6-eecc-44fe-83c6-fae04bf91cb7
            doesEmailExist(userRef, email, new EmailExistCallback() {
                @Override
                public void onCallback(boolean exists) {
                    if(!exists){
                        User currentUser = new User(email, password, name, role);
                        userRef.push().setValue(currentUser);
                        Snackbar snackbar = Snackbar.make(constraintLayout, getResources().getString(R.string.REGISTRATION_SUCCESS_MESSAGE).trim(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else{
                        Snackbar snackbar = Snackbar.make(constraintLayout, getResources().getString(R.string.DUPLICATE_EMAIL_ERROR).trim(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });
        }
    }

}
