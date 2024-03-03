package com.example.quickcash.Objects;

public class User extends com.example.quickcash.FirebaseStuff.QuickCashDBObject{
    private String email;
    private String password;
    private String name;
    private String role;

    public User() {
        this.email = "";
        this.password = "";
        this.name = "";
        this.role = "";
    }
    /*
    constructor
     */
    public User(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    /*
    getters
     */
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    /*
    Setters
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
