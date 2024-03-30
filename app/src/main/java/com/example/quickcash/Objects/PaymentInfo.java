package com.example.quickcash.Objects;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentInfo {
    private BigDecimal payment;
    private String date;
    private String receiverEmail;

    public PaymentInfo() {}

    public PaymentInfo(BigDecimal payment, String date) {
        this.payment = payment;
        this.date = date;
        this.receiverEmail = "";
    }

    public BigDecimal getPayment() { return payment; }
    public void setPayment(BigDecimal payment) { this.payment = payment; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getReceiverEmail() { return receiverEmail; }
    public void setReceiverEmail(String receiverEmail) { this.receiverEmail = receiverEmail; }

    public Date getDateObject() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.parse(date);
    }
    public void setDateFromDateObject(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.date = dateFormat.format(date);
    }
}
