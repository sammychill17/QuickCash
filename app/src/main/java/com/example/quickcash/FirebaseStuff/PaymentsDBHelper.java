package com.example.quickcash.FirebaseStuff;

import android.util.Log;

import com.example.quickcash.BusinessLogic.SanitizeEmail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentsDBHelper {

    private DatabaseReference databaseReference;

    public PaymentsDBHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Payments");
    }

    /*
    this method records payment into firebase (create method from CRUD principles)
     */
    public void recordPayment(String rawEmail, BigDecimal amount) {
        String employeeEmail = SanitizeEmail.sanitizeEmail(rawEmail);
        DatabaseReference paymentsRef = FirebaseDatabase.getInstance()
                .getReference("Payments").child(employeeEmail);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String paymentId = paymentsRef.push().getKey();

        if (paymentId != null) {
            Map<String, Object> paymentDetails = new HashMap<>();
            paymentDetails.put("date", currentDate);
            paymentDetails.put("amount", amount.toPlainString());

            paymentsRef.child(paymentId).setValue(paymentDetails)
                    .addOnSuccessListener(aVoid -> Log.d("PaymentsDBHelper", "Payment recorded successfully!"))
                    .addOnFailureListener(e -> Log.e("PaymentsDBHelper", "Failed to record payment", e));
        } else {
            Log.e("PaymentsDBHelper", "Failed to generate a unique payment ID :(");
        }
    }
}
