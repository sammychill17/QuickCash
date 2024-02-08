package com.example.quickcash;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLatitude, editTextLongitude;
    private Button buttonSaveLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        buttonSaveLocation = findViewById(R.id.buttonSaveLocation);

        /*
        sets the OnClickListener for the button to save the location
         */
        buttonSaveLocation.setOnClickListener(v -> saveLocation());
    }

    private void saveLocation() {
        /*
         Get the latitude and longitude values from the EditTexts
         */
        String strLatitude = editTextLatitude.getText().toString().trim();
        String strLongitude = editTextLongitude.getText().toString().trim();

        /*
         checking if the latitude and longitude fields are not empty
         */
        if (LocationUtil.isValidLatitude(strLatitude)
                && LocationUtil.isValidLongitude(strLongitude) ) {
            try {
                /*
                 parses the latitude and longitude to double values accordingly
                 */
                double latitude = Double.parseDouble(strLatitude);
                double longitude = Double.parseDouble(strLongitude);

                /*
                 creates a new location object using the parsed latitude and longitude
                 */
                location loc = new location(latitude, longitude);
                /*
                therefore, saves the location to Firebase using the locationTable class
                 */
                LocationTable locTable = new LocationTable();
                locTable.addLocationToDatabase(loc);
            } catch (NumberFormatException e) {
                /*
                 should notify the user that the entered values are not valid doubles
                 */
                Toast.makeText(MainActivity.this,
                        "Please enter valid numbers for latitude and longitude",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            /*
             else, should notify the user that the fields cannot be empty.
             */
            Toast.makeText(MainActivity.this,
                    "Please fill in the co-ordinates",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
