/*
* This class is meant to be the superclass for all data objects in the FirebaseDatabase that reference a user.
*
* Users in this application all have their email act as primary keys, and as such all quickCashDbObjects only need
* by default an email and a method to return the email.
*
* All data objects that are children of this method require further data and methods.
*
* This class is public but acts partially as abstract in that it should never be directly pushed into the database
* and only ever called in the dbScrounger class.
 */

package com.example.quickcash.FirebaseStuff;

public class QuickCashDBObject {

    protected String email = "";

    public String getEmail(){
        return email;
    }
}
