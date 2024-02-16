package com.example.quickcash;

public class CredentialValidator {

    /*
    This function uses a simple regular expression to determine if the given string is a valid email address.
    */
    protected boolean isValidEmailAddress(String emailAddress) {
        if (emailAddress != null && !emailAddress.isEmpty()) {
            return emailAddress.matches("^.+@.+\\..+$");
        } else {
            return false;
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
