package com.example.quickcash.Objects;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentInfo {
    private String amount;
    private int intAmount;
    private String date;
    private String receiverEmail;

    public PaymentInfo() {}

    public PaymentInfo(String amount, String date) {
        this.amount = amount;
        this.date = date;
        this.receiverEmail = "";
    }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

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

    public void convert(String receiverEmail){
        setReceiverEmail(receiverEmail);
        intAmount = Integer.parseInt(amount);
    }
    public void setIntAmount(int intAmount) {
        this.intAmount = intAmount;
    }
    public int getIntAmount() {
        return intAmount;
    }
}
