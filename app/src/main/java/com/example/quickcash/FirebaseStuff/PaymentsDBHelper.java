package com.example.quickcash.FirebaseStuff;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.BusinessLogic.SanitizeEmail;
import com.example.quickcash.Objects.Job;
import com.example.quickcash.Objects.PaymentInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PaymentsDBHelper {

    private DatabaseReference databaseReference;

    public PaymentsDBHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Payments");
    }

    public interface PaymentObjectCallback {
        void onPaymentsReceived(List<PaymentInfo> paymentInfos);
        void onError(DatabaseError error);
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

    public void getAllPayments(String email, PaymentObjectCallback callback){
        String sanitizedEmail = SanitizeEmail.sanitizeEmail(email);
        databaseReference = FirebaseDatabase.getInstance().getReference("Payments/"+sanitizedEmail);
        databaseReference.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PaymentInfo> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PaymentInfo paymentInfo = dataSnapshot.getValue(PaymentInfo.class);
                    if(paymentInfo != null){
                        paymentInfo.convert(email);
                        list.add(paymentInfo);
                    }
                }
                callback.onPaymentsReceived(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }
}
