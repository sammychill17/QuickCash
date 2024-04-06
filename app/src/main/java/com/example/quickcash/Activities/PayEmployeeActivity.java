package com.example.quickcash.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quickcash.FirebaseStuff.PaymentsDBHelper;
import com.example.quickcash.Objects.Employee;
import com.example.quickcash.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;
import android.view.View;

import java.math.BigDecimal;

public class PayEmployeeActivity extends AppCompatActivity {
    /*
     Constants required  for PayPal configuration
     */
    private static final String CLIENT_ID = "AeAVf8Z3AcS_83h1VJGSBE_svM0wBYXQQPGXnM-RU_jc5kIao5Ah8zCwrBQl9JAbjJi2tH6tCY34H6Aw";
    private static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
    private static final String CURRENCY_CODE = "USD";

    private PayPalConfiguration payPalConfig;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private EditText setAmount;
    private Button btnPay;
    private PaymentsDBHelper paymentsDBHelper;
    private Employee employee;
    private TextView statusMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_employee);
        statusMessageTextView = findViewById(R.id.statusMessageTextView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pay Employee");
        /*
         finish the activity if the back arrow is pressed
         */
        toolbar.setNavigationOnClickListener(v -> finish());

        /*
         this sets up PayPal configuration
         */
        payPalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(CLIENT_ID);

        setAmount = findViewById(R.id.paymentAmountEditText);
        btnPay = findViewById(R.id.payWithPayPalButton);

        /*
         start PayPal service
         */
        Intent serviceIntent = new Intent(this, PayPalService.class);
        serviceIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(serviceIntent);

        paymentsDBHelper = new PaymentsDBHelper();
        /*
         this retrieves the Employee object passed from previous Activity
         */
        employee = (Employee) getIntent().getSerializableExtra("employeeEmail");
        /*
        check to see if employee data is unavailable (might seem unnecessary but it was for firebase purposes)
         */
        if (employee == null) {
            Toast.makeText(this, "Employee data is not available.", Toast.LENGTH_LONG).show();
            finish();
        }
        /*
        this prepares for the result from PayPal activity
         */
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                /*
                result handling logic from PayPal activity
                 */
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        /*
                        obtains the confirmation from the intent
                         */
                        PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        /*
                        calls handlePaymentConfirmation method to handle the confirmation from paypal
                        if result is positive (or ok), approving transaction
                         */
                        handlePaymentConfirmation(confirmation);
                        /*
                        toast message in case of cancellation
                         */
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        showStatusMessage(getString(R.string.payment_canceled));
                        /*
                        toast message in case of invalid credentials
                         */
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        showStatusMessage(getString(R.string.payment_error));
                    }
                });
        /*
        clicking pay button sets the motion for processPayment method
         */
        btnPay.setOnClickListener(v -> processPayment());
    }

    /*
    creates PayPalPayment and start the PayPal service for payment processing
     */
    private void processPayment() {
        String amountStr = setAmount.getText().toString();
        if (!amountStr.isEmpty()) {
            PayPalPayment payment = new PayPalPayment(new BigDecimal(amountStr), CURRENCY_CODE,
                    "Salary Payment", PAYMENT_INTENT);

            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

            activityResultLauncher.launch(intent);
        } else {
            showStatusMessage(getString(R.string.empty_amount));
        }
    }

/*
 this method handles the payment confirmation and records the payment details in Firebase, accordingly.
 */
 private void handlePaymentConfirmation(PaymentConfirmation confirmation) {
        if (confirmation != null) {
            try {
                String paymentDetails = confirmation.toJSONObject().toString(4);
                String state = confirmation.getProofOfPayment().getState();

                if ("approved".equals(state)) {
                    showStatusMessage(getString(R.string.payment_approved));
                    paymentsDBHelper.recordPayment(employee.getEmail(), new BigDecimal(setAmount.getText().toString()));
                } else {
                    showStatusMessage(getString(R.string.payment_canceled));
                }
            } catch (JSONException e) {
                showStatusMessage(getString(R.string.payment_error));
                e.printStackTrace();
            }
        }
    }
    private void showStatusMessage(String message) {
        statusMessageTextView.setText(message);
        statusMessageTextView.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}