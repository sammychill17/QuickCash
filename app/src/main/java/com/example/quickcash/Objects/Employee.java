package com.example.quickcash.Objects;

/*
* This is meant to be a subclass of user specific to Employee.
 */
public class Employee extends User {
    public Employee(String email, String password, String name, String role) {
        super(email, password, name, role);
    }

    public Employee(){
        super("","","","");
    }
}