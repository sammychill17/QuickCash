package com.example.quickcash;

public class CredentialValidator {

    /*
    This function uses a simple regular expression to determine if the given string is a valid email address.
     */
    protected boolean isValidEmailAddress(String emailAddress) {
        // Check if email is not null and meets certain criteria
        if (emailAddress != null && !emailAddress.isEmpty()) {
            // Check if email matches the pattern
            return emailAddress.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        } else {
            return false; // Return false for null or empty email
        }
    }
    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }
    protected boolean isEmptyName(String name) {
        return name.isEmpty();
    }
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }
    protected boolean isEmptyRole(String role) {
        return role.isEmpty();
    }

    /*
    * This function returns true if any of the given strings are empty.
    * It is to be used in registration activity to determine if all fields are empty as a group function call.
     */
    protected boolean isAnyFieldEmpty(String email, String name, String password, String role){
        return isEmptyEmail(email) || isEmptyName(name) || isEmptyPassword(password) || isEmptyRole(role);
    }
}
