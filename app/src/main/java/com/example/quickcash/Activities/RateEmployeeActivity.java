package com.example.quickcash.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.quickcash.FirebaseStuff.RatingDBHelper;
import com.example.quickcash.Objects.Employee;
import com.example.quickcash.Objects.Rating;
import com.example.quickcash.Objects.Review;
import com.example.quickcash.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseError;

public class RateEmployeeActivity extends AppCompatActivity {

    private Employee employee;

    private Rating ratingObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_employee);

        Toolbar myToolbar = findViewById(R.id.our_toolbar);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        employee = (Employee) getIntent().getSerializableExtra("employee");

        TextView employeeName = findViewById(R.id.employeeNameText);
        employeeName.setText(employee.getName());

        ratingObject = new Rating();
        ratingObject.setEmployeeEmail(employee.getEmail());

        getRatingObjectFromDB();

        Button submitButton = findViewById(R.id.submitRateButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = findViewById(R.id.ratingBar);
                int rating = (int) ratingBar.getRating();

                EditText descriptionBox = findViewById(R.id.reviewDescription);
                String description = descriptionBox.getText().toString();

                Review review = new Review(rating, description);

                ratingObject.addReview(review);
                ratingObject.pushRatingToDB(new Rating.RatingCallback() {
                    @Override
                    public void onComplete() {
                        finish();
                    }

                    @Override
                    public void onError(DatabaseError error){
                        // Something wrong happened...
                        Log.d("Y no rating??!??", "Cuz %%%%%");
                        Log.d("Y no rating??!??", error.getDetails());
                        Snackbar.make(submitButton, "Database doesn't like you. Retry later.", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getRatingObjectFromDB(){
        RatingDBHelper ratingDBHelper = new RatingDBHelper();
        ratingDBHelper.getRatingsByEmployee(employee.getEmail(), new RatingDBHelper.onRatingReceivedCallback(){
            @Override
            public void onRatingReceived(Rating rating){
                if(rating!=null) {
                    ratingObject = rating;
                }
            }
            @Override
            public void onError(DatabaseError error){
                Log.d("Y no rating??!??", "Cuz error");
                Log.d("Y no rating??!??", error.getDetails());
            }
        });
    }
}