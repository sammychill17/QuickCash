package com.example.quickcash.BusinessLogic;

public class SanitizeEmail {
    public static String sanitizeEmail(String email) {
        return email.replace(".", ",");
    }
}
