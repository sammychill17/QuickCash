package com.example.quickcash;

public class CredentialValidator {
    protected boolean isValidEmailAddress(String emailAddress) {
        // Check if email is not null and meets certain criteria
        if (emailAddress != null && !emailAddress.isEmpty()) {
            // Check if email matches the pattern
            return emailAddress.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        } else {
            return false; // Return false for null or empty email
        }
    }
    protected boolean isValidName(String name) {
        // Check if name is not null and meets certain criteria
        if (name != null && !name.isEmpty()) {
            // Check if name matches the pattern
            // First name should be 2 or more alphabetic characters and additional names can be 1
            // or more alphabetic characters
            return name.matches("^[A-Za-z]{2,}+(?: [A-Za-z]+)*$");
        } else {
            return false; // Return false for null or empty name
        }
    }
    protected boolean isValidPassword(String password) {
        // Check if password is not null and meets certain criteria
        if (password != null && !password.isEmpty()) {
            // Check if password matches the pattern
            // Accepts password with at least
            // 1. one alphabetic character
            // 2. one digit
            // 3. has minimum length of 8 characters
            return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,}$");
        } else {
            return false; // Return false for null or empty password
        }
    }

    protected boolean isValidRole(String role) {
        // Check if role is not null and meets certain criteria
        if (role != null && (role.equals("Buyer") || role.equals("Seller"))) {
            // Check if role matches the allowed roles
            return true;
        } else {
            return false; // Return false for null or empty role
        }
    }
}
