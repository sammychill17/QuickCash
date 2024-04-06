package com.example.quickcash.BusinessLogic;

public class CredentialValidator {

    /*
    This function uses a simple regular expression to determine if the given string is a valid email address.
    */
    public boolean isValidEmailAddress(String emailAddress) {
        if (emailAddress != null && !emailAddress.isEmpty()) {
            return emailAddress.matches("^.+@.+\\..+$");
        } else {
            return false;
        }
    }
    public boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }
    public boolean isEmptyName(String name) {
        return name.isEmpty();
    }
    public boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }
    public boolean isEmptyRole(String role) {
        return role.isEmpty();
    }

    /*
    * This function returns true if any of the given strings are empty.
    * It is to be used in registration activity to determine if all fields are empty as a group function call.
     */
    public boolean isAnyFieldEmpty(String email, String name, String password, String role){
        return isEmptyEmail(email) || isEmptyName(name) || isEmptyPassword(password) || isEmptyRole(role);
    }
}
